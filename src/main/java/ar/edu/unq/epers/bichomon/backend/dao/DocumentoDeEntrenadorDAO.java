package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.DocumentoDeEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.Evento;

public interface DocumentoDeEntrenadorDAO {

	public List<Evento> buscarEventosDelEntrenador (String nombreDelEntrenador);
	
	public List<Evento> buscarEventosDelEntrenadorEnLugares(String nombreDelEntrenador, List<String> lugares );

	public void insertarEvento(String nombreDelEntrenador, Evento evento);

	public void save(DocumentoDeEntrenador documento);

	public void deleteCollection();
}
