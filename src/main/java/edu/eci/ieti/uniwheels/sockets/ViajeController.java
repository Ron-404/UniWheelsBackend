/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.ieti.uniwheels.sockets;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

/**
 *
 * @author J. Eduardo Arias
 */
public class ViajeController {
    
    @MessageMapping("/viaje/{idviaje}")
    public void sendMessage(@DestinationVariable String idviaje){
        
    }
}
