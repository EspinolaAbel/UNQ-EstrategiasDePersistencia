package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

public class DestinoInalcanzableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DestinoInalcanzableException(String partida, String destino) {
		super("No existe ninguna ruta para viajar desde la ubicación '"+partida+"' hasta '"+destino+"'.");
	}
}
