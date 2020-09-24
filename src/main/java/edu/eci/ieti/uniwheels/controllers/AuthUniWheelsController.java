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
public class AuthUniWheelsController {
    @Autowired
    private AuthServices authServices;

    @RequestMapping(value = "/usuarioLogeado",method = RequestMethod.GET)
    public ResponseEntity<?> isLogged(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/añadirUsuario",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Usuario usuario){

        System.out.println(usuario.toString());
        authServices.addUser(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
