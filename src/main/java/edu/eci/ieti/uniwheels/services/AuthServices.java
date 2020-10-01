package edu.eci.ieti.uniwheels.services;


import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.persistence.ImplPersistencia;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServices extends UserServices{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public void addUser(Usuario usuario) {

        uniwheelsPersistence.saveUser(usuario);
    }


    public boolean login(String username,String password) throws UniWheelsException {
        Usuario user = uniwheelsPersistence.getUserByUsername(username);
        return bCryptPasswordEncoder.matches(password,user.password);

    }


}

