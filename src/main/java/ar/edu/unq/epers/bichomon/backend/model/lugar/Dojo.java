package ar.edu.unq.epers.bichomon.backend.model.lugar;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**
 * 
 * 
 * 
 * @author Peter
 *
 */
public class Dojo extends Lugar {
	private Bicho campeon;
	public Dojo(String nombre) {
		super(nombre);
		
		
		
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) throws UvicacionIncorrectaExeption {
		
			throw new UvicacionIncorrectaExeption(super.getNombre());
		

	}

	@Override
	public void combatir(Bicho bicho) throws UvicacionIncorrectaExeption {
		// para hacer!!!!!
		
	}

	public Bicho getCampeon(){
		return this.campeon;
		
	}

	public void setCampeon(Bicho bicho){
		this.campeon=bicho;
		
	}

	@Override
	public Bicho retornarUnBichoDeLugar() {
		// Devuelve un bicho sin dueño
		return (new Bicho(this.campeon.getEspecie().dameRaiz()));
	
	
	}


}
