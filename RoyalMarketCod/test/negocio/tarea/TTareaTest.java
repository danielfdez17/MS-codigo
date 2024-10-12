package negocio.tarea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TTareaTest {

    private TTarea mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new TTarea("Tarea1", 1);
    }

    @Test
    public void testConstructWithValues() {
        assertNotNull(this.mockEntity);
        assertEquals(0, this.mockEntity.getId());
        assertEquals("Tarea1", this.mockEntity.getNombre());
        assertEquals(1, this.mockEntity.getIdProyecto());
        assertEquals(true, this.mockEntity.isActivo());
    }

    @Test
    public void testConstruct() {
        this.mockEntity = new TTarea(1, "Tarea2", 2, false);
        assertNotNull(this.mockEntity);
        assertEquals(1, this.mockEntity.getId());
        assertEquals("Tarea2", this.mockEntity.getNombre());
        assertEquals(2, this.mockEntity.getIdProyecto());
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
    public void testGetSetIdProyecto() {
        mockEntity.setIdProyecto(3);
        assertEquals(3, mockEntity.getIdProyecto());
    }

    @Test
    public void testGetSetActivo() {
        mockEntity.setActivo(false);
        assertEquals(false, mockEntity.isActivo());
    }

    @Test
    public void testToString() {
        String expectedToString = "ID: 0\nNombre: Tarea1\nID proyecto: 1\nActivo: si\n";
        assertEquals(expectedToString, mockEntity.toString());
    }
}
