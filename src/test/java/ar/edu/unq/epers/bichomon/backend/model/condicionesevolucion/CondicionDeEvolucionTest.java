package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

public abstract class CondicionDeEvolucionTest {

	protected Especie especieGenerica;
	protected Bicho bichoGenerico;
	protected CondicionDeEvolucion condicionOriginal;
	protected CondicionDeEvolucion condicionComparativa;
	
	
	@Before
	public void setUp() {
		especieGenerica = new Especie("Generico", TipoBicho.FUEGO);
		bichoGenerico = new Bicho(especieGenerica);
	}
	
	@Test
	public abstract void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue();
	@Test
	public abstract void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse();

	
//EQUALS Y HASH
	
	@Test
	public void dadasDosCondicionesDeEvolucionLasComparoPorEqualsYResultanSerIguales() {
		this.condicionOriginal.setMagnitudASuperar(999);
		this.condicionComparativa.setMagnitudASuperar(999);
		
		assertEquals(this.condicionOriginal, this.condicionComparativa);
	}
	
	@Test
	public void dadasDosCondicionesDeEvolucionLasComparoPorEqualsYResultanSerDistintas() {
		this.condicionOriginal.setMagnitudASuperar(999);
		this.condicionComparativa.setMagnitudASuperar(1000);
		
		assertNotEquals(this.condicionOriginal, this.condicionComparativa);
	}
	
	@Test
	public void dadasDosCondicionesDeEvolucionComparoSusHashYResultanSerIguales() {
		this.condicionOriginal.setMagnitudASuperar(999);
		this.condicionComparativa.setMagnitudASuperar(999);
		
		assertEquals(this.condicionOriginal.hashCode(), this.condicionComparativa.hashCode());
	}
	
	@Test
	public void dadasDosCondicionesDeEvolucionLasComparoSusHashYResultanSerDistintos() {
		this.condicionOriginal.setMagnitudASuperar(999);
		this.condicionComparativa.setMagnitudASuperar(1000);
		
		assertNotEquals(this.condicionOriginal.hashCode(), this.condicionComparativa.hashCode());
	}

}
