package com.floresdelvalle.floristeria.controlador;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejoErroresController {
    private static final Logger LOGGER = Logger.getLogger(ManejoErroresController.class.getName());

    @ExceptionHandler(IllegalArgumentException.class)
    public String recursoNoEncontrado(IllegalArgumentException exception, Model model) {
        model.addAttribute("titulo", "No encontramos ese registro");
        model.addAttribute("mensaje", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String reglaDeNegocio(IllegalStateException exception, Model model) {
        model.addAttribute("titulo", "No se puede completar esta acción");
        model.addAttribute("mensaje", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String integridadReferencial(DataIntegrityViolationException exception, Model model) {
        LOGGER.log(Level.WARNING, "Intento de eliminación con dependencias activas", exception);
        model.addAttribute("titulo", "Registro relacionado");
        model.addAttribute("mensaje", "No se puede eliminar este registro porque tiene datos relacionados en el sistema.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String errorInesperado(Exception exception, Model model) {
        LOGGER.log(Level.SEVERE, "Error no controlado en la aplicación", exception);
        model.addAttribute("titulo", "Algo no salió como esperábamos");
        model.addAttribute("mensaje", "No pudimos completar la solicitud. Intenta nuevamente.");
        return "error";
    }
}
