package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
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

	@Override
	public Bicho getBichoCampeonActualDelDojo(String nombreDojo) {
		Session session = Runner.getCurrentSession();
		
		String hql = "FROM Bichos WHERE id=( SELECT d.campeonActual.bichoCampeon FROM Dojo d WHERE nombre=:nombreDojo )";
		Query<Bicho> query = session.createQuery(hql, Bicho.class);
		query.setParameter("nombreDojo", nombreDojo);
		
		return query.getSingleResult();
	}

	@Override
	public Bicho getCampeonHistoricoDelDojo(String dojoNombre) {
		Session session = Runner.getCurrentSession();
		
		String hql_idBichoCH = 	"SELECT bichoCampeon.id FROM Campeones_historicos c "
				+ "ORDER BY (c.fechaDepuesto - c.fechaCoronadoCampeon) DESC";
				Query<Integer> query = session.createQuery(hql_idBichoCH, Integer.class);
		query.setMaxResults(1);
		Integer idBichoCH = query.getSingleResult();
		
		BichoDAO bichoDAO = new HibernateBichoDAO();
		
		return bichoDAO.getBicho(idBichoCH);
	} 
}
