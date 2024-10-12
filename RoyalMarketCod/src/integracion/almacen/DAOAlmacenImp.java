package integracion.almacen;

import negocio.almacen.TAlmacen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.TransactionManager;

public class DAOAlmacenImp implements DAOAlmacen {
	
	@Override
	public int altaAlmacen(TAlmacen ta) {
		int idAlmacen = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		
		String query = "INSERT INTO almacen (direccion, activo) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, ta.getDireccion());
			stmt.setBoolean(2, true);
			
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				idAlmacen = rs.getInt(1);
				ta.setId(idAlmacen);
			}
			else throw new SQLException();
			
		} catch (SQLException sqle) {
			idAlmacen = -1;
		}
		return idAlmacen;
	}
	
	@Override
	public TAlmacen buscarAlmacen(int id) {
		TAlmacen almacen = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM almacen WHERE id = ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				almacen = new TAlmacen(id,
						rs.getString("direccion"),
						rs.getBoolean("activo"));
			}
			else throw new SQLException();
		} catch (SQLException sqle) {
			almacen = null;
		}
		return almacen;
	}
	@Override
	public TAlmacen buscarAlmacenPorDireccion(String direccion) {
		TAlmacen almacen = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM almacen WHERE direccion = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, direccion);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				almacen = new TAlmacen(rs.getInt("id"),
						rs.getString("direccion"),
						rs.getBoolean("activo"));
			} else 
				throw new SQLException();
		} catch (SQLException e) {
			almacen = null;
		}
		return almacen;
	}
	@Override
	public List<TAlmacen> listarAlmacenes() {
		List<TAlmacen> res = new ArrayList<>();
		TAlmacen almacen = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM almacen FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				almacen = new TAlmacen(
						rs.getInt("id"),
						rs.getString("direccion"),
						rs.getBoolean("activo"));
				res.add(almacen);
			}
			if (almacen == null) throw new SQLException();
		} catch (SQLException sqle) {
			res = null;
		}
		return res;
	}
	@Override
	public int actualizarAlmacen(TAlmacen ta) {
		int res = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE almacen SET direccion = ?, activo = ? WHERE id = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, ta.getDireccion());
			stmt.setBoolean(2, true);
			stmt.setInt(3, ta.getId());
			int rows = stmt.executeUpdate();
			if (rows > 0) 
				res = ta.getId();
			else 
				throw new SQLException();
		} catch (SQLException e) {
			res = -1;
		}
		return res;
	}
	@Override
	public int bajaAlmacen(int id) {
		int res = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE almacen SET activo = ? WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, false);
			statement.setInt(2, id);
			int rows = statement.executeUpdate();
			if (rows > 0) 
				res = id;
			else 
				throw new SQLException();
		} catch (SQLException e) {
			res = -1;
		}
		return res;
	}
}