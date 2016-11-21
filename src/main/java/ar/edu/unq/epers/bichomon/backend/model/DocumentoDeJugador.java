package ar.edu.unq.epers.bichomon.backend.model;

import java.util.ArrayList;
import java.util.List;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class DocumentoDeJugador {
	
	@MongoId
	@MongoObjectId
	private String _id;
	
	private String nombre;
	private List <Evento> eventos= new ArrayList<Evento>();

	public DocumentoDeJugador() {
	}
	
	public DocumentoDeJugador(String nombreDeJugador){
		this.nombre=nombreDeJugador;
		
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Evento> getEvetos() {
		return eventos;
	}

	public void setEvetos(List<Evento> evetos) {
		this.eventos = evetos;
	}
	
	public void agregarEvento(Evento evento){
		
		this.eventos.add(evento);
		
	}

}
