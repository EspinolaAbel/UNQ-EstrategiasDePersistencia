package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class EspecieService {
	
	private EspecieDAO especieDAO;
	
	public EspecieService(EspecieDAO especieDAO) {
		this.especieDAO = especieDAO;
	}

	public List<Especie> populares() {
		return
			Runner.runInSession(() -> {
				return this.especieDAO.getMasPopulares();
			});
	}
	
	public List<Especie> impopulares() {
		return
			Runner.runInSession(() -> {
				return this.especieDAO.getMenosPopulares();
			});
	}
}
