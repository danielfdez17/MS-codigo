package negocio.factura;

import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.cliente.DAOCliente;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import negocio.almacen.TAlmacen;
import negocio.cliente.TClienteVIP;
import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;

public class TestSAFactura {
	
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final int id_tarjeta_vip = 123;
	private static final int tlf = 132456789;
	private static final boolean activo = true;
	private static final String nif = "12345678";
	
	private SAFactura saFactura;
	private DAOProducto daoProducto;
	private DAOCliente daoCliente;
	private DAOAlmacen daoAlmacen;
	private TFactura factura;
	private TCarrito carrito;
	private TProductoDevuelto productoDevuelto;
	private TProducto producto;
	private TClienteVIP cliente;
	private TAlmacen almacen;
	private TLineaFactura linea;
	
	@Before
	public void setUp() {
		FactoriaNegocio fn = FactoriaNegocio.getInstancia();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		
		this.saFactura = fn.crearSAFactura();
		this.daoProducto = fi.creaDAOProducto();
		this.daoCliente = fi.crearDAOCliente();
		this.daoAlmacen = fi.crearDAOAlmacen();
		
	}
	
	@Test
	public void generarCarritoOK() {
		assertNotNull(this.saFactura.generarCarrito(1));
	}
	
	@Test
	public void generarCarritoKO() {
		assertNull(this.saFactura.generarCarrito(-1));
	}
	
	@Test
	public void cerrarFacturaOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "A", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		
		almacen = new TAlmacen("cerrarFacturaOK");
		this.daoAlmacen.altaAlmacen(almacen);
		
		producto = new TProducto(nombre, 15, 10, almacen.getId());
		this.daoProducto.altaProducto(producto);
		
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		assertTrue(this.saFactura.cerrarFactura(carrito) > 0);
	}
	
	@Test
	public void cerrarFacturaKOCLienteInexistente() {
		assertTrue(this.saFactura.cerrarFactura(new TCarrito(new TFactura(-1))) == SAFactura.CLIENTE_INEXISTENTE);
	}
	
	@Test
	public void cerrarFacturaKOCLienteInactivo() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "B", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		this.daoCliente.bajaCliente(cliente.getId());
		assertTrue(this.saFactura.cerrarFactura(new TCarrito(new TFactura(cliente.getId()))) == SAFactura.CLIENTE_INACTIVO);
	}
	
	@Test
	public void cerrarFacturaKOProductoInexistente() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "C", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(-1, 2, 52);
		carrito.getLineas().put(-1, linea);
		assertTrue(this.saFactura.cerrarFactura(carrito) == SAFactura.PRODUCTO_INEXISTENTE);
	}
	
	@Test
	public void cerrarFacturaKOProductoInactivo() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "D", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		almacen = new TAlmacen("cerrarFacturaKOProdInactivo");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("cerrarFacturaKOProdInactivo", 20, 20, almacen.getId());
		this.daoProducto.altaProducto(producto);
		this.daoProducto.bajaProducto(producto.getId());
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		assertTrue(this.saFactura.cerrarFactura(carrito) == SAFactura.PRODUCTO_INACTIVO);
	}
	
	@Test
	public void cerrarFacturaKOStockInsuficiente() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "E", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		almacen = new TAlmacen("cerrarFacturaKOStockInsuficiente");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("cerrarFacturaKOStockInsuficiente", 20, 20, almacen.getId());
		this.daoProducto.altaProducto(producto);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), 2 * producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		assertTrue(this.saFactura.cerrarFactura(carrito) == SAFactura.STOCK_INSUFICIENTE);
	}
	
	@Test
	public void devolverProductoOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "F", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		almacen = new TAlmacen("devolverProductoOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("devolverProductoOK", 10, 10, almacen.getId());
		this.daoProducto.altaProducto(producto);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		this.saFactura.cerrarFactura(carrito);
		productoDevuelto = new TProductoDevuelto(factura.getId(), producto.getId(), producto.getStock());
		assertTrue(this.saFactura.devolverProducto(productoDevuelto) > 0);
	}
	
	@Test
	public void devolverProductoKOFacturaInexistente() {
		productoDevuelto = new TProductoDevuelto(-1, -1, 0);
		assertTrue(this.saFactura.devolverProducto(productoDevuelto) == SAFactura.FACTURA_INEXISTENTE);
	}
	
	@Test
	public void devolverProductoKOProductoNoAnyadidoAlCarrito() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "G", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		this.saFactura.cerrarFactura(carrito);
		productoDevuelto = new TProductoDevuelto(factura.getId(), -1, 0);
		assertTrue(this.saFactura.devolverProducto(productoDevuelto) == SAFactura.PRODUCTO_NO_ANYADIDO_AL_CARRITO);
	}
	
	@Test
	public void devolverProductoKOEliminarMasProductosQueComprados() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "H", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		almacen = new TAlmacen("devolverProductoKOEliminarMasProductosQueComprados");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("devolverProductoKOEliminarMasProductosQueComprados", 20, 10, almacen.getId());
		this.daoProducto.altaProducto(producto);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		this.saFactura.cerrarFactura(carrito);
		productoDevuelto = new TProductoDevuelto(factura.getId(), producto.getId(), producto.getStock() + 1);
		assertTrue(this.saFactura.devolverProducto(productoDevuelto) == SAFactura.ELIMINAR_MAS_PRODUCTOS_QUE_COMPRADOS);
	}
	
