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

    @RequestMapping(value ="/getCarros",RequestMethod.GET)
    public ResponseEntity<?> getCarros(@PathVariable String username){
        try{
            Usuario user = getLoggerUser()
            Collection<Carro> carrosCarroCollection = uniwheelsServices.getCarros(username);
            return new ResponseEntity<>(carrosCarroCollection,HttpStatus.ACEPTED);
        }
        catch (Exception e)
        {
            Logger.getLogger(UniWheelsController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/addCarro", method=RequestMethod.POST)
    public ResponseEntity<?> addCarroUsuario(@RequestBody Carro carro){
        try{
            Usuario usuario = getLoggedUser().getUsuario();
            uws.addCarroUsuario(usuario,carro);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }



    @RequestMapping(value ="/getUniversidades",RequestMethod.GET)
    public ResponseEntity<?> getUniversidades(){
        try{
            Collection<Universidad> universidadCollection = uniwheelsServices.getUniversidades();
            return new ResponseEntity<>(carrosCarroCollection,HttpStatus.ACEPTED);

        } catch (Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value="/addUniversidad", method=RequestMethod.POST)
    public ResponseEntity<?> addUniversidad(@RequestBody Universidad universidad){
        try{
            uws.addUniversidad(universidad);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/addCalificacion/{idConductor}/{idPasajero}/{calificacion}", method=RequestMethod.POST)
    public ResponseEntity<?> addUniversidad(@PathVariable String idConductor,@PathVariable String idPasajero,@PathVariable int calificacion){
        try{
            uws.addCalificacion(idConductor,idPasajero,calificacion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(UniWheelsAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
