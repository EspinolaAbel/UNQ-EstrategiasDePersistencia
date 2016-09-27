package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;

/**
 * @author Pedro
 * 
 * */
public class Pueblo extends Lugar {
	private List<Especie> especies;
	private SorteoEspecies sorteoEspecie;
	
	public Pueblo(String nombre) {
		super(nombre);
		this.especies = new ArrayList<Especie>();
		this.sorteoEspecie= new SorteoEspecies(this.especies.size());
	}
	
	public Pueblo(String nombre, List<Especie> especies) {
		super(nombre);
		this.especies = especies;
		this.sorteoEspecie= new SorteoEspecies(this.especies.size());
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
		
	/**
	 * cuando se recibe una lista de especies se deberia reasignar las probabilidades
	 * 
	 */
		this.sorteoEspecie.reasignar(especies.size());
	}
	
	public List <Especie> getEspecies(){
		return this.especies;
	}

	@Override
	public Bicho retornarUnBichoDelLugar() {
		
		return  new Bicho(this.sorteoEspecie.sortearEspecie(especies));
	}

	
	
}
