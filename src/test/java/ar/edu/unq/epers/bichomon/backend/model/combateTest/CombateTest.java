package ar.edu.unq.epers.bichomon.backend.model.combateTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Combate;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

public class CombateTest {
	private Bicho campeon ; 
	private Bicho retador;
	private Especie lagartomon= new Especie("Lagartomon", TipoBicho.AGUA);	
	private Combate combate;
	
	@Before
	public void iniciar (){
		campeon = new Bicho(this.lagartomon );
		retador = new Bicho (this.lagartomon);
		campeon.setEnergia(5);
		retador.setEnergia(5);
		combate = new Combate(this.retador, this.campeon);
	}
	
	
	/**
	 * dados dos bichos que terminaron de pelear, si ninguno se queda sin energia
	 * el ganador es  el campeon
	 */
	@Test
	public void testObtenerGanador() {
		assertEquals(campeon, combate.getGanador());
	}

	/**
	 * dados dos bichos que terminaron de pelear, si ninguno se queda sin energia
	 * el perdedor es  el retador
	 */
	@Test
	public void testObtenerPerdedor() {
		assertEquals(retador, combate.getPerdedor());
	}

	
	/**
	 * dados dos bichos que terminaron de pelear, si uno se queda sin energia
	 * el ganador es el que tiene energia aun
	 */
	@Test
	public void testObtenerPerdedorPorEnergiaSuperada() {
		//alcanzo el daño al campeon con su energia
		combate.setDañoAcumuladoCampeon(5.0) ;
		assertEquals(retador, combate.getGanador());
		assertEquals(campeon, combate.getPerdedor());
	}
	
	/** dados dos bichos,  el retador que ataca primero. cuya energia es 10 veces superio a la del
	 * campeon, este deb vencerlo con el primer golpe, dada la forma de calcular la intensidad del ataque
	 * 
	 */
	
	@Test
	public void testRetadorDiezVecesMasPoderosoCombateYyDebeGanar(){
		this.retador.setEnergia(50);
		combate.combatir();
		assertEquals(retador, combate.getGanador());
	}

	/** dados dos bichos,  el retador que ataca primero. cuya energia es 10 veces superio a la del
	 * campeon, este deb vencerlo con el primer golpe
	 * 
	 */
	
	@Test
	public void testCampeonDiezVecesMasPoderosoCombateYyDebeGanar(){
		this.campeon.setEnergia(50);
		combate.combatir();
		//gana el campeon
		assertEquals(campeon, combate.getGanador());
		assertTrue(combate.sinEnergia(retador,combate.getDañoAcumuladoRetador())) ;
	}

	/**
	 * test para probar el combate  pero usando el dummy de ataques, el cual devuelve un ataque que 
	 * es un  quinto de la energia del bicho atacante 
	 */
	@Test 
	public void testCombateDummmyRetadorDobleEnergiaQueCampeonAmboMenoresADiez(){
		//gana e retador ya que tiene mas energia y el campeon se quedara sin energia antes de los 10 ataques
		this.campeon.setEnergia(8);
		this.retador.setEnergia(9);
		combate.combatirDummy();
		assertEquals(retador, combate.getGanador());	
	}

	/**
	 * test para probar el combate  pero usando el dummy de ataques, el cual devuelve un ataque que 
	 * es un  quinto de la energia del bicho atacante 
	 */
	@Test 
	public void testCombateDummmyRetadorDobleEnergiaQueCampeonAmboMAyoresADiezGanaELCampeon(){
		//gana el campeon ya que tiene menos energia pero ambos tienes mas de 10 punto,
		//x lo que se agotan los ataques
		this.campeon.setEnergia(18);
		this.retador.setEnergia(19);
		combate.combatirDummy();
		assertEquals(campeon, combate.getGanador());
		assertEquals(20, combate.getAtaques().size());
	}


}
		
	

