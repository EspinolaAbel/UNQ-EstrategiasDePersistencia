package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import java.util.List;

import org.jongo.Aggregate.ResultsIterator;

import ar.edu.unq.epers.bichomon.backend.dao.DocumentoDeEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.DocumentoDeEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.eventos.Evento;

public class MongoDocumentoDeEntrenadorDAO extends GenericMongoDAO<DocumentoDeEntrenador> 
										implements DocumentoDeEntrenadorDAO  {

	public MongoDocumentoDeEntrenadorDAO() {
		super(DocumentoDeEntrenador.class);
		
	}
	
/**
 * reemplaza al jugador siempre que este exista, sino exite, el metodo update no hace nada
 * @param jugador
 * @param jugadorNuevo
 */
	public void reemplazar(String jugador, DocumentoDeEntrenador jugadorNuevo){
		String query= "{nombre: #}";
	    this.mongoCollection.update(query, jugador).with(jugadorNuevo);
		
	}
	/**
	 * inserta 
	 */
	public void insertarEvento(String jugador, Evento evento){
		String query1= "{nombre: #}";
		String query2="{$push :{eventos: #}}";
		this.mongoCollection.update(query1,jugador).upsert().with(query2,evento);
	}
	
	
	public DocumentoDeEntrenador buscarPorNombre(String nombreDelJugador) {
		DocumentoDeEntrenador jugadorRecuperado;
		String query = "{nombre : # }";
	
		jugadorRecuperado= this.mongoCollection.findOne(query, nombreDelJugador).as(DocumentoDeEntrenador.class);
		return jugadorRecuperado;
	
	}

	@Override
	public List<Evento> buscarEventosDelEntrenador(String nombreDelEntrenador) {

		ResultsIterator<Evento> result = this.mongoCollection.aggregate
				("{ $match: {nombre: #}}",nombreDelEntrenador)
				.and(" { $unwind: '$eventos' } ")
				.and( "{$project: {_class: '$eventos._class', tipo: '$eventos.tipo', uvicacion:'$eventos.uvicacion',fecha:'$eventos.fecha', _id:0}}")
				.and(" { $sort: { fecha: -1 } }")
				.as(Evento.class);
		return this.copyToList(result);

	}
	
	
	@Override
	public List<Evento> buscarEventosDelEntrenadorEnLugares(String nombreDelEntrenador, List<String> lugares) {
		

			ResultsIterator<Evento> result = this.mongoCollection.aggregate
					("{ $match: {nombre: #}}",nombreDelEntrenador)
					.and(" { $unwind: '$eventos' } ")
					.and("{ $match: {'eventos.uvicacion': { $in: # } } }", lugares)
					.and( "{$project: {_class: '$eventos._class',  tipo: '$eventos.tipo', uvicacion:'$eventos.uvicacion',fecha:'$eventos.fecha', _id:0}}")
					.and(" { $sort: { fecha: -1 } }")
					.as(Evento.class);

			return this.copyToList(result);

		}

	@Override
	public void deleteCollection() {
		super.deleteAll();
		// TODO Auto-generated method stub
		
	}

	
	
}
