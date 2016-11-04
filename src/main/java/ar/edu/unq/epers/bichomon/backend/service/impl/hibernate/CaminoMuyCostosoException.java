package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

public class CaminoMuyCostosoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CaminoMuyCostosoException(Entrenador entrenador, Lugar lugar, Integer costoDelViaje) {
		super("El entrenador con nombre \""+entrenador.getNombre()+"\" no puede viajar "
				+ "hacia el lugar con nombre \""+lugar.getNombre()+"\" debido a que no tiene "
				+ costoDelViaje+" monedas para pagar el viaje.");
	}

}
