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
		 * creo  una clase que sorteara entre 5 Elementos
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
	/**
	 * testeo la coleccion de probabilidades acumuladas  es acorde a los probabilidades de cada  elemento de 
	 * la coleccion de especies con probbilidadm  y que la longitud es igual a la de la coleccion de especies 
	 * con probabilidad
	 */
		
		assertEquals(sorteoEspecie.getAcumulados().size(),5 );
		assertEquals(sorteoEspecie.maximo(), 103);
		assertTrue(sorteoEspecie.getAcumulados().get(0)== 5);
		assertTrue(sorteoEspecie.getAcumulados().get(1)== 20);
		assertTrue(sorteoEspecie.getAcumulados().get(2)== 71);
		assertTrue(sorteoEspecie.getAcumulados().get(3)== 102);
		assertTrue(sorteoEspecie.getAcumulados().get(4)== 103);
	}
	
	
	
	@Test
	public void testSorteo (){
		/**realizo un sorteo  con la coleccion de probabilidades acumuladas
		 *realizo  el sorteo con el mensaje  que me pasa l resultado de antemano y  con ese numero me 
		 *debe devolver el elemento que coresponde de la coleccion
		 *
		 * sorteo en la lista "Con la probabilidad ya sorteada en 55 y me devuelve el tercer elemento"
		 * sorteo en la lista "Con la probabilidad ya sorteada en 102 y me devuelve el cuarto  elemento"
		 * sorteo en la lista "Con la probabilidad ya sorteada en 103 y me devuelve el quinto elemento"
		 * sorteo en la lista "Con la probabilidad ya sorteada en 1 y me devuelve el primer elemento"
		 * sorteo en la lista "Con la probabilidad ya sorteada en 17 y me devuelve el segundo elemento"
		 */
		
		EspecieConProbabilidad e= this.sorteoEspecie.sortearEspecie(especies,55);
		assertEquals(this.especies.get(2), e);
		
		e= this.sorteoEspecie.sortearEspecie(especies,102);
		assertEquals(this.especies.get(3), e);
		
		e= this.sorteoEspecie.sortearEspecie(especies,103);
		assertEquals(this.especies.get(4), e);
		
		e= this.sorteoEspecie.sortearEspecie(especies,1);
		assertEquals(this.especies.get(0), e);
		
		e= this.sorteoEspecie.sortearEspecie(especies,17);
		assertEquals(this.especies.get(1), e);
	}

	
}	