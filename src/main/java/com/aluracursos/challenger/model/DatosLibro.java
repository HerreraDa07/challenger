package com.aluracursos.challenger.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("title") String nombre,
                         @JsonAlias("authors") List<DatosAutor> autor,
                         @JsonAlias("languages") List<String> idioma,
                         @JsonAlias("download_count") Double descargas) {
    @Override
    public String toString() {
        return "\n" + "Nombre del libro: " + nombre + "\n" +
                "Resultados autor: " + autor + "\n" +
                "Resultados idiomas: " + idioma + "\n" +
                "Descargas: " + descargas + "\n";
    }
}