package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplPersistencia {

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

    public Usuario getUserByUsername(String username) throws UniWheelsException {
        List<Usuario> allUsers = userRepository.findAll();
        System.out.println(username);
        Usuario usuario = null;
        for(int i =0;i<allUsers.size();i++){
            System.out.println(allUsers.get(i).toString());
            if(allUsers.get(i).username.equals(username)){
                System.out.println("Entre acá");
                usuario = allUsers.get(i);
            }
        }
        if(usuario == null){
            throw new UniWheelsException(UniWheelsException.USERNAME_NOT_FOUND);
        }

        return usuario;
    }

}
