package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ar.edu.unq.epers.bichomon.backend.model.lugar.CampeonHistorico;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCampeonHistoricoDAO {
	
	/** Persiste un nuevo campeón histórico. Además se actualizan las fechas necesarias para el nuevo campeón y el campeón depuesto. */
	public void saveCampeonHistorico(CampeonHistorico ch) {
		Session session = Runner.getCurrentSession();
		this.destronarAnteriorCampeonDelDojo(ch.getFechaCoronadoCampeon(), ch.getLugarDondeEsCampeon());
		session.save(ch);
	}
	
	/** Dado un nombre de un dojo, retorna el último campeón historico de este.
	 * En caso de no haber ningún campeón hasta el momento retorna null. */
	public CampeonHistorico getUltimoCampeonDelDojo(String nombreDojo) {
		Session session = Runner.getCurrentSession();
		String hql = "FROM Campeones_historicos WHERE lugarDondeEsCampeon.nombre=:nombreDojo ORDER BY fechaCoronadoCampeon DESC";
		Query<CampeonHistorico> query = session.createQuery(hql, CampeonHistorico.class);
		query.setParameter("nombreDojo", nombreDojo);
		query.setMaxResults(1);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}
	
	/** Dada la fecha en que es coronado el nuevo campeón del dojo y el dojo, se destrona al anterior campeón.
	 * Al ex campeón se le setea la fecha en que fue depuesto, la cual coincide con la fecha de coronación del nuevo campeón.*/
	public void destronarAnteriorCampeonDelDojo(long fechaDepuesto, Dojo dojo) {
		String nombreDojo = dojo.getNombre();
		CampeonHistorico exCampeon = this.getUltimoCampeonDelDojo(nombreDojo);
		if(exCampeon != null)
			exCampeon.setFechaDepuesto(fechaDepuesto);
	}
	
}
