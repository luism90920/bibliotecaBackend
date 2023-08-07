
package com.codeOpen.biblioteca.controller;

import com.codeOpen.biblioteca.dto.EditorialDto;
import com.codeOpen.biblioteca.dto.Mensaje;
import com.codeOpen.biblioteca.entity.Editorial;
import com.codeOpen.biblioteca.service.EditorialService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/editorial")
@CrossOrigin (origins = "http://localhost:4200")
public class EditorialController {
    
    @Autowired
    EditorialService editorialService;
    
    
    @GetMapping("/lista")
    public ResponseEntity<List<Editorial>> lista(){
        List<Editorial> listEditorial = editorialService.list();
        return new ResponseEntity(listEditorial, HttpStatus.OK);
    }
    
    @GetMapping("detail/{id}")
    public ResponseEntity<Editorial> detail(@PathVariable int id){
        if(!editorialService.existById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        Editorial editorial = editorialService.getOne(id).get();
        return new ResponseEntity(editorial, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EditorialDto editorialDto){
        if(editorialService.existByNombre(editorialDto.getNombre())){
            return new ResponseEntity(new Mensaje("la editorial ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(editorialDto.getNombre())){
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Editorial editorial = new Editorial(editorialDto.getNombre());
        editorialService.save(editorial);
        return new ResponseEntity(new Mensaje("Editorial creada"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody EditorialDto editorialDto, @PathVariable int id){
        if(editorialService.existByNombre(editorialDto.getNombre())){
            return new ResponseEntity(new Mensaje("la editorial ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(editorialDto.getNombre())){
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Editorial editorial = editorialService.getOne(id).get();
        editorial.setNombre(editorialDto.getNombre());
        editorialService.save(editorial);
        return new ResponseEntity(new Mensaje("editorial modificada"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if(!editorialService.existById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        editorialService.delete(id);
        return new ResponseEntity(new Mensaje("editorial eliminada"), HttpStatus.OK);
    }
    
    
}
