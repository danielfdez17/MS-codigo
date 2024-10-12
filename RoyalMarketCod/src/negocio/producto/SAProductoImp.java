package negocio.producto;

import java.util.ArrayList;
import java.util.List;

import integracion.almacen.DAOAlmacen;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.proveedor.DAOProveedor;
import integracion.proveedor_producto.DAOProveedorProducto;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.ComprobadorSintactico;
import negocio.proveedor.TProveedor;
import negocio.proveedor_producto.TProveedorProducto;

public class SAProductoImp implements SAProducto {
	
	@Override
	public int altaProducto(TProducto tp) {
		int idProduct = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOProducto productDAO = FactoriaIntegracion.getInstancia().creaDAOProducto();
		TProducto productExist = productDAO.buscarProductoPorNombre(tp.getNombre());
		DAOAlmacen dao_almacen = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		
		if (!isValid(tp)) {
			transaction.rollback();
			
			return ERROR_SINTACTICO;
		}
		
		TAlmacen almacen_leido = dao_almacen.buscarAlmacen(tp.getIdAlmacen());
		
		if (almacen_leido == null) {
			idProduct = ALMACEN_INEXISTENTE;
			transaction.rollback();
		}
		else {
			if (!almacen_leido.isActivo()) {
				idProduct = ALMACEN_INACTIVO;
				transaction.rollback();
			}
			else {
				if (productExist != null) {
					if (productExist.isActivo()) {
						idProduct = PRODUCTO_ACTIVO;
						transaction.rollback();
					}
					else {
						productExist.setActivo(true);
						productExist.setIdAlmacen(tp.getIdAlmacen());
						productExist.setNombre(tp.getNombre());
						productExist.setPrecio(tp.getPrecio());
						productExist.setStock(tp.getStock());
						productDAO.actualizarProducto(productExist);
						idProduct = PRODUCTO_INACTIVO;
						transaction.commit();
					}
				}
				else {
					idProduct = productDAO.altaProducto(tp);
					if (idProduct > 0) 
						transaction.commit();
					else {
						idProduct = ERROR_BBDD;
						transaction.rollback();
					}
				}
			}
		}
		return idProduct;
	}

	
	@Override
	public TProducto buscarProducto(int id) {
		TProducto producto = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		producto = FactoriaIntegracion.getInstancia().creaDAOProducto().buscarProducto(id);
		if (producto == null) 
			transaction.rollback();
		else 
			transaction.commit();
		
		return producto;
	}

	@Override
	public List<TProducto> listarProductos() {
		List<TProducto> productsAvailables = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		productsAvailables = FactoriaIntegracion.getInstancia().creaDAOProducto().listarProductos();
		if (productsAvailables.isEmpty()) 
			transaction.rollback();
		else 
			transaction.commit();
		
		return productsAvailables;
	}

	
	@Override
	public List<TProducto> listarProductosPorProveedor(int idProveedor) {
		List<TProducto> productsByProveedor = new ArrayList<TProducto>();
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOProveedor daoprov = fi.crearDAOProveedor();
		DAOProveedorProducto daoPP = fi.crearDAOPoveedorProducto();
		DAOProducto daoProd = fi.creaDAOProducto();
		TProveedor tprov = daoprov.buscarProveedor(idProveedor);
		if (tprov == null) {
			transaction.rollback();
		}
		else {
			if (!tprov.isActivo()) {
				transaction.rollback();
			}
			else {
				List<TProveedorProducto> lista = daoPP.readAllByProveedor(idProveedor);
				if (lista != null) {
					for (TProveedorProducto tpp : lista) {
						TProducto producto = daoProd.buscarProducto(tpp.getIdProducto());
						if (producto != null)
							productsByProveedor.add(producto);
					}
					transaction.commit();
				}
				else {
					transaction.rollback();
				}
//				productsByProveedor = fi.creaDAOProducto().listarProductosPorProveedor(idProveedor);
//				if (productsByProveedor == null || productsByProveedor.isEmpty()) 
//					transaction.rollback();
//				else 
//					transaction.commit();
			}
		}
		return productsByProveedor;
	}

	
	@Override
	public int actualizarProducto(TProducto tp) {
		int idProduct = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOProducto productDAO = FactoriaIntegracion.getInstancia().creaDAOProducto();
		TProducto productExist = productDAO.buscarProducto(tp.getId());
		DAOAlmacen daoa = FactoriaIntegracion.getInstancia().crearDAOAlmacen();
		if (!isValid(tp)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		
		if (productExist == null) {
			idProduct = PRODUCTO_INEXISTENTE;
			transaction.rollback();
		}
		else {
			TAlmacen ta = daoa.buscarAlmacen(tp.getIdAlmacen());
			if (ta != null) {
				if (ta.isActivo()) {
					idProduct = productDAO.actualizarProducto(tp);
					
					if (idProduct > 0) 
						transaction.commit();
					else {
						idProduct = ERROR_BBDD;
						transaction.rollback();
					}
				}
				else {
					idProduct = ALMACEN_INACTIVO;
					transaction.rollback();
				}
			}
			else {
				idProduct = ALMACEN_INEXISTENTE;
				transaction.rollback();
			}
		}
		
		return idProduct;
	}

	
	@Override
	public int bajaProducto(int id) { 
		int idProduct = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOProducto productDAO = FactoriaIntegracion.getInstancia().creaDAOProducto();
		TProducto productExist = productDAO.buscarProducto(id);
		if (productExist == null) {
			idProduct = PRODUCTO_INEXISTENTE;
			transaction.rollback();
		}
		else {
			if (!productExist.isActivo()) {
				idProduct = PRODUCTO_INACTIVO;
				transaction.rollback();
			}
			else {
				idProduct = productDAO.bajaProducto(id);
				
				if (idProduct > 0) 
					transaction.commit();
				else {
					idProduct = ERROR_BBDD;
					transaction.rollback();
				}
			}
		}
		
		return idProduct;
	}


	@Override
	public List<TProducto> listarProductosPorAlmacen(int idAlmacen) {
		List<TProducto> productsByAlmacen = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		TAlmacen almacen = fi.crearDAOAlmacen().buscarAlmacen(idAlmacen);
		if (almacen == null) {
			transaction.rollback();
		}
		else {
			if (!almacen.isActivo()) {
				transaction.rollback();
			}
			else {
				productsByAlmacen = fi.creaDAOProducto().listarProductosPorAlmacen(idAlmacen);
				
				if (productsByAlmacen == null || productsByAlmacen.isEmpty()) 
					transaction.rollback();
				else 
					transaction.commit();
			}
		}
		
		return productsByAlmacen;
	}
	private boolean isValid(TProducto tp) {
		return tp != null && ComprobadorSintactico.isNombre(tp.getNombre()) && tp.getPrecio() > 0 && tp.getStock() > 0 && tp.getIdAlmacen() > 0;
	}
}