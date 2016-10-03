package ar.edu.unq.epers.bichomon.backend.model.lugar;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.model.dummys.GeneradorDeFechasDummy;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCampeonHistoricoDAO {
	
	public void saveCampeonHistorico(CampeonHistorico ch) {
		Session session = Runner.getCurrentSession();
		CampeonHistorico exCh = this.getUltimoCampeon();
		this.actualizarFechas(ch, exCh);
		
		session.save(ch);
	}
	
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
	
	public void actualizarFechas(CampeonHistorico nuevoCampeon, CampeonHistorico exCampeon) {
		int tiempoTranscurrido = GeneradorDeFechasDummy.calcularTiempoTranscurrido(exCampeon.getFechaCoronadoCampeon()); 
		int tiempoTranscurrido = (int) (Math.random() * 10) + 1;
		if(exCampeon != null) {
			int fechaActual = tiempoTranscurrido + exCampeon.getFechaCoronadoCampeon();
			nuevoCampeon.setFechaCoronadoCampeon(fechaActual);
			exCampeon.setFechaDepuesto(fechaActual);
		}
		else
			nuevoCampeon.setFechaCoronadoCampeon(tiempoTranscurrido);
	}
	
}
