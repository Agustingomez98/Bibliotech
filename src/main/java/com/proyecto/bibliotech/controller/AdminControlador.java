package com.proyecto.bibliotech.controller;

import com.proyecto.bibliotech.entity.Usuario;
import com.proyecto.bibliotech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Agustin Gomez
 */
@Controller
@RequestMapping ("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping ("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }

    @GetMapping ("/usuarios")
    public String lista (ModelMap modelo){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios",usuarios);
        return "usuario_list";
    }

    @GetMapping ("modificarRol/{id}")
    public String cambiarRol (@PathVariable String id){
        usuarioService.cambiarRol(id);
        return "redirect:/admin/usuarios";
    }

    @GetMapping ("eliminar/{id}")
    public String eliminarUsuario (@PathVariable String id){
        usuarioService.eliminarUsuario(id);
        return "redirect:/admin/usuarios";
    }

}
