package com.aluracursos.challenger.principal;

import com.aluracursos.challenger.model.*;
import com.aluracursos.challenger.repository.RepositoryAutor;
import com.aluracursos.challenger.repository.RepositoryLibro;
import com.aluracursos.challenger.service.ConsumirApi;
import com.aluracursos.challenger.service.ConversorDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private ConsumirApi consumirApi = new ConsumirApi();
    private ConversorDatos conversorDatos = new ConversorDatos();
    private final String URL = "http://gutendex.com/books/?search=";
    private RepositoryLibro repositoryLibro;
    private RepositoryAutor repositoryAutor;
    private Scanner scanner = new Scanner(System.in);

    public Principal(RepositoryLibro repositoryLibro, RepositoryAutor repositoryAutor) {
        this.repositoryLibro = repositoryLibro;
        this.repositoryAutor = repositoryAutor;
    }

    public void ejecutar() {
        menu();
    }

    private void menu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    ***** LiterAlura *****
                    Digite una opción numérica
                    1. Hacer una búsqueda por el título del libro
                    2. Historial de libros buscados
                    3. Historial de autores registrados
                    4. Historial de autores vivos en un determinado año
                    5. Historial de libros organizado por idioma
                    
                    0. Para finalizar el programa.
                    """);
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 0:
                    System.out.println("El programa va a finalizar.");
                    break;
                case 1:
                    buscar();
                    break;
                case 2:
                    historial();
                    break;
                case 3:
                    autores();
                    break;
                case 4:
                    fecha();
                    break;
                case 5:
                    idioma();
                    break;
                default:
                    System.out.println("Ingreso una opción no válida.");
                    break;
            }
        }
    }

    private void buscar() {
        System.out.println("Digite el nombre del libro que desea buscar.");
        String busqueda = scanner.nextLine();

        var json = consumirApi.consumoApi(URL + busqueda.replace(" ", "%20"));
        var datos = conversorDatos.obtenerDatosI(json, Datos.class);

        DatosLibro datosLibro = datos.datosLibros().get(0);
        String creador = datosLibro.autores().stream()
                .map(DatosAutor::nombre)
                .findFirst()
                .orElse("Autor desconocido");

        Autor autor = repositoryAutor.findByNombre(creador)
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor();
                    nuevoAutor.setNombre(creador);
                    nuevoAutor.setFechaDeNacimiento(datosLibro.autores().get(0).fechaDeNacimiento());
                    nuevoAutor.setFechaDeFallecimiento(datosLibro.autores().get(0).fechaDeFallecimiento());
                    nuevoAutor.setLibro(datosLibro.nombre());
                    return repositoryAutor.save(nuevoAutor);
                });

        Libro libro = new Libro(creador, datosLibro);
        libro.setAutor(autor);
        repositoryLibro.save(libro);


    }

    private void historial() {
        System.out.println("Historial de los libros buscados recientemente.");
        List<Libro> libros = repositoryLibro.findAll();
        System.out.println(libros);
    }

    private void autores() {
        System.out.println("Historial de autores de los libros buscados recientemente.");
        List<Autor> autores = repositoryAutor.findAll();
        System.out.println(autores);
    }

    private void fecha() {
        System.out.println("Para ver el historial de autores vivos en determinado año, digite el año que desea buscar.");
        int fecha = scanner.nextInt();
        List<Autor> autores = repositoryAutor.porFecha(fecha);
        System.out.println(autores);
    }

    private void idioma() {
        System.out.println("Para ver el historial de libros organizados por idiomas, digite el idioma.");
        System.out.println("""
                es - Español
                en - Ingles
                fr - Frances
                pt - Portugal
                """);
        String opcion = scanner.nextLine();
        switch (opcion) {
            case "es":
                if (!idiomas("es").isEmpty()) {
                    System.out.println("Libros encontrados en el idioma español: " + idiomas("es"));
                } else {
                    System.out.println("Aun no se ha registrado ningún libro en el idioma español.");
                }
                break;
            case "en":
                if (!idiomas("en").isEmpty()) {
                    System.out.println("Libros encontrados en el idioma inglés: " + idiomas("en"));
                } else {
                    System.out.println("Aun no se ha registrado ningún libro en el idioma inglés.");
                }
                break;
            case "fr":
                if (!idiomas("fr").isEmpty()) {
                    System.out.println("Libros encontrados en el idioma francés: " + idiomas("fr"));
                } else {
                    System.out.println("Aun no se ha registrado ningún libro en el idioma francés.");
                }
                break;
            case "pt":
                if (!idiomas("pt").isEmpty()) {
                    System.out.println("Libros encontrados en el idioma portugués: " + idiomas("pt"));
                } else {
                    System.out.println("Aun no se ha registrado ningún libro en el idioma portugués.");
                }
                break;
            default:
                System.out.println("Ingreso un valor no valido");
                break;
        }
    }

    private List<Libro> idiomas(String idioma) {
        return repositoryLibro.findByIdioma(idioma);
    }
}