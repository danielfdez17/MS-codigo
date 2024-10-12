package integracion.cliente;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;

public class TestDAOCliente {
	
	private DAOCliente dao;
	private TransactionManager tmanager;
	private FactoriaIntegracion fi;
	private Transaction transaction;
	private TClienteVIP tcv;
	private TClienteNormal tcn;
	
	@Before
	public void setUp() {
		this.fi = FactoriaIntegracion.getInstancia();
		this.dao = this.fi.crearDAOCliente();
		this.tcv = new TClienteVIP(123, 123456789, "12345678J", "Juan", "Sucasa", true);
		this.tcn = new TClienteNormal(321, 987654321, "98765432G", "Pepe", "Micasa", true);
		this.tmanager = TransactionManager.getInstancia();
		this.transaction = this.tmanager.nuevaTransaccion();
		this.transaction.start();
	}
	@After
	public void tearDown() {
		this.transaction.rollback();
	}
	
	// TODO insertar ok
	@Test
	public void altaVipOK() {
		assertTrue(this.dao.altaClienteVIP(tcv) > 0);
	}
	@Test
	public void altaNormalOK() {
		assertTrue(this.dao.altaClienteNormal(tcn) > 0);
	}
	// TODO leer ok
	// TODO leer por nif ok
	@Test
	public void buscarOK() {
		this.dao.altaClienteNormal(tcn);
		this.dao.altaClienteVIP(this.tcv);
		assertNotNull(this.dao.buscarCliente(tcn.getId()));
		assertNotNull(this.dao.buscarClientePorNIF(tcn.getNIF()));
		assertNotNull(this.dao.buscarCliente(tcv.getId()));
		assertNotNull(this.dao.buscarClientePorNIF(tcv.getNIF()));
	}
	// TODO leer ko
	// TODO leer por nif ko
	@Test
	public void buscarKO() {
		TCliente tc = this.dao.buscarCliente(-1);
		assertTrue(tc == null);
		tc = this.dao.buscarClientePorNIF(" ");
		assertTrue(tc == null);
	}
	// TODO listar ok
	@Test
	public void listarOK() {
		this.dao.altaClienteNormal(tcn);
		this.dao.altaClienteVIP(tcv);
		List<TCliente> res = this.dao.listarClientes();
		assertTrue(res != null && res.size() == 2);
	}
	// TODO listar ko
	@Test
	public void listarKO() {
		assertTrue(this.dao.listarClientes() == null);
	}
	// TODO actualizar ok
	@Test
	public void actualizarOK() {
		this.dao.altaClienteNormal(tcn);
		this.dao.altaClienteVIP(tcv);
		assertTrue(this.dao.actualizarClienteNormal(tcn) > 0);
		assertTrue(this.dao.actualizarClienteVIP(tcv) > 0);
	}
	// TODO actualizar ko
	@Test
	public void actualizarKO() {
		this.tcn.setId(-1);
		this.tcv.setId(-1);
		assertTrue(this.dao.actualizarClienteNormal(tcn) < 0);
		assertTrue(this.dao.actualizarClienteVIP(tcv) < 0);
	}
	// TODO baja ok
	@Test
	public void bajaOK() {
		this.dao.altaClienteNormal(tcn);
		assertTrue(this.dao.bajaCliente(tcn.getId()) > 0);
	}
	// TODO baja ko
	@Test
	public void bajaKO() {
		assertTrue(this.dao.bajaCliente(-1) < 0);
	}
}
