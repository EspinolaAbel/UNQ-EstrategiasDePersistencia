package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
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

	/** Dado el nombre de un {@link Dojo} se consulta a la base de datos por el {@link Bicho}
	 * que actualmente es campeón en dicho dojo.
	 * @param dojoNombre - nombre del dojo a consultar.
	 * @return bicho campeón actual. */
	@Override
	public Bicho getBichoCampeonActualDelDojo(String nombreDojo) {
		Session session = Runner.getCurrentSession();
		
		String hql = "FROM Bichos WHERE id=( SELECT d.campeonActual.bichoCampeon FROM Dojo d WHERE nombre=:nombreDojo )";
		Query<Bicho> query = session.createQuery(hql, Bicho.class);
		query.setParameter("nombreDojo", nombreDojo);
		
		return query.getSingleResult();
	}

	/** Dado el nombre de un {@link Dojo} se consulta a la base de datos por el {@link Bicho}
	 * que es campeón durante más tiempo en dicho dojo.
	 * @param dojoNombre - nombre del dojo a consultar.
	 * @return bicho campeón histórico. */
	@Override
	public Bicho getCampeonHistoricoDelDojo(String dojoNombre) {
		Session session = Runner.getCurrentSession();
		
		String hql = 	"SELECT bichoCampeon.id FROM Campeones_historicos c "
				+ "WHERE c.lugarDondeEsCampeon.nombre = :dojoNombre "
				+ "ORDER BY c.fechaDepuesto ASC";
		Query<Integer> query = session.createQuery(hql, Integer.class);
		query.setParameter("dojoNombre", dojoNombre);
		query.setMaxResults(1);
		Integer idBichoCH = query.getSingleResult();
		
		BichoDAO bichoDAO = new HibernateBichoDAO();
		
		return bichoDAO.getBicho(idBichoCH);
	} 
}
