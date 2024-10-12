package negocio.cliente;

import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import negocio.factoriaNegocio.FactoriaNegocio;

public class TestSACliente {
	
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final int id_tarjeta_vip = 123;
	private static final int id_tarjeta_normal = 321;
	private static final int tlf = 132456789;
	private static final boolean activo = true;
	
	private SACliente saCliente;
	private TClienteVIP tcv;
	private TClienteNormal tcn;
	
	@Before
	public void setUp() {
		FactoriaNegocio fn = FactoriaNegocio.getInstancia();
		this.saCliente = fn.crearSACliente();
		this.tcv = new TClienteVIP(id_tarjeta_vip, tlf, "", nombre, direccion, activo);
		this.tcn = new TClienteNormal(id_tarjeta_normal, tlf, "", nombre, direccion, activo);
	}
	
	@Test
	public void altaOK() {
		TClienteVIP tcv = new TClienteVIP(123, 123456789, "12345678A", "vipAltaOK", "sucasa", true);
		assertTrue(this.saCliente.altaClienteVIP(tcv) > 0);
		TClienteNormal tcn = new TClienteNormal(321, 321654987, "98765432A", "normalAltaOK", "sucasa", true);
		assertTrue(this.saCliente.altaClienteNormal(tcn) > 0);
	}
	
	@Test
	public void altaKOErrorSintactico() {
		TClienteVIP tcv = new TClienteVIP(123, 123456789, "12345678B", "vipAltaKOErrorSin1", "sucasa", true);
		assertTrue(this.saCliente.altaClienteVIP(tcv) == SACliente.ERROR_SINTACTICO);
		TClienteNormal tcn = new TClienteNormal(321, 321654987, "98765432B", "normalAltaKOErrorSin1", "sucasa", true);
		assertTrue(this.saCliente.altaClienteNormal(tcn) == SACliente.ERROR_SINTACTICO);
	}

	@Test
	public void altaKOClienteActivo() {
		TClienteVIP tcv = new TClienteVIP(123, 123456789, "12345678C", "vipAltaKOCLienteActivo", "sucasa", true);
		this.saCliente.altaClienteVIP(tcv);
		assertTrue(this.saCliente.altaClienteVIP(tcv) == SACliente.CLIENTE_ACTIVO);
		TClienteNormal tcn = new TClienteNormal(321, 321654987, "98765432C", "normalAltaKOClienteActivo", "sucasa", true);
		this.saCliente.altaClienteNormal(tcn);
		assertTrue(this.saCliente.altaClienteNormal(tcn) == SACliente.CLIENTE_ACTIVO);
	}

	@Test
	public void altaKOClienteInactivo() {
		TClienteVIP tcv = new TClienteVIP(123, 123456789, "12345678D", "vipAltaKOCLienteInactivo", "sucasa", true);
		this.saCliente.altaClienteVIP(tcv);
		this.saCliente.bajaCliente(tcv.getId());
		assertTrue(this.saCliente.altaClienteVIP(tcv) == SACliente.CLIENTE_INACTIVO);
		TClienteNormal tcn = new TClienteNormal(321, 321654987, "98765432D", "normalAltaKOClienteInactivo", "sucasa", true);
		this.saCliente.altaClienteNormal(tcn);
		this.saCliente.bajaCliente(tcn.getId());
		assertTrue(this.saCliente.altaClienteNormal(tcn) == SACliente.CLIENTE_INACTIVO);
	}

	@Test
	public void altaKOCambioTipo() {
		TClienteVIP tcv = new TClienteVIP(123, 123456789, "12345678E", "vipAltaKOCambioTipo", "sucasa", true);
		this.saCliente.altaClienteVIP(tcv);
		this.saCliente.bajaCliente(tcv.getId());
		TClienteNormal tcn = new TClienteNormal(321, 321654987, "98765432E", "normalAltaKOClienteInactivo", "sucasa", true);
		this.saCliente.altaClienteNormal(tcn);
		this.saCliente.bajaCliente(tcn.getId());
		tcv.setNIF("98765432E");
		tcn.setNIF("12345678E");
		assertTrue(this.saCliente.altaClienteVIP(tcv) == SACliente.CAMBIO_TIPO);
		assertTrue(this.saCliente.altaClienteNormal(tcn) == SACliente.CAMBIO_TIPO);
	}
	
