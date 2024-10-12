package integracion.linea_factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.TransactionManager;
import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {
	
	private static final String LINEA_FACTURA = "linea_factura";
	private static final String ID_FACTURA = "id_factura";
	private static final String ID_PRODUCTO = "id_producto";
	private static final String CANTIDAD = "cantidad";
	private static final String PRECIO_PRODUCTO = "precio_producto";

	@Override
	public int crearLinea(TLineaFactura linea) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", 
				LINEA_FACTURA, ID_FACTURA, ID_PRODUCTO, CANTIDAD, PRECIO_PRODUCTO);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, linea.getIdFactura());
			stmt.setInt(2, linea.getIdProducto());
			stmt.setInt(3, linea.getCantidad());
			stmt.setDouble(4, linea.getPrecioProducto());
			
			int rows = stmt.executeUpdate(); 
			if (rows > 0) {
				res = 1;
			}
			else {
				throw new SQLException();
			}
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = -1;
		}
		return res;
	}

	@Override
	public TLineaFactura buscarLinea(int idFactura, int idProducto) {
		TLineaFactura linea = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? FOR UPDATE", 
				LINEA_FACTURA, ID_FACTURA, ID_PRODUCTO);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idFactura);
			stmt.setInt(2, idProducto);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int cantidad = rs.getInt(CANTIDAD);
				double precioProducto = rs.getDouble(PRECIO_PRODUCTO);
				linea = new TLineaFactura(idFactura, idProducto, cantidad, precioProducto);
			}
			else {
				throw new SQLException();
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			linea = null;
		}
		return linea;
	}

	@Override
	public List<TLineaFactura> listarLineasPorFactura(int idFactura) {
		List<TLineaFactura> res = new ArrayList<TLineaFactura>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("SELECT * FROM %s WHERE %s = ? FOR UPDATE", 
				LINEA_FACTURA, ID_FACTURA);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idFactura);
			ResultSet rs = stmt.executeQuery();
			TLineaFactura linea = null;
			while (rs.next()) {
				int idProducto = rs.getInt(ID_PRODUCTO);
				int cantidad = rs.getInt(CANTIDAD);
				double precioProducto = rs.getDouble(PRECIO_PRODUCTO);
				linea = new TLineaFactura(idFactura, idProducto, cantidad, precioProducto);
				res.add(linea);
			}
			
			if (linea == null) {
				throw new SQLException();
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = null;
		}
		return res;
	}

	@Override
	public List<TLineaFactura> listarLineasPorProducto(int idProducto) {
		List<TLineaFactura> res = new ArrayList<TLineaFactura>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("SELECT * FROM %s WHERE %s = ? FOR UPDATE", 
				LINEA_FACTURA, ID_PRODUCTO);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idProducto);
			ResultSet rs = stmt.executeQuery();
			TLineaFactura linea = null;
			while (rs.next()) {
				int idFactura = rs.getInt(ID_FACTURA);
				int cantidad = rs.getInt(CANTIDAD);
				double precioProducto = rs.getDouble(PRECIO_PRODUCTO);
				linea = new TLineaFactura(idFactura, idProducto, cantidad, precioProducto);
				res.add(linea);
			}
			
			if (linea == null) {
				throw new SQLException();
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = null;
		}
		return res;
	}

	@Override
	public int actualizarLinea(TLineaFactura linea) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", 
				LINEA_FACTURA, CANTIDAD, ID_FACTURA, ID_PRODUCTO);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, linea.getCantidad());
			stmt.setInt(2, linea.getIdFactura());
			stmt.setInt(3, linea.getIdProducto());
			
			int rows = stmt.executeUpdate(); 
			if (rows > 0) {
				res = 1;
			}
			else {
				throw new SQLException();
			}
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = -1;
		}
		return res;
	}

	@Override
	public int eliminarLinea(int idFactura, int idProducto) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", 
				LINEA_FACTURA, ID_FACTURA, ID_PRODUCTO);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idFactura);
			stmt.setInt(2, idProducto);
			
			int rows = stmt.executeUpdate(); 
			if (rows > 0) {
				res = 1;
			}
			else {
				throw new SQLException();
			}
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			res = -1;
		}
		return res;
	}

}
