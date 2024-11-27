package com.aluracursos.challenger.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String creador;
    private Double descargas;
    private String idioma;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {
    }

    public Libro(String creador, DatosLibro datosLibro) {
        this.creador = creador;
        this.descargas = datosLibro.descargas();
        this.idioma = datosLibro.idioma().get(0);
        this.nombre = datosLibro.nombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n" + "Titulo: " + getNombre() + "\n" +
                "Autor: " + getCreador() + "\n" +
                "Idioma: " + getIdioma() + "\n" +
                "Número de descargas: " + getDescargas() + "\n";
    }
}