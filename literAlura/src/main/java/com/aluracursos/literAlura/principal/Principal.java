package com.aluracursos.literAlura.principal;

import com.aluracursos.literAlura.models.Autor;
import com.aluracursos.literAlura.models.DatosLibro;
import com.aluracursos.literAlura.models.DatosResultados;
import com.aluracursos.literAlura.models.Libro;
import com.aluracursos.literAlura.repository.AutoresRepository;
import com.aluracursos.literAlura.repository.LibrosRepository;
import com.aluracursos.literAlura.service.ConsumoAPI;
import com.aluracursos.literAlura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibrosRepository repositorioLibros;
    private AutoresRepository repositorioAutores;
    private List<Libro> libros;
    private List<Autor> autores;


    public Principal(LibrosRepository repository, AutoresRepository repositorioAutores) {
        this.repositorioLibros = repository;
        this.repositorioAutores = repositorioAutores;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ************************************************
                    BIENVENIDO A LITERALURA
                    
                    1 - Buscar libro por título 
                    2 - Mostrar libros consultados
                    3 - Mostrar autores registrados
                    4 - Buscar autores vivos en un año determinado
                    5 - Buscar libros por idioma
                                  
                    0 - Salir
                    
                    ************************************************
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosConsultados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresVivosPorAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private Optional<DatosLibro> getDatosLibro(){
        System.out.print("Escribe el nombre del libro que deseas buscar:    ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        DatosResultados resultados = conversor.obtenerDatos(json, DatosResultados.class);
        Optional<DatosLibro> datos = Optional.of(resultados.libros().getFirst());
        return datos;
    }

    private void buscarLibroPorTitulo(){
        Optional<DatosLibro> libroBuscado = getDatosLibro();
        if (libroBuscado.isPresent()){
            Libro libro = new Libro(libroBuscado.get());
            System.out.println(libro);
            repositorioLibros.save(libro);
            repositorioAutores.save(libro.getAutor());
        } else {
            System.out.println("No se ha encontrado el libro, por favor intente nuevamente.");
        }

    }

    private void mostrarLibrosConsultados(){
        libros = repositorioLibros.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados(){
        autores = repositorioAutores.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void buscarAutoresVivosPorAnio(){
        try {
            System.out.print("Escribe el año que desea consultar:    ");
            int anio = Integer.valueOf(teclado.nextLine());
            autores = repositorioAutores.autorVivoPorAnio(anio);
            if (!autores.isEmpty()){
                autores.stream()
                        .sorted(Comparator.comparing(Autor::getNombre))
                        .forEach(System.out::println);
            } else {
                System.out.println("No se encontraron autores resgistrados en el año: " +  anio);
            }
        } catch (Exception e){
            System.out.println("Formato invalido.");
            System.out.println("Volviendo al menú principal ...");
        }
    }

    private void buscarLibrosPorIdioma(){
        try {
            System.out.print("""
                    Seleccione el idioma que desea buscar:
                    1. EN - Ingles
                    2. ES - Español
                    3. FR - Frances
                    """);
            String lenguaje = teclado.nextLine();
            libros = repositorioLibros.libroPorLenguaje(lenguaje);
            if (!libros.isEmpty()){
                libros.stream()
                        .sorted(Comparator.comparing(Libro::getTitulo))
                        .forEach(System.out::println);
            } else {
                System.out.println("No se encontraron libros registrados en el idioma: " +  lenguaje);
            }
        } catch (Exception e){
            System.out.println("Formato invalido.");
            System.out.println("Volviendo al menú principal ...");
        }
    }
}
