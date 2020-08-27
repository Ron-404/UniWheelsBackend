package edu.eci.ieti.uniwheels.model;

public class Usuario {
    public int userId;
    public String username;
    public String direccionResidencia;
    public String password;
    public String email;
    public String universidad;
    public String numero;

    public Usuario(){}

    public Usuario(String username,String password,String email, String universidad, String direccionResidencia, String numero){
        this.username = username;
        this.password = password;
        this.email = email;
        this.universidad = universidad;
        this.direccionResidencia = direccionResidencia;
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
}
