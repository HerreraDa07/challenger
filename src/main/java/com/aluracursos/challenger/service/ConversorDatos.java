package com.aluracursos.challenger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos implements IConversorDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatosI(String datos, Class<T> clase) {
        try {
            return objectMapper.readValue(datos, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}