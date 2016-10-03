package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateLugarDAO implements LugarDAO {

	@Override
	public void saveLugar(Lugar lugar) {
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(lugar);
	}

	@Override
	public Lugar getLugar(String nombre) {
		Session session = Runner.getCurrentSession();
		return session.get(Lugar.class, nombre);
	}

}
