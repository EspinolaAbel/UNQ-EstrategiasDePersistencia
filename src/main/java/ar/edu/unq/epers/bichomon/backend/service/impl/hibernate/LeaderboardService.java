package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class LeaderboardService {

	public List<Entrenador> campeones() {
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		return
		Runner.runInSession(()-> {
			return entrenadorDAO.getEntrenadoresConBichosCampeones();
		});
	}
	
	public Especie especieLider() {
		EspecieDAO especieDAO = new HibernateEspecieDAO();
		return
		Runner.runInSession(()-> {
			return especieDAO.getEspecieLider();
		});
	}
	
	public List<Entrenador> lideres() {
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		return
		Runner.runInSession(()-> {
			return entrenadorDAO.getLideres();
		});
	}
}
