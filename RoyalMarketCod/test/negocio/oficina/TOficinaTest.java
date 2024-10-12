package negocio.oficina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TOficinaTest {
	
private TOficina mockTransfer;
	
	@Before
	public void setUp() {
		this.mockTransfer = new TOficina(1, "Oficina MS", true);
	}
	
	@Test
	public void testConstructWithName() {
		this.mockTransfer = new TOficina("Oficina MS");
		assertNotNull(this.mockTransfer);
		assertNotNull(this.mockTransfer.getId());
		assertNotNull(this.mockTransfer.getNombre());
		assertNotNull(this.mockTransfer.isActivo());
	}
	
	@Test
	public void testConstructWithAllArgs() {
		this.mockTransfer = new TOficina(1, "Oficina MS", true);
		assertNotNull(this.mockTransfer);
		assertNotNull(this.mockTransfer.getId());
		assertNotNull(this.mockTransfer.getNombre());
		assertNotNull(this.mockTransfer.isActivo());
	}
	
	@Test
	public void testGetId() {
		assertEquals(1, this.mockTransfer.getId());
	}

	@Test
	public void testSetId() {
		this.mockTransfer.setId(2);
		assertEquals(2, this.mockTransfer.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Oficina MS", this.mockTransfer.getNombre());
	}

	@Test
	public void testSetNombre() {
		this.mockTransfer.setNombre("Nueva Oficina");
		assertEquals("Nueva Oficina", this.mockTransfer.getNombre());
	}

	@Test
	public void testIsActivo() {
		assertEquals(true, this.mockTransfer.isActivo());
	}

	@Test
	public void testSetActivo() {
		this.mockTransfer.setActivo(false);
		assertEquals(false, this.mockTransfer.isActivo());
	}
	
	@Test
	public void testToStringWithActive() {
		assertNotNull(this.mockTransfer.toString());
	}
	

	@Test
	public void testToStringWithInactive() {
		this.mockTransfer.setActivo(false);
		assertNotNull(this.mockTransfer.toString());
	}

}
