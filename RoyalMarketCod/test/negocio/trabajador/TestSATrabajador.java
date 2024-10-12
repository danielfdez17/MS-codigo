package negocio.trabajador;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.almacen.SAAlmacen;
import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestSATrabajador {
	
	private static final String nif = "12345678";
	private static final int tlf = 123456789;
	private static final String nombre = "nombre";
	private static final String direccion = "direccion";
	private static final double sueldoBase = 12.0;
	private static final double precioHora = 15.0;
	private static final int horas = 4;
	
	private SATrabajador saTrabajador;
	private SAAlmacen saAlmacen;
	private TTrabajadorJCompleta ttjc;
	private TTrabajadorJParcial ttjp;
	private TAlmacen almacen;
	
	@Before
	public void setUp() {
		FactoriaNegocio fn = FactoriaNegocio.getInstancia();
		
		this.saTrabajador = fn.crearSATrabajador();
		this.saAlmacen = fn.crearSAAlmacen();
		
	}
	
	@Test
	public void altaOK() {
		almacen = new TAlmacen("altaTrabajadorOK");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "A", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "B", nombre, direccion, almacen.getId(), horas, precioHora);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "A", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "B", nombre, direccion, almacen.getId(), horas, precioHora);
		assertTrue(this.saTrabajador.altaTrabajadorJCompleta(ttjc) > 0);
		assertTrue(this.saTrabajador.altaTrabajadorJParcial(ttjp) > 0);
	}
	
	@Test
	public void altaKO() {
		almacen = new TAlmacen("altaTrabajadorKO");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif, nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif, nombre, direccion, almacen.getId(), horas, precioHora);
		assertTrue(this.saTrabajador.altaTrabajadorJCompleta(ttjc) == SATrabajador.ERROR_SINTACTICO);
		assertTrue(this.saTrabajador.altaTrabajadorJParcial(ttjp) == SATrabajador.ERROR_SINTACTICO);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "C", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "D", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		assertTrue(this.saTrabajador.altaTrabajadorJCompleta(ttjc) == SATrabajador.TRABAJADOR_ACTIVO);
		assertTrue(this.saTrabajador.altaTrabajadorJParcial(ttjp) == SATrabajador.TRABAJADOR_ACTIVO);
		this.saTrabajador.bajaTrabajador(ttjc.getId());
		this.saTrabajador.bajaTrabajador(ttjp.getId());
		ttjc.setNIF(nif + "D");
		ttjp.setNIF(nif + "C");
		assertTrue(this.saTrabajador.altaTrabajadorJCompleta(ttjc) == SATrabajador.CAMBIO_TIPO_TRABAJADOR);
		assertTrue(this.saTrabajador.altaTrabajadorJParcial(ttjp) == SATrabajador.CAMBIO_TIPO_TRABAJADOR);
		ttjc.setNIF(nif + "C");
		ttjp.setNIF(nif + "D");
		assertTrue(this.saTrabajador.altaTrabajadorJCompleta(ttjc) == SATrabajador.TRABAJADOR_INACTIVO);
		assertTrue(this.saTrabajador.altaTrabajadorJParcial(ttjp) == SATrabajador.TRABAJADOR_INACTIVO);
	}
	
	@Test
	public void buscar() {
		almacen = new TAlmacen("buscarTrabajador");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "E", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "F", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		assertNotNull(this.saTrabajador.buscarTrabajador(ttjc.getId()));
		assertTrue(this.saTrabajador.buscarTrabajador(ttjc.getId()) instanceof TTrabajadorJCompleta);
		assertNotNull(this.saTrabajador.buscarTrabajador(ttjp.getId()));
		assertTrue(this.saTrabajador.buscarTrabajador(ttjp.getId()) instanceof TTrabajadorJParcial);
	}
	
	@Test
	public void listar() {
		almacen = new TAlmacen("listarTrabajadores");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "G", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "H", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		List<TTrabajador> res = this.saTrabajador.listarTrabajadores();
		assertNotNull(res);
		assertFalse(res.isEmpty());
	}
	
	@Test
	public void listarPorAlmacen() {
		almacen = new TAlmacen("listarTrabajadoresPorAlmacen");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "I", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "J", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		List<TTrabajador> res = this.saTrabajador.listarTrabajadoresPorAlmacen(almacen.getId());
		assertNotNull(res);
		assertFalse(res.isEmpty());
		res = this.saTrabajador.listarTrabajadoresPorAlmacen(-1);
		assertTrue(res.isEmpty());
	}
	
	@Test
	public void actualizarTrabajadorOK() {
		almacen = new TAlmacen("actualizarTrabajadorOK");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "K", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "L", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		assertTrue(this.saTrabajador.actualizarTrabajadorJCompleta(ttjc) > 0);
		assertTrue(this.saTrabajador.actualizarTrabajadorJParcial(ttjp) > 0);
	}
	
	@Test
	public void actualizarTrabajadorKO() {
		almacen = new TAlmacen("actualizarTrabajadorKO");
		this.saAlmacen.altaAlmacen(almacen);
		assertTrue(this.saTrabajador.actualizarTrabajadorJCompleta(new TTrabajadorJCompleta(tlf, nif, nombre, direccion, almacen.getId(), sueldoBase)) 
				== SATrabajador.ERROR_SINTACTICO);
		assertTrue(this.saTrabajador.actualizarTrabajadorJParcial(new TTrabajadorJParcial(tlf, nif, nombre, direccion, almacen.getId(), horas, precioHora))
				== SATrabajador.ERROR_SINTACTICO);
		ttjc = new TTrabajadorJCompleta(1000, tlf, nif + "M", nombre, direccion, almacen.getId(), sueldoBase, true);
		ttjp = new TTrabajadorJParcial(1000, tlf, nif + "N", nombre, direccion, almacen.getId(), horas, precioHora, true);
		assertTrue(this.saTrabajador.actualizarTrabajadorJCompleta(ttjc) == SATrabajador.TRABAJADOR_INEXISTENTE);
		assertTrue(this.saTrabajador.actualizarTrabajadorJParcial(ttjp) == SATrabajador.TRABAJADOR_INEXISTENTE);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "M", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "N", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.bajaTrabajador(ttjc.getId());
		this.saTrabajador.bajaTrabajador(ttjp.getId());
		ttjc.setNIF(nif + "N");
		ttjp.setNIF(nif + "M");
		assertTrue(this.saTrabajador.actualizarTrabajadorJCompleta(ttjc) == SATrabajador.CAMBIO_TIPO_TRABAJADOR);
		assertTrue(this.saTrabajador.actualizarTrabajadorJParcial(ttjp) == SATrabajador.CAMBIO_TIPO_TRABAJADOR);
	}
	
	@Test
	public void bajaOK() {
		almacen = new TAlmacen("bajaTrabajadorOK");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "O", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "P", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		assertTrue(this.saTrabajador.bajaTrabajador(ttjc.getId()) == ttjc.getId());
		assertTrue(this.saTrabajador.bajaTrabajador(ttjp.getId()) == ttjp.getId());
	}
	
	@Test
	public void bajaKO() {
		almacen = new TAlmacen("bajaTrabajadorKO");
		this.saAlmacen.altaAlmacen(almacen);
		ttjc = new TTrabajadorJCompleta(tlf, nif + "Q", nombre, direccion, almacen.getId(), sueldoBase);
		ttjp = new TTrabajadorJParcial(tlf, nif + "R", nombre, direccion, almacen.getId(), horas, precioHora);
		this.saTrabajador.altaTrabajadorJCompleta(ttjc);
		this.saTrabajador.altaTrabajadorJParcial(ttjp);
		assertTrue(this.saTrabajador.bajaTrabajador(-1) == SATrabajador.TRABAJADOR_INEXISTENTE);
		this.saTrabajador.bajaTrabajador(ttjp.getId());
		this.saTrabajador.bajaTrabajador(ttjc.getId());
		assertTrue(this.saTrabajador.bajaTrabajador(ttjc.getId()) == SATrabajador.TRABAJADOR_INACTIVO);
		assertTrue(this.saTrabajador.bajaTrabajador(ttjp.getId()) == SATrabajador.TRABAJADOR_INACTIVO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
