package integracion.proveedor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.proveedor_producto.DAOProveedorProducto;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.producto.TProducto;
import negocio.proveedor.TProveedor;
import negocio.proveedor_producto.TProveedorProducto;

public class TestDAOProveedor {
	
	private DAOAlmacen daoalmacen;
	private DAOProducto daoproducto;
	private DAOProveedor daoproveedor;
	private DAOProveedorProducto daopp;
	
	private TransactionManager tmanager;
	private TAlmacen ta;
	private TProducto tprod;
	private TProveedor tprov;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	
	@Before
	public void setUp() {
		this.fi = FactoriaIntegracion.getInstancia();
		this.daoalmacen = this.fi.crearDAOAlmacen();
		this.daopp = this.fi.crearDAOPoveedorProducto();
		this.daoproducto = this.fi.creaDAOProducto();
		this.daoproveedor = this.fi.crearDAOProveedor();
		
		this.ta = new TAlmacen("almacen");
		this.daoalmacen.altaAlmacen(ta);
		
		this.tprod = new TProducto("Lays", 5, 100, this.ta.getId());
		this.daoproducto.altaProducto(tprod);
		
		this.tprov = new TProveedor(123456789, "12345678G", "prov");
		
		
		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		this.transaction.start();
	}
	
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
	@Test
	public void altaOK() {
		assertTrue(this.daoproveedor.altaProveedor(tprov) > 0);
	}
	
	@Test
	public void buscarOK() {
		this.daoproveedor.altaProveedor(tprov);
		assertTrue(this.daoproveedor.buscarProveedor(this.tprov.getId()) != null);
		assertTrue(this.daoproveedor.buscarProveedorPorNIF(this.tprov.getNif()) != null);
	}

	@Test
	public void buscarKO() {
		assertTrue(this.daoproveedor.buscarProveedor(-1) == null);
		assertTrue(this.daoproveedor.buscarProveedorPorNIF(" ") == null);
	}
	
	@Test
	public void listarOK() {
		this.daoproveedor.altaProveedor(tprov);
		List<TProveedor> res = this.daoproveedor.listarProveedores();
		assertTrue(res != null && !res.isEmpty());
	}

	@Test
	public void listarKO() {
		List<TProveedor> res = this.daoproveedor.listarProveedores();
		assertTrue(res == null);
	}
	
	@Test
	public void listarPorProdOK() {
		this.daoproducto.altaProducto(tprod);
		this.daoproveedor.altaProveedor(tprov);
		this.daopp.createProveedorProducto(new TProveedorProducto(tprov.getId(), tprod.getId()));
		List<TProveedor> res = this.daoproveedor.listarProveedoresPorProducto(tprod.getId());
		assertTrue(res != null && !res.isEmpty());
	}

	@Test
	public void listarPorProdKO() {
		List<TProveedor> res = this.daoproveedor.listarProveedoresPorProducto(tprod.getId());
		assertTrue(res == null);
	}
	
	@Test
	public void actualizarOK() {
		this.daoproveedor.altaProveedor(tprov);
		assertTrue(this.daoproveedor.actualizarProveedor(tprov) > 0);
	}

	@Test
	public void actualizarKO() {
		assertTrue(this.daoproveedor.actualizarProveedor(tprov) < 0);
	}
	
	@Test
	public void bajaOK() {
		this.daoproveedor.altaProveedor(tprov);
		assertTrue(this.daoproveedor.bajaProveedor(tprov.getId()) > 0);
	}
	
	@Test
	public void bajaKO() {
		assertTrue(this.daoproveedor.bajaProveedor(-1) < 0);
	}
	
	
}
