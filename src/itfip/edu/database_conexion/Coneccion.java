package itfip.edu.database_conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.OracleDriver;

public class Coneccion {
	private String usuario;
	private String contraseña;
	public Coneccion(String usuario, String contraseña) {
		super();
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	public Connection conectarDB() {
		Connection conn = null;
		 try {
			  DriverManager.registerDriver(new OracleDriver());    //This is for loading the odbc driver
			  System.out.println("Driver loaded Successfully");  
			  conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE?useUnicode=true&characterEncoding=utf8",usuario,contraseña); //connecting to the database
			  System.out.println("Connection Successful");
			 } catch (SQLException e) {
			  System.out.println("Some problem in connection");
			  
			  
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			 }
		return conn;
	}

}
