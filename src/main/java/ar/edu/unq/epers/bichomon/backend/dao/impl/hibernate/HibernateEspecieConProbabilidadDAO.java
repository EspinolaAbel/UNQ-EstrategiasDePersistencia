package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieConProbabilidadDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.EspecieConProbabilidad;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieConProbabilidadDAO implements EspecieConProbabilidadDAO {

	public HibernateEspecieConProbabilidadDAO() {
		super();
	}

	@Override
	public void saveEspecieConProbabilidad(EspecieConProbabilidad e) {
		Session session= Runner.getCurrentSession();
		session.save(e);
	}

	@Override
	public EspecieConProbabilidad getEspecieConProbabilidad(int id) {
		Session session= Runner.getCurrentSession();
		return session.get(EspecieConProbabilidad.class, id);
	}

}
