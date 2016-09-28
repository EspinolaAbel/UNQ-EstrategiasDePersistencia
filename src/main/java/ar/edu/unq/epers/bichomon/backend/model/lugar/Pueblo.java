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
	private List<EspecieConProbabilidad> especies;
	
		
	public Pueblo(String nombre) {
		super(nombre);
		this.especies = new ArrayList<EspecieConProbabilidad>();
	}
	
	public Pueblo(String nombre, List<EspecieConProbabilidad> especies) {
		super(nombre);
		this.especies = especies;
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
	
	public  void setEspecies(List<EspecieConProbabilidad> especies){
		this.especies=especies;

	}
	
	public List <EspecieConProbabilidad> getEspecies(){
		return this.especies;
	}

	@Override
	public Bicho retornarUnBichoDelLugar() {
		SorteoEspecies sorteo= new SorteoEspecies(this.especies);
		return  new Bicho( sorteo.sortearEspecie(this.especies).getEspecie() );
	}

	
	
}
