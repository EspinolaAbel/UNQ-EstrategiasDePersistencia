package ar.edu.unq.epers.bichomon.backend.model.lugar;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/**@author pa*/
public abstract class Lugar {
	
	private String nombre;

	/**@author pa*/
	public Lugar(String nombre){
		this.setNombre(nombre);
		
	}
	
	/**@author pa*/
	public abstract void recibirBichoAbandonado(Bicho bicho) throws UbicacionIncorrectaException ;

	/**@author pa*/
	public abstract void combatir(Bicho bicho) throws UbicacionIncorrectaException ;

	/**@author pa*/
	public abstract Bicho retornarUnBichoDelLugar();
	
	/**@author pa*/
	public String  getNombre(){
		return this.nombre;
	}
	
	/**@author pa*/
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
		
}

