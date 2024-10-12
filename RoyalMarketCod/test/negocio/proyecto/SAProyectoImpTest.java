package negocio.proyecto;

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
import negocio.empleado.TEmpleadoProyecto;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.SAOficina;
import negocio.oficina.TOficina;
import negocio.tarea.SATarea;
import negocio.tarea.TTarea;

public class SAProyectoImpTest {
	
	private static final int tlf = 123456789;
	private static final String nif = "12345678";
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final String reporteSemanal = "reporteSemanal";
	private static final String reporteTrabajo = "reporteTrabajo";
	private static final double sueldo = 500;
	
	private SAProyecto serviceProject;
	private SATarea saTarea;
	private SAEmpleado saEmpleado;
	private SAOficina saOficina;
	
	@Before
	public void setUp() {
		this.serviceProject = FactoriaNegocio.getInstancia().crearSAProyecto();
		this.saTarea = FactoriaNegocio.getInstancia().crearSATarea();
		this.saEmpleado = FactoriaNegocio.getInstancia().crearSAEmpleado();
		this.saOficina = FactoriaNegocio.getInstancia().crearSAOficina();
	}
	
	@Test
	public void testAltaProyectoFailSintactico() {
		TProyecto mockTransfer = new TProyecto("");
		int errorSintaxis = this.serviceProject.altaProyecto(mockTransfer); 
		assertEquals(SAProyecto.ERROR_SINTACTICO_NOMBRE_PROYECTO, errorSintaxis);
	}
	
	@Test
	public void testAltaProyectoFailDuplicateProject() {
		TProyecto mockTransfer = new TProyecto("Test Project");
		int id = this.serviceProject.altaProyecto(mockTransfer);
		if (id > 0) {
			int errorDuplicate = this.serviceProject.altaProyecto(mockTransfer); 
			assertEquals(errorDuplicate, SAProyecto.ERROR_PROYECTO_DUPLICADO);
		}
		else assertEquals(id, SAProyecto.ERROR_PROYECTO_DUPLICADO);
	}
	
	@Test
	public void testAltaProyectoSuccess() {
	    String generatedRandomName = this.generateNameUnit();
	    TProyecto project = new TProyecto(generatedRandomName);
	    int successAltaProject = this.serviceProject.altaProyecto(project);
	    assertTrue(successAltaProject > 0);
	}
	
	@Test
	public void testAltaProyectoAsync()  {
		final TProyecto unitThread = new TProyecto(this.generateNameUnit());
		CompletableFuture<Integer> futureCreate = CompletableFuture
													.supplyAsync(() -> this.serviceProject.altaProyecto(unitThread)); 
		CompletableFuture<Integer> futureCreateAgain = CompletableFuture
				.supplyAsync(() -> {
					try { Thread.sleep(1500); } 
						catch (InterruptedException e) {}
					return this.serviceProject.altaProyecto(unitThread);
				}); 
		int idSuccess = futureCreate.join();
		int idError = futureCreateAgain.join();
		assertTrue(idSuccess > 0);
		assertEquals(idError, SAProyecto.ERROR_PROYECTO_DUPLICADO);
	}
	
	@Test
	public void testBuscarProyectoFail() {
		int id = 0;
		TProyecto project = this.serviceProject.buscarProyecto(id);
		assertNull(project);
	}
	
	@Test
	public void testBuscarProyectoSuccess() {
		List<TProyecto> listProject = this.serviceProject.listarProyectos();
		if (!listProject.isEmpty()) {
			TProyecto project = this.serviceProject.buscarProyecto(listProject.get(0).getId());
			assertNotNull(project);
		} else {
			TProyecto mockTransfer = new TProyecto(this.generateNameUnit());
			int id = this.serviceProject.altaProyecto(mockTransfer);
			TProyecto project = this.serviceProject.buscarProyecto(id);
			assertNotNull(project);
		}
	}
	
	@Test
	public void testListarProyectoSuccess() {
		List<TProyecto> listProject = this.serviceProject.listarProyectos();
		assertNotNull(listProject);
	}
	
	@Test
	public void testListarProyectosPorEmpleadoFail() {
		int idEmpleado = 0;
		List<TProyecto> listProject = this.serviceProject.listarProyectosPorEmpleado(idEmpleado);
		assertTrue(listProject.isEmpty());
	}
	
