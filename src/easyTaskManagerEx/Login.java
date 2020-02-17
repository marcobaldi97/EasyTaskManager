package easyTaskManagerEx;

import java.sql.Connection;
import java.sql.Statement;

public class Login {

	public void main(String odbc_location, String user_db, String pass_db) {
		DtGlobalParams globalParams = new DtGlobalParams(odbc_location,user_db,pass_db);
		LoginWindowEx ventanaLogin = new LoginWindowEx(globalParams);
		ventanaLogin.setVisible(true);
	}

}
