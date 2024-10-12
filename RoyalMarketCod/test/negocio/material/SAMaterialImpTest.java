package negocio.material;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;

import negocio.empleado.SAEmpleado;
import negocio.empleado.TEmpleadoAdministrador;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.SAOficina;
import negocio.oficina.TOficina;


public class SAMaterialImpTest {
	
	private static final int tlf = 123456789;
	private static final String nif = "12345678";
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final String reporteSemanal = "reporteSemanal";
	private static final String reporteTrabajo = "reporteTrabajo";
	private static final double sueldo = 500;
	
	private SAMaterial serviceMaterial;
	private SAEmpleado saEmpleado;
	private SAOficina saOficina;
	
	@Before
	public void setUp() {
		this.serviceMaterial = FactoriaNegocio.getInstancia().crearSAMaterial();
		this.saEmpleado = FactoriaNegocio.getInstancia().crearSAEmpleado();
		this.saOficina = FactoriaNegocio.getInstancia().crearSAOficina();
	}
	
	@Test
	public void testAltaMaterialFailSintactico() {
		TMaterial mockTransfer = new TMaterial("", 1);
		int errorSintaxis = this.serviceMaterial.altaMaterial(mockTransfer); 
		assertEquals(SAMaterial.ERROR_SINTACTICO, errorSintaxis);
	}
	
	
	@Test
	public void testAltaMaterialSuccess() {
		TOficina oficina = new TOficina("altaMaterialOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "A", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
	    TMaterial material = new TMaterial("altaMaterialOK", empleado.getId());
	    int successAltaMaterial = this.serviceMaterial.altaMaterial(material);
	    assertTrue(successAltaMaterial > 0);
	}
	
//	@Test
//	public void testAltaMaterialAsync()  {
//		final TMaterial unitThread = new TMaterial(this.generateNameUnit());
//		CompletableFuture<Integer> futureCreate = CompletableFuture
//													.supplyAsync(() -> this.serviceMaterial.altaMaterial(unitThread)); 
//		CompletableFuture<Integer> futureCreateAgain = CompletableFuture
//				.supplyAsync(() -> {
//					try { Thread.sleep(1500); } 
//						catch (InterruptedException e) {}
//					return this.serviceMaterial.altaMaterial(unitThread);
//				}); 
//		int idSuccess = futureCreate.join();
//		assertTrue(idSuccess > 0);
//	}

	@Test
	public void testBuscarMaterialSuccess() {
		List<TMaterial> listMateriales = this.serviceMaterial.listarMateriales();
		if (!listMateriales.isEmpty()) {
			TMaterial material = this.serviceMaterial.buscarMaterial(listMateriales.get(0).getId());
			assertNotNull(material);
		} else {
			TOficina oficina = new TOficina("buscarMaterialOK");
			this.saOficina.altaOficina(oficina);
			TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "B", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
			this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
			TMaterial mockTransfer = new TMaterial("buscarMaterialOK", empleado.getId());
			int id = this.serviceMaterial.altaMaterial(mockTransfer);
			TMaterial material = this.serviceMaterial.buscarMaterial(id);
			assertNotNull(material);
		}
	}
	
	@Test
	public void testListarMaterialSuccess() {
		TOficina oficina = new TOficina("listarMaterialesOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "T", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
		TMaterial material = new TMaterial("listarMaterialesOK", empleado.getId());
		this.serviceMaterial.altaMaterial(material);
		List<TMaterial> listMateriales = this.serviceMaterial.listarMateriales();
		assertNotNull(listMateriales);
	}
	
	@Test
	public void testActualizarMaterialFailBySintax() {
		TMaterial mockTransfer = new TMaterial("", 1);
		int errorSintaxis = this.serviceMaterial.actualizarMaterial(mockTransfer); 
		assertEquals(SAMaterial.ERROR_SINTACTICO, errorSintaxis);
	}
	
	@Test
	public void testActualizarMaterialSuccess() {
		List<TMaterial> listMateriales = this.serviceMaterial.listarMateriales();
		if (!listMateriales.isEmpty()) {
			TMaterial m1 = listMateriales.get(0);
			m1.setNombre(this.generateNameUnit());
			int idMaterial = this.serviceMaterial.actualizarMaterial(m1);
			assertEquals(idMaterial, m1.getId());
		} else {
			TOficina oficina = new TOficina("actualizarMaterialOK");
			this.saOficina.altaOficina(oficina);
			TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "C", nombre, direccion, reporteSemanal, sueldo, oficina.getId());	
			this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
			TMaterial m1 = new TMaterial(this.generateNameUnit(), empleado.getId());
			TMaterial m2 = new TMaterial(this.generateNameUnit(), empleado.getId());
			int id1 = this.serviceMaterial.altaMaterial(m1);
			int id2 = this.serviceMaterial.altaMaterial(m2);
			m1.setId(id1); 
			m2.setId(id2); 
			m1.setNombre(this.generateNameUnit());
			m2.setNombre(this.generateNameUnit());
			int idMaterial1 = this.serviceMaterial.actualizarMaterial(m1);
			int idMaterial2 = this.serviceMaterial.actualizarMaterial(m2);
			assertEquals(id1, idMaterial1);
			assertEquals(id2, idMaterial2);
		}
	}
	

	@Test
	public void testbajaMaterialFailByMaterialDadoDeBaja() {
		TOficina oficina = new TOficina("bajaMaterialKOMaterialInactivo");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "F", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
		TMaterial material = new TMaterial("bajaMaterialKOMaterialInactivo", empleado.getId());
		this.serviceMaterial.altaMaterial(material);
		this.serviceMaterial.bajaMaterial(material.getId());
		assertTrue(this.serviceMaterial.bajaMaterial(material.getId()) == SAMaterial.MATERIAL_INACTIVO);
//		List<TMaterial> listMateriales = this.serviceMaterial.listarMateriales();
//		Optional<TMaterial> optMaterialInactive = listMateriales.stream()
//													.filter(x -> !x.isActivo() && x.getNombre().contains("Test"))
//													.findFirst();
//		if (optMaterialInactive.isPresent()) {
//			TMaterial inactive = optMaterialInactive.get();
//			int errorSintaxis = this.serviceMaterial.bajaMaterial(inactive.getId()); 
//			assertEquals(SAMaterial.MATERIAL_INACTIVO, errorSintaxis);
//		} else {
//			TMaterial mockTransfer = new TMaterial(this.generateNameUnit(), empleado.getId());
//			int mockProject = this.serviceMaterial.altaMaterial(mockTransfer);
//			int idBaja = this.serviceMaterial.bajaMaterial(mockProject); 
//			int error = this.serviceMaterial.bajaMaterial(idBaja); //Lo volvemos ha dar de baja para forzar error
//			assertEquals(SAMaterial.MATERIAL_INACTIVO, error);
//		}
	}
	
	@Test
	public void testBajaMaterialSuccess() {
		TOficina oficina = new TOficina("bajaOficinaOK");
		this.saOficina.altaOficina(oficina);
		TEmpleadoAdministrador empleado = new TEmpleadoAdministrador(tlf, nif + "Z", nombre, direccion, reporteSemanal, sueldo, oficina.getId());
		this.saEmpleado.altaEmpleadoAdminsitrador(empleado);
		TMaterial m = new TMaterial(this.generateNameUnit(), empleado.getId());
		int idMock = this.serviceMaterial.altaMaterial(m);
		int idBaja = this.serviceMaterial.bajaMaterial(idMock); 
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