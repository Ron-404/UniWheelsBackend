package edu.eci.ieti.uniwheels.persistence;

public class UniWheelsException extends Exception {
    public final static String USERNAME_NOT_FOUND = "The user that you want to enter is not available";

    public UniWheelsException(String message){
        super(message);
    }
}
