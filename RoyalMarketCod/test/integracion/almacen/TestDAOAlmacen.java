package integracion.almacen;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;

public class TestDAOAlmacen {
	
	private DAOAlmacen dao;
	private TransactionManager tmanager;
	private TAlmacen ta;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	 
	@Before
	public void setUp() {
		this.fi = FactoriaIntegracion.getInstancia();
		this.dao = this.fi.crearDAOAlmacen();
		this.ta = new TAlmacen("direccion");
		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		this.transaction.start();
	}
	@Test
	public void altaOK() {
		int res = this.dao.altaAlmacen(this.ta);
		assertTrue("altaAlmacen OK", res > 0);
	}
	@Test
	public void buscarOK() {
		this.dao.altaAlmacen(this.ta);
		this.ta = this.dao.buscarAlmacen(this.ta.getId());
		assertTrue("buscarAlmacen OK\n" + this.ta.toString(), this.ta != null);
	}
	@Test
	public void buscarKO() {
		TAlmacen almacen = this.dao.buscarAlmacen(-1);
		assertTrue("buscarAlmacen KO", almacen == null);
	}
	@Test
	public void listarOK() {
		this.dao.altaAlmacen(this.ta);
		List<TAlmacen> res = this.dao.listarAlmacenes();
		assertTrue("listarAlmacenes OK", res != null && !res.isEmpty());
	}
	@Test
	public void listarKO() {
		List<TAlmacen> res = this.dao.listarAlmacenes();
		assertTrue("listarAlmacenes KO", res == null);
	}
	@Test
	public void actualizarOK() {
		this.dao.altaAlmacen(this.ta);
		this.ta.setDireccion("dir");
		int res = this.dao.actualizarAlmacen(this.ta);
		assertTrue("actualizarAlmacen OK", res > 0);
	}
	@Test
	public void actualizarKO() {
		int res = this.dao.actualizarAlmacen(this.ta);
		assertTrue("actualizarAlmacen KO", res < 0);
	}
	@Test
	public void bajaOK() {
		this.dao.altaAlmacen(this.ta);
		int res = this.dao.bajaAlmacen(this.ta.getId());
		assertTrue("bajaAlmacen OK", res > 0);
	}
	@Test
	public void bajaKO() {
		int res = this.dao.bajaAlmacen(-1);
		assertTrue("bajaAlmacen KO", res < 0);
	}
	@Test
	public void buscarPorDireccionOK() {
		this.dao.altaAlmacen(this.ta);
		this.ta = this.dao.buscarAlmacenPorDireccion(this.ta.getDireccion());
		assertTrue("buscarAlmacenPorDireccion OK", this.ta != null);
	}
	@Test
	public void buscarPorDireccionKO() {
		TAlmacen almacen = this.dao.buscarAlmacenPorDireccion(" ");
		assertTrue("buscarAlmacenPorDireccion KO", almacen == null);
	}
	
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
}
