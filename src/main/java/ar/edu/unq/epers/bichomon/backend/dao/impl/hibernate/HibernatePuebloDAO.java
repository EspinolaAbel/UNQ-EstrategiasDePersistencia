package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;

import ar.edu.unq.epers.bichomon.backend.dao.PuebloDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernatePuebloDAO  implements PuebloDAO{

	public HibernatePuebloDAO() {
		super();
	}
	
	@Override
	public void savePueblo (Pueblo p){
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(p);
	}

	@Override
	public Pueblo getPueblo(String pueblo) {
		Session session = Runner.getCurrentSession();
		return session.get(Pueblo.class, pueblo);
	}

}
