package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
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

	/** Se consulta a base de datos por aquella especie que tenga más bichos campeones contando todos
	 * los dojos de la aplicación.
	 * Cada bicho será contado una sola vez sin importar sin un bicho salió campeón más de una vez.
	 * @return especie lider con mayor cantidad de bichos campeones. */
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

	/** Se consulta a la base de datos por aquellas 10 especies que son más populares.
	 * Se dice que son populares a aquellas especies de bichos que están presente en mayor cantidad 
	 * teniendo en cuenta a todos los entrenadores de la aplicación.
	 * @return lista de especies populares. */
	@Override
	public List<Especie> getMasPopulares() {
		Session session = Runner.getCurrentSession();
		String hql = "SELECT b.especie FROM Bichos b "
					+ "WHERE b.owner IS NOT Null GROUP BY b.especie ORDER BY COUNT(b.especie) DESC";
		Query<Especie> query = session.createQuery(hql, Especie.class);
		query.setMaxResults(10);
		return query.getResultList();
	}

	/** Se consulta a la base de datos por aquellas 10 especies que son más impopulares.
	 * Se dice que son impopulares a aquellas especies de bichos que están en mayor cantidad 
	 * en todas las guarderias de la aplicación.
	 * @return lista de especies impopulares. */
	@Override
	public List<Especie> getMenosPopulares() {
		Session session = Runner.getCurrentSession();
		String hql = "SELECT b.especie FROM Bichos b "
					+ "WHERE b.owner IS Null GROUP BY b.especie ORDER BY COUNT(b.especie) DESC";
		Query<Especie> query = session.createQuery(hql, Especie.class);
		query.setMaxResults(10);
		return query.getResultList();
	}

}
