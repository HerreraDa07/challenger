package com.aluracursos.challenger.repository;

import com.aluracursos.challenger.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryLibro extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);
}