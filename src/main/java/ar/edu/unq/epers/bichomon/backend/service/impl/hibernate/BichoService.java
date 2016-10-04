package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoService {


	EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
	Entrenador entrenador; 
	
	
	
	
	public Bicho buscar(String entrenador2) {
	
		/**
		 * buscar(String entrenador):Bicho el entrenador deberá buscar un nuevo bicho en la 
		 * localización actual en la que se encuentre. Si la captura es exitosa el bicho será 
		 * agregado al inventario del entrenador (ver sección Busquedas) y devuelto por el 
		 * servicio.
		 */
	
		
		// aca deberia crear una nueva busqueda  y ver la probabilidad de que se recupere un   bicho
		Bicho bichoRetornado=Runner.runInSession(() -> {
	
			Bicho bichoRecuperado=null;
			Entrenador entrenador = this.entrenadorDAO.getEntrenador(entrenador2);
			if ( (entrenador.getBichosCapturados().size()-1) <  entrenador.getNivelActual().getMaxCantidadDeBichos() )
				bichoRecuperado=entrenador.getUbicacionActual().retornarUnBichoDelLugar();
				entrenador.agregarBichoCapturado(bichoRecuperado);
				entrenador.setExperiencia(entrenador.getExperiencia()+10);
			return bichoRecuperado;
			});
		
		return bichoRetornado;
	}
	
	
	
	public void abandonar(String entrenador, int bicho) {
		//TODO
	}
	
	public ResultadoCombate duelo(String entrenador, int bicho) {
		//TODO
		return null;
	}
	
	public boolean puedeEvolucionar(String entrenador, int bicho) {
		//TODO
		return null != null;
	}
	
	public Bicho evolucionar(String entrenador, int bicho) {
		//TODO
		return null;
	}
}
