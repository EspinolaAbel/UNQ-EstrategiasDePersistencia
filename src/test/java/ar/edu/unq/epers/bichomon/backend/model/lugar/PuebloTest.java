package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;

public class PuebloTest {

	private Pueblo pueblo;

	@Before
	public void setUp() throws Exception {
		this.pueblo = new Pueblo("PuebloGenerico"); 
	}

	
	/** Dado un pueblo intento abandonar un bicho en él. Como en este lugar no se permite abandonar
	 * bichos se lanza una UbicacionIncorrectaException.
	 * @author ae */
	@Test
	public void dadoUnPuebloIntentoAbandonarUnBichoYSeLanzaUnaUbicacionIncorrectaException() {
		Bicho bicho = new Bicho(new Especie());
		
		try {
			pueblo.recibirBichoAbandonado(bicho);
			fail("Nunca se lanzó la exception...");
		}
		catch(UbicacionIncorrectaException e) {
			assertEquals(e.getMessage(), "En el lugar \""+pueblo.getNombre()+"\" no se permite abandonar bichos.");
		}
	}
	
	
//	@Test
//	public void 
	
	public int test() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(10);a.add(20);a.add(30);a.add(40);
		Random r = new Random();
		int num = r.nextInt(40);
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(int i = 0; i<a.size(); i++) {
			if(a.get(i) >= num) {
				array.add(a.get(i));
			}
		}
		num = r.nextInt(array.size());
//		System.out.println(num);
		return array.get(num);
	}

//	@Test
	public void test1() {
		int cua = 0;
		int tre = 0;
		int vei = 0;
		int die = 0;
				
		for(int i=0; i<4; i++){
			int r = this.test();
			switch (r) {
			case 10: die++;break;
			case 20: vei++;break;
			case 30: tre++;break;
			case 40: cua++;break;
			}
		}
		
		System.out.println("10: "+die);
		System.out.println("20: "+vei);
		System.out.println("30: "+tre);
		System.out.println("40: "+cua);
	}
}
