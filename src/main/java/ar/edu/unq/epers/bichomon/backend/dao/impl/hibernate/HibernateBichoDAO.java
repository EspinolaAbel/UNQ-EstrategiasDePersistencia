package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateBichoDAO implements BichoDAO {

	public HibernateBichoDAO() {

	}

	@Override
	public void saveBicho(Bicho bicho) {
		Session session= Runner.getCurrentSession();
		session.saveOrUpdate(bicho);
	}

	@Override
	public Bicho getBicho(int idBicho) {
		Session session = Runner.getCurrentSession();
		return session.get(Bicho.class, idBicho);
	}
	
	@Override
	public Collection<Bicho> getAllBichos() {
		Session session = Runner.getCurrentSession();
		
		String hql = "FROM Bicho";
		Query<Bicho> query = session.createQuery(hql, Bicho.class);
		
		return query.getResultList();
	}

}
