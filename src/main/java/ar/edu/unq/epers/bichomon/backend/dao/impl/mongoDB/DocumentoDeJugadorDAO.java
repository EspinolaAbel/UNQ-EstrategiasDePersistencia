package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import ar.edu.unq.epers.bichomon.backend.model.Evento;
import ar.edu.unq.epers.bichomon.backend.model.DocumentoDeJugador;

public class DocumentoDeJugadorDAO extends GenericMongoDAO<DocumentoDeJugador> {

	public DocumentoDeJugadorDAO() {
		super(DocumentoDeJugador.class);
		
	}
	
/**
 * reemplaza al jugador siempre que este exista, sino exite, el metodo update no hace nada
 * @param jugador
 * @param jugadorNuevo
 */
	public void reemplazar(String jugador, DocumentoDeJugador jugadorNuevo){
		String query= "{nombre: #}";
	    this.mongoCollection.update(query, jugador).with(jugadorNuevo);
		
	}
	/**
	 * inserta 
	 */
	public void insertarEvento(String jugador, Evento evento){
		
		DocumentoDeJugador unJugador = this.buscarPorNombre(jugador);
		if (unJugador== null){
			unJugador= new DocumentoDeJugador(jugador);
			unJugador.agregarEvento(evento);
			this.save(unJugador);
			}
		else {
			unJugador.agregarEvento(evento);
			this.reemplazar(jugador, unJugador);
		}
	}
	
	
	public DocumentoDeJugador buscarPorNombre(String nombreDelJugador) {
		DocumentoDeJugador jugadorRecuperado;
		String query = "{nombre : # }";
	
		jugadorRecuperado= this.mongoCollection.findOne(query, nombreDelJugador).as(DocumentoDeJugador.class);
		return jugadorRecuperado;
	}
	
}
