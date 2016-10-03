package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

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
	 * @param nombreLugar - el nombre del lugar a consultar.*/
	@Override
	public int getCantidadDeEntrenadoresUbicadosEnLugar(String nombreLugar) {
		Session session = Runner.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM Entrenadores WHERE ubicacion.nombre=:nombreLugar";
		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("nombreLugar", nombreLugar);
		
		return  query.getSingleResult().intValue();
	}

}
