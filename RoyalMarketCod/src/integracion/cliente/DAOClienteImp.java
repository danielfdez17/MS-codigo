package integracion.cliente;

import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.TransactionManager;

public class DAOClienteImp implements DAOCliente {

	private static final String ID = "id";
	private static final String ID_CLIENTE = "id_cliente";
	private static final String TLF = "telefono";
	private static final String NIF = "nif";
	private static final String DIRECCION = "direccion";
	private static final String NOMBRE = "nombre";
	private static final String ACTIVO = "activo";
	private static final String ID_CLIENTE_VIP = ID_CLIENTE + "_vip";
	private static final String ID_CLIENTE_NORMAL = ID_CLIENTE + "_normal";
	@Override
	public int altaClienteVIP(TClienteVIP tcv){
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "INSERT INTO cliente (telefono, nif, nombre, direccion, activo) VALUES (?, ?, ?, ?, ?);";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, tcv.getTlf());
		 	statement.setString(2, tcv.getNIF());
			statement.setString(3, tcv.getNombre());
			statement.setString(4, tcv.getDireccion());
			statement.setBoolean(5, true);
			int rows = statement.executeUpdate();
			if (rows > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				idCliente = rs.getInt(1);
				tcv.setId(idCliente);
					query = "INSERT INTO cliente_vip (id_cliente, id_cliente_vip) VALUES (?, ?)";
					try (PreparedStatement stmt = connection.prepareStatement(query)) {
						stmt.setInt(1, idCliente);
						stmt.setInt(2, tcv.getIdTarjetaVIP());
						int rows2 = stmt.executeUpdate();
						if (rows2 <= 0)
							throw new SQLException();
					} catch (SQLException sqle) {
						idCliente = -1;
					}
			} else 
				throw new SQLException();
			
		} catch (SQLException e) {
			idCliente = -1;
		}
		return idCliente;
	}
	@Override
	public int altaClienteNormal(TClienteNormal tcn){
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "INSERT INTO cliente (telefono, nif, nombre, direccion, activo) VALUES (?, ?, ?, ?, ?);";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, tcn.getTlf());
		 	statement.setString(2, tcn.getNIF());
			statement.setString(3, tcn.getNombre());
			statement.setString(4, tcn.getDireccion());
			statement.setBoolean(5, true);
			int rows = statement.executeUpdate();
			if (rows > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				idCliente = rs.getInt(1);
				tcn.setId(idCliente);
					query = "INSERT INTO cliente_normal (id_cliente, id_cliente_normal) VALUES (?, ?)";
					try (PreparedStatement stmt = connection.prepareStatement(query)) {
						stmt.setInt(1, idCliente);
						stmt.setInt(2, tcn.getIdTarjetaNormal());
						int rows2 = stmt.executeUpdate();
						if (rows2 <= 0)
							throw new SQLException();
					} catch (SQLException sqle) {
						idCliente = -1;
					}
			} else 
				throw new SQLException();
			
		} catch (SQLException e) {
			idCliente = -1;
		}
		return idCliente;
	}

	@Override
	public TCliente buscarCliente(int id) {
		TCliente cliente = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM cliente WHERE id = ? FOR UPDATE",
				querycvip = "SELECT * FROM cliente_vip WHERE id_cliente = ? FOR UPDATE",
				querycnormal = "SELECT * FROM cliente_normal WHERE id_cliente = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				boolean is_vip = true;
				int tlf = rs.getInt(TLF);
				String nif = rs.getString(NIF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				boolean activo = rs.getBoolean(ACTIVO);
				
				try (PreparedStatement stmtcvip = connection.prepareStatement(querycvip)) {
					stmtcvip.setInt(1, id);
					ResultSet rscvip = stmtcvip.executeQuery();
					if (rscvip.next()) {
						int id_tarjeta_vip = rscvip.getInt(ID_CLIENTE_VIP);
						cliente = new TClienteVIP(id, id_tarjeta_vip, tlf, nif, nombre, direccion, activo);
					}
					else throw new SQLException();
				} catch (SQLException sqle) {
					is_vip = false;
				}
				if (!is_vip) {
					try (PreparedStatement stmtcnormal = connection.prepareStatement(querycnormal)) {
						stmtcnormal.setInt(1, id);
						ResultSet rscvip = stmtcnormal.executeQuery();
						if (rscvip.next()) {
							int id_tarjeta_normal = rscvip.getInt(ID_CLIENTE_NORMAL);
							cliente = new TClienteNormal(id, id_tarjeta_normal, tlf, nif, nombre, direccion, activo);
						}
						else throw new SQLException();
					} catch (SQLException sqle) {
						cliente = null;
					}
				}
			}
			else 
				throw new SQLException();
			
		} catch (SQLException e) {
			cliente = null;
		}
		return cliente;
	}
	@Override
	public TCliente buscarClientePorNIF(String nif) {
		TCliente cliente = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM cliente WHERE nif = ? FOR UPDATE",
				querycvip = "SELECT * FROM cliente_vip WHERE id_cliente = ? FOR UPDATE",
				querycnormal = "SELECT * FROM cliente_normal WHERE id_cliente = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, nif);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				boolean is_vip = true;
				int id = rs.getInt(ID);
				int tlf = rs.getInt(TLF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				boolean activo = rs.getBoolean(ACTIVO);
				try (PreparedStatement stmtcvip = connection.prepareStatement(querycvip)) {
					stmtcvip.setInt(1, id);
					ResultSet rscvip = stmtcvip.executeQuery();
					if (rscvip.next()) {
						int id_tarjeta_vip = rscvip.getInt(ID_CLIENTE_VIP);
						cliente = new TClienteVIP(id, id_tarjeta_vip, tlf, nif, nombre, direccion, activo);
					}
					else throw new SQLException();
				} catch (SQLException sqle) {
					is_vip = false;
				}
				if (!is_vip) {
					try (PreparedStatement stmtcnormal = connection.prepareStatement(querycnormal)) {
						stmtcnormal.setInt(1, id);
						ResultSet rscvip = stmtcnormal.executeQuery();
						if (rscvip.next()) {
							int id_tarjeta_normal = rscvip.getInt(ID_CLIENTE_NORMAL);
							cliente = new TClienteNormal(id, id_tarjeta_normal, tlf, nif, nombre, direccion, activo);
						}
						else throw new SQLException();
					} catch (SQLException sqle) {
						cliente = null;
					}
				}
			} else 
				throw new SQLException();
		} catch (SQLException e) {
			cliente = null;
		}
		return cliente;
	}
	@Override
	public List<TCliente> listarClientes() {
		List<TCliente> clientes = new ArrayList<>();
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();	
		String query = "SELECT * FROM cliente FOR UPDATE",
				querycvip = "SELECT * FROM cliente_vip WHERE id_cliente = ? FOR UPDATE",
				querycnormal = "SELECT * FROM cliente_normal WHERE id_cliente = ? FOR UPDATE";
		try(Statement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery(query);
			TCliente cliente = null;
			while (rs.next()) {
				boolean is_vip = true;
				int id = rs.getInt(ID);
				int tlf = rs.getInt(TLF);
				String nif = rs.getString(NIF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				boolean activo = rs.getBoolean(ACTIVO);
				try (PreparedStatement stmtcvip = connection.prepareStatement(querycvip)) {
					stmtcvip.setInt(1, id);
					ResultSet rscvip = stmtcvip.executeQuery();
					if (rscvip.next()) {
						int id_tarjeta_vip = rscvip.getInt(ID_CLIENTE_VIP);
						cliente = new TClienteVIP(id, id_tarjeta_vip, tlf, nif, nombre, direccion, activo);
					}
					else throw new SQLException();
				} catch (SQLException sqle) {
					is_vip = false;
				}
				if (!is_vip) {
					try (PreparedStatement stmtcnormal = connection.prepareStatement(querycnormal)) {
						stmtcnormal.setInt(1, id);
						ResultSet rscvip = stmtcnormal.executeQuery();
						if (rscvip.next()) {
							int id_tarjeta_normal = rscvip.getInt(ID_CLIENTE_NORMAL);
							cliente = new TClienteNormal(id, id_tarjeta_normal, tlf, nif, nombre, direccion, activo);
						}
						else throw new SQLException();
					} catch (SQLException sqle) {
						clientes = null;
					}
				}
				clientes.add(cliente);
			}
			if (cliente == null)
				throw new SQLException("DAOCliente: No existe ningun cliente");
		} catch (SQLException e) {
			clientes = null;
		}
		return clientes;
	}
	@Override
	public int actualizarClienteVIP(TClienteVIP tcv) {
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE cliente SET telefono = ?, nif = ?,  nombre = ?, direccion = ?, activo = ? WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, tcv.getTlf());
			statement.setString(2, tcv.getNIF());
			statement.setString(3, tcv.getNombre());
			statement.setString(4, tcv.getDireccion());
			statement.setBoolean(5, true);
			statement.setInt(6, tcv.getId());
			int rows = statement.executeUpdate();
			if (rows > 0) {
				idCliente = tcv.getId();
				query = "UPDATE cliente_vip SET id_cliente_vip = ? WHERE id_cliente = ?";
				try (PreparedStatement stmt = connection.prepareStatement(query)) {
					stmt.setInt(1, tcv.getIdTarjetaVIP());
					stmt.setInt(2, idCliente);
					int rows2 = stmt.executeUpdate();
					if (rows2 <= 0) 
						throw new SQLException();
				} catch (SQLException sqle) {
					idCliente = -1;
				}
			}
			else 
				throw new SQLException();
		} catch (SQLException e) {
			idCliente = -1;
		}
		return idCliente;
	}
	@Override
	public int actualizarClienteNormal(TClienteNormal tcn) {
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE cliente SET telefono = ?, nif = ?,  nombre = ?, direccion = ?, activo = ? WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, tcn.getTlf());
			statement.setString(2, tcn.getNIF());
			statement.setString(3, tcn.getNombre());
			statement.setString(4, tcn.getDireccion());
			statement.setBoolean(5, true);
			statement.setInt(6, tcn.getId());
			int rows = statement.executeUpdate();
			if (rows > 0) {
				idCliente = tcn.getId();
				query = "UPDATE cliente_normal SET id_cliente_normal = ? WHERE id_cliente = ?";
				try (PreparedStatement stmt = connection.prepareStatement(query)) {
					stmt.setInt(1, tcn.getIdTarjetaNormal());
					stmt.setInt(2, idCliente);
					int rows2 = stmt.executeUpdate();
					if (rows2 <= 0) 
						throw new SQLException();
				} catch (SQLException sqle) {
					idCliente = -1;
				}
			}
			else 
				throw new SQLException();
		} catch (SQLException e) {
			idCliente = -1;
		}
		return idCliente;
	}
	@Override
	public int bajaCliente(int id) {
		int idCliente = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE cliente SET activo = ? WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, false);
			statement.setInt(2, id);
			int rows = statement.executeUpdate();
			if (rows > 0) 
				idCliente = id;
			else 
				throw new SQLException();
		} catch (SQLException e) {
			idCliente = -1;
		}
		
		return idCliente;
	}
	
}