package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.model.dummys.GeneradorDeFechasDummy;
import ar.edu.unq.epers.bichomon.backend.model.lugar.CampeonHistorico;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCampeonHistoricoDAO {
	
	/** Persiste un nuevo campeón histórico. Además se actualizan las fechas necesarias para el nuevo campeón y el campeón depuesto. */
	public void saveCampeonHistorico(CampeonHistorico ch) {
		Session session = Runner.getCurrentSession();
		CampeonHistorico exCh = this.getUltimoCampeon();
		this.actualizarFechas(ch, exCh);
		
		session.save(ch);
	}
	
	/** Retorna el último campeón historico de todos los dojos que fué persistido.
	 * En caso de no haber ningún campeón hasta el momento, retorna null. */
	public CampeonHistorico getUltimoCampeon() {
		Session session = Runner.getCurrentSession();
		String hql = "FROM Campeones_historicos ORDER BY fechaCoronadoCampeon DESC";
		Query<CampeonHistorico> query = session.createQuery(hql, CampeonHistorico.class);
		query.setMaxResults(1);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}
	
	/** Dados dos campeones históricos, les asigna las fechas correspondientes.
	 * Para el nuevo campeón le genera la fecha en que fué coronado campeón, y para el ex campeón, la fecha en que fue depuesto, la cual coincide
	 * con la fecha de coronación del nuevo campeón.*/
	public void actualizarFechas(CampeonHistorico nuevoCampeon, CampeonHistorico exCampeon) {
		int fechaActual;
		if(exCampeon != null) {
			fechaActual = GeneradorDeFechasDummy.generarFecha(exCampeon.getFechaCoronadoCampeon()); 
			nuevoCampeon.setFechaCoronadoCampeon(fechaActual);
			exCampeon.setFechaDepuesto(fechaActual);
		}
		else {
			fechaActual = GeneradorDeFechasDummy.generarFechaActual();
			nuevoCampeon.setFechaCoronadoCampeon(fechaActual);
		}
	}
	
}
