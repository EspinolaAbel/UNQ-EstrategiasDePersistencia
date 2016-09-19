package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**@author pa*/
public class Pueblo extends Lugar {
	private List<Especie> especies;

	public Pueblo(String nombre) {
		super(nombre);
		this.especies = new ArrayList<Especie>();
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) throws UbicacionIncorrectaException {
		//en el pueblo ni en el dojo se puede abandonar bichos
		throw new UbicacionIncorrectaException(super.getNombre());
			

		}
	

	@Override
	public void combatir(Bicho bicho) throws UbicacionIncorrectaException {
		//en el pueblo ni en la guardería se puede combatir
		throw new UbicacionIncorrectaException(super.getNombre());

	}
	
	public  void setEspecies(List<Especie> especies){
		this.especies=especies;
		
	}
	
	public List <Especie> getEspecies(){
		
		return this.especies;
		
		
	}

	@Override
	public Bicho retornarUnBichoDelLugar() {
		// TODO Auto-generated method stub
		return null;
	}
}
