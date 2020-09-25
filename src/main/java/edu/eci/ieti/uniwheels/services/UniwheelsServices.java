package edu.eci.ieti.uniwheels.services;


import org.springframework.stereotype.Service;




@Service
public class UniwheelsServices {

    @Autowired
    UniwheelsPersistence uniwheelsPersistence = null;

    public String helloWorld(){

        return "Hello World Hola Mundo";
    }

    public Collection<Carro> getCarros(String username){
        return uniwheelsPersistence.getCarros(username);
    }

    public void addCarroUsuario(Usuario user,Carro carro){
        uniwheelsPersistence.addCarroUsuario(carro);
    }

    public Collection<Universidad> getUniversidades(){
        return uniwheelsPersistence.getUniversidad();
    }

    public void addUniversidad(Universidad universidad){
        uniwheelsPersistence.addUniversidad(universidad);
    }

    public void addCalificacion(String idConductor,String idPasajero,int calificacion){
        return uniwheelsPersistence.addCalificacion(idConductor,idPasajero,calificacion);
    }

}
