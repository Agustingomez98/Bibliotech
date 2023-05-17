package com.proyecto.bibliotech.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErroresControlador implements ErrorController {

    @RequestMapping (value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage (HttpServletRequest httpRequest){

        ModelAndView errorPage = new ModelAndView("error");

        String errorMensaje = "";

        int httpErrorCodigo = getErrorCodigo(httpRequest);

        switch (httpErrorCodigo){
            case 400:{
                errorMensaje = "El recurso solicitado no existe";
                break;
            }
            case 401:{
                errorMensaje = "No se encuentra autorizado";
                break;
            }
            case 403:{
                errorMensaje = "No tiene permiso para acceder al recurso";
                break;
            }
            case 404:{
                errorMensaje = "El recurso solicitado no fue encontrado";
                break;
            }
            case 500:
                errorMensaje = "Ocurrio un error interno";
                break;
        }
        errorPage.addObject("codigo", httpErrorCodigo);
        errorPage.addObject("mensaje",errorMensaje);
        return errorPage;
    }

    private int getErrorCodigo (HttpServletRequest httpRequest){
        return  (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    public String getErrorPath(){
        return "/error.html";
    }
}
