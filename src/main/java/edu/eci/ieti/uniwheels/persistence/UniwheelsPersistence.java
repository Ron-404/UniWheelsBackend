package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.model.Usuario;

import java.util.Collection;
import java.util.List;

public interface UniwheelsPersistence {

    void saveUser(Usuario usuario);

    Usuario getUserByUsername(String username) throws UniWheelsException;

    List<Carro> getCarros(String username) throws Exception;

    void addCarroUsuario(Carro carro) throws Exception;

    List<Universidad> getUniversidad();

    void addUniversidad(Universidad universidad) throws Exception;

    void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception;

    void updateCarro(Carro carro) throws Exception;
}
