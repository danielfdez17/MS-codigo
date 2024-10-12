package negocio.almacen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TAlmacenTest {

    private TAlmacen mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new TAlmacen("123 Main St");
    }

    @Test
    public void testConstructWithValues() {
        assertNotNull(this.mockEntity);
        assertEquals(0, this.mockEntity.getId());
        assertEquals("123 Main St", this.mockEntity.getDireccion());
        assertEquals(true, this.mockEntity.isActivo());
    }

    @Test
    public void testConstruct() {
        this.mockEntity = new TAlmacen(1, "456 Side St", false);
        assertNotNull(this.mockEntity);
        assertEquals(1, this.mockEntity.getId());
        assertEquals("456 Side St", this.mockEntity.getDireccion());
        assertEquals(false, this.mockEntity.isActivo());
    }

    @Test
    public void testGetSetId() {
        mockEntity.setId(1);
        assertEquals(1, mockEntity.getId());
    }

    @Test
    public void testGetSetDireccion() {
        mockEntity.setDireccion("789 Back St");
        assertEquals("789 Back St", mockEntity.getDireccion());
    }

    @Test
    public void testGetSetActivo() {
        mockEntity.setActivo(false);
        assertEquals(false, mockEntity.isActivo());
    }

    @Test
    public void testToString() {
        String expectedString = "ID: 0\n" +
                               "Direccion: 123 Main St\n";
        assertEquals(expectedString, mockEntity.toString());
    }
}
