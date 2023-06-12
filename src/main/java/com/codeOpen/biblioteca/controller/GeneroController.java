
package com.codeOpen.biblioteca.controller;

import com.codeOpen.biblioteca.dto.GeneroDto;
import com.codeOpen.biblioteca.dto.Mensaje;
import com.codeOpen.biblioteca.entity.Genero;
import com.codeOpen.biblioteca.service.GeneroService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.aspectj.weaver.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genero")
@CrossOrigin(origins = "http://localhost:4200")
public class GeneroController {
    
    
    @Autowired
    GeneroService generoService;
    
    
    @GetMapping("/lista")
    public ResponseEntity<List<Genero>> lista(){
        List<Genero> listGenero = generoService.list();
        return new ResponseEntity(listGenero, HttpStatus.OK);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Genero> getById(@PathVariable int id){
        if(!generoService.existById(id)){
            return new ResponseEntity(new Mensaje("no exite"), HttpStatus.NOT_FOUND);
        }
        Genero genero = generoService.getOne(id).get();
        return new ResponseEntity(genero, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody GeneroDto generoDto){
        if(StringUtils.isBlank(generoDto.getNombreGenero())){
            return new ResponseEntity(new Mensaje("nombre obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Genero genero = new Genero(generoDto.getNombreGenero());
        generoService.save(genero);
        return new ResponseEntity(new Mensaje("Genero creado"), HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody GeneroDto generoDto, @PathVariable int id){
        if(StringUtils.isBlank(generoDto.getNombreGenero())){
            return new ResponseEntity(new Mensaje("nombre obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Genero genero = generoService.getOne(id).get();
        genero.setNombre(generoDto.getNombreGenero());
        generoService.save(genero);
        return new ResponseEntity(new Mensaje("Genero modificado"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if(!generoService.existById(id)){
            return new ResponseEntity(new Mensaje("no existe"),HttpStatus.NOT_FOUND);
        }
        generoService.delete(id);
        return new ResponseEntity(new Mensaje("Genero borrado"), HttpStatus.OK);
    }
}
