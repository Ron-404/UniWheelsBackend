package edu.eci.ieti.uniwheels.services;

import edu.eci.ieti.uniwheels.model.*;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.persistence.UniwheelsPersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniwheelsServices extends UserServices {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UniwheelsPersistence uniwheelsPersistence;

	public String helloWorld() {
		return "Hello World Hola Mundo";
	}

	public List<Carro> getCarros(String username) throws Exception {
		return uniwheelsPersistence.getUserByUsername(username).getCarros();
	}

	public void addCarroUsuario(String user, Carro carro) throws Exception {
		Usuario usuario = uniwheelsPersistence.getUserByUsername(user);
		usuario.addCarros(carro);
		uniwheelsPersistence.updateUser(usuario);
	}

	public List<Universidad> getUniversidades() {
		return uniwheelsPersistence.getUniversidad();
	}

	public void addUniversidad(Universidad universidad) throws Exception {
		uniwheelsPersistence.addUniversidad(universidad);
	}

	public void addCalificacion(String nameConductor, String namePasajero, double calificacion) throws UniWheelsException {
		uniwheelsPersistence.addCalificacion(nameConductor, namePasajero, calificacion);
	}

	public void updateCarro(Carro carro, Usuario usuario) throws Exception {
		uniwheelsPersistence.updateCarro(carro, usuario);
	}

	public String getUserStatus(String username) throws UniWheelsException {
		Usuario user = uniwheelsPersistence.getUserByUsername(username);
		String status = "Ninguno";
		if (user.viajesPasajero.size() > 0 || user.viajesConductor.size() > 0) {
			if (!user.viajesPasajero.get(user.viajesPasajero.size() - 1).estado.equals(Estado.Finalizado)) {
				status = "Pasajero";
			} else if (!user.viajesConductor.get(user.viajesConductor.size() - 1).estado.equals(Estado.Finalizado)) {
				status = "Conductor";
			}
		}
		return status;
	}

	public double getAverage(String username, String type) throws UniWheelsException {
		Usuario usuario = uniwheelsPersistence.getUserByUsername(username);
		double valueToReturn = 0;
		int totalScore = 0;
		if (type.equals("Conductor") && usuario.viajesConductor.size() > 0) {
			for (Conductor c : usuario.viajesConductor) {
				if (c.estado.equals(Estado.Finalizado)) {
					valueToReturn += c.calificacion.valor;
					totalScore += 1;
				}
			}
		} else if(type.equals("Pasajero") && usuario.viajesPasajero.size()>0) {
			for (Pasajero p : usuario.viajesPasajero) {
				if (p.estado.equals(Estado.Finalizado)) {
					valueToReturn += p.calificacion.valor;
					totalScore += 1;
				}
			}
		}
		valueToReturn = valueToReturn / totalScore;

		return valueToReturn;
	}

	public void updateUserBasicInfo(Usuario user) throws UniWheelsException {
		
		Usuario oldUserWithBasicChanges = getUserByUsername(user.username);
		
		// if password is received, re-encrypt it and change it
		if (user.password != null) {
			String pwd = user.password;
			String encrypt = bCryptPasswordEncoder.encode(pwd);
			oldUserWithBasicChanges.setPassword(encrypt);
		};
		
		// send the old user but with the new basic information, if not, it will delete
		// the trips, cars, etc !!!
		oldUserWithBasicChanges.setNombreCompleto(user.nombreCompleto);
		oldUserWithBasicChanges.setDireccionResidencia(user.direccionResidencia);
		oldUserWithBasicChanges.setEmail(user.email);
		oldUserWithBasicChanges.setNumero(user.numero);
		
		uniwheelsPersistence.updateUser(oldUserWithBasicChanges);
	}

	public List<Pasajero> solicitudDeViajePasajero(JSONObject infoPasajero, String usernameConductor)
			throws UniWheelsException {
		Usuario pasajero = getUserByUsername(infoPasajero.getString("usuario"));
		Usuario conductor = getUserByUsername(usernameConductor);
		Conductor viajeConductor = null;

		for (int i = 0; i < conductor.viajesConductor.size(); i++) {
			if (conductor.viajesConductor.get(i).estado.equals(Estado.Disponible)) {

				viajeConductor = conductor.viajesConductor.get(i);
				break;
			}
		}
		Pasajero viajePasajero = new Pasajero();
		viajePasajero.estado = Estado.Disponible;
		viajePasajero.username = pasajero.username;
		viajePasajero.direccionRecogida = infoPasajero.getString("direccion");
		if(viajeConductor.posiblesPasajeros == null){
			viajeConductor.setPosiblesPasajeros(new ArrayList<>());
		}
		viajeConductor.posiblesPasajeros.add(viajePasajero);
		pasajero.viajesPasajero.add(viajePasajero);
		uniwheelsPersistence.updateUser(pasajero);
		uniwheelsPersistence.updateUser(conductor);
		return viajeConductor.posiblesPasajeros;

	}

	public List<Conductor> getConductoresDisponibles(){
		return uniwheelsPersistence.getConductoresDisponibles();
	}
	public List<Conductor> getConductoresDisponibles(Viaje travel, String conducNombre)
			throws UniWheelsException {
		Usuario usuario = getUserByUsername(conducNombre);
		Conductor conductor = new Conductor();

		conductor.setEstado(Estado.Disponible);
		conductor.setPrecio(travel.getPrecio());
		conductor.setDireccionInicio(travel.getOrigen());
		conductor.setDireccionFin(travel.getDestino());

		List<Carro> carros = usuario.getCarros();
		for (Carro c : carros) {
			if (c.getPlaca().equals(travel.getCarro())) {
				conductor.setCarro(c);
				break;
			}
		}
		for(Conductor drivers : usuario.getViajesConductor()){
			if (drivers.getEstado().equals(Estado.Disponible)) {
				throw new UniWheelsException(UniWheelsException.DRIVER_NOT_AVAILABLE);
			}
		}
		usuario.viajesConductor.add(conductor);
		uniwheelsPersistence.updateUser(usuario);
		return uniwheelsPersistence.getConductoresDisponibles();
	}

	public JSONObject aceptarORechazarPasajero(JSONObject info, String usernamePasajero) throws UniWheelsException {
		Usuario usuarioPasajero = getUserByUsername(usernamePasajero);
		Usuario usuarioConductor = getUserByUsername(info.getString("usuario"));
		boolean estado = info.getBoolean("estado");
		Pasajero pasajero = null;
		for (Pasajero p : usuarioPasajero.viajesPasajero) {
			if (p.estado.equals(Estado.Disponible)) {
				pasajero = p;
				break;
			}
		}
		Conductor conductor = null;
		for (Conductor c : usuarioConductor.viajesConductor) {
			if (c.estado.equals(Estado.Disponible)) {
				conductor = c;
				for (int i = 0; i < c.posiblesPasajeros.size(); i++) {
					if (c.posiblesPasajeros.get(i).username.equals(pasajero.username)) {
						c.posiblesPasajeros.remove(i);
						break;
					}
				}
				break;
			}
		}
		pasajero.conductor = conductor;
		if (estado) {
			pasajero.estado = Estado.Aceptado;
			for (Conductor c : uniwheelsPersistence.getConductoresDisponibles()) {
				for (int i = 0; i < c.posiblesPasajeros.size(); i++) {
					if (c.posiblesPasajeros.get(i).username.equals(usernamePasajero)) {
						c.posiblesPasajeros.remove(i);
						break;
					}
				}
			}
		} else {
			pasajero.estado = Estado.Rechazado;
		}
		uniwheelsPersistence.updateUser(usuarioConductor);
		uniwheelsPersistence.updateUser(usuarioPasajero);
		JSONObject jsonADevolver = new JSONObject(conductor);
		jsonADevolver.put("estadoPasajero", pasajero.estado);
		return jsonADevolver;

	}

	public Conductor estadoConductor(Estado estado, String usernameConductor) throws UniWheelsException{
		Usuario driverUser = uniwheelsPersistence.getUserByUsername(usernameConductor);
		Conductor driver = null;
		for(Conductor c: driverUser.viajesConductor){
			if(c.estado.equals(Estado.Disponible)){
				driver = c;
				break;
			}
		}
		driver.setEstado(estado);
		uniwheelsPersistence.updateUser(driverUser);
		return driver;
	}

	public Pasajero estadoPasajero(Estado estado,String usernamePasajero) throws UniWheelsException {
		Usuario usuarioPasajero = uniwheelsPersistence.getUserByUsername(usernamePasajero);
		Pasajero pasajero = null;
		for (Pasajero p : usuarioPasajero.viajesPasajero) {
			if (p.estado.equals(Estado.Disponible) || p.estado.equals(Estado.Aceptado)) {
				pasajero = p;
				break;
			}
		}
		pasajero.setEstado(estado);
		uniwheelsPersistence.updateUser(usuarioPasajero);
		return pasajero;
	}

	public void finishTravel(String driverName, List<Pasajero> passengers) throws UniWheelsException {
		estadoConductor(Estado.Finalizado,driverName);
		for(Pasajero p: passengers){
			estadoPasajero(Estado.Finalizado,p.username);
		}

	}

	public Conductor getTravelDriver(String usernameDriver) throws UniWheelsException {
		Usuario driverUser = uniwheelsPersistence.getUserByUsername(usernameDriver);
		Conductor driver = null;
		for (Conductor c: driverUser.getViajesConductor()){
			if(c.getEstado().equals(Estado.Disponible)){
				driver = c;
				break;
			}
		}

		return driver;
	}

	public Pasajero getTravelPassenger(String usernamePassenger) throws UniWheelsException {
		Usuario passengerUser = uniwheelsPersistence.getUserByUsername(usernamePassenger);
		Pasajero passenger = null;
		for(Pasajero p:passengerUser.getViajesPasajero()){
			if(p.getEstado().equals(Estado.Aceptado)){
				passenger = p;
				break;
			}
		}
		return passenger;
	}
}
