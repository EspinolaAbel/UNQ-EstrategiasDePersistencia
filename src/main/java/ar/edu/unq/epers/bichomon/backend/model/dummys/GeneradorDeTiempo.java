package ar.edu.unq.epers.bichomon.backend.model.dummys;

/** Esta clase tiene como función representar el tiempo basándose en números enteros con precisión de nanosegundos.
 * El propósito de esta clase es la de poder generar el paso del tiempo en la aplicación y será más
 * que nada utilizada para propósitos de tests.
 * Al hacer un llamado a esta clase, esta se cargará en memoria y el tiempo actual se
 * inicializará con el tiempo actual del sistema en nanosegundos.
 * */
public class GeneradorDeTiempo {
	
	public static final boolean NANO = true , MILI = false; 

	public static long getTime() {
		if(MILI)
		return System.nanoTime();
		else
		return System.nanoTime();
	}
	
	//TODO
}
