package com.aluracursos.literAlura.repository;

import com.aluracursos.literAlura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoresRepository extends JpaRepository<Autor,Long> {
    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND :anio < a.anioMuerte")
    List<Autor> autorVivoPorAnio(int anio);
}
