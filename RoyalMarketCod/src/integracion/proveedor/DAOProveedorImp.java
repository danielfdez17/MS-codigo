package integracion.proveedor;

import negocio.proveedor.TProveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import integracion.transaction.TransactionManager;

public class DAOProveedorImp implements DAOProveedor {
	
	@Override
	public int altaProveedor(TProveedor tp) {
		int idProveedor = -1;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "INSERT INTO proveedor (telefono, nif, direccion, activo) VALUES(?, ?, ?, ?)";
		try(PreparedStatement statement = connection.prepareStatement(query,java.sql.Statement.RETURN_GENERATED_KEYS)){
			statement.setInt(1,tp.getTlf());
			statement.setString(2,tp.getNif());
			statement.setString(3, tp.getDireccion());
			statement.setBoolean(4, true);
			int rows = statement.executeUpdate();
			if (rows > 0){
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				idProveedor = rs.getInt(1);
				tp.setId(idProveedor);
			}
			else {
				throw new SQLException();
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			idProveedor = -1;
		}
		return idProveedor;
	}
	@Override
	public TProveedor buscarProveedor(int id) {
		TProveedor proveedor = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM proveedor WHERE id = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int tlf = rs.getInt("telefono");
				String nif = rs.getString("nif");
				String direccion = rs.getString("direccion");
				boolean activo = rs.getBoolean("activo");
				proveedor = new TProveedor(id, tlf, nif, direccion, activo);
			}
			else{
				throw new SQLException();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			proveedor = null;
		}
		return proveedor;
	}
	@Override
	public TProveedor buscarProveedorPorNIF(String nif) {
		TProveedor proveedor = null;
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM proveedor WHERE nif = ? FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1, nif);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				int tlf = rs.getInt("telefono");
				String direccion = rs.getString("direccion");
				boolean activo = rs.getBoolean("activo");
				proveedor = new TProveedor(id, tlf, nif, direccion, activo);
			}
			else {
				throw new SQLException();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			proveedor = null;
		}
		return proveedor;
	}
	@Override
	public List<TProveedor> listarProveedores() {
		List<TProveedor> lista = new ArrayList<TProveedor>();
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection connection = (Connection) transactionManager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM proveedor FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			ResultSet rs = statement.executeQuery();
			TProveedor tProveedor = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				int tlf = rs.getInt("telefono");
				String nif = rs.getString("nif");
				String direccion = rs.getString("direccion");
				boolean activo = rs.getBoolean("activo");
				tProveedor = new TProveedor(id, tlf, nif, direccion, activo);
				lista.add(tProveedor);
			}
			if (tProveedor == null) {
				throw new SQLException();
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			lista = null;
		}
		return lista;
	}
	@Override
	public List<TProveedor> listarProveedoresPorProducto(int id_producto) {
		List<TProveedor> lista = new ArrayList<TProveedor>();
		TransactionManager transactionManager=TransactionManager.getInstancia();
		Connection connection= (Connection )transactionManager.nuevaTransaccion().getResource();
		String sql = "SELECT id, telefono, nif, direccion, activo FROM proveedor p INNER JOIN proveedor_producto pp ON pp.id_proveedor = p.id WHERE pp.id_producto = ?"; 
		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, id_producto);
			ResultSet rs = statement.executeQuery();
			TProveedor tProveedor = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				int tlf = rs.getInt("telefono");
				String nif = rs.getString("nif");
				String direccion = rs.getString("direccion");
				boolean activo = rs.getBoolean("activo");
				tProveedor = new TProveedor(id, tlf, nif, direccion, activo);
				lista.add(tProveedor);
			}
			if (tProveedor == null){
				throw new SQLException();
			}

		} catch (SQLException e) {	
			System.out.println(e.getMessage());
			lista = null;
		}
		return lista;
	}
	@Override
	public int actualizarProveedor(TProveedor tp) {
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection cnx = (Connection) transactionManager.nuevaTransaccion().getResource();
		int res = -1;
		String q = "UPDATE proveedor SET telefono = ?, nif = ?, direccion = ?, activo = ? WHERE id = ?";
		try(PreparedStatement ps = cnx.prepareStatement(q)) {
			ps.setInt(1, tp.getTlf());
			ps.setString(2, tp.getNif());
			ps.setString(3, tp.getDireccion());
			ps.setBoolean(4, true);
			ps.setInt(5, tp.getId());
			int resultado = ps.executeUpdate();
			if (resultado > 0)
				res = tp.getId();
			else
				throw new SQLException();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			res = -1;
		}
		return res;
	}
	@Override
	public int bajaProveedor(int id) {
		TransactionManager transactionManager = TransactionManager.getInstancia();
		Connection cnx = (Connection) transactionManager.nuevaTransaccion().getResource();
		int res = -1;
		String q = "UPDATE proveedor SET activo = ? WHERE id = ?";
		try(PreparedStatement ps = cnx.prepareStatement(q)){
			ps.setBoolean(1, false);
			ps.setInt(2, id);
			int rows = ps.executeUpdate();
			if(rows > 0)
				res = id;
			else
				throw new SQLException();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			res = -1;
		}
		return res;
	}
}