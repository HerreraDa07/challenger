package com.aluracursos.challenger.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results")
        List<DatosLibro> datosLibros) {
    @Override
    public String toString() {
        return "\n" + "Resultados: " + datosLibros + "\n";
    }
}