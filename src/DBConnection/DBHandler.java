package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends Config{

	
	private Connection dbconnection;

	public Connection getConnection() 
	{
		
		String connectionString = "jdbc:mysql://" + Config.dbhost + ":" + Config.dbport + "/" + Config.dbname;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			try {
				
				dbconnection = DriverManager.getConnection(connectionString, Config.dbusername, Config.dbpassword);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return dbconnection;
	}
}
