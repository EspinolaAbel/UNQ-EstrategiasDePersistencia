package ar.edu.unq.epers.bichomon.backend.service.cache;

import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;

/** Cache que retiene todos los entrenadores campeones de Bichomon.
 * En particular, esta cache esta implementada para el servicio LeaderboardService.campeones():List<Entrenador>.
 * 
 * NOTA:
 * 		Al coronarse un nuevo campeón, esta cache pasa a tener datos inconsistentes con respecto a la base de datos que persiste los campeones.
 * 	Es por este motivo que se debe forzar la actualización de la cache de la siguiente forma:
 * 		- Cuando hay datos inconsistentes en la cache se debe borrar manualmente el mapeo desactualizado con el método removeCampeones(). 
 * 		  De esta forma, los futuros usuarios de la entrada borrada en la cache no podrán acceder a datos inconsistentes;
 * 		- El usuario de la cache debe ingresar nuevamente el mapeo para dicha entrada borrada para que esté disponible para futuras consultas. 	
 * */
public class EntrenadoresCampeonesCache {

	//Key a utilizar para traer todos los entrenadores campeones de la cache
	private static final String ENTRENADORES = "entrenadores";
	
	private RemoteCache<String, List<Entrenador>> cache;

	public EntrenadoresCampeonesCache(RemoteCache<String, List<Entrenador>> cache) {
		this.cache = cache;
	}

	
	/**Límpia toda la cache borrando todas sus entradas.*/
	public void clear() {
		this.cache.clear();
	}

	/** Se ingresa en la cache los entrenadores campeones.*/
	public List<Entrenador> put(List<Entrenador> campeones) {
		return this.cache.put(ENTRENADORES, campeones);
	}
	
	/** Se retorna los entrenadores campeones.*/
	public  List<Entrenador> get() {
		return this.cache.get(ENTRENADORES);
	}

	/** Invoked on component stop */
	public void stop() {
		this.cache.stop();
	}
	
	/** Se borra de la cache todos los campeones cacheados. */
	public void removeCampeones() {
		this.cache.remove(ENTRENADORES);
	}
	
	/** Al llamar a este método se notifica a la cache que posee datos inconsistentes y debe proceder a borrarlos.*/
	public void datosInconsistentes() {
		this.removeCampeones();
	}

	/** Se responde con la cantidad de registros en la cache.*/
	public int size() {
		return this.cache.size();
	}
}
