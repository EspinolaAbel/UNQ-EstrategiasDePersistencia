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
	List <Especie> especies ;
	@Before
	public void setUp() {
		/**
		 * creo  una clase que sorteara ente 5 Elementos
		 */
		this.sorteoEspecie= new SorteoEspecies(5);
		this.especies= new ArrayList<Especie>();
		this.especies.add( new Especie("lagartomon1", TipoBicho.AGUA)  );
		this.especies.add( new Especie("lagartomon2", TipoBicho.AGUA)  );
		this.especies.add( new Especie("lagartomon3", TipoBicho.AGUA)  );
		this.especies.add( new Especie("lagartomon4", TipoBicho.AGUA)  );
		this.especies.add( new Especie("lagartomon5", TipoBicho.AGUA)  );
		
		
	}

	@Test
	public void testLaClaseCreadaTieneCincoElementos() {
		assertEquals(sorteoEspecie.getResultados().size(),5 );
	}
	
	@Test
	public void testResultadosMenoresACienYCadaUnaEsMAyorQueLaAnterior(){
		//testeo que todos los elementos son menosres a 100%,las probablidades en la coleccion se acumulasn pero nunca supeeran el 100%
		//que no falle es algo al menos
		//no sirve del todo ya que las probabilidades asignadas son aleatorias
		Integer previa=0;
		
		for (Integer i:this.sorteoEspecie.getResultados()){
			System.out.println(i);//solo para ver lo que sale x  consola
			assertTrue(i<=100);
			assertTrue(i>=previa);
			previa=i;
		}
	}		
	
	
	@Test
	public void testReasignarCantidadDeElementos(){
		
		this.sorteoEspecie.reasignar(6);
		assertEquals(sorteoEspecie.getResultados().size(),6 );
		
	}

	
	@Test
	public void testSorteoDeUnSoloElemento (){
		/**
		 * solo voyn a tener un elemento en la lista de especies
		 */
		this.sorteoEspecie.reasignar(1);
		Especie e= this.sorteoEspecie.sortearEspecie(especies);
		assertEquals(this.especies.get(0), e);
		
		
		
	}
	

	@Test
	public void testSorteoDondeSoloElUltimoDeLaListaTieneProbabilidades (){
		/**
		 * solo voyn a tener un elementocon probabilidad en la lista de resultados
		 */
		 ArrayList <Integer> res = new ArrayList <Integer>();
		 res.add(0);
		 res.add(0);
		 res.add(100);
		 
		 res.add(0);
		 res.add(0);

		 
		 this.sorteoEspecie.setResultados(res);
		Especie e= this.sorteoEspecie.sortearEspecie(especies);
		assertEquals(this.especies.get(2), e);
		
		
		
	}
	
	
//METODOS AUXILIARES PARA TEST
	
	public EspecieConProbabilidad especieConProbabilidad(Integer prob) {
		Especie e = new Especie("nn", TipoBicho.FUEGO);
		EspecieConProbabilidad ep = new EspecieConProbabilidad(e, prob);
		return ep;
	}
}
