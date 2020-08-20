package edu.eci.ieti.uniwheels.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServices implements UserDetailsService {

    //Debe recibir un parametro de Usuario
    public void addUser() {
        String pass;
    }

    //Debe crearse una clase que extienda UserDetails y cambiarla por la que esta acá
    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }


}

