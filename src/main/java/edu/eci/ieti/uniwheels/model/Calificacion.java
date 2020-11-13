package edu.eci.ieti.uniwheels.model;

import org.springframework.data.annotation.Id;

public class Calificacion {
    @Id
    public String id;
    public double valor;


    public Calificacion(){

    }
    public Calificacion( double valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
