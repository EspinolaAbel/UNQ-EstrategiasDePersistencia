package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

public class Pueblo extends Lugar {
	private List<Especie> especies;

	public Pueblo(String nombre) {
		super(nombre);
		// TODO Auto-generated constructor stub
		this.especies=null;
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) throws UvicacionIncorrectaExeption {
		//en el pueblo no se puede abandonar bichos
		throw new UvicacionIncorrectaExeption(super.getNombre());
			

		}
	

	@Override
	public void combatir(Bicho bicho) throws UvicacionIncorrectaExeption {
		//en el pueblo no se puede combatir
		throw new UvicacionIncorrectaExeption(super.getNombre());

	}
	
	public  void setEspecies(List<Especie> especies){
		this.especies=especies;
		
	}
	
	public List <Especie> getEspecies(){
		
		return this.especies;
		
		
	}

	@Override
	public Bicho retornarUnBichoDeLugar() {
		// TODO Auto-generated method stub
		return null;
	}
}
