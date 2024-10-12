package negocio.curso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.empleado.ONEmpleado;
import negocio.tarea.ONTarea;


public class ONCursoTest {
	
	private ONCurso mockEntity;
	
	@Before
	public void setUp() {
		this.mockEntity = new ONCurso(1, "Mates",2,3, true);
	}
	
	@Test
	public void testEmptyConstruct() {
		this.mockEntity = new ONCurso();
		assertNotNull(this.mockEntity);
	}
	
	@Test
	public void testConstructWithName() {
		this.mockEntity = new ONCurso("Mates",2,3);
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.getPlazas());
		assertNotNull(this.mockEntity.getHorasDia());
		assertNotNull(this.mockEntity.isActivo());
	}
	
	@Test
	public void testConstructWithNameIdActive() {
		this.mockEntity = new ONCurso(1, "Mates",2,3, true);
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.getPlazas());
		assertNotNull(this.mockEntity.getHorasDia());
		assertNotNull(this.mockEntity.isActivo());
	}
	
	@Test
	public void testConstructWithTransfer() {
		TCurso tCurso = new TCurso(1, "Mates",2,3, true);
		this.mockEntity = new ONCurso(tCurso);
		assertNotNull(this.mockEntity);
		assertNotNull(this.mockEntity.getNombre());
		assertNotNull(this.mockEntity.getPlazas());
		assertNotNull(this.mockEntity.getHorasDia());
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
		assertEquals("Mates", this.mockEntity.getNombre());
	}

	@Test
	public void testSetNombre() {
		this.mockEntity.setNombre("Lengua");
		assertEquals("Lengua", this.mockEntity.getNombre());
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
	public void testSetPlazas() {
		this.mockEntity.setPlazas(3);
		assertEquals(3, this.mockEntity.getPlazas());
	}

	@Test
	public void testSetHorasDia() {
		this.mockEntity.setHorasDia(5);
		assertEquals(5, this.mockEntity.getHorasDia());
	}
	
	@Test
	public void testTransfer() {
		TCurso transfer = this.mockEntity.toTransfer();
		assertNotNull(transfer);
		assertEquals(transfer.getId(), this.mockEntity.getId());
		assertEquals(transfer.getNombre(), this.mockEntity.getNombre());
		assertEquals(transfer.getPlazas(),this.mockEntity.getPlazas());
		assertEquals(transfer.getHorasDia(),this.mockEntity.getHorasDia());
		assertEquals(transfer.isActivo(), this.mockEntity.isActivo());
	}

}