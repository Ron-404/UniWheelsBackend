package edu.eci.ieti.uniwheels.persistence;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import edu.eci.ieti.uniwheels.model.*;
import edu.eci.ieti.uniwheels.repository.UniversityRepository;
import edu.eci.ieti.uniwheels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImplPersistencia implements UniwheelsPersistence {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public void saveUser(Usuario usuario){
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    @Override
    public Usuario getUserByUsername(String username) throws UniWheelsException {
        Usuario usuario = userRepository.findByUsername(username);
        if(usuario == null){
            throw new UniWheelsException(UniWheelsException.USERNAME_NOT_FOUND);
        }
        return usuario;
    }

    @Override
    public List<Universidad> getUniversidad() {
        List<Universidad> allUniversitys = universityRepository.findAll();
        return allUniversitys;
    }

    @Override
    public void addUniversidad(Universidad universidad) throws Exception {
        if(universidad != null){
            universityRepository.save(universidad);
        } else {
            throw new Exception(UniWheelsException.INVALID_UNIVERSITY);
        }
    }

    @Override
    public void addCalificacion(String nameConductor, String namePasajero, double calificacion) throws UniWheelsException {
        if(calificacion > 0) {
            if(namePasajero.equals("-1")) {
                Usuario user = getUserByUsername(nameConductor);
                Calificacion qualification = new Calificacion(calificacion);
                for (Conductor driver : user.getViajesConductor()) {
                    if (driver.getEstado().equals(Estado.Disponible)) {
                        driver.setCalificacion(qualification);
                        break;
                    }
                }
                userRepository.save(user);
            }else if(nameConductor.equals("-1")){
                Usuario user = getUserByUsername(namePasajero);
                Calificacion qualification = new Calificacion(calificacion);
                for (Pasajero pass : user.getViajesPasajero()) {
                    if (pass.getEstado().equals(Estado.Disponible)) {
                        pass.setCalificacion(qualification);
                        break;
                    }
                }
                userRepository.save(user);
            } else {
                throw new UniWheelsException(UniWheelsException.USERNAME_NOT_FOUND);
            }
        } else {
            throw new UniWheelsException(UniWheelsException.INVALID_RATING);
        }
    }

    @Override
    public void updateCarro(Carro car, Usuario user) throws Exception {
        if(car != null) {
           user.setCarro(car);
           userRepository.save(user);
        } else {
            throw new Exception(UniWheelsException.INVALID_CAR);
        }
    }

    @Override
    public void updateUser(Usuario user) throws UniWheelsException {
        Usuario oldUser = getUserByUsername(user.username);
        oldUser.changeValues(user);
        userRepository.save(oldUser);
    }

    @Override
    public List<Conductor> getConductoresDisponibles(){
        List<Usuario> usuarios= userRepository.findAll();
        List<Conductor> conductorsTemp = new ArrayList<Conductor>();
        for(Usuario user:usuarios){
            for(Conductor con: user.getViajesConductor()){
                if(con.getEstado().equals(Estado.Disponible)){
                    con.setUsername(user.getUsername());
                    con.setCarro(user.getCarros().get(0));
                    conductorsTemp.add(con);
                }
            }
        }
        return conductorsTemp;
    }
}