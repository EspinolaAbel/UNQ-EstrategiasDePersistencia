package ar.edu.unq.epers.bichomon.backend.dao;

/** La interface para un mapa persistido en una base de datos.*/
public interface MapaDAO {
	
	/** Dados los nombres de dos lugares en el mapa se responde cuál es el costo del viaje
	 * entre estos dos lugares tomando la ruta más corta.*/
	public Integer calcularCostoDelViaje(String ubicacionPartida, String ubicacionDestino);
	
}
