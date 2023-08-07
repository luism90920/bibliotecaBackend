
package com.codeOpen.biblioteca.security.secService;

import com.codeOpen.biblioteca.security.secEntity.Rol;
import com.codeOpen.biblioteca.security.secEnums.RolNombre;
import com.codeOpen.biblioteca.security.secRepository.RolRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolService {
    
    @Autowired
    RolRepository rolRepository;
    
    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByNombre(rolNombre);
    }
    
    public void save(Rol rol){
        rolRepository.save(rol);
    }
  
}
