# UniWheelsBackend 

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://uniwheels-backend.herokuapp.com)
[![CircleCI](https://circleci.com/gh/Ron-404/UniWheelsBackend.svg?style=svg)](https://app.circleci.com/pipelines/github/Ron-404/UniWheelsBackend)

## Execute Project

```
git clone https://github.com/Ron-404/UniWheelsBackend.git
gradle spring-boot:run
```

## Class Model

![Class Model](images/DiagramaDeClases.png)

## API's

### Authentication
1. /auth/addUser (Register)
```
{
    "username": "orlandoagk",
    "nombreCompleto":"Orlando Antonio Gelves Kerguelen",
    "direccionResidencia":"Calle 87",
    "password":"prueba",
    "email":"orlando@hotmail.com",
    "universidad":"ECI",
    "numero":"3267895438",
    "carros":[],
    "viajesConductor":[],
    "viajesPasajero":[]
}
```
2. /login (Login)
```
{
    "username":"orlandoagk",
    "password":"prueba"
}
```

### Uniwheels Api's
