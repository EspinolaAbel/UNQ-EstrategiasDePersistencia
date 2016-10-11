package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.model.PuntosDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernatePuntosDeExperienciaDAO {

	public HibernatePuntosDeExperienciaDAO() {
		super();
	}
	
	
	public void savePuntosDeExperiencia (PuntosDeExperiencia p){
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(p);
	}

	public PuntosDeExperiencia getPuntosDeExperiencia(String tarea) {
		Session session = Runner.getCurrentSession();
		return session.get(PuntosDeExperiencia.class, tarea);
	}

	
}
