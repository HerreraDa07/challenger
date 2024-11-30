package com.aluracursos.challenger.principal;

import com.aluracursos.challenger.model.*;
import com.aluracursos.challenger.repository.RepositoryAutor;
import com.aluracursos.challenger.repository.RepositoryLibro;
import com.aluracursos.challenger.service.ConsumirApi;
import com.aluracursos.challenger.service.ConversorDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
            try {
                System.out.println("""
                        ***** LiterAlura *****
                        Digite una opción numérica
                        1. Hacer una búsqueda por el título del libro
                        2. Historial de libros buscados
                        3. Historial de autores registrados
                        4. Historial de autores vivos en un determinado año
                        5. Historial de libros organizado por idioma
                        6. Top 10 libros más descargados
                        7. Buscar autores por nombre
                        8. Estadísticas de libros registrados
                        
                        0. Para finalizar el programa.
                        **Elige una opción para continuar**
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
                        autor();
                        break;
                    case 4:
                        fecha();
                        break;
                    case 5:
                        idioma();
                        break;
                    case 6:
                        top10();
                        break;
                    case 7:
                        busquedaAutor();
                        break;
                    case 8:
                        estadisticas();
                        break;
                    default:
                        System.out.println("Ingreso una opción no válida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número. Por favor, intentelo de nuevo.");
                scanner.nextLine();
            }
        }
    }

    private void buscar() {
        System.out.println("Digite el nombre del libro que desea buscar.");
        String busqueda = scanner.nextLine();
        try {
            var json = consumirApi.consumoApi(URL + URLEncoder.encode(busqueda, StandardCharsets.UTF_8));
            var datos = conversorDatos.obtenerDatosI(json, Datos.class);

            DatosLibro datosLibro = datos.datosLibros().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);

            Libro libro = new Libro(datosAutor.nombre(), datosLibro);
            Autor autor = new Autor(libro.getNombre(), datosAutor);

            System.out.println(libro);

            Optional<Libro> libroOptional = repositoryLibro.findByNombre(libro.getNombre());
            if (libroOptional.isPresent()) {
                System.out.println("Ya existe este libro en la base de datos.");
            } else {
                repositoryLibro.save(libro);
                if (repositoryAutor.existsByNombre(autor.getNombre())) {
                    Optional<Autor> autorOptional = repositoryAutor.findByNombre(autor.getNombre());
                    if (autorOptional.isPresent()) {
                        autorOptional.get().getLibros().add(libro.getNombre());
                        repositoryAutor.save(autorOptional.get());
                    }
                } else {
                    repositoryAutor.save(autor);
                }
            }
        } catch (Exception e) {
            System.out.println("Libro no encontrado");
        }
    }

    private void historial() {
        System.out.println("Historial de los libros buscados recientemente.");
        List<Libro> libros = repositoryLibro.findAll();
        if (!libros.isEmpty()) {
            System.out.println(libros);
        } else {
            System.out.println("Aún no ha registrado ningún libro.");
        }
    }

    private void autor() {
        System.out.println("Historial de autor de los libros buscados recientemente.");
        List<Autor> autor = repositoryAutor.findAll();
        if (!autor.isEmpty()) {
            System.out.println(autor);
        } else {
            System.out.println("Aún no hay un registro de autores.");
        }
    }

    private void fecha() {
        System.out.println("Para ver el historial de autor vivos en determinado año, digite el año que desea buscar.");
        int fecha = scanner.nextInt();
        List<Autor> autor = repositoryAutor.porFecha(fecha);
        if (!autor.isEmpty()) {
            System.out.println(autor);
        } else {
            System.out.println("No hay autores registrados vivos en ese año.");
        }
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

    private void top10() {
        List<Libro> libros = repositoryLibro.top10();
        if (!libros.isEmpty()) {
            System.out.println(libros);
        } else {
            System.out.println("Aún no se han registrado libros.");
        }
    }

    private void busquedaAutor() {
        System.out.println("Digite el nombre del autor que desea buscar");
        String busqueda = scanner.nextLine();
        Optional<Autor> autor = repositoryAutor.findAll().stream()
                .filter(a -> a.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
                .findFirst();
        if (autor.isPresent()) {
            System.out.println(autor.get());
        } else {
            System.out.println("No existe ese autor");
        }
    }

    private void estadisticas() {
        List<Libro> libros = repositoryLibro.findAll();
        DoubleSummaryStatistics est = libros.stream()
                .filter(l -> l.getDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getDescargas));
        System.out.println("\nEl numero maximo de descargas de un libro es: " + est.getMax() +
                "\nEl valor minimo de descargas de un libro: " + est.getMin() +
                "\nLa suma de todas las descargas de los libros es de: " + est.getSum() +
                "\nNúmero total de libros registrados: " + est.getCount() + "\n");
    }
}