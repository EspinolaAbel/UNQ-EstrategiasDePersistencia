package ar.edu.unq.epers.bichomon.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * clase  que representa un combate entre dos bichos
 * 
 * @author Pedro Araoz
 * 
 *
 */


public class Combate {

	private Bicho retador;
	private Bicho campeon;
	private LocalDateTime fecha;
	private ArrayList<Double> ataques;
	
	private Double dañoAcumuladoRetador=0.0;
	private Double dañoAcumuladoCampeon=0.0;
	
	public Combate(Bicho retador, Bicho campeon){
		
		this.retador=retador;
		this.campeon=campeon;
		this.fecha= LocalDateTime.now();
		this.ataques= new  ArrayList<Double>();
	}
	
	
	
	
	public ResultadoCombate Combatir(){
	
		int  numAtaques=0;
		boolean combTerminado=false;
		
		while (numAtaques<10 && !combTerminado ){
			Double daño=atacar(retador);
			this.dañoAcumuladoCampeon+=daño;
			this.ataques.add( daño );
			combTerminado=sinEnergia(campeon, this.dañoAcumuladoCampeon);
			if (!combTerminado){
				daño=atacar (campeon);
				this.dañoAcumuladoRetador+=daño;
				this.ataques.add( daño );
				combTerminado=sinEnergia(retador,dañoAcumuladoRetador);
			}
			numAtaques++;
		}
		return new ResultadoCombate(getGanador(), getPerdedor(), this.ataques);
	}

//combate dummy  para simular ataques de magnitud fija igual a la 
//un punto de energia  del bicho, ya que los ataques reales  son de magnitud aleatoria
	public ResultadoCombate CombatirDummy(){
	
		int  numAtaques=0;
		boolean combTerminado=false;
		
		while (numAtaques<10 && !combTerminado ){
			Double daño=atacarDummy (retador);
			this.dañoAcumuladoCampeon+=daño;
			this.ataques.add( daño );
			combTerminado=sinEnergia(campeon, this.dañoAcumuladoCampeon);
			if (!combTerminado){
				daño=atacarDummy (retador);
				this.ataques.add( daño );
				combTerminado=sinEnergia(retador,this.dañoAcumuladoRetador);
			}
			numAtaques++;
		}
		return new ResultadoCombate(getGanador(), getPerdedor(), this.ataques);
	}

	
	
	private Double atacarDummy(Bicho bicho) {
	// atacar dummy devuelve un punto de daño
	return 1.0;
}




	public Bicho getPerdedor() {
		//el campeon pierde solo si se quedo sin energia 
		if (sinEnergia(this.campeon,this.dañoAcumuladoCampeon))
			return this.campeon 	;
		
		return this.retador;
	}
	public Bicho getGanador(){		//el campeon pierde solo si se quedo sin energia
		if (sinEnergia(this.campeon,this.dañoAcumuladoCampeon))
			return 	this.retador;
		return this.campeon;
		}
	public  boolean sinEnergia(Bicho bicho, Double dañoAcumulado) {
		//el combate esta termnado si alguno de los dos esta sin energia
		return (bicho.getEnergia()<=dañoAcumulado);
		}


	private Double  atacar(Bicho atacante ){
		// un bicho atacante , ataca al bicho atacado  produciendole un daño determinado
		return  atacante.getEnergia()* (Math.random()*0.4+ 0.1);//Random entre 0,1 y 0,4
		}
		
	public void setDañoAcumuladoRetador(Double daño){
		this.dañoAcumuladoRetador=daño;
		}
	public void setDañoAcumuladoCampeon(Double daño){
			this.dañoAcumuladoCampeon=daño;
		}
	public Double getDañoAcumuladoRetador(){
		return this.dañoAcumuladoRetador;
		}
	public Double getDañoAcumuladoCampeon(){
			return this.dañoAcumuladoCampeon;
		}




	public List<Double> getAtaques() {
		// TODO Auto-generated method stub
		return this.ataques;
	}
	
}
	
	

