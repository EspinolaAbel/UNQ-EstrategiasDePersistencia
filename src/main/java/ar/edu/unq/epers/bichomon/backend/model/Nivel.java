package ar.edu.unq.epers.bichomon.backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/** Esta clase se va a encargar de representar el {@link Nivel} actual en el que se encuentra un
 * {@link Entrenador} en el juego.*/
@Entity(name="Niveles")
public class Nivel {
	
	@Id
	private Integer numeroDeNivel;
	private Integer puntosParaSubirDeNivel;
	private Integer maxCantidadDeBichos;
	
	@OneToOne(cascade=CascadeType.ALL ,fetch=FetchType.EAGER)
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
	
	

	public Integer getNumeroDeNivel() {
		return numeroDeNivel;
	}
	
	public void setNumeroDeNivel(Integer numeroDeNivel) {
		this.numeroDeNivel = numeroDeNivel;
	}
	

	public Integer getPuntosParaSubirDeNivel() {
		return puntosParaSubirDeNivel;
	}
	
	public void setPuntosParaSubirDeNivel(Integer puntosParaSubirDeNivel) {
		this.puntosParaSubirDeNivel = puntosParaSubirDeNivel;
	}
	

	public Integer getMaxCantidadDeBichos() {
		return maxCantidadDeBichos;
	}
	
	public void setMaxCantidadDeBichos(Integer maxCantidadDeBichos) {
		this.maxCantidadDeBichos = maxCantidadDeBichos;
	}


	public Nivel getSiguienteNivel() {
		return siguienteNivel;
	}

	public void setSiguienteNivel(Nivel siguienteNivel) {
		this.siguienteNivel = siguienteNivel;
	}
	
	
	/**Prec.: Para que un Nivel sea comparable es necesario que todos sus campos estén declarados,
	 * 		que sean distintos de null. Caso contrario este método fallará al ejecutarse.*/
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o instanceof Nivel) {
			Nivel otroNivel = (Nivel) o;
			boolean mismoNroNivel = this.getNumeroDeNivel().equals( otroNivel.getNumeroDeNivel() );
		
			return mismoNroNivel;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int num = 30;
		int result = 1;
		result = num * result + (int) this.getNumeroDeNivel();
		return result;
	}
	
	
}
