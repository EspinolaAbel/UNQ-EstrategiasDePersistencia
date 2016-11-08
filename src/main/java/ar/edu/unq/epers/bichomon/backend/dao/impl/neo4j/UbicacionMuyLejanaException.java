package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

public class UbicacionMuyLejanaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UbicacionMuyLejanaException(String ubicacionPartida, String ubicacionDestino) {
		super("La ubicación '"+ubicacionDestino+"' no puede ser alcanzada con una ruta directa desde la ubicación '"+ubicacionPartida+"'.");
	}
}
