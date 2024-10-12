package negocio.oficina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.RunListener.ThreadSafe;

import negocio.empleado.SAEmpleado;
import negocio.empleado.TEmpleadoAdministrador;
import negocio.empleado.TEmpleadoTecnico;
import negocio.factoriaNegocio.FactoriaNegocio;

public class SAOficinaImpTest {

	private static final int tlf = 123456789;
	private static final String nif = "12345678";
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final double sueldo = 50.7;
	
	private SAOficina saOficina;
	private TOficina oficina;
	private SAEmpleado saEmpleado;
	
	@Before
	public void setUp() {
		this.saOficina = FactoriaNegocio.getInstancia().crearSAOficina();
		this.saEmpleado = FactoriaNegocio.getInstancia().crearSAEmpleado();
	}
	
	@Test
	public void altaOK() {
		oficina = new TOficina("altaOficinaOK");
		assertTrue(this.saOficina.altaOficina(oficina) > 0);
	}
	
	@Test
	public void altaKO() {
		oficina = new TOficina("errorsinactico 1");
		assertTrue(this.saOficina.altaOficina(oficina) == SAOficina.ERROR_SINTACTICO);
		oficina = new TOficina("altaOficinaKO");
		this.saOficina.altaOficina(oficina);
		assertTrue(this.saOficina.altaOficina(oficina) == SAOficina.OFICINA_ACTIVA);
		this.saOficina.bajaOficina(oficina.getId());
	}
	
	@Test
	public void testAltaOficinaFailSintactico() {
		TOficina mockTransfer = new TOficina("");
		int errorSintaxis = this.saOficina.altaOficina(mockTransfer); 
		assertEquals(SAOficina.ERROR_SINTACTICO, errorSintaxis);
	}
	
	@Test
	public void testAltaOficinaSuccess() {
	    String generatedRandomName = this.generateNameUnit();
	    TOficina oficina = new TOficina(generatedRandomName);
	    int successAltaProject = this.saOficina.altaOficina(oficina);
	    assertTrue(successAltaProject > 0);
	}
	
	@Test
	public void testBuscarOficinaFail() {
		int id = 0;
		TOficina oficina = this.saOficina.buscarOficina(id);
		assertNull(oficina);
	}
	
	@Test
	public void testBuscarOficinaSuccess() {
		List<TOficina> listOficinas = this.saOficina.listarOficinas();
		if (!listOficinas.isEmpty()) {
			TOficina oficina = this.saOficina.buscarOficina(listOficinas.get(0).getId());
			assertNotNull(oficina);
		} else {
			TOficina mockTransfer = new TOficina(this.generateNameUnit());
			int id = this.saOficina.altaOficina(mockTransfer);
			TOficina oficina = this.saOficina.buscarOficina(id);
			assertNotNull(oficina);
		}
	}
	
	@Test
	public void testListarOficinaSuccess() {
		List<TOficina> listOficinas = this.saOficina.listarOficinas();
		assertNotNull(listOficinas);
	}
	
	@Test
	public void testActualizarOficinaFailBySintax() {
		TOficina mockTransfer = new TOficina("");
		int errorSintaxis = this.saOficina.actualizarOficina(mockTransfer); 
		assertEquals(SAOficina.ERROR_SINTACTICO, errorSintaxis);
	}
	
	@Test
	public void testActualizarOficinaSuccess() {
		List<TOficina> listOficinas = this.saOficina.listarOficinas();
		if (!listOficinas.isEmpty()) {
			TOficina t1 = listOficinas.get(0);
			t1.setNombre(this.generateNameUnit());
			int idOficina = this.saOficina.actualizarOficina(t1);
			assertEquals(idOficina, t1.getId());
		} else {
			TOficina t1 = new TOficina(this.generateNameUnit());
			TOficina t2 = new TOficina(this.generateNameUnit());
			int id1 = this.saOficina.altaOficina(t1);
			int id2 = this.saOficina.altaOficina(t2);
			t1.setId(id1); 
			t2.setId(id2); 
			t1.setNombre(this.generateNameUnit());
			t2.setNombre(this.generateNameUnit());
			int idOficina1 = this.saOficina.actualizarOficina(t1);
			int idOficina2 = this.saOficina.actualizarOficina(t2);
			assertEquals(id1, idOficina1);
			assertEquals(id2, idOficina2);
		}
	}
	

	@Test
	public void testbajaOficinaFailByOficinaDadoDeBaja() {
		List<TOficina> listOficinas = this.saOficina.listarOficinas();
		Optional<TOficina> optOficinaInactive = listOficinas.stream()
													.filter(x -> !x.isActivo() && x.getNombre().contains("Test"))
													.findFirst();
		if (optOficinaInactive.isPresent()) {
			TOficina inactive = optOficinaInactive.get();
			int errorSintaxis = this.saOficina.bajaOficina(inactive.getId()); 
			assertEquals(SAOficina.OFICINA_INACTIVA, errorSintaxis);
		} else {
			TOficina mockTransfer = new TOficina(this.generateNameUnit());
			int mockProject = this.saOficina.altaOficina(mockTransfer);
			int idBaja = this.saOficina.bajaOficina(mockProject); 
			int error = this.saOficina.bajaOficina(idBaja); //Lo volvemos ha dar de baja para forzar error
			assertEquals(SAOficina.OFICINA_INACTIVA, error);
		}
	}
	
	@Test 
	public void testbajaOficinaFailByOficinaConEmpleadosActivos() {
		oficina = new TOficina("bajaOficinaKOEmpleadosActivos");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador admin = new TEmpleadoAdministrador(tlf, nif + "A", nombre, direccion, "empleado admin", sueldo, oficina.getId());
		TEmpleadoTecnico tecnico = new TEmpleadoTecnico(tlf, nif, nombre + "B", direccion, "empleado tecnico", sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(admin);
		this.saEmpleado.altaEmpleadoTecnico(tecnico);
		assertTrue(this.saOficina.bajaOficina(oficina.getId()) == SAOficina.ERROR_OFICINA_CON_EMPLEADOS_ACTIVOS);
	}
	
	@Test
	public void testBajaOficinaSuccess() {
		oficina = new TOficina("bajaOficinaOK");
		this.saOficina.altaOficina(oficina);
		assertTrue(this.saOficina.bajaOficina(oficina.getId()) == oficina.getId());
	}
		
	private String generateNameUnit() {
		int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 45;
        Random random = new Random();
        return "Test" + random.ints(leftLimit, rightLimit + 1)
      							  .limit(targetStringLength)
      							  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      							  .toString();
	}

}
