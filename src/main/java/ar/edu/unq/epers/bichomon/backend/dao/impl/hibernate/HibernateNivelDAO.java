package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateNivelDAO implements NivelDAO {
	
	@Override
	public void saveNivel(Nivel nivel) {
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(nivel);
	}

	@Override
	public Nivel getNivel(Integer nroNivel) {
		Session session = Runner.getCurrentSession();
		return session.get(Nivel.class, nroNivel);
	}
		

}
