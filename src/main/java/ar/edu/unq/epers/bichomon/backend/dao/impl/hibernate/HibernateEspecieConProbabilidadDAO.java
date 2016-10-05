package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieConProbabilidadDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.EspecieConProbabilidad;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieConProbabilidadDAO implements EspecieConProbabilidadDAO {

	public HibernateEspecieConProbabilidadDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveEspecieConProbabilidad(EspecieConProbabilidad e) {
		
		Session session= Runner.getCurrentSession();
		session.save(e);
		

	}

	@Override
	public EspecieConProbabilidad getEspecieConProbabilidad(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
