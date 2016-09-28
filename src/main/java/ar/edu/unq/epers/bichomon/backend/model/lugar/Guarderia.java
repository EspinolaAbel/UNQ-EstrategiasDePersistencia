package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/**@author pa*/
@Entity
public class Guarderia extends Lugar {
	
	@Transient
	private List<Bicho>  bichosAbandonados;
		
	public Guarderia(String nombre) {
		super(nombre);
		this.bichosAbandonados =  new ArrayList<Bicho>();
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) {
		this.bichosAbandonados.add(bicho);
	}

	@Override
	public void combatir(Bicho bicho) throws UbicacionIncorrectaException {
		throw new UbicacionIncorrectaException(super.getNombre());
	}

	/** Se retorna un {@link Bicho} al azar de la lista de bichos abandonados en este lugar.
	 * Luego de ser obtenido el bicho a ser retornado se lo elimina de la lista de bichos abandonados
	 * de esta guardería.
	 * @return {@link Bicho} que fue abandonado en la guardería.
	 * @author ae */
	@Override
	public Bicho retornarUnBichoDelLugar() {
		// este número va del 0 a (this.getBichosAbandonados.size() - 1)
		Integer posicionAlAzarDeLaListaDeBichos = (int) (Math.random() * this.getBichosAbandonados().size());
	
		Bicho bichoRecuperado = this.getBichosAbandonados().get(posicionAlAzarDeLaListaDeBichos);
		this.getBichosAbandonados().remove(bichoRecuperado);
		
		return bichoRecuperado;
	}
	
	public List<Bicho> getBichosAbandonados() {
		return this.bichosAbandonados;
	}

}
