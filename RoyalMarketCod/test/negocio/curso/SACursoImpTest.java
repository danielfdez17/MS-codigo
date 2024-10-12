package negocio.curso;

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
import negocio.empleado_curso.TEmpleadoCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.SAOficina;
import negocio.oficina.TOficina;

public class SACursoImpTest {
	
	private static final int tlf = 123456789;
	private static final String nif = "12345678";
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final String reporteSemanal = "reporteSemanal";
	private static final String reporteTrabajo = "reporteTrabajo";
	private static final double sueldo = 500;
	private static final int plazas = 38;
	private static final int aula = 5;
	private static final int horasDia = 4;
	
	private SACurso serviceCourse;
	private SAEmpleado saEmpleado;
	private SAOficina saOficina;

	@Before
	public void setUp() {
		this.serviceCourse = FactoriaNegocio.getInstancia().crearSACurso();
		this.saEmpleado = FactoriaNegocio.getInstancia().crearSAEmpleado();
		this.saOficina = FactoriaNegocio.getInstancia().crearSAOficina();
	}
	
	@Test
	public void testAltaCursoOnlineFailSintactico() {
		TCursoOnline tCursoOnline = new TCursoOnline("",0,0,"");
		int aux = this.serviceCourse.altaCursoOnline(tCursoOnline); 
		assertEquals(SACurso.ERROR_SINTACTICO, aux);
	}
	@Test
	public void testAltaCursoPresencialFailSintactico() {
		TCursoPresencial tCurso = new TCursoPresencial("",0,0,5);
		int aux = this.serviceCourse.altaCursoPresencial(tCurso); 
		assertEquals(SACurso.ERROR_SINTACTICO, aux);
	}
	@Test
	public void testAltaCursoOnlineFailCursoDuplicado() {
		TCursoOnline tCurso = new TCursoOnline("altaCursoOnlineKOCursoActivo", 1, 2, "asd");
		int id = this.serviceCourse.altaCursoOnline(tCurso);
		if (id > 0) {
			int errorDuplicate = this.serviceCourse.altaCursoOnline(tCurso); 
			assertEquals(errorDuplicate, SACurso.CURSO_ACTIVO);
		}
		else assertEquals(id, SACurso.ERROR_BBDD);
	}
	@Test
	public void testAltaCursoPresencialFailCursoDuplicado() {
		TCursoPresencial tCurso = new TCursoPresencial("altaCursoPresencialKOCursoActivo",1,2,4);
		int id = this.serviceCourse.altaCursoPresencial(tCurso);
		if (id > 0) {
			int errorDuplicate = this.serviceCourse.altaCursoPresencial(tCurso); 
			assertEquals(errorDuplicate, SACurso.CURSO_ACTIVO);
		}
		else assertEquals(id, SACurso.ERROR_BBDD);
	}
	
	@Test
	public void testAltaCursoOnlineSuccess() {
	    TCursoOnline curso = new TCursoOnline("altaCursoOnlineOK",2,2,"jkl");
	    int successAltaCurso = this.serviceCourse.altaCursoOnline(curso);
	    assertTrue(successAltaCurso > 0);
	}
	@Test
	public void testAltaCursoPresencialSuccess() {
	    TCursoPresencial curso = new TCursoPresencial("altaCursoPresencialOK",2,2,2);
	    int successAltaCurso = this.serviceCourse.altaCursoPresencial(curso);
	    assertTrue(successAltaCurso > 0);
	}
//	
//	@Test
//	public void testAltaCursoPresencialAsync()  {
//		final TCursoPresencial unitThread = new TCursoPresencial("plo",2,2,7);
//		CompletableFuture<Integer> futureCreate = CompletableFuture
//													.supplyAsync(() -> this.serviceCourse.altaCursoPresencial(unitThread)); 
//		CompletableFuture<Integer> futureCreateAgain = CompletableFuture
//				.supplyAsync(() -> {
//					try { Thread.sleep(1500); } 
//						catch (InterruptedException e) {}
//					return this.serviceCourse.altaCursoPresencial(unitThread);
//				}); 
//		int idSuccess = futureCreate.join();
//		int idError = futureCreateAgain.join();
//		assertTrue(idSuccess > 0);
//		assertEquals(idError, SACurso.ERROR_BBDD);
//	}
//	@Test
//	public void testAltaCursoOnlineAsync()  {
//		final TCursoOnline unitThread = new TCursoOnline("plo",2,2,"plo");
//		CompletableFuture<Integer> futureCreate = CompletableFuture
//													.supplyAsync(() -> this.serviceCourse.altaCursoOnline(unitThread)); 
//		CompletableFuture<Integer> futureCreateAgain = CompletableFuture
//				.supplyAsync(() -> {
//					try { Thread.sleep(1500); } 
//						catch (InterruptedException e) {}
//					return this.serviceCourse.altaCursoOnline(unitThread);
//				}); 
//		int idSuccess = futureCreate.join();
//		int idError = futureCreateAgain.join();
//		assertTrue(idSuccess > 0);
//		assertEquals(idError, SACurso.ERROR_BBDD);
//	}
//	
//	@Test
//	public void testBuscarCursoFail() {
//		int id = 0;
//		TCurso curso = this.serviceCourse.buscarCurso(id);
//		assertNull(curso);
//	}
	
