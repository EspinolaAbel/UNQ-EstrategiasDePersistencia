package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class EspecieService {

	public List<Especie> populares() {
		EspecieDAO especieDAO = new HibernateEspecieDAO();
		return
			Runner.runInSession(() -> {
				return especieDAO.getMasPopulares();
			});
	}
	
	public List<Especie> impopulares() {
		EspecieDAO especieDAO = new HibernateEspecieDAO();
		return
			Runner.runInSession(() -> {
				return especieDAO.getMenosPopulares();
			});
	}
}
