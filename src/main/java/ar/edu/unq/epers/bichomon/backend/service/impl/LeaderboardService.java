package ar.edu.unq.epers.bichomon.backend.service.impl;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.cache.GenericCache;
import ar.edu.unq.epers.bichomon.backend.service.runner.CacheProvider;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class LeaderboardService {
	
	private EntrenadorDAO entrenadorDAO;
	private EspecieDAO especieDAO;
	private GenericCache<String, List<Entrenador>> cacheCampeones;


	public LeaderboardService(EntrenadorDAO entrenadorDAO, EspecieDAO especieDAO){
		this.entrenadorDAO = entrenadorDAO;
		this.especieDAO = especieDAO;
		this.cacheCampeones = CacheProvider.getInstance().getEntrenadoresCampeonesCache();
	}

	public List<Entrenador> campeones() {
		List<Entrenador> lsCampeones = this.cacheCampeones.get();
		
		//si los campeones no están en la cache, se consulta la base de datos y se cachea el resultado.
		if(lsCampeones == null) {	
			lsCampeones =	Runner.runInSession(()-> {
								return this.entrenadorDAO.getEntrenadoresConBichosCampeones();
							});
			this.cacheCampeones.put(lsCampeones);
		}
		
		return lsCampeones;
	}
	
	public Especie especieLider() {
		return
		Runner.runInSession(()-> {
			return this.especieDAO.getEspecieLider();
		});
	}
	
	
	public List<Entrenador> lideres() {
		return
		Runner.runInSession(()-> {
			return this.entrenadorDAO.getLideres();
		});
	}
}
