package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;

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

	public abstract ResultadoCombate combatir(Bicho bicho) throws UbicacionIncorrectaException ;

	public abstract Bicho retornarUnBichoDelLugar(Entrenador entrenador);
	
	public String  getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	/**
	 * devuelve el tipo del lugar en  formato string
	 * @return
	 */
	public abstract String tipoDeLugar();
		
//	REDEFINICIÓN EQUALS Y HASHCODE:
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o instanceof Lugar) {
			Lugar otroLugar = (Lugar) o;
			boolean idemNombre= this.getNombre().equals(otroLugar.getNombre());
			
			return 	idemNombre;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int num = 95;
		int result = 291;
		result =	num * result + this.getNombre().hashCode();
		
		return result;
	}
}

