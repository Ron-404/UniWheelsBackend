package edu.eci.ieti.uniwheels.controllers;


import edu.eci.ieti.uniwheels.services.UniwheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/uniwheels")
public class UniWheelsController {
    @Autowired
    UniwheelsServices uniwheelsServices = null;


    @RequestMapping("/helloworld")
    public String helloWorld(){
        return uniwheelsServices.helloWorld();
    }



}
