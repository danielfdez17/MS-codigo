package integracion.factura;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.List;

import integracion.almacen.DAOAlmacen;
import integracion.cliente.DAOCliente;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.cliente.TClienteVIP;
import negocio.factura.TFactura;
import negocio.producto.TProducto;

public class TestDAOFactura {
	
	private DAOFactura dao;
	private DAOCliente daocliente;
	private DAOProducto daoproducto;
	private DAOAlmacen daoalmacen;
	private TransactionManager tmanager;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	private TFactura tf;
	private TClienteVIP tcv;
	private TProducto tp;	
	private TAlmacen ta;
	
	@Before
	public void setUp() {
		
		this.fi = FactoriaIntegracion.getInstancia();
		
		this.dao = this.fi.crearDAOFactura();
		this.daocliente = this.fi.crearDAOCliente();
		this.daoproducto = this.fi.creaDAOProducto();
		this.daoalmacen = this.fi.crearDAOAlmacen();
		
		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		
		this.tcv = new TClienteVIP(123, 123456789, "12345678G", "Juan", "alli", true);
		this.daocliente.altaClienteVIP(tcv);
		this.tf = new TFactura(tcv.getId());
		this.ta = new TAlmacen("direccion");
		this.daoalmacen.altaAlmacen(ta);
		this.tp = new TProducto("Lays", 5, 100, ta.getId());
		this.daoproducto.altaProducto(tp);
		
		
		this.transaction.start();
	}
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
	@Test
	public void cerrarOK() {
		assertTrue(this.dao.cerrarFactura(this.tf) > 0);
	}
	
	@Test
	public void cerrarKO() {
		this.tf.setIdCliente(-1);
		assertTrue(this.dao.cerrarFactura(tf) < 0);
	}
	
	@Test
	public void actualizarOK() {
		this.dao.cerrarFactura(tf);
		assertTrue(this.dao.actualizarFactura(tf) > 0);
	}
	
	@Test
	public void actualizarKO() {
		assertTrue(this.dao.actualizarFactura(tf) < 0);
	}
	
	@Test
	public void listarPorClienteOK() {
		this.dao.cerrarFactura(tf);
		List<TFactura> res = this.dao.listarFacturasPorCliente(tf.getIdCliente());
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void listarOK() {
		this.dao.cerrarFactura(tf);
		List<TFactura> res = this.dao.listarFacturas();
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void buscarOK() {
		this.dao.cerrarFactura(tf);
		assertTrue(this.dao.buscarFactura(tf.getId()) != null);
	}
	
	@Test
	public void buscarKO() {
		assertTrue(this.dao.buscarFactura(-1) == null);
	}
	
}
