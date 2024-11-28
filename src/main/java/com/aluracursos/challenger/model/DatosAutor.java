package com.aluracursos.challenger.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(@JsonAlias("name") String nombre,
                         @JsonAlias("birth_year") Integer fechaDeNacimiento,
                         @JsonAlias("death_year") Integer fechaDeFallecimiento) {
    @Override
    public String toString() {
        return "\n" + "Nombre del autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaDeNacimiento + "\n" +
                "Fecha de fallecimiento: " + fechaDeFallecimiento + "\n";
    }
}