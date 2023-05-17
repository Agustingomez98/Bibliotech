package com.proyecto.bibliotech.service;

import com.proyecto.bibliotech.entity.Imagen;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.repository.ImagenRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Agustin Gomez
 */
@Service
public class ImagenService {
    
    @Autowired
    private ImagenRepository imagenRepository;
    
    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiException{
        
        if (archivo != null){
            try {
                
                Imagen imagen = new Imagen();
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepository.save(imagen);
                
            } catch (Exception e) {
                System.err.println("Error en cargar la imagen");   
            }
        }
        return null;   
    }
    
    @Transactional
    public Imagen actualizar (MultipartFile archivo, String idImagen) throws MiException{
        
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();
                
                if (idImagen != null){
                    Optional<Imagen> respuesta = imagenRepository.findById(idImagen);
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepository.save(imagen);

            } catch (Exception e) {
                System.err.println("Error en cargar la imagen");
            }
        }
        return null;
    }

}
