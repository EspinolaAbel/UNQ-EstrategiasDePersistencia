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

	public Lugar(String nombre){
		this.setNombre(nombre);
	}
	
	public Lugar() {
		super();
	}

	public abstract void recibirBichoAbandonado(Bicho bicho) throws UbicacionIncorrectaException ;

	public abstract void combatir(Bicho bicho) throws UbicacionIncorrectaException ;

	public abstract Bicho retornarUnBichoDelLugar();
	
	public String  getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
		
}

