package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

/** La interface para un mapa persistido en una base de datos.*/
public interface MapaDAO {
	
	/** Dados los nombres de dos lugares en el mapa se responde cuál es el costo del viaje
	 * entre estos dos lugares tomando la ruta más corta.*/
	public Integer costoDelViajeMasCorto(String ubicacionPartida, String ubicacionDestino);

	/** Dados los nombres de dos lugares en el mapa se responde cuál es el costo del viaje
	 * entre estos dos lugares. La ubicación de partida y la de destino deben estar solo a una ruta
	 * de distancia.*/
	public Integer costoDelViajeDirecto(String ubicacionPartida, String ubicacionDestino);

	/** Pesiste el lugar dado en el mapa de la base de datos.*/
	public void saveLugar(Lugar lugar);

	/** Dados los nombres de dos lugares y una ruta se conecta los lugares con dicha ruta.*/
	public void crearConexion(String ubicacion1, String ubicacion2, TipoDeCamino tipoCamino);

	/** Se responde si el lugar con el nombre dado se encuentra en el mapa.*/
	public List<String> lugaresAdyacentes(String ubicacion, TipoDeCamino tipoCamino);
	
	/** Se responde si el lugar con el nombre dado se encuentra en el mapa.*/
	public List<String> lugaresAdyacentesDeCualquierTipo(String ubicacion);
	

	/** Se responde si los lugares dados se encuentran conectados a traveś de algun camino.*/
	public boolean existeLugar(String nombreLugar);

	/** Se responde si los lugares dados se encuentran conectados a traveś de algun camino.*/
	public boolean existeCamino(String partida, String destino);
	
}
