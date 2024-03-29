package com.codeOpen.biblioteca.controller;

import com.codeOpen.biblioteca.dto.LibroDto;
import com.codeOpen.biblioteca.dto.Mensaje;
import com.codeOpen.biblioteca.entity.Autor;
import com.codeOpen.biblioteca.entity.Editorial;
import com.codeOpen.biblioteca.entity.Genero;
import com.codeOpen.biblioteca.entity.Libro;
import com.codeOpen.biblioteca.service.AutorService;
import com.codeOpen.biblioteca.service.EditorialService;
import com.codeOpen.biblioteca.service.GeneroService;
import com.codeOpen.biblioteca.service.LibroService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

@RestController
@RequestMapping("/libro")
@CrossOrigin(origins = "http://localhost:4200")
public class LibroController {

    @Autowired
    LibroService libroService;    

    @Autowired
    AutorService autorService;
    
    @Autowired
    GeneroService generoService;
    
    @Autowired
    EditorialService editorialService;
    

    @GetMapping("/lista")
    public ResponseEntity<List<Libro>> list() {
        List<Libro> list = libroService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/listaLibroIdAutor/{id}")
    public ResponseEntity<List<Libro>> listLibroIdAutor(@PathVariable int id) {
        List<Libro> lista = libroService.listLibroIdAutor(id);
        return new ResponseEntity(lista, HttpStatus.OK);
    }
    
    @GetMapping("/listaLibroIdGenero/{id}")
    public ResponseEntity<List<Libro>> listaLibroIdGenero(@PathVariable int id){
        List<Libro> lista = libroService.listaLibroIdGenero(id);
        return new ResponseEntity(lista, HttpStatus.OK);
    }
    
    @GetMapping("/listaLibroIdEditorial/{id}")
    public ResponseEntity<List<Libro>> listaLibroIdEditorial(@PathVariable int id){
        List<Libro> lista = libroService.listaLibroIdEditorial(id);
        return new ResponseEntity(lista, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Libro> getById(@PathVariable int id) {
        if (!libroService.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        Libro libro = libroService.getOne(id).get();
        return new ResponseEntity(libro, HttpStatus.OK);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody LibroDto libroDto) {
        if (StringUtils.isBlank(libroDto.getTitulo())) {
            return new ResponseEntity(new Mensaje("el título es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (libroService.existsByTitulo(libroDto.getTitulo())) {
            return new ResponseEntity(new Mensaje("ese título ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(libroDto.getNombreAutor())) {
            return new ResponseEntity(new Mensaje("el autor es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(Integer.toString(libroDto.getEjemplares()))) {
            return new ResponseEntity(new Mensaje("la cantidad de ejemplares es obligatorio"),HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(libroDto.getNombreGenero())) {
            return new ResponseEntity(new Mensaje("el género es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(libroDto.getNombreEditorial())) {
            return new ResponseEntity(new Mensaje("la editorial es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        Optional<Autor> autor = autorService.getByNombre(libroDto.getNombreAutor());
        Optional<Genero> genero = generoService.getByNombre(libroDto.getNombreGenero());
        Optional<Editorial> editorial = editorialService.getByNombre(libroDto.getNombreEditorial());
        Libro libro = new Libro(libroDto.getTitulo(), autor.get(), libroDto.getEjemplares(), genero.get(), editorial.get());
        libroService.save(libro);
        return new ResponseEntity(new Mensaje("libro creado"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody LibroDto libroDto){
        if (!libroService.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        
        //el libro existe pero el nombre es de otro "id" que ya existe y no del "id" que quiero modificar
        //y tiene el mismo nombre que quiero renombrar
        if (libroService.existsByTitulo(libroDto.getTitulo()) && libroService.getByTitulo(libroDto.getTitulo()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(libroDto.getTitulo())) {
            return new ResponseEntity(new Mensaje("el título es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(Integer.toString(libroDto.getEjemplares()))) {
            return new ResponseEntity(new Mensaje("la cantidad de ejemplares es obligatorio"),HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(libroDto.getNombreGenero())) {
            return new ResponseEntity(new Mensaje("el género es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(libroDto.getNombreEditorial())) {
            return new ResponseEntity(new Mensaje("la editorial es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        Optional<Autor> autor = autorService.getByNombre(libroDto.getNombreAutor());
        Optional<Genero> genero = generoService.getByNombre(libroDto.getNombreGenero());
        Optional<Editorial> editorial = editorialService.getByNombre(libroDto.getNombreEditorial());
        Libro libro = libroService.getOne(id).get();
        libro.setTitulo(libroDto.getTitulo());
        libro.setAutor(autor.get());
        libro.setEjemplares(libroDto.getEjemplares());
        libro.setGenero(genero.get());
        libro.setEditorial(editorial.get());
        libroService.save(libro);
        return new ResponseEntity(new Mensaje("libro modificado"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!libroService.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        libroService.delete(id);
        return new ResponseEntity(new Mensaje("libro eliminado"), HttpStatus.OK);
    }

}
