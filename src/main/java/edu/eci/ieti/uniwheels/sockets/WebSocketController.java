package edu.eci.ieti.uniwheels.sockets;

import edu.eci.ieti.uniwheels.services.UniwheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import edu.eci.ieti.uniwheels.model.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    UniwheelsServices uniwheelsServices;
}
