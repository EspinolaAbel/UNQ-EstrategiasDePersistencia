package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Combate;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;

/**
 * @author Peter */
@Entity
public class Dojo extends Lugar {
	
	@OneToOne(cascade=CascadeType.ALL)
	private Campeon campeonActual;
	
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
	public ResultadoCombate combatir(Bicho bicho) throws UbicacionIncorrectaException {
		
		Combate combate = new Combate(bicho, this.bichoCampeonActual);
		return combate.Combatir();
		
		
		
		// para hacer!!!!!
	}

	public Campeon getCampeonActual(){
		return this.campeonActual;
	}

	public void setCampeonActual(Bicho bicho){
		this.campeonActual= new Campeon(this, bicho);
	}

	//TODO
	/** Devuelve un bicho sin dueño */
	@Override
	public Bicho retornarUnBichoDelLugar() {
		Bicho bicho = this.getCampeonActual().getBichoCampeon();
		return (new Bicho(bicho.getEspecie().dameRaiz()));
	}

}
