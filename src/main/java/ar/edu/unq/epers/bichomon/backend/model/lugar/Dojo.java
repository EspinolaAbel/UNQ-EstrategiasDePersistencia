package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/**
 * 
 * 
 * 
 * @author Peter
 *
 */
@Entity
public class Dojo extends Lugar {
	
	@Transient
	private Bicho campeon;
	
	public Dojo(){
		super();
	}
	
	public Dojo(String nombre) {
		super(nombre);
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) throws UbicacionIncorrectaException {
		
			throw new UbicacionIncorrectaException(super.getNombre());
		

	}

	@Override
	public void combatir(Bicho bicho) throws UbicacionIncorrectaException {
		// para hacer!!!!!
		
	}

	public Bicho getCampeon(){
		return this.campeon;
		
	}

	public void setCampeon(Bicho bicho){
		this.campeon=bicho;
		
	}

	@Override
	public Bicho retornarUnBichoDelLugar() {
		// Devuelve un bicho sin dueño
		return (new Bicho(this.campeon.getEspecie().dameRaiz()));
	
	
	}


}
