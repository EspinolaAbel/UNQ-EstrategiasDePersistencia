package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/**@author pa*/
@Entity(name = "Lugares")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Lugar {
	
	@Id
	private String nombre;

	/**@author pa*/
	public Lugar(String nombre){
		this.setNombre(nombre);
		
	}
	
	public Lugar() {
		super();
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

