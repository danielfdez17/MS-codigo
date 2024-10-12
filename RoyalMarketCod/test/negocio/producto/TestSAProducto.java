package negocio.producto;

import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.proveedor.DAOProveedor;
import integracion.proveedor_producto.DAOProveedorProducto;
import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor.TProveedor;
import negocio.proveedor_producto.TProveedorProducto;

public class TestSAProducto {
	
	private static final String nombre = "nombre";
	private static final int tlf = 123546789;
	private static final String nif = "123456789";
	private static final String direccion = "direccion";
	private static final int stock = 20;
	private static final double precio = 2.3;
	
	private SAProducto saProducto;
	private DAOAlmacen daoAlmacen;
	private DAOProveedor daoProveedor;
	private DAOProveedorProducto daoPP;
	private TAlmacen almacen;
	private TProducto producto;
	private TProveedor proveedor;
	
	@Before
	public void setUp() {
		FactoriaNegocio fn = FactoriaNegocio.getInstancia();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		this.saProducto = fn.crearSAProducto();
		this.daoAlmacen = fi.crearDAOAlmacen();
		this.daoProveedor = fi.crearDAOProveedor();
		this.daoPP = fi.crearDAOPoveedorProducto();
	}
	
	@Test
	public void altaOK() {
		almacen = new TAlmacen("altaProductoOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("altaProductoOK", 20, 20, almacen.getId());
		assertTrue(this.saProducto.altaProducto(producto) > 0);
	}
	
	@Test
	public void altaKOErrorSintactico() {
		assertTrue(this.saProducto.altaProducto(new TProducto("errorsintactico1", 32, 32, 32)) == SAProducto.ERROR_SINTACTICO);
	}
	
	@Test
	public void altaKOAlmacenInexistente() {
		assertTrue(this.saProducto.altaProducto(new TProducto(nombre, 32, 32, 1000)) == SAProducto.ALMACEN_INEXISTENTE);
	}
	@Test
	public void altaKOAlmacenInactivo() {
		almacen = new TAlmacen("altaProductoKOAlmacenInactivo");
		this.daoAlmacen.altaAlmacen(almacen);
		this.daoAlmacen.bajaAlmacen(almacen.getId());
		producto = new TProducto(nombre, 20, 20, almacen.getId());
		assertTrue(this.saProducto.altaProducto(producto) == SAProducto.ALMACEN_INACTIVO);
	}
	@Test
	public void altaKOProductoActivo() {
		almacen = new TAlmacen("altaProductoKOProdActico");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("altaProductoKOProdActivo", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		assertTrue(this.saProducto.altaProducto(producto) == SAProducto.PRODUCTO_ACTIVO);
	}
	
	@Test
	public void altaKOProductoInactivo() {
		almacen = new TAlmacen("altaProductoKOProdInactico");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("altaProductoKOProdInactivo", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		this.saProducto.bajaProducto(producto.getId());
		assertTrue(this.saProducto.altaProducto(producto) == SAProducto.PRODUCTO_INACTIVO);
	}
	
	@Test
	public void buscarProducto() {
		almacen = new TAlmacen("buscarOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("buscarProductoOK", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		assertNotNull(this.saProducto.buscarProducto(producto.getId()));
		assertNull(this.saProducto.buscarProducto(-1));
	}
	
	@Test
	public void listarProductos() {
		almacen = new TAlmacen("listarProductosOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("listarProductosOK", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		List<TProducto> res = this.saProducto.listarProductos();
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void listarProductosPorProveedorOK() {
		proveedor = new TProveedor(tlf, nif + "A", direccion);
		this.daoProveedor.altaProveedor(proveedor);
		almacen = new TAlmacen("listarProductosPorProv");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("listarProductosPorProv", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		this.daoPP.createProveedorProducto(new TProveedorProducto(proveedor.getId(), producto.getId()));
		List<TProducto> res = this.saProducto.listarProductosPorProveedor(proveedor.getId());
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void listarProductosPorProveedorKO() {
		assertTrue(this.saProducto.listarProductosPorProveedor(-1).isEmpty());
	}
	
	@Test
	public void actualizarProductoOK() {
		almacen = new TAlmacen("actualizarProductoOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("actualizarProductoOK", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		assertTrue(this.saProducto.actualizarProducto(producto) > 0);
	}
	
	public void actualizarProductoKOErrorSintactico() {
		assertTrue(this.saProducto.actualizarProducto(new TProducto("1", 20, 20, 20)) == SAProducto.ERROR_SINTACTICO);
	}
	
	@Test
	public void actualizarProductoKOProdInexistente() {
		assertTrue(this.saProducto.actualizarProducto(new TProducto(nombre, 20, 20, 20)) == SAProducto.PRODUCTO_INEXISTENTE);
	}
	
	@Test
	public void actualizarProductoKOAlmacenInexistente() {
		almacen = new TAlmacen("actualizarProdKOAlmacenInexistente");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("actualizarProdKOAlmacenInexistente", precio, stock, almacen.getId());
		this.saProducto.altaProducto(producto);
		producto.setIdAlmacen(10000);
		assertTrue(this.saProducto.actualizarProducto(producto) == SAProducto.ALMACEN_INEXISTENTE);
	}
	
	@Test
	public void actualizarProductoKOAlmacenInactivo() {
		almacen = new TAlmacen("actualizarProdKOAlmacenInactivo");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("actualizarProdKOAlmacenInactivo", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		almacen = new TAlmacen("actualizarProdKOAlmacenInactivoInactivo");
		this.daoAlmacen.altaAlmacen(almacen);
		this.daoAlmacen.bajaAlmacen(almacen.getId());
		producto.setIdAlmacen(almacen.getId());
		assertTrue(this.saProducto.actualizarProducto(producto) == SAProducto.ALMACEN_INACTIVO);
	}
	
	@Test
	public void bajaOK() {
		almacen = new TAlmacen("bajaProdOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("bajaProdOK", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		assertTrue(this.saProducto.bajaProducto(producto.getId()) == producto.getId());
	}
	
	@Test
	public void bajaKO() {
		assertTrue(this.saProducto.bajaProducto(-1) == SAProducto.PRODUCTO_INEXISTENTE);
		almacen = new TAlmacen("bajaProdKO");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("bajaProdKO", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		this.saProducto.bajaProducto(producto.getId());
		assertTrue(this.saProducto.bajaProducto(producto.getId()) == SAProducto.PRODUCTO_INACTIVO);
	}
	
	@Test
	public void listarPorAlmacenesOK() {
		almacen = new TAlmacen("listarProdPorAlmacenOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("listarProdPorAlmacenOK", 20, 20, almacen.getId());
		this.saProducto.altaProducto(producto);
		List<TProducto> res = this.saProducto.listarProductosPorAlmacen(almacen.getId());
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void listarPorAlmacenKO() {
		assertNull(this.saProducto.listarProductosPorAlmacen(-1));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
