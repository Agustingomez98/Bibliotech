package com.proyecto.bibliotech.repository;

import com.proyecto.bibliotech.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agustin Gomez
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo (@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> busquedaPorAutor (@Param("nombre") String nombre);
}
