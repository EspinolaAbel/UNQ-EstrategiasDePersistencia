package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.query.Query;

import java.util.Collection;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCondicionDeEvolucionDAO implements CondicionDeEvolucionDAO {

	@Override
	public void saveCondicion(CondicionDeEvolucion condicion) {
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(condicion);
	}

	@Override
	public CondicionDeEvolucion getCondicion(Integer id) {
		Session session = Runner.getCurrentSession();
		return session.get(CondicionDeEvolucion.class, id);
	}

	@Override
	public Collection<CondicionDeEvolucion> getAllCondiciones() {
		Session session = Runner.getCurrentSession();
		String hql = "FROM CondicionDeEvolucion";

		Query<CondicionDeEvolucion> query = session.createQuery(hql, CondicionDeEvolucion.class); 

		return query.getResultList();
	}
}
