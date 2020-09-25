package edu.eci.ieti.uniwheels.services;


import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.persistence.UniwheelsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class UniwheelsServices {

    @Autowired
    UniwheelsPersistence uniwheelsPersistence = null;

    public String helloWorld(){

        return "Hello World Hola Mundo";
    }

    public Collection<Carro> getCarros(String username){
        return uniwheelsPersistence.getCarros(username);
    }

    public void addCarroUsuario(DetallesUsuario user, Carro carro){
        uniwheelsPersistence.addCarroUsuario(carro);
    }

    public Collection<Universidad> getUniversidades(){
        return uniwheelsPersistence.getUniversidad();
    }

    public void addUniversidad(Universidad universidad){
        uniwheelsPersistence.addUniversidad(universidad);
    }

    public void addCalificacion(String idConductor,String idPasajero,int calificacion){
        uniwheelsPersistence.addCalificacion(idConductor,idPasajero,calificacion);
    }

}
