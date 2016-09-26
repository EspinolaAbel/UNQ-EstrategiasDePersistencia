package ar.edu.unq.epers.bichomon.backend.model.lugar;

public class UbicacionIncorrectaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8138325694066688551L;

	public  UbicacionIncorrectaException(String lugar) {
		super("En el lugar \""+lugar+"\" no se permite abandonar bichos.");
	}
	

}
