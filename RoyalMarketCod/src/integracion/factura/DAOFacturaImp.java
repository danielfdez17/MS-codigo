package integracion.factura;

import negocio.factura.TFactura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import integracion.transaction.TransactionManager;

public class DAOFacturaImp implements DAOFactura {
	
	private static final String ID = "id";
	private static final String ID_CLIENTE = "id_cliente";
	private static final String FECHA = "fecha";
	private static final String ACTIVO = "activo";
	private static final String COSTE_TOTAL = "coste_total";
	@Override
	public TFactura buscarFactura(int id) {
		TFactura factura = null;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM factura WHERE id = ? FOR UPDATE";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				factura = new TFactura(id,
						rs.getDouble(COSTE_TOTAL),
						rs.getDate(FECHA),
						rs.getInt(ID_CLIENTE),
						rs.getBoolean(ACTIVO));
			}
			else throw new SQLException();
			
		} catch(SQLException sqle) {
			factura = null;
		}
		return factura;
	}

	@Override
	public List<TFactura> listarFacturas() {
		List<TFactura> lista = new ArrayList<TFactura>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM factura FOR UPDATE";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery(query);
			TFactura factura = null;
			while (rs.next()) {
				factura = new TFactura(
						rs.getInt(ID),
						rs.getDouble(COSTE_TOTAL),
						rs.getDate(FECHA),
						rs.getInt(ID_CLIENTE),
						rs.getBoolean(ACTIVO));
				lista.add(factura);
			}
			if (factura == null) throw new SQLException();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			lista = null;
		}
 		return lista;
	}

	@Override
	public List<TFactura> listarFacturasPorCliente(int idCliente) {
		List<TFactura> lista = new ArrayList<TFactura>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM factura WHERE id_cliente = ? FOR UPDATE";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idCliente);
			ResultSet rs = statement.executeQuery();
			TFactura factura = null;
			while (rs.next()) {
				factura = new TFactura(
						rs.getInt(ID),
						rs.getDouble(COSTE_TOTAL),
						rs.getDate(FECHA),
						idCliente,
						rs.getBoolean(ACTIVO));
				lista.add(factura);
			}
			if (factura == null) throw new SQLException();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			lista = null;
		}
		return lista;
	}

	@Override
	public int actualizarFactura(TFactura tf) {
		int idFactura = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "UPDATE factura SET coste_total = ?, fecha = ?, id_cliente = ?, activo = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDouble(1, tf.getCosteTotal());
			stmt.setDate(2, tf.getFecha());
			stmt.setInt(3, tf.getIdCliente());
			stmt.setBoolean(4, tf.isActivo());
			stmt.setInt(5, tf.getId());
			
			int rows = stmt.executeUpdate();
			if (rows > 0) idFactura = tf.getId();
			else throw new SQLException();
			
		} catch (SQLException sqle) {
			idFactura = -1;
		}
		return idFactura;
	}

	@Override
	public int cerrarFactura(TFactura tf) {
		int idFactura = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "INSERT INTO factura (coste_total, activo, id_cliente, fecha) VALUES (?, ?, ?, ?)";
//		String query = "INSERT INTO factura (coste_total, activo, id_cliente) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setDouble(1, tf.getCosteTotal());
			stmt.setBoolean(2, false);
			stmt.setInt(3, tf.getIdCliente());
			stmt.setDate(4, tf.getFecha()); 
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				idFactura = rs.getInt(1);
				tf.setId(idFactura);
			}
			else throw new SQLException();
			
		} catch (SQLException sqle) {
			idFactura = -1;
		}
		return idFactura;
	}

}