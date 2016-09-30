package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.hibernate.HibernateNivelDAO;

public class HibernateNivelDAOTest {

	Nivel nivel1, nivel2;
	private NivelDAO nivelDao;
	
	@Before
	public void setUp() {
		this.nivelDao = new HibernateNivelDAO();
	}
	
	@Test
	public void dadoUnNivelLoPersistoEnLaBaseDeDatosBichomonYLuegoLoRecuperoDeLaMismaParaComprobarQueSeHayaPersistidoDeManeraCorrecta(){
		this.nivel1 = new Nivel();
		this.nivel1.setNumeroDeNivel(1);
		this.nivel1.setPuntosParaSubirDeNivel(100);
		this.nivel1.setMaxCantidadDeBichos(10);
		this.nivel1.setSiguienteNivel(nivel2);
		
		this.nivel2 = new Nivel();
		this.nivel2.setNumeroDeNivel(2);
		this.nivel2.setPuntosParaSubirDeNivel(200);
		this.nivel2.setMaxCantidadDeBichos(20);
		
		this.nivelDao.saveNivel(nivel1);
		
		Nivel nivelRecuperado = this.nivelDao.getNivel(1);
		
		assertEquals(nivelRecuperado.getNumeroDeNivel(), (Integer) 1);
		assertEquals(nivelRecuperado.getPuntosParaSubirDeNivel(), (Integer) 100);
		assertEquals(nivelRecuperado.getMaxCantidadDeBichos(), (Integer) 10);
		assertEquals(nivelRecuperado.getSiguienteNivel(), this.nivel2);
		
	}

	/** Dados un nivel le pido me de su siguiente nivel y me lo responde.
	 * - El nivel 1 tiene como siguiente nivel el nivel 2*/
	@Test
	public void dadoUnNivelLePidoMeDeSuSiguienteNivel() {
		nivel1 = new Nivel();
		nivel1.setNumeroDeNivel(1);
		nivel2 = new Nivel();
		nivel2.setNumeroDeNivel(2);
		
		nivel1.setSiguienteNivel(nivel2);
		
		assertEquals(nivel1.getSiguienteNivel(), nivel2);
	}

}
