package com.proyecto.bibliotech.controller;

import com.proyecto.bibliotech.entity.Editorial;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.service.EditorialService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping("registrar")
    public String registrar(){
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,ModelMap modelo){
        
        try {
            editorialService.crearEditorial(nombre);
            modelo.put("exito", "La editorial se cargo correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Editorial> editoriales = editorialService.listarEditoriales();
        
        modelo.addAttribute("editoriales",editoriales);
        
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar (@PathVariable String id,ModelMap modelo){
        
        modelo.put("editorial", editorialService.getOne(id));
        
        return "editorial_modificar.html"; 
    }
    
    @PostMapping ("/modificar/{id}")
    public String modificar (@PathVariable String id,String nombre,ModelMap modelo){
        
        try {
            editorialService.modificarEditorial(id, nombre);
            return "redirect:../lista";
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
        
    }
}
