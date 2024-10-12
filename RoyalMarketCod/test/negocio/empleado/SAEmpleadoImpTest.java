package negocio.empleado;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import negocio.curso.SACurso;
import negocio.curso.TCursoPresencial;
import negocio.empleado_curso.TEmpleadoCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.SAOficina;
import negocio.oficina.TOficina;

public class SAEmpleadoImpTest {
	
	private static final int tlf = 123456789;
	private static final String nif = "12345678";
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final String reporteSemanal = "reporteSemanal";
	private static final String reporteTrabajo = "reporteTrabajo";
	private static final double sueldo = 500;
	
	private SAEmpleado serviceEmployee;
	private SAOficina saOficina;
	private SACurso serviceCourse;

	@Before
	public void setUp() {
		this.serviceEmployee = FactoriaNegocio.getInstancia().crearSAEmpleado();
		this.saOficina = FactoriaNegocio.getInstancia().crearSAOficina();
		this.serviceCourse = FactoriaNegocio.getInstancia().crearSACurso();
	}
	
	@Test
	public void testAltaEmpleadoAdministradorFailEmpleadoDuplicado() {
		TOficina oficina = new TOficina("altaEmpAdminEmpleadoDuplicado");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador tEmpleado = new TEmpleadoAdministrador(tlf, nif + "A", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		int id = this.serviceEmployee.altaEmpleadoAdminsitrador(tEmpleado);
		if (id > 0) {
			int errorDuplicate = this.serviceEmployee.altaEmpleadoAdminsitrador(tEmpleado); 
			assertEquals(errorDuplicate, SAEmpleado.EMPLEADO_ACTIVO);
		}
		else assertEquals(id, SAEmpleado.ERROR_BBDD);
	}
	@Test
	public void testAltaEmpleadoTecnicoFailEmpleadoDuplicado() {
		TOficina oficina = new TOficina("altaEmpTecnicoEmpleadoDuplicado");
		this.saOficina.altaOficina(oficina);
		TEmpleadoTecnico tEmpleado = new TEmpleadoTecnico(tlf, nif + "B", nombre, direccion, reporteTrabajo, sueldo, oficina.getId());
		int id = this.serviceEmployee.altaEmpleadoTecnico(tEmpleado);
		if (id >= 0) {
			int errorDuplicate = this.serviceEmployee.altaEmpleadoTecnico(tEmpleado); 
			assertEquals(errorDuplicate, SAEmpleado.EMPLEADO_ACTIVO);
		}
		else assertEquals(id, SAEmpleado.ERROR_BBDD);
	}
	
	@Test
	public void testAltaEmpleadoAdministradorSuccess() {
		TOficina oficina = new TOficina("altaEmpAdminOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "C", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
	    int successAltaEmpleado = this.serviceEmployee.altaEmpleadoAdminsitrador(empleado);
	    assertTrue(successAltaEmpleado > 0);
	}
	@Test
	public void testAltaEmpleadoTecnicoSuccess() {
		TOficina oficina = new TOficina("altaEmpTecnicoOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoTecnico empleado = new TEmpleadoTecnico(tlf, nif + "D", nombre, direccion, reporteTrabajo, sueldo, oficina.getId());
	    int successAltaEmpleado = this.serviceEmployee.altaEmpleadoTecnico(empleado);
	    assertTrue(successAltaEmpleado > 0);
	}
	

	@Test
	public void testBuscarEmpleadoFail() {
		int id = 0;
		TEmpleado empleado = this.serviceEmployee.buscarEmpleado(id);
		assertNull(empleado);
	}
	
	@Test
	public void testBuscarEmpleadoSuccess() {
		java.util.List<TEmpleado> listProject = this.serviceEmployee.listarEmpleados();
		if (!listProject.isEmpty()) {
			TEmpleado project = this.serviceEmployee.buscarEmpleado(listProject.get(0).getId());
			assertNotNull(project);
		} else {
			TOficina oficina = new TOficina("buscarEmpleadoOK");
			this.saOficina.altaOficina(oficina);
			TEmpleadoAdministrador tEmpleado = new TEmpleadoAdministrador(12,"ll","ii","mm", "hola", 0, oficina.getId());
			int id = this.serviceEmployee.altaEmpleadoAdminsitrador(tEmpleado);
			TEmpleado project = this.serviceEmployee.buscarEmpleado(id);
			assertNotNull(project);
		}
	}
	
	@Test
	public void testListarEmpleadoSuccess() {
		TOficina oficina = new TOficina("listarEmpleadosOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "T", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.serviceEmployee.altaEmpleadoAdminsitrador(empleado);
		assertFalse(this.serviceEmployee.listarEmpleados().isEmpty());
	}
	
	@Test
	public void testListarEmpleadosPorCursoFail() {
		int idEmpleado = 0;
		java.util.List<TEmpleado> listProject = this.serviceEmployee.listarEmpleadosPorCurso(idEmpleado);
		assertTrue(listProject.isEmpty());
	}
	
	
	@Test
	public void testActualizarEmpleadoAdministradorSuccess() {
		TOficina oficina = new TOficina("actualizarEmpAdminOK");
		this.saOficina.altaOficina(oficina);
		 TEmpleadoAdministrador empleado1 = new TEmpleadoAdministrador(12,"ll","ii","mm", "hola", 0, oficina.getId());
		 TEmpleadoAdministrador empleado2 = new TEmpleadoAdministrador(13,"ll","ii","mm", "hola", 0, oficina.getId());
		 int id1 = this.serviceEmployee.altaEmpleadoAdminsitrador(empleado1);
		 int id2 = this.serviceEmployee.altaEmpleadoAdminsitrador(empleado2);
		 empleado1.setId(id1);
		 empleado2.setId(id2);
		 empleado1.setNombre("Pablo");
		 empleado2.setNombre("Jonny");
		 int idEmpleado1 = this.serviceEmployee.actualizarEmpleadoAdministrador(empleado1);
		 int idEmpleado2 = this.serviceEmployee.actualizarEmpleadoAdministrador(empleado2);
		 assertEquals(id1, idEmpleado1);
		 assertEquals(id2, idEmpleado2);
		
	}
	@Test
	public void testActualizarEmpleadoTecnicoSuccess() {
		TOficina oficina = new TOficina("actualizarEmpTecnicoOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoTecnico empleado1 = new TEmpleadoTecnico(12,"ll","ii","mm", "hola", 0, oficina.getId());
		TEmpleadoTecnico empleado2 = new TEmpleadoTecnico(13,"ll","ii","mm", "hola", 0, oficina.getId());
		 int id1 = this.serviceEmployee.altaEmpleadoTecnico(empleado1);
		 int id2 = this.serviceEmployee.altaEmpleadoTecnico(empleado2);
		 empleado1.setId(id1);
		 empleado2.setId(id2);
		 empleado1.setNombre("Pablo");
		 empleado2.setNombre("Jonny");
		 int idEmpleado1 = this.serviceEmployee.actualizarEmpleadoTecnico(empleado1);
		 int idEmpleado2 = this.serviceEmployee.actualizarEmpleadoTecnico(empleado2);
		 assertEquals(id1, idEmpleado1);
		 assertEquals(id2, idEmpleado2);
		
	}

	@Test
	public void testbajaEmpleadoFailByEmpleadoDadoDeBaja() {
		TOficina oficina = new TOficina("bajaEmpleadoKOEmpInactivo");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "G", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.serviceEmployee.altaEmpleadoAdminsitrador(empleado);
		this.serviceEmployee.bajaEmpleado(empleado.getId());
		assertTrue(this.serviceEmployee.bajaEmpleado(empleado.getId()) == SAEmpleado.EMPLEADO_INACTIVO);
	}
	
	@Test
	public void testBajaEmpleadoSuccess() {
		TOficina oficina = new TOficina("bajaEmpOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoTecnico tco = new TEmpleadoTecnico(tlf, nif + "Z", nombre, direccion, reporteTrabajo, sueldo, oficina.getId());
		int resAlt = this.serviceEmployee.altaEmpleadoTecnico(tco);
		int idBaja = this.serviceEmployee.bajaEmpleado(resAlt);
		assertEquals(idBaja, resAlt);
	}
	
	@Test
	public void testActualizarNivelEmpleadoCurso() {
		TOficina oficina = new TOficina("testActualizarNivelEmpleadoCurso");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "X", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.serviceEmployee.altaEmpleadoAdminsitrador(empleado);
		TCursoPresencial curso = new TCursoPresencial("testCursoPresencial", 38, 5, 40);
		this.serviceCourse.altaCursoPresencial(curso);
		TEmpleadoCurso e = new TEmpleadoCurso(empleado.getId(), curso.getId(), 5);
		this.serviceEmployee.vincularCurso(e);
		e.setNivel(5000);
		int n = this.serviceEmployee.actualizarNivelCursoEmpleado(e);
		assertTrue(n == 1);
	}
	
}
