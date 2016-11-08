package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.TipoDeCaminoDAO;
import ar.edu.unq.epers.bichomon.backend.model.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateTipoDeCaminoDAO implements TipoDeCaminoDAO{

	public HibernateTipoDeCaminoDAO() {
	}

	@Override
	public void saveTipoDeCamino(TipoDeCamino tipoDeCamino) {
		Session session=Runner.getCurrentSession();
		session.save(tipoDeCamino);
			
	}

	@Override
	public TipoDeCamino getTipoDeCamino(String tipoDeCamino) {
		Session session= Runner.getCurrentSession();
		return session.get(TipoDeCamino.class,tipoDeCamino);
	}

}
