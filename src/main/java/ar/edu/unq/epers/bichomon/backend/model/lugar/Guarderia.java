package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;

/**@author pa*/
@Entity
public class Guarderia extends Lugar {
	
	@OneToMany //(cascade=CascadeType.MERGE)
	private List<Bicho>  bichosAbandonados;
		
	public Guarderia() {
		super();
	}
	
	public Guarderia(String nombre) {
		super(nombre);
		this.bichosAbandonados =  new ArrayList<Bicho>();
	}

	@Override
	public void recibirBichoAbandonado(Bicho bicho) {
		this.bichosAbandonados.add(bicho);
	}

	@Override
	public ResultadoCombate combatir(Bicho bicho) throws UbicacionIncorrectaException {
		throw new UbicacionIncorrectaException(super.getNombre());
		
	}

	/** Se retorna un {@link Bicho} al azar de la lista de bichos abandonados en este lugar.
	 * Luego de ser obtenido el bicho a ser retornado se lo elimina de la lista de bichos abandonados
	 * de esta guardería.
	 * @return {@link Bicho} que fue abandonado en la guardería. */
	@Override
	public Bicho retornarUnBichoDelLugar(Entrenador entrenador) {
		
		Bicho bichoRecuperado= null;
		List <Bicho> bichosFiltrados = descartarExBichosDelEntrenador(entrenador);

		if (!bichosFiltrados.isEmpty()){
			// este número va del 0 a (this.getBichosAbandonados.size() - 1)
			Integer posicionAlAzarDeLaListaDeBichos = (int) (Math.random() * bichosFiltrados.size());
			bichoRecuperado = bichosFiltrados.get(posicionAlAzarDeLaListaDeBichos);
			this.getBichosAbandonados().remove(bichoRecuperado);
			}
		return bichoRecuperado;
	}
	
	public List<Bicho> getBichosAbandonados() {
		return this.bichosAbandonados;
	}
	
	
	/**
	 * este metodo filtra los bichos de la lista de bichos abandonados cuyo ultimo duenño  es el pasado 
	 * por parametro. como resultado se devuelve una lista de bichos los cuales  el entrenador puede capturar
	 * @param entrenadorAFiltrar
	 * @return
	 */
	 public List<Bicho> descartarExBichosDelEntrenador(Entrenador entrenadorAFiltrar ){
		 
		 List <Bicho> listaDeBichosFiltrados= new ArrayList <Bicho>();
		 for (Bicho b : this.bichosAbandonados){
			 if (b.getIdUltimoDueño()!=entrenadorAFiltrar.getNombre()){
				 listaDeBichosFiltrados.add(b);
			 	}
		 	}
		 return listaDeBichosFiltrados;
	 }

	@Override
	public String tipoDeLugar() {
		// TODO Auto-generated method stub
		return "Guarderia";
	}

}
