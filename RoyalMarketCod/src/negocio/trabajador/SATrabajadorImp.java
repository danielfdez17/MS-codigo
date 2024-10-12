package negocio.trabajador;

import java.util.ArrayList;
import java.util.List;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.trabajador.DAOTrabajador;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.ComprobadorSintactico;

public class SATrabajadorImp implements SATrabajador {
	
	@Override
	public int altaTrabajadorJCompleta(TTrabajadorJCompleta ttjc) {
		int res = TRABAJADOR_INEXISTENTE;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		DAOAlmacen daoa = fi.crearDAOAlmacen();
		transaction.start();
		// ES VALIDO
		if (this.isCompletaValid(ttjc)) {
			TTrabajador leido = daot.buscarTrabajadorPorNIF(ttjc.getNIF());
			// NO EXISTE UNO CON EL MISMO NIF
			if (leido == null) {
				// SE CREA
				res = daot.altaTrabajadorJCompleta(ttjc);
				// SI HAY ERROR EN BBDD
				if (res < 0) {
					res = ERROR_BBDD;
					transaction.rollback();
				}
				// SI NO HAY ERROR EN BBDD
				else {
					transaction.commit();
				}
			}
			// EXISTE UNO CON EL MISMO NIF
			else {
				// SI HAY QUE REACTIVARLO
				if (!leido.isActivo()) {
					// SI SON DEL MISMO TIPO
					if (leido instanceof TTrabajadorJCompleta) {
						res = TRABAJADOR_INACTIVO;
						TAlmacen ta = daoa.buscarAlmacen(ttjc.getIdAlmacen());
						int checkAlmacen = this.checkAlmacen(ta);
						if (checkAlmacen > 0) {
							TTrabajadorJCompleta ttjcLeido = (TTrabajadorJCompleta) leido;
							ttjcLeido.setTlf(ttjc.getTlf());
							ttjcLeido.setNIF(ttjc.getNIF());
							ttjcLeido.setDireccion(ttjc.getDireccion());
							ttjcLeido.setIdAlmacen(ttjc.getIdAlmacen());
							ttjcLeido.setActivo(true);
							ttjcLeido.setSueldoBase(ttjc.getSueldoBase());
							daot.actualizarTrabajadorJCompleta(ttjcLeido);
							transaction.commit();
						}
						else {
							res = checkAlmacen;
							transaction.rollback();
						}
					}
					else {
						res = CAMBIO_TIPO_TRABAJADOR;
						transaction.rollback();
					}
				}
				// SI YA ESTA ACTIVO
				else {
					res = TRABAJADOR_ACTIVO;
					transaction.rollback();
				}
			}
		}
		else {
			res = ERROR_SINTACTICO;
			transaction.rollback();
		}
		return res;
	}
	
