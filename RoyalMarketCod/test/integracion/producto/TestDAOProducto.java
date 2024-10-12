package integracion.producto;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.producto.TProducto;

public class TestDAOProducto {
	
	private DAOProducto daoproducto;
	private DAOAlmacen daoalmacen;
	private TransactionManager tmanager;
	private TProducto tp;
	private TAlmacen ta;
	private FactoriaIntegracion fi;
	private Transaction transaction;

	@Before
	public void setUp() {
		this.fi = FactoriaIntegracion.getInstancia();
		this.daoalmacen = this.fi.crearDAOAlmacen();
		this.daoproducto = this.fi.creaDAOProducto();
		
		this.ta = new TAlmacen("calle");
		this.daoalmacen.altaAlmacen(ta);
		this.tp = new TProducto("Lays", 5, 100, this.ta.getId());
		
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
		assertTrue(this.daoproducto.altaProducto(tp) > 0);
	}
	
	@Test
	public void altaKO() {
		this.tp.setIdAlmacen(-1);
		assertTrue(this.daoproducto.altaProducto(tp) < 0);
	}
	
	@Test
	public void buscarOK() {
		this.daoproducto.altaProducto(tp);
		assertTrue(this.daoproducto.buscarProducto(tp.getId()) != null);
		assertTrue(this.daoproducto.buscarProductoPorNombre(tp.getNombre()) != null);
	}
	
	@Test
	public void buscarKO() {
		assertTrue(this.daoproducto.buscarProducto(-1) == null);
		assertTrue(this.daoproducto.buscarProductoPorNombre(" ") == null);
	}
	
	@Test
	public void listarOK() {
		this.daoproducto.altaProducto(tp);
		List<TProducto> res = this.daoproducto.listarProductos();
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void actualizarOK() {
		this.daoproducto.altaProducto(tp);
		assertTrue(this.daoproducto.actualizarProducto(tp) > 0);
	}
	
	@Test
	public void actualizarKO() {
		assertTrue(this.daoproducto.actualizarProducto(tp) < 0);
	}

	@Test
	public void bajaOK() {
		this.daoproducto.altaProducto(tp);
		assertTrue(this.daoproducto.bajaProducto(tp.getId()) > 0);
	}
	
	@Test
	public void bajaKO() {
		assertTrue(this.daoproducto.bajaProducto(-1) < 0);
	}
	
	@Test
	public void listarPorAlmacenOK() {
		this.daoproducto.altaProducto(tp);
		List<TProducto> res = this.daoproducto.listarProductosPorAlmacen(tp.getIdAlmacen());
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void listarPorAlmacenKO() {
		List<TProducto> res = this.daoproducto.listarProductosPorAlmacen(-1);
		assertTrue(res == null);
	}
}
