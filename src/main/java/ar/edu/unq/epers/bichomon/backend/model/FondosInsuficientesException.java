package ar.edu.unq.epers.bichomon.backend.model;

public class FondosInsuficientesException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FondosInsuficientesException(Integer monto) {
		super("El entrenador no puede pagar el monto "+monto+" debido a que no tiene suficientes monedas.");
	}

}
