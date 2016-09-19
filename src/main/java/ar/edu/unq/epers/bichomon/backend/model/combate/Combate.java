package ar.edu.unq.epers.bichomon.backend.model.combate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

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
			this.ataques.add(  atacar (retador, campeon) );
			
			if (!(combTerminado=sinEnergia(campeon))){
				this.ataques.add(  atacar (campeon, retador) );
				combTerminado=sinEnergia(retador);
			}
			numAtaques++;
		}
		return new ResultadoCombate(getGanador(), getPrededor(), this.ataques);
	}

	
	private Bicho getPrededor() {
		//el campeon pierde solo si se quedo sin energia 
		if (sinEnergia(this.campeon))
			return this.campeon 	;
		
		return this.retador;
	}
	private Bicho getGanador(){		//el campeon pierde solo si se quedo sin energia
		if (sinEnergia(this.campeon))
			return 	this.retador;
		return this.campeon;
		}

	private Double  atacar(Bicho atacante,  Bicho atacado ){
		// un bicho atacante , ataca al bicho atacado  produciendole un daño determinado
		Double daño= atacante.getEnergia()* (Math.random()*0.5+ 0.5);//Random entre 0,5 y 1
		
		if (atacado.getEnergia()< daño)
			atacado.setEnergia(0);
			else
				atacado.setEnergia( atacado.getEnergia()-daño.intValue() );
		return daño;
		}
		
	private boolean sinEnergia(Bicho bicho) {
		//el combate esta termnado si alguno de los dos esta sin energia
		return (bicho.getEnergia()==0);
		}

}
	
	

