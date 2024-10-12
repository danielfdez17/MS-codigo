package negocio.tarea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import negocio.proyecto.ONProyecto;

public class ONTareaTest {

    private ONTarea mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new ONTarea("Tarea1");
    }

    @Test
    public void testConstructWithValues() {
        assertNotNull(this.mockEntity);
        assertEquals(0, this.mockEntity.getId());
        assertEquals("Tarea1", this.mockEntity.getNombre());
        assertEquals(false, this.mockEntity.isActivo());
    }

    @Test
    public void testConstruct() {
        this.mockEntity = new ONTarea(1, "Tarea2", false);
        assertNotNull(this.mockEntity);
        assertEquals(1, this.mockEntity.getId());
        assertEquals("Tarea2", this.mockEntity.getNombre());
        assertEquals(false, this.mockEntity.isActivo());
    }

    @Test
    public void testGetSetId() {
        mockEntity.setId(1);
        assertEquals(1, mockEntity.getId());
    }

    @Test
    public void testGetSetNombre() {
        mockEntity.setNombre("Tarea3");
        assertEquals("Tarea3", mockEntity.getNombre());
    }

    @Test
    public void testGetVersion() {
        assertEquals(0, mockEntity.getVersion());
    }

    @Test
    public void testSetVersion() {
        mockEntity.setVersion(1);
        assertEquals(1, mockEntity.getVersion());
    }

    @Test
    public void testGetSetActivo() {
        mockEntity.setActivo(false);
        assertEquals(false, mockEntity.isActivo());
    }

    @Test
    public void testToTransfer() {
    	this.mockEntity.setProyecto(new ONProyecto(1));
        TTarea transferEntity = mockEntity.toTransfer();
        assertEquals(0, transferEntity.getId());
        assertEquals("Tarea1", transferEntity.getNombre());
        assertEquals(false, transferEntity.isActivo());
    }
}
