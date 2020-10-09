package edu.eci.ieti.uniwheels.sockets;

import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.services.UniwheelsServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import edu.eci.ieti.uniwheels.model.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniwheelsServices uniwheelsServices;


    @MessageMapping("/solicitudPasajero.{usernameConductor}")
    public void pasajeroSolicitudDeViaje(String infoPasajero, @DestinationVariable String usernameConductor){
        JSONObject infoDelPasajero = new JSONObject(infoPasajero);
        try {
            List<Pasajero> posiblesPasajeros= uniwheelsServices.solicitudDeViajePasajero(infoDelPasajero,usernameConductor);
            msgt.convertAndSend("/solicitudPasajero."+usernameConductor,posiblesPasajeros);
        } catch (UniWheelsException e) {
            e.printStackTrace();
            msgt.convertAndSend("/solicitudPasajero."+usernameConductor,"No encontré el usuario pasajero o el conductor");
        }

    }
}
