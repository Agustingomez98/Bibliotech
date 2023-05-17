package com.proyecto.bibliotech;

import com.proyecto.bibliotech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Agustin Gomez
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UsuarioService usuarioService;
    
    @Autowired
    public void configureGloblal (AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioService)
                .passwordEncoder(new BCryptPasswordEncoder());//Encriptacion de la contraseña
    }
    
    @Override
    protected void configure (HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/admin/*").hasAnyRole("ADMIN")
                    .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                    .permitAll() //Permitir el acceso a los archivos estaticos
                
                .and().formLogin() //Configuracion del formulario de logueo
                    .loginPage("/login")//Definir la url para logearse
                    .loginProcessingUrl("/logincheck") //Definir la URL con la que Sprin va a autenticar un usuario
                    //Configuracion de credenciales
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/inicio")
                    .permitAll()
                .and().logout() //Configuracion para el cierre de sesion
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and().csrf()
                    . disable();
                        
    }
    
}
