package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

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

	public void saveLugar(Lugar lugar);

	public void crearConeccion(String ubicacion1, String ubicacion2, String tipoCamino);

	public List<String> lugaresAdyacentes(String ubicacion, String tipoCamino);
	
}
