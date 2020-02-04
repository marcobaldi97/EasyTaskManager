package easyTaskManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;



public class Main {

	public static void main(String args[]) {
		Ini ini = null;
		try {
			ini = new Ini(new File("C:/MirtransTasks/preferences.ini"));
		} catch (InvalidFileFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.util.prefs.Preferences prefs = new IniPreferences(ini);
		System.out.println(ini.get("header", "user_db"));
		/*
		String odbc_location = "jdbc:postgresql://localhost:5432/easyTasksDB";
		String user_db = "postgres";
		String pass_db = "MirTrans101";
		*/
		String odbc_location = ini.get("header", "odbc_location");
		String user_db = ini.get("header", "user_db");
		String pass_db = ini.get("header", "pass_db");
        try (Connection connection = DriverManager.getConnection(odbc_location, user_db, pass_db)) {
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            Login loginCall = new Login();
            loginCall.main(odbc_location, user_db, pass_db);
            System.out.println("Reading user records...");
            System.out.printf("%-30.30s  %-30.30s%n", "User", "Password");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.persona");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("usuario"), resultSet.getString("passwordusuario"));
            }
 
        } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
	}

}
