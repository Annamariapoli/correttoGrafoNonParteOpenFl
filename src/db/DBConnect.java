package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnect {

	private final static String jdbcURL = "jdbc:mysql://localhost/openflights?user=root&password=";


	public static Connection getConnection() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new RuntimeException("Driver jdbc non trovato" , e);
		}
		Connection c;
		
		try{
			c = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException ("Impossibile connettersi", e);
		}
		return c;
	}

	
}
