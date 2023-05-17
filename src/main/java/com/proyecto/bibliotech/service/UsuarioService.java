package com.proyecto.bibliotech.service;

import com.proyecto.bibliotech.entity.Imagen;
import com.proyecto.bibliotech.entity.Usuario;
import com.proyecto.bibliotech.enumeraciones.Rol;
import com.proyecto.bibliotech.exception.MiException;
import com.proyecto.bibliotech.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Agustin Gomez
 */
@Service
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ImagenService imagenSerivce;
    
    @Transactional
    public void registrar(MultipartFile archivo,String nombre,String email,String password,String password2) throws MiException{
        
        validar(nombre, email, password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));//Criptacion del password
        usuario.setRol(Rol.USER);
        
        Imagen imagen = imagenSerivce.guardar(archivo);
        
        usuario.setImagen(imagen);
        
        usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void actualizar(MultipartFile archivo,String idUsuario,String nombre,String email,String password,String password2) throws MiException{
        
        validar(nombre, email, password, password2);
        
        Optional<Usuario> respuesta = usuarioRepository.findById(idUsuario);
        
        if (respuesta.isPresent()){
        
        Usuario usuario = respuesta.get();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));//Criptacion del password
        usuario.setRol(Rol.USER);
        
        String idImagen = null;
        
        if (usuario.getImagen() != null) {
            idImagen = usuario.getImagen().getId();
        }
        
        Imagen imagen = imagenSerivce.actualizar(archivo,idImagen);
        
        usuario.setImagen(imagen);
        
        usuarioRepository.save(usuario);
        }
    }
    
    public Usuario getOne(String id){
        return usuarioRepository.getOne(id);
    }
    
    public void validar(String nombre,String email,String password,String password2) throws MiException{
        
        if (nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede estar vacio o ser incorrecto");
        }
        if (email == null || email.isEmpty()){
            throw new MiException("El email no puede ser nulo o estar vacio");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La contrasña no puede estar vacia o ser menos a 5 digitos");
        }
        if (!password2.equals(password)){
            throw new MiException ("La contraseña no coincide");
        }
    }

    @Transactional
    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepository.findAll();
        System.out.println(usuarios);
        return usuarios;
    }

    @Transactional
    public void cambiarRol (String id){
        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            if (usuario.getRol().equals(Rol.USER)){
                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.USER);
            }
        }
    }

    @Transactional
    public void eliminarUsuario (String id){
        usuarioRepository.deleteById(id);
    }

    @Override
    //Configuracion del usuario 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepository.buscarPorEmail(email);
        
        if (usuario != null){
            
            List <GrantedAuthority> permisos = new ArrayList(); //Coleccion para guardar los permisos que se le va a aignar al usuario
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());//Permisos predeterminado para usuario
            
            permisos.add(p);
            
            //Recuperar los atributos de la solicitud HTTP
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(),usuario.getPassword(),permisos);
        }else{
            return null;
        }
        
    }
    
}