	@Test
	public void testListarProyectosPorEmpleadoSuccess() {
		TOficina oficina = new TOficina("listarProyectosPorEmpleado");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "F", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
		TProyecto proyecto = new TProyecto("listarProyectosPorEmpleado");
		this.serviceProject.altaProyecto(proyecto);
		TEmpleadoProyecto tep = new TEmpleadoProyecto(empleado.getId(), proyecto.getId());
		this.saEmpleado.vincularProyecto(tep);
		List<TProyecto> res = this.serviceProject.listarProyectosPorEmpleado(empleado.getId());
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void testActualizarProyectoFailBySintax() {
		TProyecto mockTransfer = new TProyecto("");
		int errorSintaxis = this.serviceProject.actualizarProyecto(mockTransfer); 
		assertEquals(SAProyecto.ERROR_SINTACTICO_NOMBRE_PROYECTO, errorSintaxis);
	}
	
	@Test
	public void testActualizarProyectoFailByDuplicateProject() {
		List<TProyecto> listProject = this.serviceProject.listarProyectos();
		if (!listProject.isEmpty() && listProject.size() > 1) {
			TProyecto t1 = listProject.get(0);
			TProyecto t2 = listProject.get(1);
			t1.setNombre(t2.getNombre());
			int idProject = this.serviceProject.actualizarProyecto(t1);
			assertEquals(idProject, SAProyecto.ERROR_PROYECTO_DUPLICADO);
		} else {
			TProyecto t1 = new TProyecto(this.generateNameUnit());
			TProyecto t2 = new TProyecto(this.generateNameUnit());
			int id1 = this.serviceProject.altaProyecto(t1);
			int id2 = this.serviceProject.altaProyecto(t2);
			t1.setId(id1); t1.setActivo(false);
			t2.setId(id2); t2.setActivo(false);
			t1.setNombre(t2.getNombre());
			int idProject = this.serviceProject.actualizarProyecto(t1);
			assertEquals(idProject, SAProyecto.ERROR_PROYECTO_DUPLICADO);
		}
	}
	
	@Test
	public void testActualizarProyectoSuccess() {
		List<TProyecto> listProject = this.serviceProject.listarProyectos();
		if (!listProject.isEmpty()) {
			TProyecto t1 = listProject.get(0);
			t1.setNombre(this.generateNameUnit());
			int idProject = this.serviceProject.actualizarProyecto(t1);
			assertEquals(idProject, t1.getId());
		} else {
			TProyecto t1 = new TProyecto(this.generateNameUnit());
			TProyecto t2 = new TProyecto(this.generateNameUnit());
			int id1 = this.serviceProject.altaProyecto(t1);
			int id2 = this.serviceProject.altaProyecto(t2);
			t1.setId(id1); 
			t2.setId(id2); 
			t1.setNombre(this.generateNameUnit());
			t2.setNombre(this.generateNameUnit());
			int idProject1 = this.serviceProject.actualizarProyecto(t1);
			int idProject2 = this.serviceProject.actualizarProyecto(t2);
			assertEquals(id1, idProject1);
			assertEquals(id2, idProject2);
		}
	}
	

	@Test
	public void testbajaProyectoFailByProjectDadoDeBaja() {
		List<TProyecto> listProject = this.serviceProject.listarProyectos();
		Optional<TProyecto> optProjectInactive = listProject.stream()
													.filter(x -> !x.isActivo() && x.getNombre().contains("Test"))
													.findFirst();
		if (optProjectInactive.isPresent()) {
			TProyecto inactive = optProjectInactive.get();
			int errorSintaxis = this.serviceProject.bajaProyecto(inactive.getId()); 
			assertEquals(SAProyecto.ERROR_PROYECTO_DADO_DE_BAJA, errorSintaxis);
		} else {
			TProyecto mockTransfer = new TProyecto(this.generateNameUnit());
			int mockProject = this.serviceProject.altaProyecto(mockTransfer);
			int idBaja = this.serviceProject.bajaProyecto(mockProject); 
			int error = this.serviceProject.bajaProyecto(idBaja); //Lo volvemos ha dar de baja para forzar error
			assertEquals(SAProyecto.ERROR_PROYECTO_DADO_DE_BAJA, error);
		}
	}
	
	@Test 
	public void testbajaProyectoFailByProjectConEmpleadosActivos() {
		TProyecto proyecto = new TProyecto("bajaProyectoKOEmpleadosActivos");
		this.serviceProject.altaProyecto(proyecto);
		TOficina oficina = new TOficina("bajaProyectoKOEmpleadosActivos");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(123456789, "12345678G", "nombre", "direccion", "reporteSemanal", 100, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
		TEmpleadoProyecto tep = new TEmpleadoProyecto(empleado.getId(), proyecto.getId());
		this.saEmpleado.vincularProyecto(tep);
		assertFalse(this.serviceProject.listarProyectosPorEmpleado(empleado.getId()).isEmpty());
		int error = this.serviceProject.bajaProyecto(proyecto.getId());
		assertEquals(SAProyecto.ERROR_PROYECTO_CON_EMPLEADOS_ACTIVOS, error);
	}
	
	@Test 
	public void testbajaProyectoFailByProjectConTareasActivas() {
		TProyecto proyecto = new TProyecto("bajaProyectoKOTareasActivas");
		this.serviceProject.altaProyecto(proyecto);
		TTarea tarea = new TTarea("bajaProyectoKOTareasActivas", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		assertTrue(this.serviceProject.bajaProyecto(proyecto.getId()) == SAProyecto.ERROR_PROYECTO_CON_TAREAS_ACTIVAS);
	}
	
	@Test
	public void testBajaProyectoSuccess() {
		TProyecto t = new TProyecto(this.generateNameUnit());
		int idMock = this.serviceProject.altaProyecto(t);
		int idBaja = this.serviceProject.bajaProyecto(idMock); 
		assertEquals(idMock, idBaja);
	}
		
	private String generateNameUnit() {
		int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 45;
        Random random = new Random();
        return "Test " + random.ints(leftLimit, rightLimit + 1)
      							  .limit(targetStringLength)
      							  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      							  .toString();
	}
}