//	@Test // TODO este test no tiene sentido porque el producto no existe pero la linea que contiene a dicho producto si
//	public void devolverProductoKOProductoInexistente() {}
	@Test
	public void devolverFacturaOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "I", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		almacen = new TAlmacen("devolverFacturaOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("devolverFacturaOK", 10, 10, almacen.getId());
		this.daoProducto.altaProducto(producto);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		this.saFactura.cerrarFactura(carrito);
		productoDevuelto = new TProductoDevuelto(factura.getId(), producto.getId(), producto.getStock());
		assertTrue(this.saFactura.devolverFacturaCompleta(factura.getId()) > 0);
	}
	
//	@Test // TODO este test no tiene sentido porque el producto no existe pero la linea que contiene a dicho producto si
//	public void devolverFacturaKOProductoInexistente() {}
	@Test
	public void actualizarFacturaOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "J", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		this.saFactura.cerrarFactura(carrito);
		factura.setCosteTotal(20);
		assertTrue(this.saFactura.actualizarFactura(factura) > 0);
	}
	@Test
	public void actualizarFacturaKO() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "K", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		assertTrue(this.saFactura.actualizarFactura(factura) == SAFactura.FACTURA_INEXISTENTE);
	}
	@Test
	public void listarPorProductoOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "L", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		almacen = new TAlmacen("listarPorProductoOK");
		this.daoAlmacen.altaAlmacen(almacen);
		producto = new TProducto("listarPorProductoOK", 10, 10, almacen.getId());
		this.daoProducto.altaProducto(producto);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		this.saFactura.cerrarFactura(carrito);
		List<TFactura> res = this.saFactura.listarFacturasPorProducto(producto.getId());
		assertNotNull(res);
		assertTrue(!res.isEmpty());
	}
	@Test
	public void listarPorProductoKOProdInexistente() {
		assertNull(this.saFactura.listarFacturasPorProducto(-1));
	}
	
	@Test
	public void listarPorClienteOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "M", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		this.saFactura.cerrarFactura(carrito);
		List<TFactura> res = this.saFactura.listarFacturasPorCliente(cliente.getId());
		assertNotNull(res);
		assertTrue(!res.isEmpty());
	}
	
	@Test
	public void listarPorClienteKOClienteInexistente() {
		assertNull(this.saFactura.listarFacturasPorCliente(-1));
	}
	
	@Test
	public void listarKO() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "N", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		this.saFactura.cerrarFactura(carrito);
		List<TFactura> res = this.saFactura.listarFacturas();
		assertNotNull(res);
		assertTrue(!res.isEmpty());
	}
	
	@Test
	public void buscarOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "O", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		this.saFactura.cerrarFactura(carrito);
		assertNotNull(this.saFactura.buscarFactura(factura.getId()));
	}
	
	@Test
	public void buscarKO() {
		assertNull(this.saFactura.buscarCarrito(-1));
	}
	
	@Test
	public void buscarCarritoOK() {
		cliente = new TClienteVIP(id_tarjeta_vip, tlf, nif + "P", nombre, direccion, activo);
		this.daoCliente.altaClienteVIP(cliente);
		
		almacen = new TAlmacen("buscarCarritoOK");
		this.daoAlmacen.altaAlmacen(almacen);
		
		producto = new TProducto("buscarCarritoOK", 15, 10, almacen.getId());
		this.daoProducto.altaProducto(producto);
		
		factura = new TFactura(cliente.getId());
		carrito = new TCarrito(factura);
		linea = new TLineaFactura(producto.getId(), producto.getStock(), producto.getPrecio());
		carrito.getLineas().put(producto.getId(), linea);
		this.saFactura.cerrarFactura(carrito);
		assertNotNull(this.saFactura.buscarCarrito(factura.getId()));
	}
	
	@Test
	public void buscarCarritoKO() {
		assertNull(this.saFactura.buscarCarrito(-1));
	}
}
