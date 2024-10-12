package negocio;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import negocio.factoriaNegocio.ComprobadorSintactico;

public class TestComprobadorSintactico {
	
	@Test
	public void direccion() {
		assertTrue(ComprobadorSintactico.isDireccion("hola"));
		assertFalse(ComprobadorSintactico.isDireccion(""));
		assertFalse(ComprobadorSintactico.isDireccion("1"));
	}
	
	@Test
	public void nombre() {
		assertTrue(ComprobadorSintactico.isNombre("pepe"));
		assertFalse(ComprobadorSintactico.isNombre(""));
		assertFalse(ComprobadorSintactico.isNombre("1"));
	}
	
	@Test
	public void nif() {
		assertTrue(ComprobadorSintactico.isNIF("12345678H"));
		assertFalse(ComprobadorSintactico.isNIF(""));
		assertFalse(ComprobadorSintactico.isNIF("123456789"));
	}
	
	@Test
	public void tlf() {
		assertTrue(ComprobadorSintactico.isTlf("123456789"));
		assertFalse(ComprobadorSintactico.isTlf(""));
		assertFalse(ComprobadorSintactico.isTlf("hola"));
	}
	
}
