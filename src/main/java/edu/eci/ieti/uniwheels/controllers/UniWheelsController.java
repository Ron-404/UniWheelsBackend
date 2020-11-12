package edu.eci.ieti.uniwheels.controllers;


import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.model.Usuario;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.services.UniwheelsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/uniwheels")
@CrossOrigin(origins = "*")
public class UniWheelsController extends BaseController{
    @Autowired
    UniwheelsServices uniwheelsServices = null;


    @RequestMapping("/helloworld")
    public String helloWorld(){
        return uniwheelsServices.helloWorld();
    }

    @RequestMapping(value ="/getCarros/{username}",method = RequestMethod.GET)
    public ResponseEntity<?> getCarros(@PathVariable String username){
        try{
            Collection<Carro> carrosCarroCollection = uniwheelsServices.getCarros(username);
            return new ResponseEntity<>(carrosCarroCollection, HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/addCarro/{username}", method= RequestMethod.POST)
    public ResponseEntity<?> addCarroUsuario(@RequestBody Carro carro,@PathVariable String username){
        try{
            uniwheelsServices.addCarroUsuario(username,carro);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/updateCarro/{username}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateCarro(@RequestBody Carro carro,@PathVariable String username){
        try{
            Usuario usuario = getCurrentUser(uniwheelsServices.getUserByUsername(username));
            uniwheelsServices.updateCarro(carro,usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value ="/getUniversidades",method = RequestMethod.GET)
    public ResponseEntity<?> getUniversidades(){
        try{
            List<Universidad> universidadCollection = uniwheelsServices.getUniversidades();
            return new ResponseEntity<>(universidadCollection,HttpStatus.ACCEPTED);

        } catch (Exception e){
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value="/addUniversidad", method=RequestMethod.POST)
    public ResponseEntity<?> addUniversidad(@RequestBody Universidad universidad){
        try{
            System.out.println("w");
            uniwheelsServices.addUniversidad(universidad);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/addCalificacion/{idConductor}/{idPasajero}/{calificacion}", method=RequestMethod.POST)
    public ResponseEntity<?> addCalificacion(@PathVariable String idConductor,@PathVariable String idPasajero,@PathVariable int calificacion){
        try{
            uniwheelsServices.addCalificacion(idConductor,idPasajero,calificacion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/userStatus/{username}",method = RequestMethod.GET)
    public ResponseEntity<?> getUserState(@PathVariable String username){
        try {
            String state = uniwheelsServices.getUserStatus(username);
            return new ResponseEntity<>(state,HttpStatus.OK);
        } catch (UniWheelsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @RequestMapping(value="/getAverage/{username}/{type}",method=RequestMethod.GET)
    public ResponseEntity<?> getAverage(@PathVariable String username,@PathVariable String type){
        try {
            float average = uniwheelsServices.getAverage(username,type);
            return new ResponseEntity<>(average,HttpStatus.OK);
        } catch (UniWheelsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/updateUser",method=RequestMethod.PUT)
    public ResponseEntity<?> getAverage(@RequestBody Usuario usuario){
        try {
            uniwheelsServices.updateUserBasicInfo(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UniWheelsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
