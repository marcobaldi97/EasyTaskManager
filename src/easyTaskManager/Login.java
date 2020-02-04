package easyTaskManager;

import java.sql.Connection;
import java.sql.Statement;

public class Login {

	public void main(String odbc_location, String user_db, String pass_db) {
		LoginWindow ventanaLogin = new LoginWindow(odbc_location,user_db,pass_db);
		ventanaLogin.main(null,odbc_location,user_db,pass_db);
	}

}
