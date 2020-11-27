package edu.eci.ieti.uniwheels.sockets;

import edu.eci.ieti.uniwheels.model.*;
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
            msgt.convertAndSend("/uniwheels/passengerRequest."+usernameDriver,possiblePassengers);
        } catch (UniWheelsException e) {
            e.printStackTrace();
            msgt.convertAndSend("/uniwheels/passengerRequest."+usernameDriver,"No encontré el usuario pasajero o el conductor");
        }
    }

    @MessageMapping("/offerTravel.{driverUsername}")
    public void ofrecerViaje(Viaje travel, @DestinationVariable String driverUsername ) throws UniWheelsException {
        boolean hasRoute = true;
        if(travel.getPrecio() == null){
            msgt.convertAndSend("/uniwheels/drivers",uniwheelsServices.getConductoresDisponibles());
            hasRoute = false;
        }
        try {
            if(hasRoute) {
                List<Conductor> availableDrivers = uniwheelsServices.getConductoresDisponibles(travel, driverUsername);
                msgt.convertAndSend("/uniwheels/drivers", availableDrivers);
            }
        } catch (UniWheelsException e) {
            e.printStackTrace();
            msgt.convertAndSend("/uniwheels/drivers",e.getMessage());
        }
    }

    @MessageMapping("/acceptOrRejectPassenger.{usernamePassenger}")
    public void aceptarORechazarPasajero(NuevoEstado state, @DestinationVariable String usernamePassenger){
        try{
            JSONObject json = uniwheelsServices.aceptarORechazarPasajero(state,usernamePassenger);
            msgt.convertAndSend("/uniwheels/acceptOrRejectPassenger."+usernamePassenger,json.toMap());
        } catch (Exception e){
            msgt.convertAndSend("/uniwheels/acceptOrRejectPassenger."+usernamePassenger,"No se encontró un pasajero o conductor con el username dado");
        }

    }

    @MessageMapping("/passengerState.{usernamePassenger}")
    public void estadoPasajero(Estado state, @DestinationVariable String usernamePassenger){
        try {
            msgt.convertAndSend("/uniwheels/passengerState."+usernamePassenger,uniwheelsServices.estadoPasajero(state,usernamePassenger));
        } catch (UniWheelsException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/finishTravel.{useranameDriver}")
    public void finishTravel(List<Pasajero> passengers,@DestinationVariable String usernameDriver){
        try {
            uniwheelsServices.finishTravel(usernameDriver,passengers);
            msgt.convertAndSend("/finishTravel."+usernameDriver,"The travel is finished");
        } catch (UniWheelsException e) {
            msgt.convertAndSend("/finishTravel."+usernameDriver,"I got a error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
