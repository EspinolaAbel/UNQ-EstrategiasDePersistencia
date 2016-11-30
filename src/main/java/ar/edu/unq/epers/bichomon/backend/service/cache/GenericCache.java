package ar.edu.unq.epers.bichomon.backend.service.cache;

import org.infinispan.client.hotrod.RemoteCache;


/** Implementación que me permite acceder a datos cacheados por servicios Bichomon.
 * 
 * NOTA:
 * 		Al suceder un cierto evento en la aplicación que afecte los datos de la cache, esta cache pasa a tener datos inconsistentes con 
 * 	respecto a la base de datos subyacente.
 * 	Es por este motivo que se debe forzar la actualización de la cache de la siguiente forma:
 * 		- Cuando hay datos inconsistentes en la cache se debe borrar manualmente el mapeo desactualizado con el método datosInconsistentes(key),
 * 		  donde 'key' es la clave del mapeo desactualizado. 
 * 		  De esta forma, los futuros usuarios de la entrada borrada en la cache no podrán acceder a datos inconsistentes;
 * 		- El usuario de la cache debe ingresar nuevamente el mapeo para dicha entrada borrada para que esté disponible para futuras consultas. 	
 * */
public class GenericCache<K,V> {

	private RemoteCache<K,V> cache;

	//Key para obtener todos los entrenadores campeones.
	private K defaultKey;
	
	public GenericCache(RemoteCache<K,V> cache, K defaultKey) {
		this.cache = cache;
		this.defaultKey = defaultKey;
	}
	
	public GenericCache(RemoteCache<K,V> cache) {
		this(cache, null);
	}

	
	/**Límpia toda la cache borrando todas sus entradas.*/
	public void clear() {
		this.cache.clear();
	}

	/** Se ingresa un nuevo registro cacheable.*/
	public V put(K key, V value) {
		return this.cache.put(key, value);
	}
	
	public V put(V value) {
		return this.put(defaultKey, value);
	}
	
	/** Dado una key, se retorna el value para dicho key.*/
	public V get(K key) {
		return this.cache.get(key);
	}
	
	public V get() {
		return this.get(defaultKey);
	}

	/** Invoked on component stop */
	public void stop() {
		this.cache.stop();
	}
	
	/** Dada una key, se borra de la cache el mapeo para dicha key. */
	public void remove(K key) {
		this.cache.remove(key);
	}
	
	public void remove() {
		this.remove(defaultKey);
	}
	
	/** Al llamar a este método se notifica a la cache que posee datos inconsistentes y debe proceder a borrarlos.
	 * @param key - key cuyo value mapeado tiene datos inconsistentes.*/
	public void datosInconsistentes(K key) {
		this.remove(key);
	}
	
	public void datosInconsistentes() {
		this.datosInconsistentes(defaultKey);
	}

	/** Se responde con la cantidad de registros en la cache.*/
	public int size() {
		return this.cache.size();
	}
	
	
}
