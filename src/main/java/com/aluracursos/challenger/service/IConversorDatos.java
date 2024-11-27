package com.aluracursos.challenger.service;

public interface IConversorDatos {
    <T> T obtenerDatosI(String datos, Class<T> clase);
}