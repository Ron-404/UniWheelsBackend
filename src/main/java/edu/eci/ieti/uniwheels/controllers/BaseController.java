package edu.eci.ieti.uniwheels.controllers;

import edu.eci.ieti.uniwheels.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public class BaseController {
    public Usuario getCurrentUser(@AuthenticationPrincipal Usuario user) {
        return user;
    }
}
