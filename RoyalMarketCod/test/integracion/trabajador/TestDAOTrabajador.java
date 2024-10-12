package integracion.trabajador;

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
import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;

public class TestDAOTrabajador {
	
	private DAOAlmacen daoalmacen;
	private DAOTrabajador daot;
	private TransactionManager tmanager;
	private TAlmacen ta;
	private TTrabajadorJParcial ttjp;
	private TTrabajadorJCompleta ttjc;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	
	@Before
	public void setUp() {
		this.fi = FactoriaIntegracion.getInstancia();
		this.daoalmacen = fi.crearDAOAlmacen();
		this.daot = fi.crearDAOTrabajador();

		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		this.transaction.start();

		
		this.ta = new TAlmacen("almacen");
		this.daoalmacen.altaAlmacen(ta);
		
		this.ttjc = new TTrabajadorJCompleta(123456789, "12345678K", "nombre", "dir", this.ta.getId(), 1000);
		this.ttjp = new TTrabajadorJParcial(987654321, "98765432S", "nombres", "dirs", this.ta.getId(), 5, 26);
		
	}
	
	
	@Test
	public void altaOK() {
		assertTrue(this.daot.altaTrabajadorJCompleta(ttjc) > 0);
		assertTrue(this.daot.altaTrabajadorJParcial(ttjp) > 0);
	}

	@Test
	public void altaKO() {
		this.ttjc.setIdAlmacen(-1);
		this.ttjp.setIdAlmacen(-1);
		assertTrue(this.daot.altaTrabajadorJCompleta(ttjc) < 0);
		assertTrue(this.daot.altaTrabajadorJParcial(ttjp) < 0);
	}
	
	@Test
	public void buscarOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.altaTrabajadorJParcial(ttjp);
		assertTrue(this.daot.buscarTrabajador(ttjc.getId()) != null);
		assertTrue(this.daot.buscarTrabajador(ttjp.getId()) != null);
		assertTrue(this.daot.buscarTrabajadorPorNIF(ttjp.getNIF()) != null);
		assertTrue(this.daot.buscarTrabajadorPorNIF(ttjc.getNIF()) != null);
	}

	@Test
	public void buscarKO() {
		assertTrue(this.daot.buscarTrabajador(ttjc.getId()) == null);
		assertTrue(this.daot.buscarTrabajador(ttjp.getId()) == null);
		assertTrue(this.daot.buscarTrabajadorPorNIF(ttjp.getNIF()) == null);
		assertTrue(this.daot.buscarTrabajadorPorNIF(ttjc.getNIF()) == null);
	}
	
	@Test
	public void listarOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.altaTrabajadorJParcial(ttjp);
		List<TTrabajador> res = this.daot.listarTrabajadores();
		assertTrue(res != null && !res.isEmpty());
	}

	@Test
	public void listarKO() {
		List<TTrabajador> res = this.daot.listarTrabajadores();
		assertTrue(res == null);
	}

	@Test
	public void listarPorAlmacenOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.altaTrabajadorJParcial(ttjp);
		List<TTrabajador> res = this.daot.listarTrabajadoresPorAlmacen(ttjc.getIdAlmacen());
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void listarPorAlmacenKO() {
		List<TTrabajador> res = this.daot.listarTrabajadoresPorAlmacen(-1);
		assertTrue(res == null);
	}
	
	@Test
	public void actualizarOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.altaTrabajadorJParcial(ttjp);
		assertTrue(this.daot.actualizarTrabajadorJCompleta(ttjc) > 0);
		assertTrue(this.daot.actualizarTrabajadorJParcial(ttjp) > 0);
	}

	@Test
	public void actualizarKO() {
		assertTrue(this.daot.actualizarTrabajadorJCompleta(ttjc) < 0);
		assertTrue(this.daot.actualizarTrabajadorJParcial(ttjp) < 0);
	}

	@Test
	public void bajaOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.altaTrabajadorJParcial(ttjp);
		assertTrue(this.daot.bajaTrabajador(ttjc.getId()) > 0);
		assertTrue(this.daot.bajaTrabajador(ttjp.getId()) > 0);
	}
	
	@Test
	public void bajaKO() {
		assertTrue(this.daot.bajaTrabajador(ttjc.getId()) < 0);
		assertTrue(this.daot.bajaTrabajador(ttjp.getId()) < 0);
	}

	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
}
