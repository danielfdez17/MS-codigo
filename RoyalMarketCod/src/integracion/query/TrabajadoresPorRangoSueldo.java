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

public class TrabajadoresPorRangoSueldo implements Query {
	@SuppressWarnings("unchecked")
	public Object execute(Object param) {
		ArrayList<Double> rango = (ArrayList<Double>) param;
		Double minimo = rango.get(0), maximo = rango.get(1);
		ArrayList<TTrabajador> res = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		Connection connection = (Connection) transaction.getResource();
		
		String query = "SELECT * FROM trabajador FOR UPDATE",
				queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? AND sueldo_base >= ? AND sueldo_base <= ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TTrabajador trabajador = null;
				int id = rs.getInt("id");
				int tlf = rs.getInt("telefono");
				String nif = rs.getString("nif");
				String nombre = rs.getString("nombre");
				String direccion = rs.getString("direccion");
				int idAlmacen = rs.getInt("id_almacen");
				boolean activo = rs.getBoolean("activo");
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					stmtJCompleta.setDouble(2, minimo);
					stmtJCompleta.setDouble(3, maximo);
					ResultSet rsjcompleta = stmtJCompleta.executeQuery();
					if (rsjcompleta.next()) {
						double sueldoBase = rsjcompleta.getDouble("sueldo_base");
						trabajador = new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo);
//						if (minimo <= sueldo_base && sueldo_base <= maximo) 
					}
						res.add(trabajador);
				} catch (SQLException sqle) {}
			}
		} catch (SQLException sqle) {
			res = null;
		}
		if (res.size() == 0) res = null;
		
		tmanager.eliminaTransaccion();
		return res;
	}
}