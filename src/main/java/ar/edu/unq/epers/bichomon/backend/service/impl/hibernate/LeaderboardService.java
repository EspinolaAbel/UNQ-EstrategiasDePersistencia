package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class LeaderboardService {

	public List<Entrenador> campeones() {
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		Runner.runInSession(()-> {
			return entrenadorDAO.getEntrenadoresConBichosCampeones();
		});
		return null;
	}
	
	public Especie especieLider() {
		//TODO
		return null;
	}
	
	public List<Entrenador> lideres() {
		//TODO
		return null;
	}
}
