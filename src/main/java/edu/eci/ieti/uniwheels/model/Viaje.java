package edu.eci.ieti.uniwheels.model;

public class Viaje {
    private String precio;
    private String origen;
    private String destino;
    private String carro;

    public Viaje(){
    }

    public Viaje(String precio, String origen, String destino, String carro) {
        this.precio = precio;
        this.origen = origen;
        this.destino = destino;
        this.carro = carro;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }
}
