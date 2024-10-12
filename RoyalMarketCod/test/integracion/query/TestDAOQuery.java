package integracion.query;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.trabajador.DAOTrabajador;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;


public class TestDAOQuery {
	
	private DAOAlmacen daoalmacen;
	private DAOTrabajador daot;
	private TransactionManager tmanager;
	private TAlmacen ta;
	private TTrabajadorJParcial ttjp;
	private TTrabajadorJCompleta ttjc;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	private FactoriaQuery fq;
	private Query qDespedidos;
	private Query qJCompleta;
	private Query qRango;
	
	@Before
	public void setUp() {
		this.fq = FactoriaQuery.getInstancia();
		this.qDespedidos = fq.crearQueryTrabajadoresDespedidos();
		this.qJCompleta = fq.crearQueryTrabajadoresJCompleta();
		this.qRango = fq.crearQueryTrabajadoresPorRangoDeSueldo();
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
	
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryDespedidosOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.actualizarTrabajadorJParcial(ttjp);
		this.daot.bajaTrabajador(ttjp.getId());
		this.daot.bajaTrabajador(ttjc.getId());
		List<TTrabajador> res = (List<TTrabajador>) qDespedidos.execute(null);
		assertTrue(res != null && !res.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryDespedidosKO() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.actualizarTrabajadorJParcial(ttjp);
		List<TTrabajador> res = (List<TTrabajador>) qDespedidos.execute(null);
		assertTrue(res == null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void queryJCompletaOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.actualizarTrabajadorJParcial(ttjp);
		this.daot.bajaTrabajador(ttjp.getId());
		this.daot.bajaTrabajador(ttjc.getId());
		List<TTrabajador> res = (List<TTrabajador>) qJCompleta.execute(null);
		assertTrue(res != null && !res.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryJCompletaKO() {
		this.daot.actualizarTrabajadorJParcial(ttjp);
		List<TTrabajador> res = (List<TTrabajador>) qJCompleta.execute(null);
		assertTrue(res == null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void queryRangoOK() {
		this.daot.altaTrabajadorJCompleta(ttjc);
		this.daot.actualizarTrabajadorJParcial(ttjp);
		this.daot.bajaTrabajador(ttjp.getId());
		this.daot.bajaTrabajador(ttjc.getId());
		ArrayList<Double> rangos = new ArrayList<Double>();
		rangos.add(999.0);
		rangos.add(1001.0);
		List<TTrabajador> res = (List<TTrabajador>) qRango.execute(rangos);
		assertTrue(res != null && !res.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryRangoKO() {
		this.daot.actualizarTrabajadorJParcial(ttjp);
		ArrayList<Double> rangos = new ArrayList<Double>();
		rangos.add(999.0);
		rangos.add(1001.0);
		List<TTrabajador> res = (List<TTrabajador>) qRango.execute(rangos);
		assertTrue(res == null);
	}

}
