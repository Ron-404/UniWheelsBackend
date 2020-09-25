package edu.eci.ieti.uniwheels.services;


import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.persistence.UniwheelsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class UniwheelsServices {

    @Autowired
    UniwheelsPersistence uniwheelsPersistence = null;

    public String helloWorld(){

        return "Hello World Hola Mundo";
    }

    public List<Carro> getCarros(String username) throws Exception {
        return uniwheelsPersistence.getCarros(username);
    }

    public void addCarroUsuario(DetallesUsuario user, Carro carro) throws Exception {
        uniwheelsPersistence.addCarroUsuario(carro);
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

    public void updateCarro(Carro carro) throws Exception {
        uniwheelsPersistence.updateCarro(carro);
    }
}
