//LO EJECUTO POR PRIMERA VEZ PARA CREAR LA BASE DE DATOS DE ROLES

/*
package com.codeOpen.biblioteca.util;

import com.codeOpen.biblioteca.security.secEntity.Rol;
import com.codeOpen.biblioteca.security.secEnums.RolNombre;
import com.codeOpen.biblioteca.security.secService.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner{
    
    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);
    }
    
    
    
}

*/