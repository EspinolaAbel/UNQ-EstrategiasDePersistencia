package ar.edu.unq.epers.bichomon.backend.model.combate;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class ResultadoCombate {
	
	private Bicho ganador;
	private Bicho perdedor;
	private List <Double> ataquesRealizados;

	
	public ResultadoCombate(Bicho ganador, Bicho perdedor, List ataques){
		this.ganador = ganador;
		this.perdedor= perdedor;
		this.ataquesRealizados= ataques;
		
	}
	
}
