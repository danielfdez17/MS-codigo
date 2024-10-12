package negocio.trabajador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TTrabajadorTest {

    private TTrabajador mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new TTrabajador(1, 123456789, "ABC123", "John Doe", "123 Main St", 1, true);
    }

    @Test
    public void testEmptyConstruct() {
        this.mockEntity = new TTrabajador(123456789, "ABC123", "John Doe", "123 Main St", 1);
        assertNotNull(this.mockEntity);
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
    public void testGetTlf() {
        assertEquals(123456789, this.mockEntity.getTlf());
    }

    @Test
    public void testSetTlf() {
        this.mockEntity.setTlf(987654321);
        assertEquals(987654321, this.mockEntity.getTlf());
    }

    @Test
    public void testGetNIF() {
        assertEquals("ABC123", this.mockEntity.getNIF());
    }

    @Test
    public void testSetNIF() {
        this.mockEntity.setNIF("XYZ789");
        assertEquals("XYZ789", this.mockEntity.getNIF());
    }

    @Test
    public void testGetNombre() {
        assertEquals("John Doe", this.mockEntity.getNombre());
    }

    @Test
    public void testSetNombre() {
        this.mockEntity.setNombre("Jane Doe");
        assertEquals("Jane Doe", this.mockEntity.getNombre());
    }

    @Test
    public void testGetDireccion() {
        assertEquals("123 Main St", this.mockEntity.getDireccion());
    }

    @Test
    public void testSetDireccion() {
        this.mockEntity.setDireccion("456 Oak St");
        assertEquals("456 Oak St", this.mockEntity.getDireccion());
    }

    @Test
    public void testGetIdAlmacen() {
        assertEquals(1, this.mockEntity.getIdAlmacen());
    }

    @Test
    public void testSetIdAlmacen() {
        this.mockEntity.setIdAlmacen(2);
        assertEquals(2, this.mockEntity.getIdAlmacen());
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
    public void testToString() {
        String expected = "ID: 1\n" +
                "Telefono: 123456789\n" +
                "Nombre: John Doe\n" +
                "Direccion: 123 Main St\n" +
                "ID almacen: 1\n";
        assertEquals(expected, this.mockEntity.toString());
    }
}
