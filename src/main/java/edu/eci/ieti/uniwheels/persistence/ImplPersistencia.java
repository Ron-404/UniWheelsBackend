package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ImplPersistencia {
    private Usuario USER = new Usuario();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void saveUser(Usuario usuario){
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        USER = usuario;
    }

    public Usuario getUser(){
        return USER;
    }

}
