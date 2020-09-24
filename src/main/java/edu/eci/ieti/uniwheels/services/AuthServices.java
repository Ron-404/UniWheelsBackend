package edu.eci.ieti.uniwheels.services;


import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.persistence.ImplPersistencia;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public DetallesUsuario loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        System.out.println(username+"    Servicess");
        try {
            usuario = uwp.getUserByUsername(username);

        } catch (UniWheelsException e) {
            throw new UsernameNotFoundException("User not found");
        }
        return new DetallesUsuario(usuario);

    }
}

