package ar.edu.unq.epers.bichomon.backend.service.especie;

/**
 * Situación excepcional en que una especie buscada no es
 * encontrada.
 */
public class EspecieNoExistenteException extends RuntimeException {

	private static final long serialVersionUID = -8138325694066688551L;

	public EspecieNoExistenteException(String especie) {
		super("No se encuentra la especie [" + especie + "]");
	}
	
}
