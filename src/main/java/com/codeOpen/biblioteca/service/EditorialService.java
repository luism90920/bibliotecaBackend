
package com.codeOpen.biblioteca.service;

import com.codeOpen.biblioteca.entity.Editorial;
import com.codeOpen.biblioteca.repository.EditorialRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EditorialService {
    
    @Autowired
    EditorialRepository editorialRepository;
    
    
    public List<Editorial> list(){
        return editorialRepository.findAll();
    }
    
    public Optional<Editorial> getOne(int id){
        return editorialRepository.findById(id);
    }
    
    public Optional<Editorial> getByNombre(String nombre){
        return editorialRepository.findByNombre(nombre);
    }
    
    public void save(Editorial editorial){
        editorialRepository.save(editorial);
    }
    
    public void delete(int id){
        editorialRepository.deleteById(id);
    }
    
    public boolean existById(int id){
        return editorialRepository.existsById(id);
    }
    
    public boolean existByNombre(String nombre){
        return editorialRepository.existsByNombre(nombre);
    }
    
    
}
