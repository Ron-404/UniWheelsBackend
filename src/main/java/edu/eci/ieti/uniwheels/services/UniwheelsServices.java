package edu.eci.ieti.uniwheels.services;

import edu.eci.ieti.uniwheels.model.*;
import edu.eci.ieti.uniwheels.persistence.UniWheelsException;
import edu.eci.ieti.uniwheels.persistence.UniwheelsPersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

	public void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception {
		uniwheelsPersistence.addCalificacion(idConductor, idPasajero, calificacion);
	}

	public void updateCarro(Carro carro, Usuario usuario) throws Exception {
		uniwheelsPersistence.updateCarro(carro, usuario);
	}

	public String getUserStatus(String username) throws UniWheelsException {
		Usuario user = uniwheelsPersistence.getUserByUsername(username);
		String status = "Ninguno";
		if (user.viajesPasajero.size() > 0 || user.viajesConductor.size() > 0) {
			if (!user.viajesPasajero.get(user.viajesPasajero.size() - 1).estado.equals("Finalizado")) {
				status = "Pasajero";
			} else if (!user.viajesConductor.get(user.viajesConductor.size() - 1).estado.equals("Finalizado")) {
				status = "Conductor";
			}
		}
		return status;
	}

	public float getAverage(String username, String type) throws UniWheelsException {
		Usuario usuario = uniwheelsPersistence.getUserByUsername(username);
		float valueToReturn = 0;
		int totalScore = 0;
		if (type.equals("Conductor") && usuario.viajesConductor.size() > 0) {
			for (Conductor c : usuario.viajesConductor) {
				if (c.estado.equals("Finalizado")) {
					valueToReturn += c.calificacion.valor;
					totalScore += 1;
				}
			}
		} else {
			for (Pasajero p : usuario.viajesPasajero) {
				if (p.estado.equals("Finalizado")) {
					valueToReturn += p.calificacion.valor;
					totalScore += 1;
				}
			}
		}
		valueToReturn = valueToReturn / totalScore;
		return valueToReturn;
	}

	public void updateUserBasicInfo(Usuario user) throws UniWheelsException {
		// if password is received, re-encrypt it and change it
		if (user.password != null) {
			String pwd = user.password;
			String encrypt = bCryptPasswordEncoder.encode(pwd);
			user.setPassword(encrypt);
		}
		;
		Usuario oldUserWithBasicChanges = getUserByUsername(user.username);
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
		Usuario pasajero = getUserByUsername(infoPasajero.getString("username"));
		Usuario conductor = getUserByUsername(usernameConductor);
		Conductor viajeConductor = null;
		for (int i = 0; i < conductor.viajesConductor.size(); i++) {
			if (conductor.viajesConductor.get(i).estado.equals("Disponible")) {
				viajeConductor = conductor.viajesConductor.get(i);
				break;
			}
		}

		Pasajero viajePasajero = new Pasajero();
		viajePasajero.estado = "Disponible";
		viajePasajero.username = pasajero.username;
		viajePasajero.direccionRecogida = infoPasajero.getString("direccion");
		viajeConductor.posiblesPasajeros.add(viajePasajero);
		pasajero.viajesPasajero.add(viajePasajero);
		uniwheelsPersistence.updateUser(pasajero);
		uniwheelsPersistence.updateUser(conductor);
		return viajeConductor.posiblesPasajeros;

	}

	public List<Conductor> getConductoresDisponibles(JSONObject jsonObject, String conducNombre)
			throws UniWheelsException {
		Usuario usuario = getUserByUsername(conducNombre);
		Conductor conductor = new Conductor();

		conductor.setEstado("Disponible");
		conductor.setPrecio(jsonObject.getString("precio"));
		conductor.setDireccionInicio(jsonObject.getString("origen"));
		conductor.setDireccionFin(jsonObject.getString("destino"));

		List<Carro> carros = usuario.getCarros();
		for (Carro c : carros) {
			if (c.getPlaca().equals(jsonObject.getString("carro"))) {
				conductor.setCarro(c);
				break;
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
			if (p.estado.equals("Disponible")) {
				pasajero = p;
				break;
			}
		}
		Conductor conductor = null;
		for (Conductor c : usuarioConductor.viajesConductor) {
			if (c.estado.equals("Disponible")) {
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
			pasajero.estado = "Aceptado";
			for (Conductor c : uniwheelsPersistence.getConductoresDisponibles()) {
				for (int i = 0; i < c.posiblesPasajeros.size(); i++) {
					if (c.posiblesPasajeros.get(i).username.equals(usernamePasajero)) {
						c.posiblesPasajeros.remove(i);
						break;
					}
				}
			}
		} else {
			pasajero.estado = "Rechazado";
		}
		uniwheelsPersistence.updateUser(usuarioConductor);
		uniwheelsPersistence.updateUser(usuarioPasajero);
		JSONObject jsonADevolver = new JSONObject(conductor);
		jsonADevolver.put("estado", pasajero.estado);
		return jsonADevolver;

	}
}
