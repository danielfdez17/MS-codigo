package integracion.producto;

import negocio.producto.TProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.TransactionManager;
public class DAOProductoImp implements DAOProducto {
	
	private static final String ID = "id";
	private static final String NOMBRE = "nombre";
	private static final String PRECIO = "precio";
	private static final String STOCK = "stock";
	private static final String ACTIVO = "activo";
	private static final String ID_ALMACEN = "id_almacen";
	@Override
	public int altaProducto(TProducto tp) {
		int idProduct = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "INSERT INTO producto (precio, stock, activo, nombre, id_almacen) VALUES (?, ?, ?, ?, ?);";
		try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setDouble(1, tp.getPrecio());
		 	statement.setInt(2, tp.getStock());
			statement.setBoolean(3, true);
			statement.setString(4, tp.getNombre());
			statement.setInt(5, tp.getIdAlmacen());
			int rows = statement.executeUpdate();
			if (rows > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				idProduct = rs.getInt(1);
				tp.setId(idProduct);
			} else 
				throw new SQLException();
			
		} catch (SQLException e) {
			idProduct = -1;
			System.out.println(e.getMessage());
		}
		
		return idProduct;
	}

	@Override
	public TProducto buscarProducto(int id) {
		TProducto product = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
	
		String query = "SELECT * FROM producto WHERE id = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				product = new TProducto(id, 
										rs.getString(NOMBRE), 
										rs.getDouble(PRECIO), 
										rs.getInt(STOCK), 
										rs.getBoolean(ACTIVO), 
										rs.getInt(ID_ALMACEN));
			}
			else 
				throw new SQLException();
			
		} catch (SQLException e) {
			product = null;
		}
			
		return product;
	}

	@Override
	public TProducto buscarProductoPorNombre(String nombre) {
		TProducto product = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM producto WHERE nombre = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, nombre);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				product = new TProducto(rs.getInt(ID), 
										nombre, 
										rs.getDouble(PRECIO), 
										rs.getInt(STOCK), 
										rs.getBoolean(ACTIVO), 
										rs.getInt(ID_ALMACEN));
			} else 
				throw new SQLException();
		} catch (SQLException e) {
			product = null;
		}
		
		
		return product;
	}

	@Override
	public List<TProducto> listarProductos() {
		List<TProducto> products = new ArrayList<TProducto>();
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM producto FOR UPDATE";
		try(Statement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery(query);
			TProducto product = null;
			while (rs.next()) {
				product = new TProducto(rs.getInt(ID), 
										rs.getString(NOMBRE), 
										rs.getDouble(PRECIO), 
										rs.getInt(STOCK), 
										rs.getBoolean(ACTIVO), 
										rs.getInt(ID_ALMACEN));
				products.add(product);
			}
			
			if (product == null)
				throw new SQLException();
			
		} catch (SQLException e) {
			products = null;
		}
		
		
		return products;
	}

	@Override
	public int actualizarProducto(TProducto tp) {
		int idProduct = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE producto SET precio = ?, stock = ?,  activo = ?, nombre = ?, id_almacen = ? WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDouble(1, tp.getPrecio());
			statement.setInt(2, tp.getStock());
			statement.setBoolean(3, tp.isActivo());
			statement.setString(4, tp.getNombre());
			statement.setInt(5, tp.getIdAlmacen());
			statement.setInt(6, tp.getId());

			int rows = statement.executeUpdate();
			if (rows > 0) 
				idProduct = tp.getId();
			else 
				throw new SQLException();
		} catch (SQLException e) {
			idProduct = -1;
		}
		return idProduct;
	}
	@Override
	public int bajaProducto(int id) {
		int idProduct = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "UPDATE producto SET activo = ? WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, false);
			statement.setInt(2, id);
			int rows = statement.executeUpdate();
			if (rows > 0) 
				idProduct = id;
			else 
				throw new SQLException();
		} catch (SQLException e) {
			idProduct = -1;
		}
		
		return idProduct;
	}

	@Override
	public List<TProducto> listarProductosPorAlmacen(int idAlmacen) {
		List<TProducto> listProduct = new ArrayList<>();
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		
		String query = "SELECT * FROM producto WHERE id_almacen = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAlmacen);
			ResultSet rs = statement.executeQuery();
			TProducto product = null;
			while(rs.next()) {
				product = new TProducto(rs.getInt(ID), 
										rs.getString(NOMBRE), 
										rs.getDouble(PRECIO), 
										rs.getInt(STOCK), 
										rs.getBoolean(ACTIVO), 
										rs.getInt(ID_ALMACEN));
			
				listProduct.add(product);
			} 
			if (product == null) 
				throw new SQLException();
		} catch (SQLException e) {
			listProduct = null;
			System.out.println("DAOProducto: " + e.getMessage());
		}
		
		return listProduct;
	}
	
	

}