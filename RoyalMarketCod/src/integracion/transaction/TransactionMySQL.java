package integracion.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionMySQL implements Transaction {
	
	private static final String FROM_WHERE = "TransactionMySQL%s";
	
	private Connection con;
	
	public TransactionMySQL() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
			final String urlDatabase = "jdbc:mysql://localhost:3306/royalmarket";
			final String nameUser = "root";
			final String passUser = "";
			this.con = DriverManager.getConnection(urlDatabase, nameUser, passUser);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			this.con = null;
		} 
			catch (ClassNotFoundException e) {
			System.out.println("Clase No encontrada: " + e.getMessage());
		}
	}

	public void start() {
		try {
			this.con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		try {
			this.con.commit();
			this.con.close();
			TransactionManager.getInstancia().eliminaTransaccion();
		} catch (SQLException e) {
			System.out.println(String.format(FROM_WHERE, ".commit()"));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void rollback() {
		try {
			this.con.rollback();
			this.con.close();
			TransactionManager.getInstancia().eliminaTransaccion();
		} catch (SQLException e) {
			System.out.println(String.format(FROM_WHERE, ".rollback()"));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public Object getResource() {
		return this.con;
	}
}