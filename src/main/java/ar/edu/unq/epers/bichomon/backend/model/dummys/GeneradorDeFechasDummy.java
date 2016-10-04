package ar.edu.unq.epers.bichomon.backend.model.dummys;

/** Esta clase tiene como función generar fechas basadas en números enteros. Todas las fechas generadas son aletorias y solo son utiles
 * para simular el transcurso del tiempo en la aplicación.*/
public class GeneradorDeFechasDummy {

	/** Dada una fecha antigua genera una nueva fecha posterior a la dada. Todas las fechas devueltas seran mayores a la fecha dada.
	 * @param antiguaFecha - una fecha pasada a partir de la cual se calculará la nueva fecha.
	 * @return fecha posterior a la fecha dada. */
	public static int generarFecha(Integer antiguaFecha) {
		//genera una cantidad de tiempo aleatorio entre 10 y 1 
		int tiempoTranscurrido = tiempoTranscurridoAleatorio();
		int fechaActual = antiguaFecha + tiempoTranscurrido;
		return fechaActual;
	}

	/** Genera una nueva fecha aleatoria.
	 * @return fecha positiva mayor a cero. */
	public static int generarFechaActual() {
		return tiempoTranscurridoAleatorio();
	}
	
	/** Retorna numeros aleatorios.
	 * @return int > 0 */
	private static int tiempoTranscurridoAleatorio() {
		return (int) (Math.random() * 10) + 1;
	}
	
}
