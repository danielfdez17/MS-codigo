package negocio.trabajador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TTrabajadorJParcialTest {

    private TTrabajadorJParcial mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new TTrabajadorJParcial(1, 123456789, "ABC123", "John Doe", "123 Main St", 1, 20, 25.0, true);
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
        assertEquals(20, this.mockEntity.getHoras());
        assertEquals(25.0, this.mockEntity.getPrecioHora(), 0.001);
    }

    @Test
    public void testGetHoras() {
        assertEquals(20, this.mockEntity.getHoras());
    }

    @Test
    public void testSetHoras() {
        this.mockEntity.setHoras(30);
        assertEquals(30, this.mockEntity.getHoras());
    }

    @Test
    public void testGetPrecioHora() {
        assertEquals(25.0, this.mockEntity.getPrecioHora(), 0.001);
    }

    @Test
    public void testSetPrecioHora() {
        this.mockEntity.setPrecioHora(30.0);
        assertEquals(30.0, this.mockEntity.getPrecioHora(), 0.001);
    }

    @Test
    public void testToString() {
        String expected = "ID: 1\n" +
                "Telefono: 123456789\n" +
                "Nombre: John Doe\n" +
                "Direccion: 123 Main St\n" +
                "ID almacen: 1\n" +
                "Horas: 20\n" +
                "Precio por hora: 25.0\n" +
                "Activo: si\n";
        assertEquals(expected, this.mockEntity.toString());
    }
}
