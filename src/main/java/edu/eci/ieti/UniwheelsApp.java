package edu.eci.ieti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class UniwheelsApp {

    public static void main(String[] args) {       
        SpringApplication.run(UniwheelsApp.class, args);
       ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.WARN);

    }

}

