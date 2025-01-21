package com.aluracursos.literAlura.repository;

import com.aluracursos.literAlura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l WHERE l.lenguaje ILIKE %:lenguaje%")
    List<Libro> libroPorLenguaje(String lenguaje);
}
