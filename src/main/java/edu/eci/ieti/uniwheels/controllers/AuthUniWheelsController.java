package edu.eci.ieti.uniwheels.controllers;

import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUniWheelsController extends BaseController {
    @Autowired
    private AuthServices authServices;

    @RequestMapping(value = "/loggedUser",method = RequestMethod.GET)
    public ResponseEntity<?> isLogged(){
        return new ResponseEntity<>(getLoggedUser(),HttpStatus.OK);
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Usuario usuario){

        authServices.addUser(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
