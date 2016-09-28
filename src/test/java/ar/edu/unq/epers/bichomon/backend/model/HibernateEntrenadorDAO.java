package ar.edu.unq.epers.bichomon.backend.model;

import java.util.List;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEntrenadorDAO implements EntrenadorDAO {

	@Override
	public void saveEntrenador(Entrenador entrenador) {
		Session session = Runner.getCurrentSession();
		session.save(entrenador);
	}

	@Override
	public Entrenador getEntrenador(String nombre) {
		Session session = Runner.getCurrentSession();
		return session.get(Entrenador.class, nombre);
	}

	@Override
	public List<Entrenador> getAllEntrenadores() {
		// TODO Auto-generated method stub
		return null;
	}

}
