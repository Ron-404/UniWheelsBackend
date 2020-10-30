package edu.eci.ieti.uniwheels.sockets;

import edu.eci.ieti.uniwheels.model.Conductor;
import edu.eci.ieti.uniwheels.model.Estado;
import edu.eci.ieti.uniwheels.model.Pasajero;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.services.UniwheelsServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ws")
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniwheelsServices uniwheelsServices;

    @MessageMapping("/passengerRequest.{usernameDriver}")
    public void pasajeroSolicitudDeViaje(String infoPassenger, @DestinationVariable String usernameDriver){
        JSONObject infoOfPassenger = new JSONObject(infoPassenger);
        try {
            List<Pasajero> possiblePassengers= uniwheelsServices.solicitudDeViajePasajero(infoOfPassenger,usernameDriver);
            msgt.convertAndSend("/passengerRequest."+usernameDriver,possiblePassengers);
        } catch (UniWheelsException e) {
            e.printStackTrace();
            msgt.convertAndSend("/passengerRequest."+usernameDriver,"No encontré el usuario pasajero o el conductor");
        }
    }

    @MessageMapping("/offerTravel.{driverUsername}")
    public void ofrecerViaje(String route, @DestinationVariable String driverUsername ) throws UniWheelsException {
        JSONObject infoDriver = new JSONObject(route);
        boolean flag = true;
        if(route == null){
            msgt.convertAndSend("/drivers",uniwheelsServices.getConductoresDisponibles());
            flag = true;
        }
        try {
            if(flag) {
                List<Conductor> availableDrivers = uniwheelsServices.getConductoresDisponibles(infoDriver, driverUsername);
                msgt.convertAndSend("/drivers", availableDrivers);
            }
        } catch (UniWheelsException e) {
            e.printStackTrace();
            msgt.convertAndSend("/drivers","No se encontraron conductores disponibles");
        }
    }

    @MessageMapping("/acceptOrRejectPassenger.{usernamePassenger}")
    public void aceptarORechazarPasajero(String state,@DestinationVariable String usernamePassenger){
        JSONObject estadoJSON = new JSONObject(state);
        try{
            JSONObject json = uniwheelsServices.aceptarORechazarPasajero(estadoJSON,usernamePassenger);

            msgt.convertAndSend("/acceptOrRejectPassenger."+usernamePassenger,json.toMap());
        } catch (Exception e){
            msgt.convertAndSend("/acceptOrRejectPassenger."+usernamePassenger,"No se encontró un pasajero o conductor con el username dado");
        }

    }

    @MessageMapping("/passengerState.{usernamePassenger}")
    public void estadoPasajero(Estado state, @DestinationVariable String usernamePassenger){
        try {

            msgt.convertAndSend("/passengerState."+usernamePassenger,uniwheelsServices.estadoPasajero(state,usernamePassenger));
        } catch (UniWheelsException e) {
            e.printStackTrace();
        }
    }
}
