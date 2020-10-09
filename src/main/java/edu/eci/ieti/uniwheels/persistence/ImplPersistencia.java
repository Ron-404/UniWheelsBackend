package edu.eci.ieti.uniwheels.persistence;

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
    public void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception {
        if(calificacion > 0) {
            if(idPasajero != null) {
                if(idConductor != null){
                    //Connect with repository
                } else {
                    throw new Exception(UniWheelsException.DRIVER_NOT_FOUND);
                }
            } else {
                throw new Exception(UniWheelsException.PASANGER_NOT_FOUND);
            }
        } else {
            throw new Exception(UniWheelsException.INVALID_RATING);
        }
    }

    @Override
    public void updateCarro(Carro carro, Usuario usuario) throws Exception {
        if(carro != null) {
           List<Carro> allCarros = usuario.getCarros();
           for(Carro car: allCarros){
               if(car.getPlaca().equals(carro.getPlaca())){
                   car.setColor(carro.getColor());
                   car.setMarca(carro.getMarca());
                   car.setModelo(carro.getModelo());
                   break;
               }
           }
           userRepository.save(usuario);
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
                if(con.getEstado().equals("Disponible")){
                    conductorsTemp.add(con);
                }
            }
        }
        return conductorsTemp;
    }
}