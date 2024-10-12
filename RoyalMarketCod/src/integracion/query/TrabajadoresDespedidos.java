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
import negocio.trabajador.TTrabajadorJParcial;

public class TrabajadoresDespedidos implements Query {
	public Object execute(Object param) {
		ArrayList<TTrabajador> res = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Transaction transaction = tmanager.nuevaTransaccion();
		Connection connection = (Connection) transaction.getResource();
		
		String query = "SELECT * FROM trabajador WHERE activo = ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setBoolean(1, false);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TTrabajador trabajador = null;
				boolean is_j_completa = true;
				String queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? FOR UPDATE", 
						queryJParcial = "SELECT * FROM trabajador_jornada_parcial WHERE id_trabajador = ? FOR UPDATE";
				int id = rs.getInt("id");
				int tlf = rs.getInt("telefono");
				String nif = rs.getString("nif");
				String nombre = rs.getString("nombre");
				String direccion = rs.getString("direccion");
				int idAlmacen = rs.getInt("id_almacen");
				boolean activo = rs.getBoolean("activo");
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					ResultSet rsjcompleta = stmtJCompleta.executeQuery();
					if (rsjcompleta.next()) {
						double sueldoBase = rsjcompleta.getDouble("sueldo_base");
						trabajador = new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo);
					}
				} catch (SQLException sqle) {
					is_j_completa = false;
				}
				if (!is_j_completa) {
					try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
						stmtJParcial.setInt(1, id);
						ResultSet rsjparcial = stmtJParcial.executeQuery();
						if (rsjparcial.next()) {
							int horas = rsjparcial.getInt("horas");
							double precioHora = rsjparcial.getDouble("precio_hora");
							trabajador = new TTrabajadorJParcial(id, tlf, nif, nombre, direccion, idAlmacen, horas, precioHora, activo);
						}
					} catch (SQLException sqle) {
						
					}
				}
				trabajador.setId(id);
				res.add(trabajador);
			}
			if (res.size() == 0) res = null;
		} catch (SQLException sqle) {
			res = null;
		} catch (Exception e) {
			res = null;
		}
		
		tmanager.eliminaTransaccion();
		return res;
	}
}