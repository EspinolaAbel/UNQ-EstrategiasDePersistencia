package ar.edu.unq.epers.bichomon.backend.service.impl;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class LeaderboardService {
	
	private EntrenadorDAO entrenadorDAO;
	private EspecieDAO especieDAO;


	public LeaderboardService(EntrenadorDAO entrenadorDAO, EspecieDAO especieDAO){
		this.entrenadorDAO = entrenadorDAO;
		this.especieDAO = especieDAO;
	}

	public List<Entrenador> campeones() {
		return
		Runner.runInSession(()-> {
			return this.entrenadorDAO.getEntrenadoresConBichosCampeones();
		});
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