	@Test
	public void buscarOK() {
		TClienteVIP tcv = new TClienteVIP(123, 132456789, "12345678F", "juan", "sucasa", true);
		TClienteNormal tcn = new TClienteNormal(321, 987654321, "98765432F", "juan", "lejos", true);
		this.saCliente.altaClienteVIP(tcv);
		this.saCliente.altaClienteNormal(tcn);
		assertNotNull(this.saCliente.buscarCliente(tcv.getId()));
		assertNotNull(this.saCliente.buscarCliente(tcn.getId()));
	}
	
	@Test
	public void buscarKO() {
		assertNull(this.saCliente.buscarCliente(-1));
	}
	
	@Test
	public void listarOK() {
		TClienteNormal tcn = new TClienteNormal(123456, 123456789, "12345678G", "nombre", "direccion", true);
		this.saCliente.altaClienteNormal(tcn);
		List<TCliente> res = this.saCliente.listarClientes();
		assertTrue(res != null && !res.isEmpty());
	}
	@Test
	public void actualizarOK() {
		TClienteVIP tcv = new TClienteVIP(123, 456123789, "12345678H", "nombre", "direccion", true);
		TClienteNormal tcn = new TClienteNormal(1235, 123456789, "98765432H", "nombre", "direccion", true);
		this.saCliente.altaClienteNormal(tcn);
		this.saCliente.altaClienteVIP(tcv);
		assertTrue(this.saCliente.actualizarClienteNormal(tcn) > 0);
		assertTrue(this.saCliente.actualizarClienteVIP(tcv) > 0);
	}
	
	@Test
	public void actualizarKOErrorSintactico() {
		TClienteVIP tcv = new TClienteVIP(id_tarjeta_vip, tlf, "12345678I", "asdnf2", direccion, activo);
		TClienteNormal tcn = new TClienteNormal(id_tarjeta_normal, tlf, "98765432I", "asdfa5", direccion, activo);
		assertTrue(this.saCliente.actualizarClienteNormal(tcn) == SACliente.ERROR_SINTACTICO);
		assertTrue(this.saCliente.actualizarClienteVIP(tcv) == SACliente.ERROR_SINTACTICO);
	}
	
	@Test
	public void actualizarKOClienteInexistente() {
		TClienteVIP tcv = new TClienteVIP(id_tarjeta_vip, tlf, "12345678J", nombre, direccion, activo);
		TClienteNormal tcn = new TClienteNormal(id_tarjeta_normal, tlf, "98765432J", nombre, direccion, activo);
		assertTrue(this.saCliente.actualizarClienteNormal(tcn) == SACliente.CLIENTE_INEXISTENTE);
		assertTrue(this.saCliente.actualizarClienteVIP(tcv) == SACliente.CLIENTE_INEXISTENTE);
	}
	
	@Test
	public void actualizarKOCambioTipo() {
		TClienteVIP tcv = new TClienteVIP(id_tarjeta_vip, tlf, "12345678K", nombre, direccion, activo);
		TClienteNormal tcn = new TClienteNormal(id_tarjeta_normal, tlf, "98765432K", nombre, direccion, activo);
		this.saCliente.altaClienteNormal(tcn);
		this.saCliente.altaClienteVIP(tcv);
		tcv.setNIF("98765432K");
		tcn.setNIF("12345678K");
		assertTrue(this.saCliente.actualizarClienteNormal(tcn) == SACliente.CAMBIO_TIPO);
		assertTrue(this.saCliente.actualizarClienteVIP(tcv) == SACliente.CAMBIO_TIPO);
	}
	
	@Test
	public void bajaOK() {
		tcv.setNIF("12345678L");
		tcn.setNIF("98765432L");
		this.saCliente.altaClienteNormal(tcn);
		this.saCliente.altaClienteVIP(tcv);
		assertTrue(this.saCliente.bajaCliente(tcv.getId()) == tcv.getId());
		assertTrue(this.saCliente.bajaCliente(tcn.getId()) == tcn.getId());
	}
	
	@Test
	public void bajaKOClienteInexistente() {
		assertTrue(this.saCliente.bajaCliente(-1) == SACliente.CLIENTE_INEXISTENTE);
	}
	
	@Test
	public void bajaKOClienteInactivo() {
		this.tcv.setNIF("12345678M");
		this.tcn.setNIF("98765432M");
		this.saCliente.altaClienteNormal(tcn);
		this.saCliente.altaClienteVIP(tcv);
		this.saCliente.bajaCliente(tcv.getId());
		this.saCliente.bajaCliente(tcn.getId());
		assertTrue(this.saCliente.bajaCliente(tcv.getId()) == SACliente.CLIENTE_INACTIVO);
		assertTrue(this.saCliente.bajaCliente(tcn.getId()) == SACliente.CLIENTE_INACTIVO);
	}
}
