package edu.eci.ieti.uniwheels.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
public class Usuario {
    @Id
    public String userId;
    public String username;
    public String nombreCompleto;
    public String direccionResidencia;
    public String password;
    public String email;
    public String universidad;
    public String numero;
    public List<Carro> carros;
    public List<Conductor> viajesConductor;
    public List<Pasajero> viajesPasajero;

    public Usuario(){}

    public Usuario(String username,String nombreCompleto,String password,String email,
                   String universidad, String direccionResidencia, String numero, List<Carro> carros,
                   List<Conductor> viajesConductor, List<Pasajero> viajesPasajero){
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.password = password;
        this.email = email;
        this.universidad = universidad;
        this.direccionResidencia = direccionResidencia;
        this.numero = numero;
        this.carros = carros;
        this.viajesConductor = viajesConductor;
        this.viajesPasajero = viajesPasajero;
    }

    public List<Conductor> getViajesConductor() {
        return viajesConductor;
    }

    public void setViajesConductor(List<Conductor> viajesConductor) {
        this.viajesConductor = viajesConductor;
    }

    public List<Pasajero> getViajesPasajero() {
        return viajesPasajero;
    }

    public void setViajesPasajero(List<Pasajero> viajesPasajero) {
        this.viajesPasajero = viajesPasajero;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", direccionResidencia='" + direccionResidencia + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", universidad='" + universidad + '\'' +
                ", numero='" + numero + '\'' +
                ", carros=" + carros +
                ", viajesConductor=" + viajesConductor +
                ", viajesPasajero=" + viajesPasajero +
                '}';
    }
}
