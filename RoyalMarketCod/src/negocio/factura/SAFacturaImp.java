package negocio.factura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import integracion.cliente.DAOCliente;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.factura.DAOFactura;
import integracion.linea_factura.DAOLineaFactura;
import integracion.producto.DAOProducto;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.cliente.TCliente;
import negocio.producto.TProducto;

public class SAFacturaImp implements SAFactura {
	
	@Override
	public TCarrito generarCarrito(int idCliente) {
		if (idCliente > 0) {
			return new TCarrito(new TFactura(idCliente));
		}
		return null;
		
	}
	
	@Override
	public TCarrito buscarCarrito(int idFactura) {
		TCarrito carrito = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOFactura daof = fi.crearDAOFactura();
		DAOLineaFactura daolf = fi.crearDAOLineaFactura();
		TFactura leida = daof.buscarFactura(idFactura);
		if (leida != null) {
			List<TLineaFactura> lineas = daolf.listarLineasPorFactura(idFactura);
			if (lineas != null) {
				carrito = new TCarrito(leida);
				HashMap<Integer, TLineaFactura> lineasCarrito = carrito.getLineas();
				for (TLineaFactura l : lineas) {
					lineasCarrito.put(l.getIdProducto(), l);
				}
			}
			else {
				transaction.rollback();
			}
		}
		else {
			carrito = null;
			transaction.rollback();
		}
		return carrito;
	}

	@Override
	public TFactura buscarFactura(int id) {
	 TransactionManager transactionManager = TransactionManager.getInstancia();
	 Transaction transaction = transactionManager.nuevaTransaccion();
	 transaction.start();
	 TFactura tFactura = FactoriaIntegracion.getInstancia().crearDAOFactura().buscarFactura(id);
	 
	 if (tFactura == null) transaction.rollback();
	 else transaction.commit();
	 
	 return tFactura;
	}

	@Override
	public List<TFactura> listarFacturas() {
		List<TFactura> lista = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		
		lista = FactoriaIntegracion.getInstancia().crearDAOFactura().listarFacturas();
	 
		if (lista == null || lista.isEmpty()) transaction.rollback();
		else transaction.commit();
		
		return lista;
	}

	@Override
	public List<TFactura> listarFacturasPorCliente(int idCliente) {
		List<TFactura> lista = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOCliente daoCliente = FactoriaIntegracion.getInstancia().crearDAOCliente();
		TCliente cliente = daoCliente.buscarCliente(idCliente);
		if (cliente != null) {
			lista = FactoriaIntegracion.getInstancia().crearDAOFactura().listarFacturasPorCliente(idCliente);
			if (lista == null || lista.isEmpty()) transaction.rollback();
			else transaction.commit();
		}
		else {
			transaction.rollback();
		}
		
		return lista;
	}

	@Override
	public List<TFactura> listarFacturasPorProducto(int idProducto) {
		List<TFactura> lista = new ArrayList<TFactura>();
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOFactura daoFactura = fi.crearDAOFactura();
		DAOLineaFactura daoL = fi.crearDAOLineaFactura();
		DAOProducto daoProducto = fi.creaDAOProducto();
		if (daoProducto.buscarProducto(idProducto) != null) {
			List<TLineaFactura> lineas = daoL.listarLineasPorProducto(idProducto);
			if (lineas != null) {
				for (TLineaFactura l : lineas) {
					TFactura f = daoFactura.buscarFactura(l.getIdFactura());
					if (f != null)
						lista.add(f);
				}
				transaction.commit();
			}
			else {
				transaction.rollback();
			}
		}
		else {
			lista = null;
			transaction.rollback();
		}
		 
//		if (lista == null || lista.isEmpty()) transaction.rollback();
//		else transaction.commit();
		
		return lista;
	}

	@Override
	public int actualizarFactura(TFactura tf) {
		TFactura leida = null;
		int res = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		
		DAOFactura daoFactura = FactoriaIntegracion.getInstancia().crearDAOFactura();
		 
		leida = daoFactura.buscarFactura(tf.getId());
		 
		if (leida != null) {
			res = daoFactura.actualizarFactura(tf);
			if (res > 0) transaction.commit();
			else {
				transaction.rollback();
				res = ERROR_BBDD;
			}
		}
		else {
			transaction.rollback();
			res = FACTURA_INEXISTENTE;
		}
		
		return res;
	}
	
	@Override
	public int devolverFacturaCompleta(int idFactura) {
		int res = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOFactura daoFactura = fi.crearDAOFactura();
		DAOProducto daoProducto = fi.creaDAOProducto();
		TFactura facturaLeida = daoFactura.buscarFactura(idFactura);
		if (facturaLeida != null) {
			DAOLineaFactura daolf = fi.crearDAOLineaFactura();
			List<TLineaFactura> lineas = daolf.listarLineasPorFactura(idFactura);
			for (TLineaFactura l : lineas) {
				TProducto productoLeido = daoProducto.buscarProducto(l.getIdProducto());
				if (productoLeido != null) {
					int cantidad = l.getCantidad();
					
					productoLeido.setActivo(true);
					productoLeido.setStock(productoLeido.getStock() + cantidad);
					if (daoProducto.actualizarProducto(productoLeido) < 0) {
						res = ERROR_BBDD;
						transaction.rollback();
					}
					if (daolf.eliminarLinea(l.getIdFactura(), l.getIdProducto()) < 0) {
						res = ERROR_BBDD;
						transaction.rollback();
					}
				}
				else {
					transaction.rollback();
					res = PRODUCTO_INEXISTENTE;
					break;
				}
			}
			facturaLeida.setCosteTotal(0);
			facturaLeida.setActivo(false);
			res = daoFactura.actualizarFactura(facturaLeida);
			if (res > 0) {
				transaction.commit();
			}
			else {
				transaction.rollback();
				res = ERROR_BBDD;
			}
		}
		else {
			transaction.rollback();
			res = FACTURA_INEXISTENTE;
		}
		
		return res;
	}
	
