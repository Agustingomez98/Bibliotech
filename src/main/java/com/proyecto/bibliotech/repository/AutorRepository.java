
package com.proyecto.bibliotech.repository;

import com.proyecto.bibliotech.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agustin Gomez
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, String> {
    
}
