
package com.proyecto.bibliotech.service;

import com.proyecto.bibliotech.entity.Autor;
import com.proyecto.bibliotech.entity.Editorial;
import com.proyecto.bibliotech.entity.Libro;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.repository.AutorRepository;
import com.proyecto.bibliotech.repository.EditorialRepository;
import com.proyecto.bibliotech.repository.LibroRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LibroService {
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private EditorialRepository editorialRepository;
    
    @Transactional
    public void crearLibro (Long isbn,String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
             
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Autor autor = autorRepository.findById(idAutor).get();
        
        Editorial editorial = editorialRepository.findById(idEditorial).get();
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplates(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepository.save(libro);
        
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros = new ArrayList();
        
        libros = libroRepository.findAll();
        
        return libros;
    }
    
    public void modificarLibro (Long isbn,String titulo,Integer ejemplares, String idAutor,String idEditorial) throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respuesta = libroRepository.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepository.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepository.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        //Corroborar que las respuestas contengan un valor no nulo
        if (respuestaAutor.isPresent()){ 
            autor = respuestaAutor.get();
        }
        
        if (respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        
        if (respuesta.isPresent()){
            
            Libro libro = respuesta.get();
            
            libro.setTitulo(titulo);
            libro.setEjemplates(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            libroRepository.save(libro);
        }
        
        
    }
    
    public Libro getOne(Long isbn){
        return libroRepository.getOne(isbn);
    }
    
    private void validar(Long isbn,String titulo,Integer ejemplares, String idAutor,String idEditorial) throws MiException{
        
        if (isbn == null){
            throw new MiException("El isbn no puede venir vacio");
        }
        if (titulo.isEmpty() || titulo == null){
            throw new MiException ("El titulo no puede estar vacio o ser nulo");
        }
        if (ejemplares == null){
            throw new MiException("Los ejemplates no pueden venir vacio");
        }
        if (idAutor.isEmpty() || idAutor == null){
            throw new MiException ("El autor no puede estar vacio o ser nulo");
        }
        if (idEditorial.isEmpty() || idEditorial == null){
            throw new MiException ("La editorial no puede estar vacio o ser nulo");
        }
    }
    
}
