
package com.proyecto.bibliotech.repository;

import com.proyecto.bibliotech.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agustin Gomez
 */
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, String>{
    
}
