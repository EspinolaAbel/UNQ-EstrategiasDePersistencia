package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;


/**@author pa*/
@Entity 
public class Pueblo extends Lugar {
	
	@OneToMany (cascade= CascadeType.ALL , fetch= FetchType.LAZY)
	private List<EspecieConProbabilidad> especies;

	public Pueblo(){
		super();
	}

	
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
	public ResultadoCombate combatir(Bicho bicho) throws UbicacionIncorrectaException {
		//en el pueblo ni en la guardería se puede combatir
		throw new UbicacionIncorrectaException(super.getNombre());
	}
	
	public  void setEspecies(List<EspecieConProbabilidad> especies){
		this.especies=especies;

	}
	
	public List <EspecieConProbabilidad> getEspecies(){
		return this.especies;
	}
	/**
	 * devuelve un bicho del lugar, lo devuelve sin dueño.
	 */
	@Override
	public Bicho retornarUnBichoDelLugar(Entrenador entrenador) {
		// la lista de especies  siempre esta inicializada, 
		// si la lista de especies esta vacia devuelve null
		if (this.especies.isEmpty()){
			return null;
			}
			else{
				SorteoEspecies sorteo= new SorteoEspecies(this.especies);
				return  new Bicho( sorteo.sortearEspecie(this.especies).getEspecie() );
					}	
	}


	@Override
	public String tipoDeLugar() {
		return "Pueblo";
	}

	
	
}
