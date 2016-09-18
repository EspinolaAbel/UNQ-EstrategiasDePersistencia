package ar.edu.unq.epers.bichomon.backend.model.lugar;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public abstract class Lugar {
	private String nombre;

	public  Lugar(String nombre){
		this.nombre=nombre;
		
	}
	
	public abstract void recibirBichoAbandonado(Bicho bcho) throws UvicacionIncorrectaExeption ;

	public abstract void combatir(Bicho bicho) throws UvicacionIncorrectaExeption ;

	public abstract Bicho retornarUnBichoDeLugar();
	public String  getNombre(){
		return this.nombre;
	}
	
	
		
	}

