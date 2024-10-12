package integracion.proveedor_producto;

import negocio.proveedor_producto.TProveedorProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.TransactionManager;

public class DAOProveedorProductoImp implements DAOProveedorProducto {
	
	@Override
	public int createProveedorProducto(TProveedorProducto tpp) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "INSERT INTO proveedor_producto (id_proveedor, id_producto) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, tpp.getIdProveedor());
			stmt.setInt(2, tpp.getIdProducto());
			int rows = stmt.executeUpdate();
			if (rows > 0) res = 1;
			else throw new SQLException();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = -1;
		}
		
		return res;
	}
	@Override
	public List<TProveedorProducto> readAllByProveedor(int id_proveedor) {
		List<TProveedorProducto> res = new ArrayList<TProveedorProducto>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM proveedor_producto WHERE id_proveedor = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id_proveedor);
			ResultSet rs = stmt.executeQuery();
			TProveedorProducto tpp = null;
			while (rs.next()) {
				tpp = new TProveedorProducto(
						id_proveedor,
						rs.getInt("id_producto"));
				res.add(tpp);
			}
			if (tpp == null) throw new SQLException();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = null;
		}
		return res;
	}
	@Override
	public List<TProveedorProducto> readAllByProducto(int id_producto) {
		List<TProveedorProducto> res = new ArrayList<TProveedorProducto>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM proveedor_producto WHERE id_producto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id_producto);
			ResultSet rs = stmt.executeQuery();
			TProveedorProducto tpp = null;
			while (rs.next()) {
				tpp = new TProveedorProducto(
						rs.getInt("id_proveedor"),
						id_producto);
				res.add(tpp);
			}
			if (tpp == null) throw new SQLException();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = null;
		}
		return res;
	}
	@Override
	public int deleteProveedorProducto(int id_proveedor, int id_producto) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		
		String query = "DELETE FROM proveedor_producto WHERE id_proveedor = ? AND id_producto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id_proveedor);
			stmt.setInt(2, id_producto);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				res = 1;				
			}
			else { 
				res = -1;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = -1;
		}
		
		return res;
	}
	@Override
	public TProveedorProducto readProveedorProducto(int id_proveedor, int id_producto) {
		TProveedorProducto res = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM proveedor_producto WHERE id_proveedor = ? AND id_producto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id_proveedor);
			stmt.setInt(2, id_producto);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				res = new TProveedorProducto(id_proveedor, id_producto);
				
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = null;
		}
		
		return res;
	}
}