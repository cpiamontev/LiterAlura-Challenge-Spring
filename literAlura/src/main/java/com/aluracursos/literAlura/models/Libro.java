package com.aluracursos.literAlura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private Autor autor;
    private String lenguaje;
    private long descargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autores().getFirst());
        this.lenguaje = datosLibro.lenguajes().getFirst();
        this.descargas = datosLibro.descargas();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public long getDescargas() {
        return descargas;
    }

    public void setDescargas(long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return """
                ----- LIBRO -----
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                -----------------
                """.formatted(titulo, autor.getNombre(), lenguaje, descargas);
    }
}
