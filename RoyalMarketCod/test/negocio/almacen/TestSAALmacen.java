package negocio.almacen;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;

public class TestSAALmacen {
	
	private SAAlmacen sa;
	private TAlmacen ta;
	private DAOProducto daoProd;
	private TProducto tp;
	@Before
	public void setUp() {
		this.sa = FactoriaNegocio.getInstancia().crearSAAlmacen();
		this.daoProd = FactoriaIntegracion.getInstancia().creaDAOProducto();
	}
	
	// CREATE
	@Test
	public void altaOK() {
		this.ta = new TAlmacen("altaOK");
		assertTrue(this.sa.altaAlmacen(ta) > 0);
	}
	@Test
	public void altaKOErrorSintactico() {
		assertTrue(this.sa.altaAlmacen(new TAlmacen("error sintactico1")) == SAAlmacen.ERROR_SINTACTICO);
	}
	
	@Test
	public void altaKOAlmacenActivo() {
		this.ta = new TAlmacen("almacenActivo");
		this.sa.altaAlmacen(ta);
		assertTrue(this.sa.altaAlmacen(this.ta) == SAAlmacen.ALMACEN_ACTIVO);
		
	}
	@Test
	public void altaKOAlmacenInactivo() {
		TAlmacen almacen = new TAlmacen("almacenInactivo");
		this.sa.altaAlmacen(almacen);
		this.sa.bajaAlmacen(almacen.getId());
		almacen = this.sa.buscarAlmacen(almacen.getId());
		assertTrue(this.sa.altaAlmacen(almacen) == SAAlmacen.ALMACEN_INACTIVO);
		
	}
	
	@Test
	public void buscarOK() {
		this.ta = new TAlmacen("buscar");
		this.sa.altaAlmacen(ta);
		assertTrue(this.sa.buscarAlmacen(ta.getId()) != null);
	}
	@Test
	public void buscarKO() {
		assertTrue(this.sa.buscarAlmacen(-1) == null);
	}
	
	@Test
	public void listar() {
		assertTrue(this.sa.listarAlmacenes() != null && !this.sa.listarAlmacenes().isEmpty());
	}
	
	@Test
	public void actualizarOK() {
		this.ta = new TAlmacen("actualizarOK");
		this.sa.altaAlmacen(ta);
		assertTrue(this.sa.actualizarAlmacen(ta) == this.ta.getId());
	}
	
	@Test
	public void actualizarKOErrorSintactico() {
		this.ta = new TAlmacen("actualizarKOBien");
		this.sa.altaAlmacen(ta);
		this.ta.setDireccion("actualizarErrorSintactico1");
		assertTrue(this.sa.actualizarAlmacen(ta) == SAAlmacen.ERROR_SINTACTICO);
	}
	
	@Test
	public void actualizarKOAlmacenInexistente() {
		assertTrue(this.sa.actualizarAlmacen(new TAlmacen(-1, "hola", true)) == SAAlmacen.ALMACEN_INEXISTENTE);
	}
	
	@Test
	public void bajaOK() {
		this.ta = new TAlmacen("bajaOK");
		this.sa.altaAlmacen(ta);
		assertTrue(this.sa.bajaAlmacen(ta.getId()) == ta.getId());
	}
	
	@Test
	public void bajaKOAlmacenInexistente() {
		assertTrue(this.sa.bajaAlmacen(-1) == SAAlmacen.ALMACEN_INEXISTENTE);
	}
	
	@Test
	public void bajaKOAlmacenInactivo() {
		this.ta = new TAlmacen("bajaAlmacenInactivo");
		this.sa.altaAlmacen(ta);
		this.sa.bajaAlmacen(ta.getId());
		assertTrue(this.sa.bajaAlmacen(ta.getId()) == SAAlmacen.ALMACEN_INACTIVO);
	}
	
	@Test
	public void bajaKOProductosActivos() {
		this.ta = new TAlmacen("almacenConProdActivos");
		this.sa.altaAlmacen(ta);
		this.tp = new TProducto("prodActivo", 5, 465, this.ta.getId());
		this.daoProd.altaProducto(tp);
		assertTrue(this.sa.bajaAlmacen(ta.getId()) == SAAlmacen.PRODUCTOS_EN_ALMACEN);
	}
	
	@Test
	public void bajaOKProductosInactivos() {
		this.ta = new TAlmacen("almacenConProdInactivos");
		this.sa.altaAlmacen(ta);
		this.tp = new TProducto("prodInactivo", 5, 465, this.ta.getId());
		this.daoProd.altaProducto(tp);
		this.daoProd.bajaProducto(tp.getId());
		assertTrue(this.sa.bajaAlmacen(ta.getId()) == ta.getId());
	}
	
}
