package ar.edu.unq.epers.bichomon.backend.model.lugar;

import ar.edu.unq.epers.bichomon.backend.model.Especie;

public class EspecieConProbabilidad {

	private Especie especie;
	private Integer probabilidad;
	

	public EspecieConProbabilidad(Especie especie, Integer probabilidadDeAparecer) {
		this.especie = especie;
		this.probabilidad = probabilidadDeAparecer;
	}

	
	public int getProbabilidad(){
		return this.probabilidad;
	}
}
