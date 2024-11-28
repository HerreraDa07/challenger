package com.aluracursos.challenger.repository;

import com.aluracursos.challenger.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositoryAutor extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :fecha AND a.fechaDeFallecimiento >= :fecha")
    List<Autor> porFecha(int fecha);

    boolean existsByNombre(String nombre);

    Optional<Autor> findByNombre(String nombre);
}