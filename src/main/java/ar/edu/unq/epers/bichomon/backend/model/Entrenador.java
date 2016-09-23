package ar.edu.unq.epers.bichomon.backend.model;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

public class Entrenador {
	
	
	private String  nombre;
	private int experiencia;
	private Lugar ubicacion;
	private Nivel nivelActual;
	private List<Bicho> bichos;


	/**@return El nombre de el {@link Entrenador}.
	 * @author ae */
	public String getNombre() {
		return nombre;
	}


	/**@param nombre - En nombre del {@link Entrenador}.
	 * @author ae */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**@return El valor numérico de experiencia del {@link Entrenador}.
	 * @author ae */
	public int getExperiencia() {
		return experiencia;
	}


	/**@param experiencia - Valor numérico de la nueva experiencia de el {@link Entrenador}.
	 * @author ae */
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}


	/**@return {@link Lugar} donde se encuentra el {@link Entrenador}.
	 * @author ae */
	public Lugar getUbicacionActual() {
		return ubicacion;
	}


	/**@param ubicacion - El nuevo {@link Lugar} donde se ubicará el {@link Entrenador}.
	 * @author ae */
	public void setUbicacionActual(Lugar ubicacion) {
		this.ubicacion = ubicacion;
	}

	
	/**@return El {@link Nivel} actual en el que se encuentra el {@link Entrenador}.
	 * @author ae */
	public Nivel getNivelActual() {
		return this.nivelActual;
	}
	
	
	/**@param nivelActual - El nuevo {@link Nivel} al que subirá el {@link Entrenador}.
	 * @author ae */
	public void setNivelActual(Nivel nivelActual) {
		this.nivelActual = nivelActual;
	} 
	
	
    public  void capturarBicho(Bicho bicho){
    	this.bichos.add(bicho);
    	
    }
    
	/**@param bicho - El {@link int }establece que bicchos de la coleccion sera eliminado
	 * @author ae */
	
    public void descartarBicho(int bicho){
		this.bichos.remove(bicho);
    }
	
}
