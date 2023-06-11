
package com.codeOpen.biblioteca.dto;

import com.codeOpen.biblioteca.entity.Autor;


public class LibroDto {
    
    private String titulo;
    
    private String nombreAutor;
    
    private int ejemplares;
    
    private String nombreEditorial;
    
    private String nombreGenero;

    public LibroDto() {
    }

    public LibroDto(String titulo, String nombreAutor, int ejemplares, String nombreGenero, String nombreEditorial) {
        this.titulo = titulo;
        this.nombreAutor = nombreAutor;
        this.ejemplares = ejemplares;
        this.nombreGenero = nombreGenero;
        this.nombreEditorial = nombreEditorial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    
    
    
    
    
}
