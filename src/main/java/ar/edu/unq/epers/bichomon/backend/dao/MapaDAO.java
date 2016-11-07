package ar.edu.unq.epers.bichomon.backend.dao;

/** La interface para un mapa persistido en una base de datos.*/
public interface MapaDAO {
	
	/** Dados los nombres de dos lugares en el mapa se responde cuál es el costo del viaje
	 * entre estos dos lugares tomando la ruta más corta.*/
	public Integer costoDelViajeMasCorto(String ubicacionPartida, String ubicacionDestino);

	/** Dados los nombres de dos lugares en el mapa se responde cuál es el costo del viaje
	 * entre estos dos lugares. La ubicación de partida y la de destino deben estar solo a una ruta
	 * de distancia.*/
	public Integer costoDelViajeDirecto(String ubicacionPartida, String ubicacionDestino);
	
}
