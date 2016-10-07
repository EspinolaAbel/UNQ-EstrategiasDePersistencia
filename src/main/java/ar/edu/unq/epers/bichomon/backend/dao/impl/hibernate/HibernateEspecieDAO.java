package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieDAO implements EspecieDAO {

	@Override
	public void saveEspecie(Especie e) {
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(e);
	}

	@Override
	public Especie getEspecie(String nombre) {
		Session session = Runner.getCurrentSession();
		Especie e = session.get(Especie.class, nombre);
		return e;
	}

	@Override
	public List<Especie> getAllEspecies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Especie getEspecieLider() {
		Session session = Runner.getCurrentSession();
		String hql = "SELECT b.especie FROM Bichos b WHERE b IN"
					+ "(SELECT DISTINCT c.bichoCampeon FROM Campeones_historicos c)"
					+ "GROUP BY b.especie ORDER BY COUNT(b.especie) DESC ";
		Query<Especie> query =  session.createQuery(hql, Especie.class);
		query.setMaxResults(1);
		return query.getSingleResult();
	}

}
