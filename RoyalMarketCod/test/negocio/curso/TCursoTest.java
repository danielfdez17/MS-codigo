package negocio.curso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TCursoTest {

    private TCurso trasferPrueba;

    @Before
    public void setUp() {
        this.trasferPrueba = new TCurso(1, "Mates",1,1, true);
    }

    @Test
    public void testConstructWithName() {
        this.trasferPrueba = new TCurso("Mates",1,1);
        assertNotNull(this.trasferPrueba);
        assertNotNull(this.trasferPrueba.getId());
        assertNotNull(this.trasferPrueba.getNombre());
        assertNotNull(this.trasferPrueba.isActivo());
    }

    @Test
    public void testConstructWithAllArgs() {
        this.trasferPrueba = new TCurso(1, "Mates",1,1, true);
        assertNotNull(this.trasferPrueba);
        assertNotNull(this.trasferPrueba.getId());
        assertNotNull(this.trasferPrueba.getNombre());
        assertNotNull(this.trasferPrueba.isActivo());
    }

    @Test
    public void testGetId() {
        assertEquals(1, this.trasferPrueba.getId());
    }

    @Test
    public void testSetId() {
        this.trasferPrueba.setId(2);
        assertEquals(2, this.trasferPrueba.getId());
    }
    @Test
    public void testGetNombre() {
        assertEquals("Mates", this.trasferPrueba.getNombre());
    }

    @Test
    public void testSetNombre() {
        this.trasferPrueba.setNombre("Lenuga");
        assertEquals("Lenuga", this.trasferPrueba.getNombre());
    }

    @Test
    public void testIsActivo() {
        assertEquals(true, this.trasferPrueba.isActivo());
    }

    @Test
    public void testSetActivo() {
        this.trasferPrueba.setActivo(false);
        assertEquals(false, this.trasferPrueba.isActivo());
    }

    @Test
    public void testToStringWithActive() {
        assertNotNull(this.trasferPrueba.toString());
    }


    @Test
    public void testToStringWithInactive() {
        this.trasferPrueba.setActivo(false);
        assertNotNull(this.trasferPrueba.toString());
    }
}