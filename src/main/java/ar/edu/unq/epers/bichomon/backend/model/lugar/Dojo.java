package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Combate;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;

/**
 * @author Peter */
@Entity
public class Dojo extends Lugar {
	
	@OneToOne(cascade=CascadeType.ALL)
	private CampeonHistorico campeonActual;
	
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
		return combate.combatir();
	}

	public CampeonHistorico getCampeonActual(){
		return this.campeonActual;
	}

	public void setCampeonActual(Bicho bicho){
		this.campeonActual= new CampeonHistorico(this, bicho);
	}

	public boolean hayCampeon(){
		return this.campeonActual!=null;
	}
	
	/** Devuelve un bicho sin dueño */
	@Override
	public Bicho retornarUnBichoDelLugar(Entrenador entrenador) {
		
		if (hayCampeon()){
			Bicho bichoCampeon = this.getCampeonActual().getBichoCampeon();
			return (new Bicho(bichoCampeon.getEspecie().dameRaiz()));
			}else return null;
	}
}
