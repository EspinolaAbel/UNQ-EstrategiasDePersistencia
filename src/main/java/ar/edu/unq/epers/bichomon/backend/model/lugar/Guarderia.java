package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/**@author pa*/
public class Guarderia extends Lugar {
	
	private List<Bicho>  bichos;
	
	
	
	public Guarderia(String nombre) {
		super(nombre);
		this.bichos=  new ArrayList<Bicho>();
	
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) throws UbicacionIncorrectaException {
		this.bichos.add(bicho);

	}

	@Override
	public void combatir(Bicho bicho) throws UbicacionIncorrectaException {
		// TODO Auto-generated method stub

		throw new UbicacionIncorrectaException(super.getNombre());
	

	}

	@Override
	public Bicho retornarUnBichoDelLugar() {
		// TODO Auto-generated method stub
		return null;
	}

}
