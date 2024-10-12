package negocio.proveedor;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.proveedor_producto.DAOProveedorProducto;
import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import negocio.proveedor_producto.TProveedorProducto;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestSAProveedor {
	
	private static final int tlf = 123456789;
	private static final String direccion = "direccion";
	private static final String nif = "12345678";
	private static final int stock = 20;
	private static final double precio = 0.3;
	
	private SAProveedor saProveedor;
	private DAOProducto daoProducto;
	private DAOAlmacen daoAlmacen;
	private DAOProveedorProducto daoPP;
	private TProveedor proveedor;
	private TProducto producto;
	private TAlmacen almacen;
	private TProveedorProducto pp;
	
	@Before
	public void setUp() {
		FactoriaNegocio fn = FactoriaNegocio.getInstancia();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		
		this.saProveedor = fn.crearSAProveedor();
		this.daoAlmacen = fi.crearDAOAlmacen();
		this.daoProducto = fi.creaDAOProducto();
		this.daoPP = fi.crearDAOPoveedorProducto();
	}
	
	@Test
	public void altaOK() {
		proveedor = new TProveedor(tlf, nif + "A", direccion);
		assertTrue(this.saProveedor.altaProveedor(proveedor) > 0);
	}
	
	@Test
	public void altaKO() {
		proveedor = new TProveedor(tlf, nif, direccion);
		assertTrue(this.saProveedor.altaProveedor(proveedor) == SAProveedor.ERROR_SINTACTICO);
		proveedor = new TProveedor(tlf, nif + "B", direccion);
		this.saProveedor.altaProveedor(proveedor);
		assertTrue(this.saProveedor.altaProveedor(proveedor) == SAProveedor.PROVEEDOR_ACTIVO);
		this.saProveedor.bajaProveedor(proveedor.getId());
		assertTrue(this.saProveedor.altaProveedor(proveedor) == SAProveedor.PROVEEDOR_INACTIVO);
	}
	
	@Test
	public void buscar() {
		proveedor = new TProveedor(tlf, nif + "C", direccion);
		this.saProveedor.altaProveedor(proveedor);
		assertNotNull(this.saProveedor.buscarProveedor(proveedor.getId()));
		assertNull(this.saProveedor.buscarProveedor(-1));
	}
	
	@Test
	public void listar() {
		proveedor = new TProveedor(tlf, nif + "D", direccion);
		this.saProveedor.altaProveedor(proveedor);
		List<TProveedor> res = this.saProveedor.listarProveedores();
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void listarPorProductoOK() {
		proveedor = new TProveedor(tlf, nif + "E", direccion);
		this.saProveedor.altaProveedor(proveedor);
		almacen = new TAlmacen("listarProvPorProdOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("listarProvPorProdOK", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		pp = new TProveedorProducto(proveedor.getId(), producto.getId());
		this.daoPP.createProveedorProducto(pp);
		List<TProveedor> res = this.saProveedor.listarProveedoresPorProducto(producto.getId());
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void vincularProductoOK() {
		almacen = new TAlmacen("vincularProdOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("vincularProdOK", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "F", direccion);
		this.saProveedor.altaProveedor(proveedor);
		assertTrue(this.saProveedor.vincularProducto(proveedor.getId(), producto.getId()) > 0);
	}
	
	@Test
	public void vincularProductoKO() {
		almacen = new TAlmacen("vincularProdKO");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("vincularProdKO", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "G", direccion);
		this.saProveedor.altaProveedor(proveedor);
		this.saProveedor.vincularProducto(proveedor.getId(), producto.getId());
		assertTrue(this.saProveedor.vincularProducto(proveedor.getId(), producto.getId()) == SAProveedor.YA_VINCULADOS);
		this.daoPP.deleteProveedorProducto(proveedor.getId(), producto.getId());
		this.daoProducto.bajaProducto(producto.getId());
		assertTrue(this.saProveedor.vincularProducto(proveedor.getId(), producto.getId()) == SAProveedor.PRODUCTO_INACTIVO);
		assertTrue(this.saProveedor.vincularProducto(proveedor.getId(), -1) == SAProveedor.PRODUCTO_INEXISTENTE);
		this.saProveedor.bajaProveedor(proveedor.getId());
		assertTrue(this.saProveedor.vincularProducto(proveedor.getId(), producto.getId()) == SAProveedor.PROVEEDOR_INACTIVO);
		assertTrue(this.saProveedor.vincularProducto(-1, producto.getId()) == SAProveedor.PROVEEDOR_INEXSITENTE);
	}
	
	@Test
	public void desvincularProductoOK() {
		almacen = new TAlmacen("desvincularProdOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("desvincularProdOK", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "H", direccion);
		this.saProveedor.altaProveedor(proveedor);
		pp = new TProveedorProducto(proveedor.getId(), producto.getId());
		this.daoPP.createProveedorProducto(pp);
		assertTrue(this.saProveedor.desvincularProducto(proveedor.getId(), producto.getId()) > 0);
	}
	
	@Test
	public void desvincularProductoKO() {
		almacen = new TAlmacen("desvincularProdKO");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("desvincularProdKO", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "I", direccion);
		this.saProveedor.altaProveedor(proveedor);
		pp = new TProveedorProducto(proveedor.getId(), producto.getId());
		this.daoPP.createProveedorProducto(pp);
		this.saProveedor.vincularProducto(proveedor.getId(), producto.getId());
		this.saProveedor.desvincularProducto(proveedor.getId(), producto.getId());
		assertTrue(this.saProveedor.desvincularProducto(proveedor.getId(), producto.getId()) == SAProveedor.YA_DESVINCULADOS);
		this.daoProducto.bajaProducto(producto.getId());
		assertTrue(this.saProveedor.desvincularProducto(proveedor.getId(), producto.getId()) == SAProveedor.PRODUCTO_INACTIVO);
		assertTrue(this.saProveedor.desvincularProducto(proveedor.getId(), -1) == SAProveedor.PRODUCTO_INEXISTENTE);
		this.saProveedor.bajaProveedor(proveedor.getId());
		assertTrue(this.saProveedor.desvincularProducto(proveedor.getId(), producto.getId()) == SAProveedor.PROVEEDOR_INACTIVO);
		assertTrue(this.saProveedor.desvincularProducto(-1, producto.getId()) == SAProveedor.PROVEEDOR_INEXSITENTE);
	}
	
	@Test
	public void actualizarProveedorOK() {
		almacen = new TAlmacen("actualizarProveedorOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("actualizarProveedorOK", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "J", direccion);
		this.saProveedor.altaProveedor(proveedor);
		assertTrue(this.saProveedor.actualizarProveedor(proveedor) == proveedor.getId());
	}
	
	@Test
	public void actualizarProveedorKO() {
		almacen = new TAlmacen("actualizarProveedorKO");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("actualizarProveedorKO", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "K", direccion);
		this.saProveedor.altaProveedor(proveedor);
		proveedor.setNif(nif);
		assertTrue(this.saProveedor.actualizarProveedor(proveedor) == SAProveedor.ERROR_SINTACTICO);
		proveedor.setNif(nif + "K");
		proveedor.setId(10000);
		assertTrue(this.saProveedor.actualizarProveedor(proveedor) == SAProveedor.PROVEEDOR_INEXSITENTE);
	}
	
	@Test
	public void bajaOK() {
		almacen = new TAlmacen("bajaProveedorOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("bajaProveedorOK", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "L", direccion);
		this.saProveedor.altaProveedor(proveedor);
		assertTrue(this.saProveedor.bajaProveedor(proveedor.getId()) == proveedor.getId());
	}
	
	@Test
	public void bajaKO() {
		assertTrue(this.saProveedor.bajaProveedor(-1) == SAProveedor.PROVEEDOR_INEXSITENTE);
		almacen = new TAlmacen("bajaProveedorKO");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("bajaProveedorKO", precio, stock, almacen.getId());
		this.daoProducto.altaProducto(producto);
		proveedor = new TProveedor(tlf, nif + "M", direccion);
		this.saProveedor.altaProveedor(proveedor);
		pp = new TProveedorProducto(proveedor.getId(), producto.getId());
		this.daoPP.createProveedorProducto(pp);
		assertTrue(this.saProveedor.bajaProveedor(proveedor.getId()) == SAProveedor.PRODUCTOS_VINCULADOS);
		this.daoPP.deleteProveedorProducto(proveedor.getId(), producto.getId());
		this.saProveedor.bajaProveedor(proveedor.getId());
		assertTrue(this.saProveedor.bajaProveedor(proveedor.getId()) == SAProveedor.PROVEEDOR_INACTIVO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
