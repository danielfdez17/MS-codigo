package negocio.proveedor;

import java.util.List;

import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.proveedor.DAOProveedor;
import integracion.proveedor_producto.DAOProveedorProducto;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.factoriaNegocio.ComprobadorSintactico;
import negocio.producto.TProducto;
import negocio.proveedor_producto.TProveedorProducto;

public class SAProveedorImp implements SAProveedor {

	@Override
	public int altaProveedor(TProveedor tp) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOProveedor dao = FactoriaIntegracion.getInstancia().crearDAOProveedor();
		
		if (!isValid(tp)) {
			transaction.rollback();
			
			return ERROR_SINTACTICO;
		}
		
		TProveedor leido = dao.buscarProveedorPorNIF(tp.getNif());
		if (leido != null) {
			if (leido.isActivo()) {
				res = PROVEEDOR_ACTIVO;
				transaction.rollback();
			}
			else {
				res = PROVEEDOR_INACTIVO;
				leido.setActivo(true);
				leido.setDireccion(tp.getDireccion());
				leido.setNif(tp.getNif());
				leido.setTlf(tp.getTlf());
				dao.actualizarProveedor(tp);
				transaction.commit();
			}
		}
		else {
			res = dao.altaProveedor(tp);
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
	public TProveedor buscarProveedor(int id) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOProveedor dao = FactoriaIntegracion.getInstancia().crearDAOProveedor();
		TProveedor leido = dao.buscarProveedor(id);
		if (leido == null) transaction.rollback();
		else transaction.commit();
		
		return leido;
	}

	@Override
	public List<TProveedor> listarProveedores() {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOProveedor dao = FactoriaIntegracion.getInstancia().crearDAOProveedor();
		List<TProveedor> res = dao.listarProveedores();
		if (res == null || res.isEmpty()) transaction.rollback();
		else transaction.commit();
		
		return res;
	}
	@Override
	public List<TProveedor> listarProveedoresPorProducto(int idProducto) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOProveedor dao = FactoriaIntegracion.getInstancia().crearDAOProveedor();
		List<TProveedor> res = dao.listarProveedoresPorProducto(idProducto);
		if (res == null || res.isEmpty()) transaction.rollback();
		else transaction.commit();
		
		return res;
	}
	@Override
	public int vincularProducto(int idProveedor, int idProducto) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOProveedor daoprov = fi.crearDAOProveedor();
		DAOProducto daoprod = fi.creaDAOProducto();
		DAOProveedorProducto daoprovprod = fi.crearDAOPoveedorProducto();
		int res = 1;
		TProveedor proveedor = daoprov.buscarProveedor(idProveedor);
		if (proveedor != null) {
			if (proveedor.isActivo()) {
				TProducto producto = daoprod.buscarProducto(idProducto);
				if (producto != null) {
					if (producto.isActivo()) {
						TProveedorProducto provprod = daoprovprod.readProveedorProducto(idProveedor, idProducto);
						if (provprod == null) {
							res = daoprovprod.createProveedorProducto(new TProveedorProducto(idProveedor, idProducto));
							if (res < 0) {
								res = ERROR_BBDD;
								transaction.rollback();
							}
							else {
								transaction.commit();
							}
						}
						else {
							res = YA_VINCULADOS;
							transaction.rollback();
						}
					}
					else {
						res = PRODUCTO_INACTIVO;
						transaction.rollback();
					}
				}
				else {
					res = PRODUCTO_INEXISTENTE;
					transaction.rollback();
				}
			}
			else {
				res = PROVEEDOR_INACTIVO;
				transaction.rollback();
			}
		}
		else {
			res = PROVEEDOR_INEXSITENTE;
			transaction.rollback();
		}
		
		return res;
	}
	@Override
	public int desvincularProducto(int idProveedor, int idProducto) {
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOProveedor daoprov = fi.crearDAOProveedor();
		DAOProducto daoprod = fi.creaDAOProducto();
		DAOProveedorProducto daoprovprod = fi.crearDAOPoveedorProducto();
		int res = 1;
		TProveedor proveedor = daoprov.buscarProveedor(idProveedor);
		if (proveedor != null) {
			if (proveedor.isActivo()) {
				TProducto producto = daoprod.buscarProducto(idProducto);
				if (producto != null) {
					if (producto.isActivo()) {
						TProveedorProducto provprod = daoprovprod.readProveedorProducto(idProveedor, idProducto);
						if (provprod == null) {
							res = YA_DESVINCULADOS;
							transaction.rollback();
						}
						else {
							res = daoprovprod.deleteProveedorProducto(idProveedor, idProducto);
							if (res < 0) {
								res = ERROR_BBDD;
								transaction.rollback();
							}
							else {
								transaction.commit();
							}
						}
					}
					else {
						res = PRODUCTO_INACTIVO;
						transaction.rollback();
					}
				}
				else {
					res = PRODUCTO_INEXISTENTE;
					transaction.rollback();
				}
			}
			else {
				res = PROVEEDOR_INACTIVO;
				transaction.rollback();
			}
		}
		else {
			res = PROVEEDOR_INEXSITENTE;
			transaction.rollback();
		}
		
		return res;
	}
	@Override
	public int actualizarProveedor(TProveedor tp) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOProveedor dao = FactoriaIntegracion.getInstancia().crearDAOProveedor();
		TProveedor leido = dao.buscarProveedor(tp.getId());
		
		if (!isValid(tp)) {
			transaction.rollback();
			
			return ERROR_SINTACTICO;
		}
		
		if (leido == null) {
			res = PROVEEDOR_INEXSITENTE;
			transaction.rollback();
		}
		else {
			res = dao.actualizarProveedor(tp);
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
	public int bajaProveedor(int id) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		DAOProveedor dao = FactoriaIntegracion.getInstancia().crearDAOProveedor();
		TProveedor leido = dao.buscarProveedor(id);
		
		if (leido == null) {
			res = PROVEEDOR_INEXSITENTE;
			transaction.rollback();
		}
		else {
			if (leido.isActivo()) {
				List<TProveedorProducto> lista = FactoriaIntegracion.getInstancia().crearDAOPoveedorProducto().readAllByProveedor(id);
				if (lista == null || lista.isEmpty()) {
					res = dao.bajaProveedor(id);
					if (res < 0) {
						res = ERROR_BBDD;
						transaction.rollback();
					}
					else {
						transaction.commit();
					}
				}
				else {
					res = PRODUCTOS_VINCULADOS;
					transaction.rollback();
				}
			}
			else {
				res = PROVEEDOR_INACTIVO;
				transaction.rollback();
			}
		}
		
		return res;
	}
	private boolean isValid(TProveedor tp) {
		return tp != null && tp.getTlf() > 0 && ComprobadorSintactico.isNIF(tp.getNif()) && ComprobadorSintactico.isDireccion(tp.getDireccion());
	}
}