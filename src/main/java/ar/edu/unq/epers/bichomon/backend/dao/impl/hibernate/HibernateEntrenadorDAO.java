package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEntrenadorDAO implements EntrenadorDAO {

	public HibernateEntrenadorDAO() {
	}

	@Override
	public void saveEntrenador(Entrenador entrenador) {
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(entrenador);
	}

	@Override
	public Entrenador getEntrenador(String nombre) {
		Session session = Runner.getCurrentSession();
		return session.get(Entrenador.class, nombre);
	}

	@Override
	public List<Entrenador> getAllEntrenadores() {		
		Session  session=Runner.getCurrentSession();
		Query<Entrenador> query = session.createQuery("FROM Entrenador", Entrenador.class);
		return query.getResultList();
	}

}
