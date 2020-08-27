package edu.eci.ieti.uniwheels.services;


import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.persistence.ImplPersistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServices implements UserDetailsService {

    @Autowired
    private ImplPersistencia uwp;

    public void addUser(Usuario usuario) {

        uwp.saveUser(usuario);
    }

    @Override
    public DetallesUsuario loadUserByUsername(String username) {
        return new DetallesUsuario(uwp.getUser());
    }


}

