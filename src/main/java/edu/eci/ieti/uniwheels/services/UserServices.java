package edu.eci.ieti.uniwheels.services;

import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.persistence.ImplPersistencia;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.persistence.UniwheelsPersistence;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServices {
    @Autowired
    UniwheelsPersistence uniwheelsPersistence;
    public Usuario getUserByUsername(String username) throws UniWheelsException {
        return uniwheelsPersistence.getUserByUsername(username);
    }
    
    public Usuario getBasicInfoUser(String username) throws UniWheelsException {
    	Usuario user = uniwheelsPersistence.getUserByUsername(username);
        // not show vars
        user.password = null;
        user.userId = null;
        user.carros = null;
        user.viajesConductor =null;
        user.viajesPasajero =null;
        return user;
    }
}
