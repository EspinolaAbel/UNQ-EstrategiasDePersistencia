package ar.edu.unq.epers.bichomon.backend.model;

import java.util.List;

public class ResultadoCombate {
	
	private Bicho ganador;
	private Bicho perdedor;
	@SuppressWarnings("unused")
	private List <Double> ataquesRealizados;

	
	public ResultadoCombate(Bicho ganador, Bicho perdedor, List<Double> ataques){
		this.ganador = ganador;
		this.perdedor= perdedor;
		this.ataquesRealizados= ataques;
	}
		
	public  Bicho getGanador (){
		return this.ganador;
	}

	public  Bicho getPerdedor (){
		return this.perdedor;
	}
	
}