	@Test
	public void testBuscarCursoSuccess() {
		List<TCurso> listCurso = this.serviceCourse.listarCursos();
		if (!listCurso.isEmpty()) {
			TCurso curso = this.serviceCourse.buscarCurso(listCurso.get(0).getId());
			assertNotNull(curso);
		} else {
			TCursoOnline tCurso = new TCursoOnline("buscarCursoOK",2,2,"rty");
			int id = this.serviceCourse.altaCursoOnline(tCurso);
			TCurso curso = this.serviceCourse.buscarCurso(id);
			assertNotNull(curso);
		}
	}
	
	@Test
	public void testListarCursoSuccess() {
		List<TCurso> listCurso = this.serviceCourse.listarCursos();
		assertNotNull(listCurso);
	}
	
	@Test
	public void testListarCursosPorEmpleadoFail() {
		int idEmpleado = 0;
		List<TCurso> listCurso = this.serviceCourse.listarCursosPorEmpleado(idEmpleado);
		assertTrue(listCurso.isEmpty());
	}
	
	@Test
	public void testListarCursosPorEmpleadoSuccess() {
		TOficina oficina = new TOficina("oficinaTtestListarCursosPorEmpleadoSuccess");
		int idOffice = this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador tEmpleado = new TEmpleadoAdministrador(tlf, "12345679X", "TestJosejkodsng", direccion, reporteSemanal, sueldo, idOffice);
		int idEmpleado = this.saEmpleado.altaEmpleadoAdminsitrador(tEmpleado);
		TCursoPresencial curso = new TCursoPresencial("cursoPresencialTestCursoListarCursosPorEmpleadoSuccess", plazas, horasDia, aula);
		int idCurse = this.serviceCourse.altaCursoPresencial(curso);
		this.saEmpleado.vincularCurso(new TEmpleadoCurso(idEmpleado, idCurse, 5));
		List<TCurso> listCurso = this.serviceCourse.listarCursosPorEmpleado(idEmpleado);
		assertFalse("Crear primero la Entidad en BBDD", listCurso.isEmpty());
	}
	
	
	@Test
	public void testActualizarCursoOnlineFailBySintax() {
		TCursoOnline tCurso = new TCursoOnline("",2,2,"ghj");
		int aux = this.serviceCourse.actualizarCursoOnline(tCurso); 
		assertEquals(SACurso.ERROR_SINTACTICO, aux);
	}
	@Test
	public void testActualizarCursoPresencialFailBySintax() {
		TCursoPresencial tCurso = new TCursoPresencial("",2,2,9);
		int aux = this.serviceCourse.actualizarCursoPresencial(tCurso); 
		assertEquals(SACurso.ERROR_SINTACTICO, aux);
	}

