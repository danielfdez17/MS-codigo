package negocio.proyecto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.empleado.ONEmpleado;
import negocio.tarea.ONTarea;


public class ONProyectoTest {
	
	private ONProyecto mockEntity;
	
	@Before
	public void setUp() {
		this.mockEntity = new ONProyecto(1, "Proyecto MS", true);
	}
	
	@Test
	public void testEmptyConstruct() {
		this.mockEntity = new ONProyecto();
		assertNotNull(this.mockEntity);
	}
	
	@Test
	public void testConstructWithName() {
		this.mockEntity = new ONProyecto("Proyecto MS");
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.isActivo());
		assertNotNull(this.mockEntity.getTask());
		assertNotNull(this.mockEntity.getEmpleados());
	}
	
	@Test
	public void testConstructWithNameIdActive() {
		this.mockEntity = new ONProyecto(1, "Proyecto MS", true);
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.isActivo());
		assertNotNull(this.mockEntity.getTask());
		assertNotNull(this.mockEntity.getEmpleados());
	}
	
	@Test
	public void testConstructWithTransfer() {
		TProyecto pT = new TProyecto(1, "Proyecto MS", true);
		this.mockEntity = new ONProyecto(pT);
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.isActivo());
		assertNotNull(this.mockEntity.getTask());
		assertNotNull(this.mockEntity.getEmpleados());
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
		assertEquals("Proyecto MS", this.mockEntity.getNombre());
	}

	@Test
	public void testSetNombre() {
		this.mockEntity.setNombre("Nuevo Proyecto");
		assertEquals("Nuevo Proyecto", this.mockEntity.getNombre());
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
	public void testSetTareas() {
		List<ONTarea> nuevasTareas = new ArrayList<>();
		this.mockEntity.setTareas(nuevasTareas);
		assertEquals(nuevasTareas, this.mockEntity.getTask());
	}

	@Test
	public void testSetEmpleados() {
		List<ONEmpleado> nuevosEmpleados = new ArrayList<>();
		this.mockEntity.setEmpleados(nuevosEmpleados);
		assertEquals(nuevosEmpleados, this.mockEntity.getEmpleados());
	}
	
	@Test
	public void testTransfer() {
		TProyecto transfer = this.mockEntity.toTransfer();
		assertNotNull(transfer);
		assertEquals(transfer.getId(), this.mockEntity.getId());
		assertEquals(transfer.getNombre(), this.mockEntity.getNombre());
		assertEquals(transfer.isActivo(), this.mockEntity.isActivo());
	}

}
