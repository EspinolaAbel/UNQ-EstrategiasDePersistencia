package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ar.edu.unq.epers.bichomon.backend.model.Especie;



@Entity
public class EspecieConProbabilidad implements Serializable{

	
	/**
	 * 
	 */
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ;
	
	@OneToOne
	private Especie especie;
	private Integer probabilidad;
	

	public EspecieConProbabilidad(){	}
	
	public EspecieConProbabilidad(Especie especie, Integer probabilidadDeAparecer) {
		this.especie = especie;
		this.probabilidad = probabilidadDeAparecer;
	}

	
	public int getProbabilidad(){
		return this.probabilidad;
	}


	public Especie getEspecie() {

		return this.especie;
	}
}
