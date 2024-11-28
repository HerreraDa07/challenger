package com.aluracursos.challenger.repository;

import com.aluracursos.challenger.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryLibro extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);

    Optional<Libro> findByNombre(String nombre);
}