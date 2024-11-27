package com.aluracursos.challenger.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(@JsonAlias("name") String nombre,
                         @JsonAlias("birth_year") Integer fechaDeNacimiento,
                         @JsonAlias("death_year") Integer fechaDeFallecimiento) {
}