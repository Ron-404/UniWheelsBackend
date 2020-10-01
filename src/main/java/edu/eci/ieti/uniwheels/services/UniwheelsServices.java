package edu.eci.ieti.uniwheels.services;


import edu.eci.ieti.uniwheels.model.*;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.persistence.UniwheelsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UniwheelsServices extends UserServices{

    @Autowired
    UniwheelsPersistence uniwheelsPersistence;

    public String helloWorld(){
        return "Hello World Hola Mundo";
    }

    public List<Carro> getCarros(String username) throws Exception {
        return uniwheelsPersistence.getUserByUsername(username).getCarros();
    }

    public void addCarroUsuario(String user, Carro carro) throws Exception {
        Usuario usuario = uniwheelsPersistence.getUserByUsername(user);
        usuario.addCarros(carro);
        updateUser(usuario);
    }

    public List<Universidad> getUniversidades(){
        return uniwheelsPersistence.getUniversidad();
    }

    public void addUniversidad(Universidad universidad) throws Exception {
        uniwheelsPersistence.addUniversidad(universidad);
    }

    public void addCalificacion(String idConductor,String idPasajero,int calificacion) throws Exception {
        uniwheelsPersistence.addCalificacion(idConductor,idPasajero,calificacion);
    }

    public void updateCarro(Carro carro,Usuario usuario) throws Exception {
        uniwheelsPersistence.updateCarro(carro,usuario);
    }

    public String getUserState(String username) throws UniWheelsException {
        Usuario user = uniwheelsPersistence.getUserByUsername(username);
        String state = "Ninguno";
        if(user.viajesPasajero.size()>0||user.viajesConductor.size()>0){
            if (!user.viajesPasajero.get(user.viajesPasajero.size() - 1).estado.equals("Finished")) {
                state = "Pasajero";
            } else if (!user.viajesConductor.get(user.viajesConductor.size() - 1).estado.equals("Finished")){
                state = "Conductor";
            }
        }
        return state;
    }

    public float getAverage(String username, String type) throws UniWheelsException {
        Usuario usuario = uniwheelsPersistence.getUserByUsername(username);
        float valueToReturn = 0;
        int totalCalifications = 0;
        if(type.equals("Conductor") && usuario.viajesConductor.size()>0){
            for(Conductor c : usuario.viajesConductor){
                if(c.estado.equals("Finalizado")){
                    valueToReturn+=c.calificacion.valor;
                    totalCalifications+=1;
                }
            }
            valueToReturn = valueToReturn/totalCalifications;
        } else {
            for(Pasajero p : usuario.viajesPasajero){
                if(p.estado.equals("Finalizado")){
                    valueToReturn+=p.calificacion.valor;
                    totalCalifications+=1;
                }
            }
            valueToReturn = valueToReturn/totalCalifications;
        }
        return valueToReturn;
    }

    public void updateUser(Usuario user) throws UniWheelsException {
        uniwheelsPersistence.updateUser(user);

    }
}
