package negocio.trabajador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TTrabajadorJCompletaTest {

    private TTrabajadorJCompleta mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new TTrabajadorJCompleta(1, 123456789, "ABC123", "John Doe", "123 Main St", 1, 50000.0, true);
    }

    @Test
    public void testConstructWithValues() {
        assertNotNull(this.mockEntity);
        assertEquals(1, this.mockEntity.getId());
        assertEquals(123456789, this.mockEntity.getTlf());
        assertEquals("ABC123", this.mockEntity.getNIF());
        assertEquals("John Doe", this.mockEntity.getNombre());
        assertEquals("123 Main St", this.mockEntity.getDireccion());
        assertEquals(1, this.mockEntity.getIdAlmacen());
        assertEquals(true, this.mockEntity.isActivo());
        assertEquals(50000.0, this.mockEntity.getSueldoBase(), 0.001);
    }

    @Test
    public void testGetSueldoBase() {
        assertEquals(50000.0, this.mockEntity.getSueldoBase(), 0.001);
    }

    @Test
    public void testSetSueldoBase() {
        this.mockEntity.setSueldoBase(60000.0);
        assertEquals(60000.0, this.mockEntity.getSueldoBase(), 0.001);
    }

    @Test
    public void testToString() {
        String expected = "ID: 1\n" +
                "Telefono: 123456789\n" +
                "Nombre: John Doe\n" +
                "Direccion: 123 Main St\n" +
                "ID almacen: 1\n" +
                "Sueldo base: 50000.0\n" +
                "Activo: si\n";
        assertEquals(expected, this.mockEntity.toString());
    }
}
