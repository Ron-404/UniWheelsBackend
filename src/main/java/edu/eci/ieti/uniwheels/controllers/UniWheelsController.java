package edu.eci.ieti.uniwheels.controllers;


import edu.eci.ieti.uniwheels.model.Carro;
import edu.eci.ieti.uniwheels.model.DetallesUsuario;
import edu.eci.ieti.uniwheels.model.Universidad;
import edu.eci.ieti.uniwheels.model.Usuario;
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
public class UniWheelsController extends BaseController{
    @Autowired
    UniwheelsServices uniwheelsServices = null;


    @RequestMapping("/helloworld")
    public String helloWorld(){
        return uniwheelsServices.helloWorld();
    }

    @RequestMapping(value ="/getCarros",method = RequestMethod.GET)
    public ResponseEntity<?> getCarros(@PathVariable String username){
        try{
            DetallesUsuario user = getLoggedUser();
            Collection<Carro> carrosCarroCollection = uniwheelsServices.getCarros(username);
            return new ResponseEntity<>(carrosCarroCollection, HttpStatus.ACCEPTED);
        }
        catch (Exception e)
        {
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/addCarro", method= RequestMethod.POST)
    public ResponseEntity<?> addCarroUsuario(@RequestBody Carro carro){
        try{
            DetallesUsuario usuario = getLoggedUser();
            uniwheelsServices.addCarroUsuario(usuario,carro);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/updateCarro/{carro}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateCarro(@PathVariable  Carro carro){
        try{
            uniwheelsServices.updateCarro(carro);
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

}
