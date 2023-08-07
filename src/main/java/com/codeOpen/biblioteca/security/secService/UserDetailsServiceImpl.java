
package com.codeOpen.biblioteca.security.secService;

// servicio del usuarioPrincipal

import com.codeOpen.biblioteca.security.secEntity.Usuario;
import com.codeOpen.biblioteca.security.secEntity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// implementamos la interfaz UserDetailsService
// transforma la clase Usuario en un UsuarioPrincipal
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
        //retorna un UsuarioPrincipal, donde transformamos los roles en authorities
        return UsuarioPrincipal.build(usuario);
    }
    
    
    
}
