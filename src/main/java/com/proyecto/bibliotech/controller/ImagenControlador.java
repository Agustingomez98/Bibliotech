package com.proyecto.bibliotech.controller;


import com.proyecto.bibliotech.entity.Usuario;
import com.proyecto.bibliotech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Agustin Gomez
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    UsuarioService usuarioService; 
    
    @GetMapping ("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id) {
        
        Usuario usuario = usuarioService.getOne(id);
        
        byte[] imagen = usuario.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
    }
    
}
