package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class Guarderia extends Lugar {
	
	private List<Bicho>  bichos;
	
	
	
	public Guarderia(String nombre) {
		super(nombre);
		// TODO Auto-generated constructor stub
		this.bichos=  new ArrayList<Bicho>();
	
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) throws UvicacionIncorrectaExeption {
		this.bichos.add(bicho);

	}

	@Override
	public void combatir(Bicho bicho) throws UvicacionIncorrectaExeption {
		// TODO Auto-generated method stub

		throw new UvicacionIncorrectaExeption(super.getNombre());
	

	}

	@Override
	public Bicho retornarUnBichoDeLugar() {
		// TODO Auto-generated method stub
		return null;
	}

}
