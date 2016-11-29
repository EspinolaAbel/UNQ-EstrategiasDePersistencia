package ar.edu.unq.epers.bichomon.backend.service.impl;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.DocumentoDeEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Evento;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class FeedService {

	private DocumentoDeEntrenadorDAO documentoDAO;
	//mapa dao hibernate dao tambien los voy a necesitar
	private MapaDAO mapaDAO;
	private EntrenadorDAO entrenadorDAO;
	
	
	public FeedService(DocumentoDeEntrenadorDAO documentoDAO, MapaDAO mapaDAO, EntrenadorDAO entrenadorDAO) {
		super();
		this.entrenadorDAO=entrenadorDAO;
		this.mapaDAO=mapaDAO;
		this.documentoDAO = documentoDAO;
	}




	/**
	 * El mensaje feedEntrenador(String entrenador) que devolverá la lista de eventos que involucren
	 *  al entrenador provisto. Esa lista incluirá eventos relacionados a todos los viajes que haya hecho 
	 *  el entrenador (arribos), a todas las capturas, a todos los bichos que haya abandonado y a todas 
	 *  las coronaciones en las que haya sido coronado o destronado. 
	 * Dicha lista debe contener primero a los eventos más recientes.
	 */

	public List<Evento> feedEntrenador(String entrenador){
	
		return	this.documentoDAO.buscarEventosDelEntrenador(entrenador);
		
	}
	
/**
 * El mensaje feedUbicacion(String entrenador) que devolverá el feed de eventos principal 
 * que debe mostrarse al usuario. El mismo deberá incluír todas los eventos de su ubicación 
 * actual y todos los eventos de las ubicaciones que estén conectadas con la misma.
 *  Dicha lista debe contener primero a los eventos más recientes.
 */
	
	public List<Evento> feedUbicacion(String nombreDelEntrenador/*, List<String> lugares*/){
		
		String uvicacionActual =	Runner.runInSession(() -> {
						Entrenador e= this.entrenadorDAO.getEntrenador(nombreDelEntrenador);
						return e.getUbicacionActual().getNombre();		
					});
		
		List<String> lugaresDeBusqueda =RunnerNeo4J.runInSession(()->{
				List<String> lugares = this.mapaDAO.lugaresAdyacentesDeCualquierTipo(uvicacionActual);
				return lugares;
			});
		lugaresDeBusqueda.add(uvicacionActual);
		return this.documentoDAO.buscarEventosDelEntrenadorEnLugares(nombreDelEntrenador,lugaresDeBusqueda);
		
	}
	
	

}
