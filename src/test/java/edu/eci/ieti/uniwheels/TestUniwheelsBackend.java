package edu.eci.ieti.uniwheels;

import static org.junit.Assert.*;

import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.Conductor;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.model.Viaje;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.repository.UserRepository;
import edu.eci.ieti.uniwheels.services.AuthServices;
import edu.eci.ieti.uniwheels.services.UniwheelsServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.data.mongodb.uri=mongodb+srv://admin:adminwhells@uniwhellsback.vxj50.mongodb.net","spring.data.mongodb.database=UniWheelsTest"})
public class TestUniwheelsBackend {
    @Autowired
    UniwheelsServices uniwheelsServices;

    @Autowired
    AuthServices authServices;

    @Autowired
    UserRepository userRepository;

    @Test
    public void thisWillCreateAUser(){
        userRepository.deleteAll();
        Usuario user = new Usuario();
        user.setUsername("nigi");
        user.setNombreCompleto("Fulanito Perez");
        user.setDireccionResidencia("Calle 32 #65-9454");
        user.setPassword("prueba");
        user.setEmail("nigi@mail.com");
        user.setNumero("329485964");
        user.setUniversidad("ECI");
        user.setCarros(new ArrayList<>());
        user.setViajesConductor(new ArrayList<>());
        user.setViajesPasajero(new ArrayList<>());
        authServices.addUser(user);
        Usuario otherUser = null;
        try {
            otherUser = authServices.getUserByUsername("nigi");
            otherUser = authServices.getUserByUsername("nigiDOs");
        } catch (UniWheelsException e) {
            assertEquals("This user is not created",e.getMessage(),UniWheelsException.USERNAME_NOT_FOUND);
        }
        assertEquals("Is created a User with nigi username",user.username,otherUser.username);
    }

    @Test
    public void thisWillAddACarToAUser(){
        try {
            uniwheelsServices.addCarroUsuario("nigi",new Carro("XYZ123", "Lamborghini", "Gris", "Urus"));
            List<Carro> cars = uniwheelsServices.getCarros("nigi");
            assertEquals("The size of the list should be 1",cars.size(),1);
            assertEquals("The first element of the cars should be have a license plate called XYZ123",cars.get(0).placa,"XYZ123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void thisWillOfferATravel(){
        Viaje travel = new Viaje("3000", "ECI", "Mi Casa", "XYZ123");
        List<Conductor> driversAvailable = null;
        try {
            driversAvailable = uniwheelsServices.getConductoresDisponibles(travel,"nigi");
        } catch (UniWheelsException e) {
            e.printStackTrace();
        }
        assertEquals("The size of the list of driversAvailable sholud be 1",driversAvailable.size(),1);
    }
}
