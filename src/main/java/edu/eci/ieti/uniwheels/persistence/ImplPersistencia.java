package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.repository.CarroRepository;
import edu.eci.ieti.uniwheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplPersistencia implements UniwheelsPersistence {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public void saveUser(Usuario usuario){
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    @Override
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

    @Override
    public List<Carro> getCarros(String username) throws Exception {
        if(username != null)
        {
            //Connect with repository
        }
        else
            throw new Exception(UniWheelsException.USERNAME_NOT_FOUND);
        return null;
    }

    @Override
    public void addCarroUsuario(Carro carro) throws Exception {
        if(carro != null){
            //Connect with repository
        }
        else
            throw new Exception(UniWheelsException.CAR_NOT_FOUND);

    }

    @Override
    public List<Universidad> getUniversidad() {
        return null;
    }

    @Override
    public void addUniversidad(Universidad universidad) throws Exception {
        if(universidad != null){
            //Connect with repository
        }
        else
            throw new Exception(UniWheelsException.INVALID_UNIVERSITY);
    }

    @Override
    public void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception {
        if(calificacion > 0)
        {
            if(idPasajero != null)
            {
                if(idConductor != null){
                    //Connect with repository
                }
                else
                    throw  new Exception(UniWheelsException.DRIVER_NOT_FOUND);
            }
            else
                throw new Exception(UniWheelsException.PASANGER_NOT_FOUND);
        }
        else
            throw new Exception(UniWheelsException.INVALID_RATING);
    }

    @Override
    public void updateCarro(Carro carro) throws Exception {
        if(carro != null)
        {
            //Connect with repository
        }
        else
            throw new Exception(UniWheelsException.INVALID_CAR);
    }
}
