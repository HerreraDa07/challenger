package com.aluracursos.challenger.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autor;
    private String nombre;
    private String idioma;
    private Double descargas;

    public Libro() {
    }

    public Libro(String autor, DatosLibro datosLibro) {
        this.autor = autor;
        this.nombre = datosLibro.nombre();
        this.idioma = datosLibro.idioma().get(0);
        this.descargas = datosLibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "\n" + "Autor: " + getAutor() + "\n" +
                "Titulo: " + getNombre() + "\n" +
                "Idioma: " + getIdioma() + "\n" +
                "Número de descargas: " + getDescargas() + "\n";
    }
}