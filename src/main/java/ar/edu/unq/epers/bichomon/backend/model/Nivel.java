package ar.edu.unq.epers.bichomon.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/** Esta clase se va a encargar de representar el {@link Nivel} actual en el que se encuentra un
 * {@link Entrenador} en el juego.*/
@Entity
public class Nivel {
	
	@Id
	private Integer numeroDeNivel;
	private Integer puntosParaSubirDeNivel;
	private Integer maxCantidadDeBichos;
	
	@OneToOne
	private Nivel siguienteNivel;
	
	
	public Nivel() {}
	
	public Nivel(Integer numeroNivel, Integer puntosParaSubirDeNivel, Integer maxCantidadDeBichos) {
		this.setNumeroDeNivel(numeroNivel);
		this.setPuntosParaSubirDeNivel(puntosParaSubirDeNivel);
		this.setMaxCantidadDeBichos(maxCantidadDeBichos);
	}
	
	public Nivel(Integer numeroNivel, Integer puntosParaSubirDeNivel, Integer maxCantidadDeBichos, Nivel siguienteNivel) {
		this(numeroNivel, puntosParaSubirDeNivel, maxCantidadDeBichos);
		this.setSiguienteNivel(siguienteNivel);
	}
	
	
	/** 
	 * @return numeroDeNivel - El número de este {@link Nivel}
	 * @author ae */
	public Integer getNumeroDeNivel() {
		return numeroDeNivel;
	}
	
	/** 
	 * @param numeroDeNivel - Número de este {@link Nivel}.
	 * @author ae */
	public void setNumeroDeNivel(Integer numeroDeNivel) {
		this.numeroDeNivel = numeroDeNivel;
	}
	
	/**
	 * @return puntosParaSubirDeNivel - La cantidad de puntos necesarios para subir al próximo {@link Nivel}.
	 * @author ae */
	public Integer getPuntosParaSubirDeNivel() {
		return puntosParaSubirDeNivel;
	}
	
	/**
	 * @param puntosParaSubirDeNivel - La cantidad de puntos necesarios para subir al próximo {@link Nivel}.
	 * @author ae */
	public void setPuntosParaSubirDeNivel(Integer puntosParaSubirDeNivel) {
		this.puntosParaSubirDeNivel = puntosParaSubirDeNivel;
	}
	
	/**
	 * @return maxCantidadDeBichos - Máxima cantidad de {@link Bicho}s que el {@link Entrenador} que se
	 * 								encuentra en este {@link Nivel} puede capturar.
	 * @author ae */
	public Integer getMaxCantidadDeBichos() {
		return maxCantidadDeBichos;
	}
	
	/**
	 * @param maxCantidadDeBichos the maxCantidadDeBichos to set
	 */
	public void setMaxCantidadDeBichos(Integer maxCantidadDeBichos) {
		this.maxCantidadDeBichos = maxCantidadDeBichos;
	}
	/**
	 * @return the siguienteNivel
	 */
	public Nivel getSiguienteNivel() {
		return siguienteNivel;
	}
	/**
	 * @param siguienteNivel the siguienteNivel to set
	 */
	public void setSiguienteNivel(Nivel siguienteNivel) {
		this.siguienteNivel = siguienteNivel;
	}
	
	@Override
	public boolean equals(Object o){
		if((o.getClass() != Nivel.class) || (o == null))
			return false;
		Nivel otroNivel = (Nivel) o;
		boolean mismoNroNivel = this.getNumeroDeNivel() == otroNivel.getNumeroDeNivel();
		boolean mismaMaxCantidadDeBichos = this.getMaxCantidadDeBichos() == otroNivel.getMaxCantidadDeBichos();
		boolean mismosPuntosParaSubirDeNivel = this.getPuntosParaSubirDeNivel() == otroNivel.getPuntosParaSubirDeNivel();
		boolean mismoSiguienteNivel;
		if(this.getSiguienteNivel() == null || otroNivel.getSiguienteNivel() == null)
			mismoSiguienteNivel = this.getSiguienteNivel() == otroNivel.getSiguienteNivel();
		else
			mismoSiguienteNivel = this.getSiguienteNivel().equals(otroNivel.getSiguienteNivel());										 
		
		return mismoNroNivel && mismaMaxCantidadDeBichos && mismosPuntosParaSubirDeNivel && mismoSiguienteNivel;
	}
	
	@Override
	public int hashCode() {
		final int num = 30;
		int result = 1;
		result = num * result + (int) this.getNumeroDeNivel() + (int) this.getMaxCantidadDeBichos()
					+ (int) this.getPuntosParaSubirDeNivel() + (this.getSiguienteNivel()==null ? 0 : this.getSiguienteNivel().hashCode());
		return result;
	}
	
	
}