	@Test
	public void testActualizarCursoOnlineFailCursoInexistente() {
		
		TCursoOnline tco = new TCursoOnline("cursoActualizarCursoOnlineKOCursoInexistente", 22, 2, "klm");
		int resAlt = this.serviceCourse.altaCursoOnline(tco);
		assertEquals(resAlt, tco.getId());

		TCursoOnline tco2 = new TCursoOnline(0, "cursoActualizarCursoOnlineKOCursoInexistente", 22, 2, "klk", true);
		int resAct = this.serviceCourse.actualizarCursoOnline(tco2);
		assertEquals(resAct, SACurso.CURSO_INEXISTENTE);
	}
	@Test
	public void testActualizarCursoPresencialFailCursoInexistente() {
		
//		TCursoPresencial tco = new TCursoPresencial("actualizarCursoPresencialKOCursoInexistente", 22, 2, 3);
//		int resAlt = this.serviceCourse.altaCursoPresencial(tco);
//		assertEquals(resAlt, tco.getId());

		TCursoPresencial tco2 = new TCursoPresencial(999999, "cursoActualizarCursoPresencialKOCursoInexistente", 22, 2, 5, true);
		int resAct = this.serviceCourse.actualizarCursoPresencial(tco2);
		assertEquals(resAct, SACurso.CURSO_INEXISTENTE);
	}
	@Test
	public void testActualizarCursoOnlineSuccess() {
	
		TCursoOnline tco = new TCursoOnline("cursoActualizarCursoOnlineOK", 22, 2, "klm");
		int resAlt = this.serviceCourse.altaCursoOnline(tco);
		assertEquals(resAlt, tco.getId());

		TCursoOnline tco2 = new TCursoOnline(resAlt, "cursoActualizarCursoOnlineOK", 22, 2, "klk", true);
		int resAct = this.serviceCourse.actualizarCursoOnline(tco2);
		assertEquals(resAct, tco2.getId());	
	}
	@Test
	public void testActualizarCursoPresencialSuccess() {
		TCursoPresencial tco = new TCursoPresencial("cursoActualizarCursoPresencialOK", 22, 2, 9);
		int resAlt = this.serviceCourse.altaCursoPresencial(tco);
		assertEquals(resAlt, tco.getId());

		TCursoPresencial tco2 = new TCursoPresencial(resAlt, "cursoActualizarCursoPresencialOK", 22, 2, 2, true);
		int resAct = this.serviceCourse.actualizarCursoPresencial(tco2);
		assertEquals(resAct, tco2.getId());
		
	}

	@Test
	public void testbajaCursoFailByCursoDadoDeBaja() {
		
		TCursoPresencial tco = new TCursoPresencial("cursoBajaCursoKOCursoInactivo", 22, 2, 9);
		int resAlt = this.serviceCourse.altaCursoPresencial(tco);
		assertEquals(resAlt, tco.getId());
		
		int idBaja = this.serviceCourse.bajaCurso(tco.getId()); 
		
		int idFail = this.serviceCourse.bajaCurso(tco.getId());

		assertEquals(SACurso.CURSO_INACTIVO, idFail);
	}
	
	@Test 
	public void testbajaCursoFailByConEmpleadosActivos() {
		TOficina oficina = new TOficina("oficinaBajaCursoKOEmpleadosActivos");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "L", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
		TCursoPresencial curso = new TCursoPresencial("cursoBajaCursoKOEmpleadosActivos", plazas, horasDia, aula);
		this.serviceCourse.altaCursoPresencial(curso);
		this.saEmpleado.vincularCurso(new TEmpleadoCurso(empleado.getId(), curso.getId(), 5));
		int idCurso = 1;
		int error = this.serviceCourse.bajaCurso(idCurso);
		assertEquals(SACurso.CURSO_CON_EMPLEADOS, error);
	}

	@Test
	public void testBajaCursoSuccess() {
		TCursoPresencial tco = new TCursoPresencial("cursoBajaCursoOK", 22, 2, 9);
		int resAlt = this.serviceCourse.altaCursoPresencial(tco);
		assertEquals(resAlt, tco.getId());
		
		int idBaja = this.serviceCourse.bajaCurso(tco.getId()); 
		assertEquals(resAlt, idBaja);
	}

}