	@Override
	public int altaTrabajadorJParcial(TTrabajadorJParcial ttjp) {
		int res = TRABAJADOR_INEXISTENTE;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		DAOAlmacen daoa = fi.crearDAOAlmacen();
		transaction.start();
		// ES VALIDO
		if (this.isParcialValid(ttjp)) {
			TTrabajador leido = daot.buscarTrabajadorPorNIF(ttjp.getNIF());
			// NO EXISTE UNO CON EL MISMO NIF
			if (leido == null) {
				// SE CREA
				res = daot.altaTrabajadorJParcial(ttjp);
				// SI HAY ERROR EN BBDD
				if (res < 0) {
					res = ERROR_BBDD;
					transaction.rollback();
				}
				// SI NO HAY ERROR EN BBDD
				else {
					transaction.commit();
				}
			}
			// EXISTE UNO CON EL MISMO NIF
			else {
				// SI HAY QUE REACTIVARLO
				if (!leido.isActivo()) {
					if (leido instanceof TTrabajadorJParcial) {
						res = TRABAJADOR_INACTIVO;
						TAlmacen ta = daoa.buscarAlmacen(ttjp.getIdAlmacen());
						int checkAlmacen = this.checkAlmacen(ta);
						if (checkAlmacen > 0) {
							TTrabajadorJParcial ttjpLeido = (TTrabajadorJParcial) leido;
							ttjpLeido.setTlf(ttjp.getTlf());
							ttjpLeido.setNIF(ttjp.getNIF());
							ttjpLeido.setDireccion(ttjp.getDireccion());
							ttjpLeido.setIdAlmacen(ttjp.getIdAlmacen());
							ttjpLeido.setActivo(true);
                            ttjpLeido.setHoras(ttjp.getHoras());
                            ttjpLeido.setPrecioHora(ttjp.getPrecioHora());
							daot.actualizarTrabajadorJParcial(ttjpLeido);
							transaction.commit();
						}
						else {
							res = checkAlmacen;
							transaction.rollback();
						}
					}
					// SI NO SON DEL MISMO TIPO
					else {
						res = CAMBIO_TIPO_TRABAJADOR;
						transaction.rollback();
					}
				}
				// SI YA ESTA ACTIVO
				else {
					res = TRABAJADOR_ACTIVO;
					transaction.rollback();
				}
			}
		}
		else {
			res = ERROR_SINTACTICO;
			transaction.rollback();
		}
		return res;
	}

	@Override
	public TTrabajador buscarTrabajador(int id) {
		TTrabajador tt = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		transaction.start();
		tt = daot.buscarTrabajador(id);
		if (tt == null) transaction.rollback();
		else transaction.commit();
		return tt;
	}

	@Override
	public List<TTrabajador> listarTrabajadores() {
		List<TTrabajador> lista = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		transaction.start();
		lista = daot.listarTrabajadores();
		if (lista == null || lista.isEmpty()) transaction.rollback();
		else transaction.commit();
		return lista;
	}

	@Override
	public List<TTrabajador> listarTrabajadoresPorAlmacen(int idAlmacen) {
		List<TTrabajador> lista = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		DAOAlmacen daoa = fi.crearDAOAlmacen();
		transaction.start();
		TAlmacen almacen = daoa.buscarAlmacen(idAlmacen);
		int checkAlmacen = this.checkAlmacen(almacen);
		if (checkAlmacen > 0) {
			lista = daot.listarTrabajadores();
			if (lista == null || lista.isEmpty()) transaction.rollback();
			else transaction.commit();
		}
		else {
			transaction.rollback();
		}
		return lista;
	}

	@Override
	public int actualizarTrabajadorJCompleta(TTrabajadorJCompleta ttjc) {
		int res = TRABAJADOR_INEXISTENTE;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		DAOAlmacen daoa = fi.crearDAOAlmacen();
		transaction.start();
		if (this.isCompletaValid(ttjc)) {
			TTrabajador leido = daot.buscarTrabajadorPorNIF(ttjc.getNIF());
			if (leido == null) {
				res = TRABAJADOR_INEXISTENTE;
				transaction.rollback();
			}
			else {
				if (leido instanceof TTrabajadorJCompleta) {
					res = ttjc.getId();
					TAlmacen ta = daoa.buscarAlmacen(ttjc.getIdAlmacen());
					int checkAlmacen = this.checkAlmacen(ta);
					if (checkAlmacen > 0) {
						TTrabajadorJCompleta ttjcLeido = (TTrabajadorJCompleta) leido;
						ttjcLeido.setTlf(ttjc.getTlf());
						ttjcLeido.setNIF(ttjc.getNIF());
						ttjcLeido.setDireccion(ttjc.getDireccion());
						ttjcLeido.setIdAlmacen(ttjc.getIdAlmacen());
						ttjcLeido.setActivo(true);
						ttjcLeido.setSueldoBase(ttjc.getSueldoBase());
						daot.actualizarTrabajadorJCompleta(ttjcLeido);
						transaction.commit();
					}
					else {
						res = checkAlmacen;
						transaction.rollback();
					}
				}
				else {
					res = CAMBIO_TIPO_TRABAJADOR;
					transaction.rollback();
				}
			}
		}
		else {
			res = ERROR_SINTACTICO;
			transaction.rollback();
		}
		return res;
	}
	
