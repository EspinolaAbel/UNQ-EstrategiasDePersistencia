package ar.edu.unq.epers.bichomon.backend.model.lugar;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

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
