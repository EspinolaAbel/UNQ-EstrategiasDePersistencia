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
	
//
//	@OneToOne(cascade =CascadeType.ALL)
//	private Bicho bichoCampeonActual;
//=======

	//esto me rompe algo
	
	@OneToOne(cascade=CascadeType.ALL)
	private CampeonHistorico campeonActual;
//>>>>>>> afe453ef5a6aa658c0fef8827fe8f6e1260f05b2
	
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
		
		Combate combate = new Combate(bicho, this.getCampeonActual().getBichoCampeon());
		return combate.Combatir();
		
		
		
		// para hacer!!!!!
	}

	public CampeonHistorico getCampeonActual(){
		return this.campeonActual;
	}

	public void setCampeonActual(Bicho bicho){
		this.campeonActual= new CampeonHistorico(this, bicho);
	}

	//TODO
	/** Devuelve un bicho sin dueño */
	@Override
	public Bicho retornarUnBichoDelLugar() {
		Bicho bicho = this.getCampeonActual().getBichoCampeon();
		return (new Bicho(bicho.getEspecie().dameRaiz()));
	}

}
