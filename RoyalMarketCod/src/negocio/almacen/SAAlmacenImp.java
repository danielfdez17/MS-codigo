package negocio.almacen;

import java.util.List;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.factoriaNegocio.ComprobadorSintactico;
import negocio.producto.TProducto;

public class SAAlmacenImp implements SAAlmacen {
	

	@Override
	public int altaAlmacen(TAlmacen ta) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOAlmacen dao = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		
		if (!isValid(ta)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		TAlmacen leido = dao.buscarAlmacenPorDireccion(ta.getDireccion());
		
		if (leido != null) {
			if (leido.isActivo()) {
				res = ALMACEN_ACTIVO;
				transaction.rollback();
			}
			else {
				res = ALMACEN_INACTIVO;
				leido.setActivo(true);
				leido.setDireccion(ta.getDireccion());
				dao.actualizarAlmacen(leido);
				transaction.commit();
			}
		}
		else {
			res = dao.altaAlmacen(ta);
			if (res < 0) {
				res = ERROR_BBDD;
				transaction.rollback();
			}
			else {
				transaction.commit();
			}
		}
		
		return res;
	}
	@Override
	public TAlmacen buscarAlmacen(int id) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOAlmacen dao = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		TAlmacen leido = dao.buscarAlmacen(id);
		if (leido == null) transaction.rollback();
		else transaction.commit();
		return leido;
	}
	@Override
	public List<TAlmacen> listarAlmacenes() {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOAlmacen dao = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		List<TAlmacen> res = dao.listarAlmacenes();
		if (res == null || res.isEmpty()) transaction.rollback();
		else transaction.commit();
		return res;
	}
	@Override
	public int actualizarAlmacen(TAlmacen ta) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOAlmacen dao = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		
		if (!isValid(ta)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		TAlmacen leido = dao.buscarAlmacen(ta.getId());
		
		int res = -1;
		if (leido == null) {
			res = ALMACEN_INEXISTENTE;
			transaction.rollback();
		}
		else {
			res = dao.actualizarAlmacen(ta);
			if (res < 0) {
				res = ERROR_BBDD;
				transaction.rollback();
			}
			else {
				transaction.commit();
			}
		}
		
		return res;
	}
	@Override
	public int bajaAlmacen(int id) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOAlmacen dao = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		int res = ALMACEN_INEXISTENTE;
		TAlmacen leido = dao.buscarAlmacen(id);
		if (leido == null) {
			transaction.rollback();
		}
		else {
			if (!leido.isActivo()) {
				res = ALMACEN_INACTIVO;
				transaction.rollback();
			}
			else {
				List<TProducto> lista = FactoriaIntegracion.getInstancia().creaDAOProducto().listarProductosPorAlmacen(id);
				if (lista != null) {
					boolean prodActivo = false;
					for (TProducto p : lista) {
						if (p.isActivo()) {
							prodActivo = true;
							break;
						}
					}
					if (!prodActivo) {
						res = dao.bajaAlmacen(id);
						if (res < 0) {
							res = ERROR_BBDD;
							transaction.rollback();
						}
						else {
							transaction.commit();
						}					
					}
					else {
						res = PRODUCTOS_EN_ALMACEN;
						transaction.rollback();
					}
				}
				else {
					res = dao.bajaAlmacen(id);
					if (res < 0) {
						res = ERROR_BBDD;
						transaction.rollback();
					}
					else {
						transaction.commit();
					}					
				}
			}
		}
		
		return res;
	}
	private boolean isValid(TAlmacen ta) {
		return ta != null && ComprobadorSintactico.isDireccion(ta.getDireccion());
	}
}