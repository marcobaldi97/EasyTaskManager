package easyTaskManagerEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DtGlobalParams {
	String odbc_location, user_db, pass_db;
	
	public DtGlobalParams(String param_odbc_location, String param_user_db, String param_pass_db) {
		odbc_location = param_odbc_location;
		user_db = param_user_db;
		pass_db = param_pass_db;
	}
	
	public String get_odbc_location() {
		return odbc_location;
	}
	
	public String get_user_db() {
		return odbc_location;
	}
	
	public String get_pass_db() {
		return odbc_location;
	}
	
	public void set_odbc_location(String param) {
		odbc_location = param;
	}
	
	public void set_user_db(String param) {
		user_db = param;
	}
	
	public void set_pass_db(String param) {
		pass_db = param;
	}	
	
	public Connection getConnectionLocal() {
		try {
			return DriverManager.getConnection(odbc_location, user_db, pass_db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
