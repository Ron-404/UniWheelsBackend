package edu.eci.ieti.uniwheels.persistence;

import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.Universidad;

import java.util.Collection;

public interface UniwheelsPersistence {

    Collection<Carro> getCarros(String username);

    void addCarroUsuario(Carro carro);

    Collection<Universidad> getUniversidad();

    void addUniversidad(Universidad universidad);

    void addCalificacion(String idConductor, String idPasajero, int calificacion);
}
