package com.aluracursos.literAlura.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "autores")
public class Autor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int anioNacimiento;
    private int anioMuerte;
    @Column(unique = true)
    private String nombre;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.anioMuerte = datosAutor.anioMuerte();
        this.anioNacimiento = datosAutor.anioNacimiento();
        this.nombre = datosAutor.nombre();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public int getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(int anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return """
                ---------- AUTOR ----------
                Nombre: %s
                Año de nacimiento: %d
                Año de fallecimiento: %d
                ---------------------------
                """.formatted(nombre, anioNacimiento, anioMuerte);
    }
}
