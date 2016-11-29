package ar.edu.unq.epers.bichomon.backend.service.runner;

import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.cache.CantidadEntrenadoresCache;
import ar.edu.unq.epers.bichomon.backend.service.cache.EntrenadoresCampeonesCache;


public class CacheProvider {

	//Singleton CacheProvider
	private static CacheProvider INSTANCE;
	
	private RemoteCacheManager cacheManager;
	
	//Caches:
	private CantidadEntrenadoresCache cantEntrenadoresCache;
	private EntrenadoresCampeonesCache entCampeonesCache;
	
	
	private CacheProvider() {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer().host("127.0.0.1").port(11222);
		
		this.cacheManager = new RemoteCacheManager(builder.build());
		
		this.loadCaches();
	}

	
	public synchronized static CacheProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CacheProvider();
		}
		return INSTANCE;
	}
	

	/**
	 * En este método se generan todas las caches a utilizarse por los servicios.
	 * */
	private void loadCaches() {
		//Lugar - Cant.Entrenadores en el lugar
		RemoteCache<String, Integer> cache1 = this.cacheManager.getCache("entrenadores-en-lugar");
		this.cantEntrenadoresCache = new CantidadEntrenadoresCache(cache1);

		//Entrenadores campeones
		RemoteCache<String, List<Entrenador>> cache2 = this.cacheManager.getCache("entrenadores-campeones");
		this.entCampeonesCache = new EntrenadoresCampeonesCache(cache2);
	}
	

	
	public CantidadEntrenadoresCache getCantidadEntrenadoresCache() {
		return this.cantEntrenadoresCache;
	}
	
	public EntrenadoresCampeonesCache getEntrenadoresCampeonesCache() {
		return this.entCampeonesCache;
	}
	
}
