
package com.proyecto.bibliotech.service;

import com.proyecto.bibliotech.entity.Autor;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.repository.AutorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Agustin Gomez
 */
@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Transactional
    public void crearAutor (String nombre) throws MiException{
        
        validar(nombre, nombre);
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorRepository.save(autor);
        
    }
    
    public List<Autor> listarAutores(){
        
        List<Autor> autores = new ArrayList();
        
        autores = autorRepository.findAll();
        
        return autores;
        
    }
    
    public void modificarAutor(String id, String nombre) throws MiException{
        
        validar(id, nombre);
        
        Optional<Autor> respuesta = autorRepository.findById(id);
        
        if (respuesta.isPresent()){
            
            Autor autor = respuesta.get();
            
            autor.setNombre(nombre);
            
            autorRepository.save(autor);            
        }
        
    }
    
    public Autor getOne(String id){
        return autorRepository.getOne(id);
    }
    
    private void validar (String id,String nombre) throws MiException{
        
        if(id == null || id.isEmpty()){
            throw new MiException("El id no puede ser nulo o estar vacio");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        
    }
    
}
