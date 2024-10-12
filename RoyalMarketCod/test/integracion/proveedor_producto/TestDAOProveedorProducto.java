package integracion.proveedor_producto;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.proveedor.DAOProveedor;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.producto.TProducto;
import negocio.proveedor.TProveedor;
import negocio.proveedor_producto.TProveedorProducto;

public class TestDAOProveedorProducto {
	
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
		
		this.daoalmacen = fi.crearDAOAlmacen();
		this.daopp = fi.crearDAOPoveedorProducto();
		this.daoproducto = fi.creaDAOProducto();
		this.daoproveedor = fi.crearDAOProveedor();
		
		this.ta = new TAlmacen("almacen");
		this.daoalmacen.altaAlmacen(ta);
		
		this.tprod = new TProducto("nombre", 456, 654, this.ta.getId());
		this.daoproducto.altaProducto(tprod);
		
		this.tprov = new TProveedor(123456789, "12345687G", "prov");
		this.daoproveedor.altaProveedor(tprov);
		
		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		this.transaction.start();
	}
	
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
	@Test
	public void createOK() {
		assertTrue(this.daopp.createProveedorProducto(new TProveedorProducto(tprov.getId(), tprod.getId())) > 0);
	}

	@Test
	public void createKO() {
		assertTrue(this.daopp.createProveedorProducto(new TProveedorProducto(-1, -1)) < 0);
	}
	
	@Test
	public void listarPorProvOK() {
		this.daopp.createProveedorProducto(new TProveedorProducto(tprov.getId(), tprod.getId()));
		List<TProveedorProducto> res = this.daopp.readAllByProveedor(tprov.getId());
		assertTrue(res != null && !res.isEmpty());
	}

	@Test
	public void listarPorProvKO() {
		List<TProveedorProducto> res = this.daopp.readAllByProveedor(tprov.getId());
		assertTrue(res == null);
	}

	@Test
	public void listarPorProdOK() {
		this.daopp.createProveedorProducto(new TProveedorProducto(tprov.getId(), tprod.getId()));
		List<TProveedorProducto> res = this.daopp.readAllByProducto(tprod.getId());
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void listarPorProdKO() {
		List<TProveedorProducto> res = this.daopp.readAllByProducto(tprod.getId());
		assertTrue(res == null);
	}
	
	@Test
	public void eliminarOK() {
		this.daopp.createProveedorProducto(new TProveedorProducto(tprov.getId(), tprod.getId()));
		assertTrue(this.daopp.deleteProveedorProducto(tprov.getId(), tprod.getId()) > 0);
	}

	@Test
	public void eliminarKO() {
		assertTrue(this.daopp.deleteProveedorProducto(tprov.getId(), tprod.getId()) < 0);
	}

	@Test
	public void buscarOK() {
		this.daopp.createProveedorProducto(new TProveedorProducto(tprov.getId(), tprod.getId()));
		assertTrue(this.daopp.readProveedorProducto(tprov.getId(), tprod.getId()) != null);
	}
	
	@Test
	public void buscarKO() {
		assertTrue(this.daopp.readProveedorProducto(tprov.getId(), tprod.getId()) == null);
	}

}