	public int devolverProducto(TProductoDevuelto productoDevuelto) {
		int res = FACTURA_INEXISTENTE;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		transaction.start();
		
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOFactura daoFactura = fi.crearDAOFactura();
		DAOProducto daoProducto = fi.creaDAOProducto();
		DAOLineaFactura daoLineaFactura = fi.crearDAOLineaFactura();
		TFactura leida = daoFactura.buscarFactura(productoDevuelto.getIdFactura());
		if (leida != null) {
			TLineaFactura linea = daoLineaFactura.buscarLinea(productoDevuelto.getIdFactura(), productoDevuelto.getIdProducto());
			if (linea != null) {
				if (productoDevuelto.getCantidad() <= linea.getCantidad()) {
					linea.setCantidad(linea.getCantidad() - productoDevuelto.getCantidad());
					TProducto producto = daoProducto.buscarProducto(productoDevuelto.getIdProducto());
					if (producto != null) {
						producto.setStock(producto.getStock() + productoDevuelto.getCantidad());
						if (daoProducto.actualizarProducto(producto) < 0) {
							res = ERROR_BBDD;
							transaction.rollback();
						}
						if (daoLineaFactura.actualizarLinea(linea) < 0) {
							res = ERROR_BBDD;
							transaction.rollback();
						}
						double precioLinea = productoDevuelto.getCantidad() * linea.getPrecioProducto();
						leida.setCosteTotal(leida.getCosteTotal() - precioLinea);
						res = daoFactura.actualizarFactura(leida);
						if (res > 0) {
							transaction.commit();
						}
						else {
							res = ERROR_BBDD;
							transaction.rollback();
						}
					}
					else {
						res = PRODUCTO_INEXISTENTE;
						transaction.rollback();
					}
				}
				else {
					res = ELIMINAR_MAS_PRODUCTOS_QUE_COMPRADOS;
					transaction.rollback();
				}
			}
			else {
				res = PRODUCTO_NO_ANYADIDO_AL_CARRITO;
				transaction.rollback();
			}
		}
		else {
			res = FACTURA_INEXISTENTE;
			transaction.rollback();
		}
		return res;
	}
	
	@Override
	public int cerrarFactura(TCarrito carrito) {
		int res = FACTURA_INEXISTENTE;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		
		FactoriaIntegracion fi = FactoriaIntegracion.getInstancia();
		DAOFactura daoFactura = fi.crearDAOFactura();
		DAOCliente daoCliente = fi.crearDAOCliente();
		DAOProducto daoProducto = fi.creaDAOProducto();
		DAOLineaFactura daoLinea = fi.crearDAOLineaFactura();
		
		if (carrito != null) {
			TCliente clienteLeido = daoCliente.buscarCliente(carrito.getFactura().getIdCliente());
			if (clienteLeido != null) {
				if (clienteLeido.isActivo()) {
					for (Map.Entry<Integer, TLineaFactura> lineas : carrito.getLineas().entrySet()) {
						TProducto productoLeido = daoProducto.buscarProducto(lineas.getKey());
						if (productoLeido != null) {
							if (productoLeido.isActivo()) {
								int cantidad = lineas.getValue().getCantidad();
								if (cantidad <= productoLeido.getStock()) {
									productoLeido.setStock(productoLeido.getStock() - cantidad);
									daoProducto.actualizarProducto(productoLeido);
									carrito.getFactura().setCosteTotal(carrito.getFactura().getCosteTotal() + (cantidad * productoLeido.getPrecio()));
								}
								else {
									transaction.rollback();
									res = STOCK_INSUFICIENTE;
									break;
								}
							}
							else {
								transaction.rollback();
								res = PRODUCTO_INACTIVO;
								break;
							}
						}
						else {
							transaction.rollback();
							res = PRODUCTO_INEXISTENTE;
							break;
						}
					}
					if (res == FACTURA_INEXISTENTE) {
						res = daoFactura.cerrarFactura(carrito.getFactura());
						if (res > 0) {
							for (Map.Entry<Integer, TLineaFactura> lineas : carrito.getLineas().entrySet()) {
								TLineaFactura linea = lineas.getValue();
								linea.setIdFactura(res);;
								if (daoLinea.crearLinea(linea) < 0) {
									res = ERROR_BBDD;
									transaction.rollback();
								}
							}
							transaction.commit();
						}
						else {
							transaction.rollback();
							res = ERROR_BBDD;
						}
					}
				} 
				else {
					transaction.rollback();
					res = CLIENTE_INACTIVO; 
				}
			}
			else {
				transaction.rollback();
				res = CLIENTE_INEXISTENTE;
			}
		}
		else {
			transaction.rollback();
			res = FACTURA_INEXISTENTE;
		}
		

		
		return res;
	}
		 
}