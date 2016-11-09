package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

import org.neo4j.driver.v1.StatementResult;

import ar.edu.unq.epers.bichomon.backend.model.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

public interface LugarDAONeo4j {

	public void saveLugar(Lugar lugar);
	public String recuperarLugar(Lugar lugarARecuperar);
	public void crearConeccion(String ubicacion1, String ubicacion2, String tipoCamino);
	public void crearConeccion(String ubicacion1, String ubicacion2, TipoDeCamino tipoCamino);
	public List<String> recuperarRelacion(String lugar1, String lugar2);
	public List<String> conectados(String lugar, String string);
	public void eliminarNodo(String identificador);
	public void eliminarConecciones(String nombreDelLugar);
	public void eliminarLugar(String nombreDelLugar);
	
	
}
