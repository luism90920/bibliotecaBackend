
package com.codeOpen.biblioteca.security.secController;

import com.codeOpen.biblioteca.dto.Mensaje;
import com.codeOpen.biblioteca.security.jwt.JwtProvider;
import com.codeOpen.biblioteca.security.secDto.JwtDto;
import com.codeOpen.biblioteca.security.secDto.LoginUsuario;
import com.codeOpen.biblioteca.security.secDto.NuevoUsuario;
import com.codeOpen.biblioteca.security.secEntity.Rol;
import com.codeOpen.biblioteca.security.secEntity.Usuario;
import com.codeOpen.biblioteca.security.secEnums.RolNombre;
import com.codeOpen.biblioteca.security.secService.RolService;
import com.codeOpen.biblioteca.security.secService.UsuarioService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
    //cuando creemos el usuario debemos codificar el password
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    RolService rolService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario = new Usuario(
            nuevoUsuario.getNombre(), 
            nuevoUsuario.getNombreUsuario(),
            nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        
        usuarioService.save(usuario);
        
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }
     
    
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        
        // Autenticamos el Usuario con el Login, con los datos que vienen en "loginUsuario"
        Authentication authentication = 
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        //A partir de la autenticación generamos el token
        String jwt = jwtProvider.generateToken(authentication);
        
        //Obtenemos el usuarioPrincipal a través de authentication, que era con los roles transformados en authorities
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // armamos jwtDto
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
