package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieDAO implements EspecieDAO {

	@Override
	public void saveEspecie(Especie e) {
		Session session = Runner.getCurrentSession();
		session.save(e);
	}

	@Override
	public Especie getEspecie(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Especie> getAllEspecies() {
		// TODO Auto-generated method stub
		return null;
	}

}
