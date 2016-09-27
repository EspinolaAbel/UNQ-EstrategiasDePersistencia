package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

public class SorteoEspeciesTest {
	SorteoEspecies sorteoEspecie;
	List <EspecieConProbabilidad> especies ;
	@Before
	public void setUp() {
		/**
		 * creo  una clase que sorteara ente 5 Elementos
		 */
		
		this.especies= new ArrayList<EspecieConProbabilidad>();
		
		Especie e= new Especie("lagartomon1", TipoBicho.AGUA);
		this.especies.add(new EspecieConProbabilidad(e,5));
	    e= new Especie("lagartomon2", TipoBicho.AGUA);
		this.especies.add(new EspecieConProbabilidad(e, 15  ));
		e= new Especie("lagartomon3", TipoBicho.AGUA);
		this.especies.add(new EspecieConProbabilidad(e, 51  ));
		e= new Especie("lagartomon4", TipoBicho.AGUA);
		this.especies.add(new EspecieConProbabilidad(e, 31));
		e= new Especie("lagartomon5", TipoBicho.AGUA);
		this.especies.add(new EspecieConProbabilidad(e, 1));
		
		sorteoEspecie =new SorteoEspecies(this.especies);

	}

	@Test
	public void testLaClaseCreadaTieneCincoElementosYunaProbabilidadAcumuladaDe() {//probabilidad acumualada=103
		assertEquals(sorteoEspecie.getResultados().size(),5 );
		
		assertTrue(sorteoEspecie.getResultados().get(0)== 5);
		assertTrue(sorteoEspecie.getResultados().get(1)== 20);
		assertTrue(sorteoEspecie.getResultados().get(2)== 71);
		assertTrue(sorteoEspecie.getResultados().get(3)== 102);
		assertTrue(sorteoEspecie.getResultados().get(4)== 103);
	}
	
	@Test
	public void testReasignarCantidadDeElementos(){
		
	}

	
	@Test
	public void testSorteo (){
		/**
		 * sorteo en la lista "Con la probabilidad ya sorteada en 55 y me devuelve el tercer elemento"
		 */
		
		EspecieConProbabilidad e= this.sorteoEspecie.sortearEspecie(especies,55);
		assertEquals(this.especies.get(2), e);
		 e= this.sorteoEspecie.sortearEspecie(especies,102);
		assertEquals(this.especies.get(3), e);
		 e= this.sorteoEspecie.sortearEspecie(especies,103);
		assertEquals(this.especies.get(4), e);
		 e= this.sorteoEspecie.sortearEspecie(especies,1);
		assertEquals(this.especies.get(0), e);
		
		
	}
}	