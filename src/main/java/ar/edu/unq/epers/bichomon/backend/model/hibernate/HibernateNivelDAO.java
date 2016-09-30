package ar.edu.unq.epers.bichomon.backend.model.hibernate;

import java.util.List;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateNivelDAO implements NivelDAO {
	
	private Session session;

	@Override
	public void saveNivel(Nivel nivel) {
		Session session = Runner.getCurrentSession();
		session.save(nivel);
	}

	@Override
	public Nivel getNivel(Integer nroNivel) {
		Session session = Runner.getCurrentSession();
		return session.get(Nivel.class, nroNivel);
	}

	@Override
	public List<Nivel> getNiveles() {
		Session session = Runner.getCurrentSession();
		throw new RuntimeException("Todavía no está definido este método.");
	}
	
	

}
