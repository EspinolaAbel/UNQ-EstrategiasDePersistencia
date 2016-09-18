package ar.edu.unq.epers.bichomon.backend.model.lugar;

public class UvicacionIncorrectaExeption extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8138325694066688551L;

	public  UvicacionIncorrectaExeption(String lugar) {
		
		super("el lugar [" + lugar + "] no esta permitido para la accion  que pretende realizar");
	}
	

}
