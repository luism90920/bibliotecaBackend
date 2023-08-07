
package com.codeOpen.biblioteca.security.secRepository;

import com.codeOpen.biblioteca.security.secEntity.Rol;
import com.codeOpen.biblioteca.security.secEnums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Rol, Integer>{
    
    Optional<Rol> findByNombre(RolNombre rolNombre);
}
