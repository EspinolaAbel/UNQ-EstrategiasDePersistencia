package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateBichoDAO implements BichoDAO {

	public HibernateBichoDAO() {

	}

	@Override
	public void saveBicho(Bicho bicho) {
		Session session= Runner.getCurrentSession();
		session.save(bicho);
	}

	@Override
	public Bicho getBicho(int idBicho) {
		Session session = Runner.getCurrentSession();
		return session.get(Bicho.class, idBicho);
	}

}
