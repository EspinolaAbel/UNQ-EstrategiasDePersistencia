package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoService {


	private EntrenadorDAO entrenadorDAO ;
	private BichoDAO bichoDAO;
	
	
	
	
	public BichoService (EntrenadorDAO entrenadorDAO, BichoDAO bichoDAO ){
		this.entrenadorDAO=entrenadorDAO;
		this.bichoDAO=bichoDAO;
	}
	
	/**
	 * buscar(String entrenador):Bicho el entrenador deberá buscar un nuevo bicho en la 
	 * localización actual en la que se encuentre. Si la captura es exitosa el bicho será 
	 * agregado al inventario del entrenador (ver sección Busquedas) y devuelto por el 
	 * servicio.
	 */

	public Bicho buscar(String entrenador2) {
		// aca deberia crear una nueva busqueda  y ver la probabilidad de que se recupere un   bicho
		Bicho bichoRetornado=Runner.runInSession(() -> {
	
			Bicho bichoRecuperado=null;
			Entrenador entrenador = this.entrenadorDAO.getEntrenador(entrenador2);
			if ( (entrenador.getBichosCapturados().size()) <  entrenador.getNivelActual().getMaxCantidadDeBichos() ){
				bichoRecuperado=entrenador.getUbicacionActual().retornarUnBichoDelLugar();
				entrenador.agregarBichoCapturado(bichoRecuperado);
				entrenador.setExperiencia(entrenador.getExperiencia()+10);}
			return bichoRecuperado;
			});
		
		return bichoRetornado;
	}
	
	
	/**
	 * abandonar(String entrenador, int bicho):void el entrenador abandonará el bicho especificado en la
	 *  localización actual.Si la ubicación no es una Guarderia se arrojará UbicacionIncorrectaException.
	 * 
	 * @param entrenador
	 * @param bicho
	 */
	public void abandonar(String entrenador, int bicho) {
		
		Runner.runInSession(() -> {

			Entrenador e= this.entrenadorDAO.getEntrenador(entrenador);
			Bicho b= this.bichoDAO.getBicho(bicho)	;	
			e.getUbicacionActual().recibirBichoAbandonado(b);
			e.descartarBichoCapturado(b);

		return null;
		});
	//} catch (UbicacionIncorrectaException uvIncEx){/* por ahora no hago nada  conla excepcion */	}
	
		
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
