package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.*;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEntrenadorDAO implements EntrenadorDAO {

	public HibernateEntrenadorDAO() {
	}

	@Override
	public void saveEntrenador(Entrenador entrenador) {
		Session  session=Runner.getCurrentSession();
		session.save(entrenador);
		

	}

	@Override
	public Entrenador getEntrenador(String nombre) {
		Session  session=Runner.getCurrentSession();
		Entrenador e=session.get(Entrenador.class, nombre);
		return e;
	}

	@Override
	public List<Entrenador> getAllEntrenadores() {
		
		 Session  session=Runner.getCurrentSession();
		 List<Entrenador> e= session.createQuery("from Entrenador").list();

		return e;
	}

}
