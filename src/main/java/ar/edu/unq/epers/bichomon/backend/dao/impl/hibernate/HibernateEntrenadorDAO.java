package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEntrenadorDAO implements EntrenadorDAO {

	public HibernateEntrenadorDAO() {
		super();
	}

	@Override
	public void saveEntrenador(Entrenador entrenador) {
		Session session = Runner.getCurrentSession();
		session.saveOrUpdate(entrenador);
	}

	@Override
	public Entrenador getEntrenador(String nombre) {
		Session session = Runner.getCurrentSession();
		return session.get(Entrenador.class, nombre);
	}
	
	
	
	/** Dado un nombre de {@link Lugar} persistido en BBDD, retorno la cantidad de {@link Entrenador}es que actualmente
	 * están ubicados en ese lugar.
	 * @param nombreLugar - el nombre del lugar a consultar.
	 * @return la cantidad de entrenadores en el lugar dado.*/
	@Override
	public int getCantidadDeEntrenadoresUbicadosEnLugar(String nombreLugar) {
		Session session = Runner.getCurrentSession();
		
		String hql = "SELECT COUNT(*) FROM Entrenadores WHERE ubicacion.nombre=:nombreLugar";
		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("nombreLugar", nombreLugar);
		
		return  query.getSingleResult().intValue();
	}

	
	
	/** Se responde con la lista de todos aquellos {@link Entrenador}es que posean  
	 * algún {@link Bicho} campeón que es campeón actualmente en algún {@link Dojo}.
	 * @return lista de entrenadores que posean bicho actualmesnte campeones.*/
	@Override
	public List<Entrenador> getEntrenadoresConBichosCampeones() {
		Session session = Runner.getCurrentSession();
		
		String hql = "SELECT DISTINCT e FROM Entrenadores e "
					+ "WHERE e in "
					+ 	"(SELECT c.bichoCampeon.owner FROM Campeones_historicos c "
					+ 	"WHERE c.fechaDepuesto IS null ORDER BY c.fechaCoronadoCampeon ASC)";
		Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
		
		return query.getResultList();
	}

	
	
	/** Se responde con los diez entrenadores para los cuales el valor de poder combinado de todos sus
	 * {@link Bicho}s sea superior.
	 * @return lista de entrenadores que posean bichos con mayor poder.*/
	@Override
	public List<Entrenador> getLideres() {
		Session session = Runner.getCurrentSession();
		
		String hql = "SELECT b.owner FROM Bichos b GROUP BY b.owner ORDER BY SUM(b.energia) DESC";
		Query<Entrenador> query = session.createQuery(hql, Entrenador.class);
		query.setMaxResults(10);
		
		return query.getResultList();
	}

}
