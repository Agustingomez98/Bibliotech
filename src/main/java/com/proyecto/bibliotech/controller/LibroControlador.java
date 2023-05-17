package com.proyecto.bibliotech.controller;

import com.proyecto.bibliotech.entity.Autor;
import com.proyecto.bibliotech.entity.Editorial;
import com.proyecto.bibliotech.entity.Libro;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.service.AutorService;
import com.proyecto.bibliotech.service.EditorialService;
import com.proyecto.bibliotech.service.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Agustin Gomez
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private AutorService autorService;
    
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        
        modelo.addAttribute("autores",autores);
        modelo.addAttribute("editoriales",editoriales);
        
        
        return "libro_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn,@RequestParam String titulo,@RequestParam(required = false) Integer ejemplares,
                           String idAutor,@RequestParam String idEditorial,ModelMap modelo){
        
        try {
            libroService.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            
            modelo.put("exito", "El libro se cargo correctamente");
        } catch (MiException ex) {
            
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            
            modelo.put("error", ex.getMessage());
            
            return "libro_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Libro> libros = libroService.listarLibros();
        
        modelo.addAttribute("libros",libros);
        
        return "libro_list.html";        
    }
    
    @GetMapping("/modificar/{isbn}")
    public String modificar (@PathVariable Long isbn,ModelMap modelo){
        
        modelo.put("libro", libroService.getOne(isbn));
        
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        return "libro_modificar.html";
    }
    
    @PostMapping("/modificar/{isbn}")
    public String modifcar (@PathVariable Long isbn,String titulo,Integer ejemplares,String idAutor,String idEditorial,ModelMap modelo){
        
        try {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroService.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            
            return "redirect:../lista";
            
        } catch (MiException ex) {
            List<Autor> autores = autorService.listarAutores();
            List<Editorial> editoriales = editorialService.listarEditoriales();
            
            modelo.put("error", ex.getMessage());
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            
            return "libro_modificar.html";
        }
        
        
    }
        
}
