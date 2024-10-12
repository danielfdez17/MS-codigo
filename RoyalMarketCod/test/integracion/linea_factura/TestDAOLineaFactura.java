package integracion.linea_factura;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.cliente.DAOCliente;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.factura.DAOFactura;
import integracion.producto.DAOProducto;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.cliente.TClienteVIP;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.producto.TProducto;

public class TestDAOLineaFactura {

	private DAOLineaFactura daoLinea;
	private DAOFactura daoFactura;
	private DAOCliente daocliente;
	private DAOProducto daoProducto;
	private DAOAlmacen daoAlmacen;

	private TLineaFactura tl;
	private TFactura tf;
	private TClienteVIP vip;
	private TProducto tp;
	private TAlmacen ta;
	
	private TransactionManager tmanager;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	
	
	
	@Before
	public void setUp() {
		this.fi = FactoriaIntegracion.getInstancia();
		this.daoLinea = this.fi.crearDAOLineaFactura();
		this.daoFactura = this.fi.crearDAOFactura();
		this.daocliente = this.fi.crearDAOCliente();
		this.daoProducto = this.fi.creaDAOProducto();
		this.daoAlmacen = this.fi.crearDAOAlmacen();
		
		this.ta = new TAlmacen("alli");
		this.daoAlmacen.altaAlmacen(ta);
		
		this.tp = new TProducto("Lays", 10, 100, ta.getId());
		this.daoProducto.altaProducto(tp);
		
		this.vip = new TClienteVIP(123, 132456789, "56695125F", "Juan", "lejos", true);
		this.daocliente.altaClienteVIP(vip);
		
		this.tf = new TFactura(vip.getId());
		this.daoFactura.cerrarFactura(tf);
		
		this.tl = new TLineaFactura(tf.getId(), tp.getId(), 10, tp.getPrecio());
		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		this.transaction.start();
	}
	
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
	@Test
	public void crearOK() {
		assertTrue(this.daoLinea.crearLinea(tl) > 0);
	}
	
	@Test
	public void crearKO() {
		assertTrue(this.daoLinea.crearLinea(new TLineaFactura(-1, -1, -1, -1)) < 0);
	}
	
	@Test
	public void buscarOK() {
		this.daoLinea.crearLinea(tl);
		assertTrue(this.daoLinea.buscarLinea(this.tl.getIdFactura(), this.tl.getIdProducto()) != null);
	}
	
	@Test
	public void buscarKO() {
		assertTrue(this.daoLinea.buscarLinea(-1, -1) == null);
	}
	
	@Test
	public void listarPorFacturaOK() {
		this.daoLinea.crearLinea(tl);
		List<TLineaFactura> res = this.daoLinea.listarLineasPorFactura(tl.getIdFactura());
		assertTrue(res != null && !res.isEmpty());
	}

	@Test
	public void listarPorFacturaKO() {
		List<TLineaFactura> res = this.daoLinea.listarLineasPorFactura(tl.getIdFactura());
		assertTrue(res == null);
	}
	
	@Test
	public void listarPorProductoOK() {
		this.daoLinea.crearLinea(tl);
		List<TLineaFactura> res = this.daoLinea.listarLineasPorProducto(tl.getIdProducto());
		assertTrue(res != null && !res.isEmpty());
	}

	@Test
	public void listarPorProductoKO() {
		List<TLineaFactura> res = this.daoLinea.listarLineasPorFactura(-1);
		assertTrue(res == null);
	}
	
	@Test
	public void actualizarOK() {
		this.daoLinea.crearLinea(tl);
		assertTrue(this.daoLinea.actualizarLinea(tl) > 0);
	}

	@Test
	public void actualizarKO() {
		assertTrue(this.daoLinea.actualizarLinea(tl) < 0);
	}

	@Test
	public void eliminarOK() {
		this.daoLinea.crearLinea(tl);
		assertTrue(this.daoLinea.eliminarLinea(tl.getIdFactura(), tl.getIdProducto()) > 0);
	}
	
	@Test
	public void eliminarKO() {
		assertTrue(this.daoLinea.eliminarLinea(-1, -1) < 0);
	}
	
}
