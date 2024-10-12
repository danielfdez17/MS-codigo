package negocio.cliente;

import java.util.List;

import integracion.cliente.DAOCliente;
import integracion.factoriaIntegracion.FactoriaIntegracion;
import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.factoriaNegocio.ComprobadorSintactico;

public class SAClienteImp implements SACliente {
	@Override
	public int altaClienteVIP(TClienteVIP tcv) {
		int idClient = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOCliente clientDAO = FactoriaIntegracion.getInstancia().crearDAOCliente();
		
		if (!this.isVipValid(tcv)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		TCliente clientExist = clientDAO.buscarClientePorNIF(tcv.getNIF());
		
		if (clientExist != null) {
			if (clientExist.isActivo()) {
				idClient = CLIENTE_ACTIVO;
				transaction.rollback();
			}
			else {
				if (clientExist instanceof TClienteVIP) {
					clientExist.setDireccion(tcv.getDireccion());
					clientExist.setActivo(true);
					clientExist.setNIF(tcv.getNIF());
					clientExist.setNombre(tcv.getNombre());
					clientExist.setTlf(tcv.getTlf());
					int res = clientDAO.actualizarClienteVIP(tcv);
					if (res < 0) {
						transaction.rollback();
						idClient = ERROR_BBDD;
					}
					else {
						idClient = CLIENTE_INACTIVO;
						transaction.commit();
					}
				}
				else {
					idClient = CAMBIO_TIPO;
					transaction.rollback();
				}
			}
		} 
		else {
			idClient = clientDAO.altaClienteVIP(tcv);
			if (idClient < 0) {
				idClient = ERROR_BBDD;
				transaction.rollback();
			}
			else {
				transaction.commit();
			}
		}
		return idClient;
	}
	
	@Override
	public int altaClienteNormal(TClienteNormal tcn) {
		int idClient = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOCliente clientDAO = FactoriaIntegracion.getInstancia().crearDAOCliente();
		
		if (!this.isNormalValid(tcn)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		TCliente clientExist = clientDAO.buscarClientePorNIF(tcn.getNIF());
		
		if (clientExist != null) {
			if (clientExist.isActivo()) {
				idClient = CLIENTE_ACTIVO;
				transaction.rollback();
			}
			else {
				if (clientExist instanceof TClienteNormal) {
					clientExist.setDireccion(tcn.getDireccion());
					clientExist.setActivo(true);
					clientExist.setNIF(tcn.getNIF());
					clientExist.setNombre(tcn.getNombre());
					clientExist.setTlf(tcn.getTlf());
					int res = clientDAO.actualizarClienteNormal(tcn);
					if (res < 0) {
						transaction.rollback();
						idClient = ERROR_BBDD;
					}
					else {
						idClient = CLIENTE_INACTIVO;
						transaction.commit();
					}
				}
				else {
					idClient = CAMBIO_TIPO;
					transaction.rollback();
				}
			}
		} 
		else {
			idClient = clientDAO.altaClienteNormal(tcn);
			if (idClient < 0) {
				idClient = ERROR_BBDD;
				transaction.rollback();
			}
			else {
				transaction.commit();
			}
		}
		return idClient;
	}
	@Override
	public TCliente buscarCliente(int id) {
		TCliente cliente = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		cliente = FactoriaIntegracion.getInstancia().crearDAOCliente().buscarCliente(id);
		if (cliente == null) transaction.rollback();
		else transaction.commit();
		return cliente;
	}
	@Override
	public List<TCliente> listarClientes() {
		List<TCliente> clientes = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		clientes = FactoriaIntegracion.getInstancia().crearDAOCliente().listarClientes();
		if (clientes == null || clientes.isEmpty()) transaction.rollback();
		else transaction.commit();
		return clientes;
	}
	
	@Override
	public int actualizarClienteVIP(TClienteVIP tcv) {
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOCliente clientDAO = FactoriaIntegracion.getInstancia().crearDAOCliente();
		TCliente clientExist = clientDAO.buscarClientePorNIF(tcv.getNIF());
		
		if (!this.isVipValid(tcv)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		
		if (clientExist == null) {
			idCliente = CLIENTE_INEXISTENTE;
			transaction.rollback();
		}
		else {
			if (clientExist instanceof TClienteVIP) {
				idCliente = clientDAO.actualizarClienteVIP(tcv);
				if (idCliente > 0) 
					transaction.commit();
				else {
					idCliente = ERROR_BBDD;
					transaction.rollback();
				}
			}
			else {
				idCliente = CAMBIO_TIPO;
				transaction.rollback();
			}
		}
		return idCliente;
	}
	
	@Override
	public int actualizarClienteNormal(TClienteNormal tcn) {
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOCliente clientDAO = FactoriaIntegracion.getInstancia().crearDAOCliente();
		TCliente clientExist = clientDAO.buscarClientePorNIF(tcn.getNIF());
		
		if (!this.isNormalValid(tcn)) {
			transaction.rollback();
			return ERROR_SINTACTICO;
		}
		
		if (clientExist == null) {
			idCliente = CLIENTE_INEXISTENTE;
			transaction.rollback();
		}
		else {
			if (clientExist instanceof TClienteNormal) {
				idCliente = clientDAO.actualizarClienteNormal(tcn);
				if (idCliente > 0) 
					transaction.commit();
				else {
					idCliente = ERROR_BBDD;
					transaction.rollback();
				}
			}
			else {
				idCliente = CAMBIO_TIPO;
				transaction.rollback();
			}
		}
		return idCliente;
	}
	@Override
	public int bajaCliente(int id) {
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Transaction transaction = transactionManager.nuevaTransaccion();
		transaction.start();
		DAOCliente clientDAO = FactoriaIntegracion.getInstancia().crearDAOCliente();
		TCliente clientExist = clientDAO.buscarCliente(id);
		if (clientExist == null) {
			idCliente = CLIENTE_INEXISTENTE;
			transaction.rollback();
		}
		else {
			if (!clientExist.isActivo()) {
				idCliente = CLIENTE_INACTIVO;
				transaction.rollback();
			}
			else {
				idCliente = clientDAO.bajaCliente(id);
				if (idCliente > 0) 
					transaction.commit();
				else {
					idCliente = ERROR_BBDD;
					transaction.rollback();
				}
			}
		}
		return idCliente;
	}
	private boolean isValid(TCliente tc) {
		return tc != null && ComprobadorSintactico.isDireccion(tc.getDireccion()) && ComprobadorSintactico.isNIF(tc.getNIF())
				&& ComprobadorSintactico.isNombre(tc.getNombre());
	}
	private boolean isVipValid(TClienteVIP tcv) {
		return this.isValid(tcv) && tcv.getIdTarjetaVIP() > 0;
	}
	private boolean isNormalValid(TClienteNormal tcn) {
		return this.isValid(tcn) && tcn.getIdTarjetaNormal() > 0;
	}
}