package negocio.empleado;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TEmpleadoTecnicoTest {
	
    private TEmpleadoTecnico mockEntity;
    
    @Before
    public void setUp() {
        this.mockEntity = new TEmpleadoTecnico(123456789, "ABC123", "John Doe", "123 Main St", "reporteTrabajo", 50000.0, 1);
    }
    
    @Test
    public void testConstructWithValues() {
        assertNotNull(this.mockEntity);
        assertEquals(0, this.mockEntity.getId());
        assertEquals(123456789, this.mockEntity.getTlf());
        assertEquals("ABC123", this.mockEntity.getNif());
        assertEquals("John Doe", this.mockEntity.getNombre());
        assertEquals("123 Main St", this.mockEntity.getDireccion());
        assertEquals(true, this.mockEntity.isActivo());
        assertEquals("reporteTrabajo", this.mockEntity.getReporteTrabajo());
        assertEquals(50000.0, this.mockEntity.getSueldo(), 0.001);
        assertEquals(1, this.mockEntity.getIdOficina());
    }
    
    @Test
    public void testConstruct() {
        this.mockEntity = new TEmpleadoTecnico(1, 123456789, "ABC123", "John Doe", "123 Main St", "reporteTrabajo", 50000.0, 1, true);
        assertNotNull(this.mockEntity);
        assertEquals(1, this.mockEntity.getId());
        assertEquals(123456789, this.mockEntity.getTlf());
        assertEquals("ABC123", this.mockEntity.getNif());
        assertEquals("John Doe", this.mockEntity.getNombre());
        assertEquals("123 Main St", this.mockEntity.getDireccion());
        assertEquals(true, this.mockEntity.isActivo());
        assertEquals("reporteTrabajo", this.mockEntity.getReporteTrabajo());
        assertEquals(50000.0, this.mockEntity.getSueldo(), 0.001);
        assertEquals(1, this.mockEntity.getIdOficina());
    }

    @Test
    public void testGetSetId() {
        mockEntity.setId(1);
        assertEquals(1, mockEntity.getId());
    }

    @Test
    public void testGetSetTlf() {
        mockEntity.setTlf(123456789);
        assertEquals(123456789, mockEntity.getTlf());
    }

    @Test
    public void testGetSetNif() {
        mockEntity.setNif("ABC123");
        assertEquals("ABC123", mockEntity.getNif());
    }

    @Test
    public void testGetSetNombre() {
        mockEntity.setNombre("John Doe");
        assertEquals("John Doe", mockEntity.getNombre());
    }

    @Test
    public void testGetSetDireccion() {
        mockEntity.setDireccion("123 Main St");
        assertEquals("123 Main St", mockEntity.getDireccion());
    }

    @Test
    public void testGetSetSueldo() {
        mockEntity.setSueldo(50000.0);
        assertEquals(50000.0, mockEntity.getSueldo(), 0.001);
    }

    @Test
    public void testGetSetActivo() {
        mockEntity.setActivo(true);
        assertEquals(true, mockEntity.isActivo());
    }
    
    @Test
    public void testGetSetReporteTrabajo() {
        mockEntity.setReporteTrabajo("a");
        assertEquals("a", mockEntity.getReporteTrabajo());
    }

    @Test
    public void testGetSetIdOficina() {
        mockEntity.setIdOficina(2);
        assertEquals(2, mockEntity.getIdOficina());
    }
    
    @Test
    public void testToString() {
        assertNotNull(this.mockEntity.toString());
    }

    @Test
    public void testGetNomina() {
        assertEquals(this.mockEntity.getSueldo() * this.mockEntity.getReporteTrabajo().length() / 100 , this.mockEntity.getNomina(), 0.001);
    }
}
