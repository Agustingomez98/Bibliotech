package com.proyecto.bibliotech.service;

import com.proyecto.bibliotech.entity.Editorial;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.repository.EditorialRepository;
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
public class EditorialService {
    
    @Autowired
    private EditorialRepository editorialRepository;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        validar(nombre, nombre);
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialRepository.save(editorial);
        
    }
    
    public List<Editorial> listarEditoriales(){
        
        List<Editorial> editoriales = new ArrayList();
        
        editoriales = editorialRepository.findAll();
        
        return editoriales;
        
    }
    
    public void modificarEditorial (String id, String nombre) throws MiException{
        
        validar(id, nombre);
        
        Optional<Editorial> respuesta = editorialRepository.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
             
            editorial.setNombre(nombre);
            
            editorialRepository.save(editorial);
        }
        
    }
    
     private void validar (String id,String nombre) throws MiException{
        
        if(id == null || id.isEmpty()){
            throw new MiException("El id no puede ser nulo o estar vacio");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        
    }
     
     public Editorial getOne(String id){
         return editorialRepository.getOne(id);
     }
    
}
