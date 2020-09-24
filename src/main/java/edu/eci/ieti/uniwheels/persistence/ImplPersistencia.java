package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ImplPersistencia {
    private Usuario USER = new Usuario();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    public void saveUser(Usuario usuario){
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    public Usuario getUser(){
        return USER;
    }

}
