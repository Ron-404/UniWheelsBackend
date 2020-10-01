package edu.eci.ieti.uniwheels.controllers;

import edu.eci.ieti.uniwheels.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", methods= {RequestMethod.OPTIONS})
public class BaseController {
    public Usuario getCurrentUser(@AuthenticationPrincipal Usuario user) {
        return user;
    }
}
