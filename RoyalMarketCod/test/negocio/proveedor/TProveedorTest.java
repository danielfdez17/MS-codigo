package negocio.proveedor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TProveedorTest {

    private TProveedor mockEntity;

    @Before
    public void setUp() {
        this.mockEntity = new TProveedor(123456789, "ABC123", "123 Main St");
    }

    @Test
    public void testConstructWithValues() {
        assertNotNull(this.mockEntity);
        assertEquals(0, this.mockEntity.getId());
        assertEquals(123456789, this.mockEntity.getTlf());
        assertEquals("ABC123", this.mockEntity.getNif());
        assertEquals("123 Main St", this.mockEntity.getDireccion());
        assertEquals(true, this.mockEntity.isActivo());
    }

    @Test
    public void testConstruct() {
        this.mockEntity = new TProveedor(1, 987654321, "XYZ789", "456 Side St", false);
        assertNotNull(this.mockEntity);
        assertEquals(1, this.mockEntity.getId());
        assertEquals(987654321, this.mockEntity.getTlf());
        assertEquals("XYZ789", this.mockEntity.getNif());
        assertEquals("456 Side St", this.mockEntity.getDireccion());
        assertEquals(false, this.mockEntity.isActivo());
    }

    @Test
    public void testGetSetId() {
        mockEntity.setId(1);
        assertEquals(1, mockEntity.getId());
    }

    @Test
    public void testGetSetTlf() {
        mockEntity.setTlf(987654321);
        assertEquals(987654321, mockEntity.getTlf());
    }

    @Test
    public void testGetSetNif() {
        mockEntity.setNif("XYZ789");
        assertEquals("XYZ789", mockEntity.getNif());
    }

    @Test
    public void testGetSetDireccion() {
        mockEntity.setDireccion("456 Side St");
        assertEquals("456 Side St", mockEntity.getDireccion());
    }

    @Test
    public void testGetSetActivo() {
        mockEntity.setActivo(false);
        assertEquals(false, mockEntity.isActivo());
    }

    @Test
    public void testToString() {
        assertNotNull(mockEntity.toString());
    }
}

