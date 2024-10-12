package negocio.oficina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.empleado.ONEmpleado;

public class ONOficinaTest {
	private ONOficina mockEntity;
	
	@Before
	public void setUp() {
		this.mockEntity = new ONOficina(1, "Oficina MS", true);
	}
	
	@Test
	public void testEmptyConstruct() {
		this.mockEntity = new ONOficina();
		assertNotNull(this.mockEntity);
	}
	
	@Test
	public void testConstructWithName() {
		this.mockEntity = new ONOficina("Oficina MS");
		this.mockEntity.setEmpleados(new ArrayList<>());
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.getEmpleados());
	}
	
	@Test
	public void testConstructWithNameIdActive() {
		this.mockEntity = new ONOficina(1, "Oficina MS", true);
		this.mockEntity.setEmpleados(new ArrayList<>());
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.isActivo());
		assertNotNull(this.mockEntity.getEmpleados());
	}
	
	@Test
	public void testConstructWithTransfer() {
		TOficina oT = new TOficina(1, "Oficina MS", true);
		this.mockEntity = new ONOficina(oT);
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.isActivo());
	}
	
	@Test
	public void testGetId() {
		assertEquals(1, this.mockEntity.getId());
	}

	@Test
	public void testSetId() {
		this.mockEntity.setId(2);
		assertEquals(2, this.mockEntity.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Oficina MS", this.mockEntity.getNombre());
	}

	@Test
	public void testSetNombre() {
		this.mockEntity.setNombre("Nueva Oficina");
		assertEquals("Nueva Oficina", this.mockEntity.getNombre());
	}

	@Test
	public void testGetVersion() {
		assertEquals(0, this.mockEntity.getVersion());
	}

	@Test
	public void testSetVersion() {
		this.mockEntity.setVersion(1);
		assertEquals(1, this.mockEntity.getVersion());
	}

	@Test
	public void testIsActivo() {
		assertEquals(true, this.mockEntity.isActivo());
	}

	@Test
	public void testSetActivo() {
		this.mockEntity.setActivo(false);
		assertEquals(false, this.mockEntity.isActivo());
	}

	@Test
	public void testSetEmpleados() {
		List<ONEmpleado> nuevosEmpleados = new ArrayList<>();
		this.mockEntity.setEmpleados(nuevosEmpleados);
		assertEquals(nuevosEmpleados, this.mockEntity.getEmpleados());
	}
	
	@Test
	public void testTransfer() {
		TOficina transfer = this.mockEntity.toTransfer();
		assertNotNull(transfer);
		assertEquals(transfer.getId(), this.mockEntity.getId());
		assertEquals(transfer.getNombre(), this.mockEntity.getNombre());
		assertEquals(transfer.isActivo(), this.mockEntity.isActivo());
	}

}
