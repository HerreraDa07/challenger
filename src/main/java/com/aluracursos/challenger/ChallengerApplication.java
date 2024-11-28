package com.aluracursos.challenger;

import com.aluracursos.challenger.principal.Principal;
import com.aluracursos.challenger.repository.RepositoryAutor;
import com.aluracursos.challenger.repository.RepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengerApplication implements CommandLineRunner {
    @Autowired
    private RepositoryLibro repositoryLibro;
    @Autowired
    private RepositoryAutor repositoryAutor;

    public static void main(String[] args) {
        SpringApplication.run(ChallengerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositoryLibro, repositoryAutor);
        principal.ejecutar();
    }
}