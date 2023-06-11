
package com.codeOpen.biblioteca.service;

import com.codeOpen.biblioteca.entity.Genero;
import com.codeOpen.biblioteca.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GeneroService {
    
    @Autowired
    GeneroRepository generoRepository;
    
    public List<Genero> list(){
        return generoRepository.findAll();
    }
    
    public Optional<Genero> getOne(int id){
        return generoRepository.findById(id);
    }
    
    public Optional<Genero> getByNombre(String nombre){
        return generoRepository.findByNombre(nombre);
    }
    
    public void save(Genero genero){
        generoRepository.save(genero);
    }
    
    public void delete(int id){
        generoRepository.deleteById(id);
    }
    
    public boolean existById(int id){
        return generoRepository.existsById(id);
    }
    
    public boolean existByNombre(String nombre){
        return generoRepository.existsByNombre(nombre);
    }
    
}
