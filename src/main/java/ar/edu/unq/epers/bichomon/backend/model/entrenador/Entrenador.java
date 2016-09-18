package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

public class Entrenador {
	
	
	private String  nombre;
	private int experiencia;
	private Lugar ubicacion;
	private Nivel nivelActual;
	
	
	/**@return El {@link Nivel} actual en el que se encuentra el {@link Entrenador}.
	 * @author ae */
	public Nivel getNivelActual() {
		return this.nivelActual;
	} 
}
