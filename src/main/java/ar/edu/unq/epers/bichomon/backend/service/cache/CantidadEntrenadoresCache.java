package ar.edu.unq.epers.bichomon.backend.service.cache;

import org.infinispan.client.hotrod.RemoteCache;

/** Cache que retiene la cantidad de entrenadores que hay ubicados en una determinada ubicación del mapa Bichomon.
 * En particular, esta cache esta implementada para el servicio MapaService.cantidadEntrenadores(String ubicacion):int
 * 
 *  * NOTA:
 * 		Al trasladarse un entrenador de ubicación, esta cache pasa a tener datos inconsistentes con respecto a la base de datos que persiste los 
 * 	los entrenadores y sus ubicaciones.
 * 	Es por este motivo que se debe forzar la actualización de la cache de la siguiente forma:
 * 		- Cuando hay datos inconsistentes en la cache se debe borrar manualmente el mapeo desactualizado con el método 
 * 		  removeCantidadEntrenadores(nombreLugar), siendo nombreLugar el nombre del lugar cuya cantidad de entrenadores en esa ubicación cambió. 
 * 		  De esta forma, los futuros usuarios de la entrada borrada en la cache no podrán acceder a datos inconsistentes;
 * 		- El usuario de la cache debe ingresar nuevamente el mapeo para dicha entrada borrada para que esté disponible para futuras consultas. 	
 * */
public class CantidadEntrenadoresCache {

	private RemoteCache<String, Integer> cache;

	public CantidadEntrenadoresCache(RemoteCache<String, Integer> cache) {
		this.cache = cache;
	}

	
	/**Límpia toda la cache borrando todas sus entradas.*/
	public void clear() {
		this.cache.clear();
	}

	/** Se ingresa un nuevo resgistro cacheable de cantidad de entrenadores en una determinada ubicación.*/
	public Integer put(String nombreLugar, Integer cantidad) {
		return this.cache.put(nombreLugar, cantidad);
	}
	
	/** Dado un nombre de una ubicación en el mapa Bichomon, se retorna la cantidad de entrenadores en ese lugar.*/
	public Integer get(String nombreLugar) {
		return this.cache.get(nombreLugar);
	}

	/** Invoked on component stop */
	public void stop() {
		this.cache.stop();
	}
	
	/** Dado el nombre de un lugar, se borra de la cache el mapeo para dicho lugar. */
	public void removeCantidadEntrenadores(String nombreLugar) {
		this.cache.remove(nombreLugar);
	}
	
	/** Al llamar a este método se notifica a la cache que posee datos inconsistentes y debe proceder a borrarlos.
	 * @param key - nombre del lugar con datos inconsistentes.*/
	public void datosInconsistentes(String key) {
		this.removeCantidadEntrenadores(key);
	}

	/** Se responde con la cantidad de registros en la cache.*/
	public int size() {
		return this.cache.size();
	}

}
