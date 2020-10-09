package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.Conductor;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.model.Usuario;
import java.util.List;

public interface UniwheelsPersistence {

    void saveUser(Usuario usuario);

    Usuario getUserByUsername(String username) throws UniWheelsException;

    List<Universidad> getUniversidad();

    void addUniversidad(Universidad universidad) throws Exception;

    void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception;

    void updateCarro(Carro carro,Usuario usuario) throws Exception;

    void updateUser(Usuario user) throws UniWheelsException;

    List<Conductor> getConductoresDisponibles();
}
