package integracion.trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.TransactionManager;
import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;

public class DAOTrabajadorImp implements DAOTrabajador {
	
	private static final String ID = "id";
	private static final String ID_TRABAJADOR = "id_trabajador";
	private static final String TLF = "telefono";
	private static final String NIF = "nif";
	private static final String NOMBRE = "nombre";
	private static final String DIRECCION = "direccion";
	private static final String ID_ALMACEN = "id_almacen";
	private static final String ACTIVO = "activo";
	private static final String SUELDO_BASE = "sueldo_base";
	private static final String HORAS = "horas";
	private static final String PRECIO_HORA = "precio_horas";

	@Override
	public int altaTrabajadorJCompleta(TTrabajadorJCompleta ttjc) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("INSERT INTO trabajador (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)", 
				TLF, NIF, NOMBRE, DIRECCION, ID_ALMACEN, ACTIVO);
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, ttjc.getTlf());
			stmt.setString(2, ttjc.getNIF());
			stmt.setString(3, ttjc.getNombre());
			stmt.setString(4, ttjc.getDireccion());
			stmt.setInt(5, ttjc.getIdAlmacen());
			stmt.setBoolean(6, true);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
				ttjc.setId(res);
				String queryJCompleta = String.format("INSERT INTO trabajador_jornada_completa (%s, %s) VALUES (?, ?)", ID_TRABAJADOR, SUELDO_BASE);
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, res);
					stmtJCompleta.setDouble(2, ttjc.getSueldoBase());
					int rowsjc = stmtJCompleta.executeUpdate();
					if (rowsjc <= 0)
						throw new SQLException();
				}
				catch (SQLException sqle) {
					System.out.println(sqle.getMessage());
				}
			}
			else {
						throw new SQLException();
			}
		} 
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return res;
	}
	
	@Override
	public int altaTrabajadorJParcial(TTrabajadorJParcial ttjp) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("INSERT INTO trabajador (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)", 
				TLF, NIF, NOMBRE, DIRECCION, ID_ALMACEN, ACTIVO);
		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, ttjp.getTlf());
			stmt.setString(2, ttjp.getNIF());
			stmt.setString(3, ttjp.getNombre());
			stmt.setString(4, ttjp.getDireccion());
			stmt.setInt(5, ttjp.getIdAlmacen());
			stmt.setBoolean(6, true);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
				ttjp.setId(res);
				String queryJParcial = String.format("INSERT INTO trabajador_jornada_parcial (%s, %s, %s) VALUES (?, ?, ?)", ID_TRABAJADOR, HORAS, PRECIO_HORA);
				try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
					stmtJParcial.setInt(1, res);
					stmtJParcial.setInt(2, ttjp.getHoras());
					stmtJParcial.setDouble(3, ttjp.getPrecioHora());
					int rowsjp = stmtJParcial.executeUpdate();
					if (rowsjp <= 0) 
						throw new SQLException();
				}
				catch (SQLException sqle) {
					System.out.println(sqle.getMessage());
				}
			}
			else {
						throw new SQLException();
			}
		} 
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return res;
	}

	@Override
	public TTrabajador buscarTrabajador(int id) {
		TTrabajador leido = null; 
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM trabajador WHERE id = ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				boolean isComplete = true;
				int tlf = rs.getInt(TLF);
				String nif = rs.getString(NIF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				int idAlmacen = rs.getInt(ID_ALMACEN);
				boolean activo = rs.getBoolean(ACTIVO);
				String queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? FOR UPDATE";
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					ResultSet rsjc = stmtJCompleta.executeQuery();
					if (rsjc.next()) {
						double sueldoBase = rsjc.getDouble(SUELDO_BASE);
						leido = new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo);
					}
						throw new SQLException();
				}
				catch (SQLException sqle) {
					isComplete = false;
					System.out.println(sqle.getMessage());
				}
				if (!isComplete) {
					String queryJParcial = "SELECT * FROM trabajador_jornada_parcial WHERE id_trabajador = ? FOR UPDATE";
					try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
						stmtJParcial.setInt(1, id);
						ResultSet rsjp = stmtJParcial.executeQuery();
						if (rsjp.next()) {
							int horas = rsjp.getInt(HORAS);
							double precioHora = rsjp.getDouble(PRECIO_HORA);
							leido = new TTrabajadorJParcial(id, tlf, nif, nombre, direccion, idAlmacen, horas, precioHora, activo);
						}
						throw new SQLException();
					}
					catch (SQLException sqle) {
						System.out.println(sqle.getMessage());
					}
				}
			}
			else {
						throw new SQLException();
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return leido;
	}

	@Override
	public TTrabajador buscarTrabajadorPorNIF(String nif) {
		TTrabajador leido = null; 
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM trabajador WHERE " + NIF + " = ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, nif);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				boolean isComplete = true;
				int id = rs.getInt(ID);
				int tlf = rs.getInt(TLF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				int idAlmacen = rs.getInt(ID_ALMACEN);
				boolean activo = rs.getBoolean(ACTIVO);
				String queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? FOR UPDATE";
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					ResultSet rsjc = stmtJCompleta.executeQuery();
					if (rsjc.next()) {
						double sueldoBase = rsjc.getDouble(SUELDO_BASE);
						leido = new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo);
					}
						throw new SQLException();
				}
				catch (SQLException sqle) {
					isComplete = false;
					System.out.println(sqle.getMessage());
				}
				if (!isComplete) {
					String queryJParcial = "SELECT * FROM trabajador_jornada_parcial WHERE id_trabajador = ? FOR UPDATE";
					try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
						stmtJParcial.setInt(1, id);
						ResultSet rsjp = stmtJParcial.executeQuery();
						if (rsjp.next()) {
							int horas = rsjp.getInt(HORAS);
							double precioHora = rsjp.getDouble(PRECIO_HORA);
							leido = new TTrabajadorJParcial(id, tlf, nif, nombre, direccion, idAlmacen, horas, precioHora, activo);
						}
						throw new SQLException();
					}
					catch (SQLException sqle) {
						System.out.println(sqle.getMessage());
					}
				}
			}
			else {
						throw new SQLException();
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return leido;
	}

	@Override
	public List<TTrabajador> listarTrabajadores() {
		List<TTrabajador> lista = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM trabajador FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			TTrabajador tt = null;
			while (rs.next()) {
				boolean isComplete = true;
				int id = rs.getInt(ID);
				int tlf = rs.getInt(TLF);
				String nif = rs.getString(NIF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				int idAlmacen = rs.getInt(ID_ALMACEN);
				boolean activo = rs.getBoolean(ACTIVO);
				String queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? FOR UPDATE";
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					ResultSet rsjc = stmtJCompleta.executeQuery();
					if (rsjc.next()) {
						double sueldoBase = rsjc.getDouble(SUELDO_BASE);
						tt = new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo);
					}
					else
						throw new SQLException();
				}
				catch (SQLException sqle) {
					isComplete = false;
					System.out.println(sqle.getMessage());
				}
				if (!isComplete) {
					String queryJParcial = "SELECT * FROM trabajador_jornada_parcial WHERE id_trabajador = ? FOR UPDATE";
					try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
						stmtJParcial.setInt(1, id);
						ResultSet rsjp = stmtJParcial.executeQuery();
						if (rsjp.next()) {
							int horas = rsjp.getInt(HORAS);
							double precioHora = rsjp.getDouble(PRECIO_HORA);
							tt = new TTrabajadorJParcial(id, tlf, nif, nombre, direccion, idAlmacen, horas, precioHora, activo);
						}
						throw new SQLException();
					}
					catch (SQLException sqle) {
						System.out.println(sqle.getMessage());
					}
				}
				lista.add(tt);
			}
			if (tt == null) 
				throw new SQLException();
		}
		catch (SQLException sqle) {
			lista = null;
			System.out.println(sqle.getMessage());
		}
		return lista;
	}

	@Override
	public List<TTrabajador> listarTrabajadoresPorAlmacen(int idAlmacen) {
		List<TTrabajador> lista = new ArrayList<TTrabajador>();
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = "SELECT * FROM trabajador WHERE " + ID_ALMACEN + " = ? FOR UPDATE";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, idAlmacen);
			ResultSet rs = stmt.executeQuery();
			TTrabajador tt = null;
			while (rs.next()) {
				boolean isComplete = true;
				int id = rs.getInt(ID);
				int tlf = rs.getInt(TLF);
				String nif = rs.getString(NIF);
				String nombre = rs.getString(NOMBRE);
				String direccion = rs.getString(DIRECCION);
				boolean activo = rs.getBoolean(ACTIVO);
				String queryJCompleta = "SELECT * FROM trabajador_jornada_completa WHERE id_trabajador = ? FOR UPDATE";
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setInt(1, id);
					ResultSet rsjc = stmtJCompleta.executeQuery();
					if (rsjc.next()) {
						double sueldoBase = rsjc.getDouble(SUELDO_BASE);
						tt = new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo);
					}
					else 
						throw new SQLException();
				}
				catch (SQLException sqle) {
					isComplete = false;
					System.out.println(sqle.getMessage());
				}
				if (!isComplete) {
					String queryJParcial = "SELECT * FROM trabajador_jornada_parcial WHERE id_trabajador = ? FOR UPDATE";
					try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
						stmtJParcial.setInt(1, id);
						ResultSet rsjp = stmtJParcial.executeQuery();
						if (rsjp.next()) {
							int horas = rsjp.getInt(HORAS);
							double precioHora = rsjp.getDouble(PRECIO_HORA);
							tt = new TTrabajadorJParcial(id, tlf, nif, nombre, direccion, idAlmacen, horas, precioHora, activo);
						}
						throw new SQLException();
					}
					catch (SQLException sqle) {
						System.out.println(sqle.getMessage());
					}
				}
				lista.add(tt);
			}
			if (tt == null) 
				throw new SQLException();
		}
		catch (SQLException sqle) {
			lista = null;
			System.out.println(sqle.getMessage());
		}
		return lista;
	}

	@Override
	public int actualizarTrabajadorJCompleta(TTrabajadorJCompleta ttjc) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("UPDATE trabajador SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", 
				NIF, NOMBRE, DIRECCION, ID_ALMACEN, ACTIVO, ID);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, ttjc.getNIF());
			stmt.setString(2, ttjc.getNombre());
			stmt.setString(3, ttjc.getDireccion());
			stmt.setInt(4, ttjc.getIdAlmacen());
			stmt.setBoolean(5, true);
			stmt.setInt(6, ttjc.getId());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				String queryJCompleta = String.format("UPDATE trabajador_jornada_completa SET %s = ? WHERE %s = ?", 
						SUELDO_BASE, ID_TRABAJADOR);
				try (PreparedStatement stmtJCompleta = connection.prepareStatement(queryJCompleta)) {
					stmtJCompleta.setDouble(1, ttjc.getSueldoBase());
					stmtJCompleta.setInt(2, ttjc.getId());
					int rowsjc = stmtJCompleta.executeUpdate();
					res = ttjc.getId();
					if (rowsjc <= 0)
						throw new SQLException();
				}
				catch (SQLException sqle) {
					System.out.println(sqle.getMessage());
				}
			}
			else {
						throw new SQLException();
			}
		} 
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return res;
	}
	
	@Override
	public int actualizarTrabajadorJParcial(TTrabajadorJParcial ttjp) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("UPDATE trabajador SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", 
				NIF, NOMBRE, DIRECCION, ID_ALMACEN, ACTIVO, ID);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, ttjp.getNIF());
			stmt.setString(2, ttjp.getNombre());
			stmt.setString(3, ttjp.getDireccion());
			stmt.setInt(4, ttjp.getIdAlmacen());
			stmt.setBoolean(5, true);
			stmt.setInt(6, ttjp.getId());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				String queryJParcial = String.format("UPDATE trabajador_jornada_parcial SET %s = ?, %s = ? WHERE %s = ?", 
						HORAS, PRECIO_HORA, ID_TRABAJADOR);
				try (PreparedStatement stmtJParcial = connection.prepareStatement(queryJParcial)) {
					stmtJParcial.setInt(1, ttjp.getHoras());
					stmtJParcial.setDouble(2, ttjp.getPrecioHora());
					stmtJParcial.setInt(3, ttjp.getId());
					int rowsjp = stmtJParcial.executeUpdate();
					res = ttjp.getId();
					if (rowsjp <= 0) 
						throw new SQLException();
				}
				catch (SQLException sqle) {
					System.out.println(sqle.getMessage());
				}
			}
			else {
						throw new SQLException();
			}
		} 
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return res;
	}

	@Override
	public int bajaTrabajador(int id) {
		int res = -1;
		TransactionManager tmanager = TransactionManager.getInstancia();
		Connection connection = (Connection) tmanager.nuevaTransaccion().getResource();
		String query = String.format("UPDATE trabajador SET %s = ? WHERE %s = ?", ACTIVO, ID);
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setBoolean(1, false);
			stmt.setInt(2, id);
			int rows = stmt.executeUpdate();
			res = id;
			if (rows <= 0) {
						throw new SQLException();
			}
		} 
		catch (SQLException sqle) {
			res = -1;
			System.out.println(sqle.getMessage()); 
		}
		return res;
	}
	
}