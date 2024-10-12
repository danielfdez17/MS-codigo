package integracion.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import integracion.transaction.Transaction;
import integracion.transaction.TransactionManager;
import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;

public class TrabajadoresJCompleta implements Query {
	public Object execute(Object param) {
		ArrayList<TTrabajador> res = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		Connection connection = (Connection) transaction.getResource();
		
		String query = "SELECT * FROM trabajador FOR UPDATE",
				queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TTrabajador trabajador = null;
				int id = rs.getInt("id");
				int tlf = rs.getInt("telefono");
				String nif = rs.getString("nif");
				String nombre = rs.getString("nombre");
				String direccion = rs.getString("direccion");
				int id_almacen = rs.getInt("id_almacen");
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					ResultSet rsjcompleta = stmtJCompleta.executeQuery();
					double sueldo_base = rsjcompleta.getDouble("sueldo_base");
					trabajador = new TTrabajadorJCompleta(
							tlf, 
							nif, 
							nombre, 
							direccion, 
							id_almacen, 
							sueldo_base);
				} catch (SQLException sqle) {} 
				res.add(trabajador);
			}
		} catch (SQLException sqle) {
			res = null;
		}
		if (res.size() == 0) res = null;
		
		tmanager.eliminaTransaccion();
		return res;
	}
}