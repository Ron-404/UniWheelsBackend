package edu.eci.ieti.uniwheels.controllers;

import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {
    protected DetallesUsuario getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DetallesUsuario userDetails = null;
        if (principal instanceof DetallesUsuario) {
            userDetails = (DetallesUsuario) principal;
        }
        return userDetails;
    }
}
