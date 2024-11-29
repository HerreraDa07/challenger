package com.aluracursos.challenger.repository;

import com.aluracursos.challenger.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositoryLibro extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);

    Optional<Libro> findByNombre(String nombre);

    @Query("SELECT l FROM Libro l ORDER BY l.descargas DESC LIMIT 10")
    List<Libro> top10();
}