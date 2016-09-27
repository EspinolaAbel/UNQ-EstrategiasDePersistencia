package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCondicionDeEvolucionDAO implements CondicionDeEvolucionDAO {

	@Override
	public void guardar(CondicionDeEvolucion condicion) {
		Session session = Runner.getCurrentSession();
		session.save(condicion);
	}

	@Override
	public CondicionDeEvolucion recuperar() {
		// TODO Auto-generated method stub
		return null;
	}

}