	@Override
	public int actualizarTrabajadorJParcial(TTrabajadorJParcial ttjp) {
		int res = TRABAJADOR_INEXISTENTE;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		DAOAlmacen daoa = fi.crearDAOAlmacen();
		transaction.start();
		if (this.isParcialValid(ttjp)) {
			TTrabajador leido = daot.buscarTrabajadorPorNIF(ttjp.getNIF());
			if (leido == null) {
				res = TRABAJADOR_INEXISTENTE;
				transaction.rollback();
			}
			else {
				if (leido instanceof TTrabajadorJParcial) {
					res = ttjp.getId();
					TAlmacen ta = daoa.buscarAlmacen(ttjp.getIdAlmacen());
					int checkAlmacen = this.checkAlmacen(ta);
					if (checkAlmacen > 0) {
						TTrabajadorJParcial ttjpLeido = (TTrabajadorJParcial) leido;
						ttjpLeido.setTlf(ttjp.getTlf());
						ttjpLeido.setNIF(ttjp.getNIF());
						ttjpLeido.setDireccion(ttjp.getDireccion());
						ttjpLeido.setIdAlmacen(ttjp.getIdAlmacen());
						ttjpLeido.setActivo(true);
						ttjpLeido.setHoras(ttjp.getHoras());
						ttjpLeido.setPrecioHora(ttjp.getPrecioHora());
						daot.actualizarTrabajadorJParcial(ttjpLeido);
						transaction.commit();
					}
					else {
						res = checkAlmacen;
						transaction.rollback();
					}
				}
				else {
					res = CAMBIO_TIPO_TRABAJADOR;
					transaction.rollback();
				}
			}
		}
		else {
			res = ERROR_SINTACTICO;
			transaction.rollback();
		}
		return res;
	}

	@Override
	public int bajaTrabajador(int id) {
		int res = TRABAJADOR_INEXISTENTE;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOTrabajador daot = fi.crearDAOTrabajador();
		transaction.start();
		TTrabajador tt = daot.buscarTrabajador(id);
		if (tt == null) {
			res = TRABAJADOR_INEXISTENTE;
			transaction.rollback();
		}
		else {
			if (!tt.isActivo()) {
				res = TRABAJADOR_INACTIVO;
				transaction.rollback();
			}
			else {
				res = daot.bajaTrabajador(id);
				if (res > 0) {
					transaction.commit();
				}
				else {
					res = ERROR_BBDD;
					transaction.rollback();
				}
			}
		}
		return res;
	}
	private boolean isValid(TTrabajador tt) {
		return tt != null && ComprobadorSintactico.isNIF(tt.getNIF()) 
			&& ComprobadorSintactico.isNombre(tt.getNombre())
			&& ComprobadorSintactico.isDireccion(tt.getDireccion())
			&& ComprobadorSintactico.isTlf(Integer.toString(tt.getTlf()))
			&& tt.getIdAlmacen() > 0;
	}
	private boolean isCompletaValid(TTrabajadorJCompleta ttjc) {
		return this.isValid(ttjc) && ttjc.getSueldoBase() > 0;
	}
	private boolean isParcialValid(TTrabajadorJParcial ttjp) {
		return this.isValid(ttjp) && ttjp.getHoras() > 0 && ttjp.getPrecioHora() > 0;
	}
	private int checkAlmacen(TAlmacen ta) {
		if (ta == null) return ALMACEN_INEXISTENTE;
		if (!ta.isActivo()) return ALMACEN_INACTIVO;
		return ta.getId();
	}
	
}