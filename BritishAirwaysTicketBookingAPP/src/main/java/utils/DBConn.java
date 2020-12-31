package utils;

import java.sql.*;  
public class DBConn{  
	Connection con;
	Statement stmt;
	String JDBC_URL="";
	String username="";
	String password="";
	public void DBsetup() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		con=DriverManager.getConnection(JDBC_URL,username,password);  
		  
	}
	public ResultSet execteQuery(String query) throws SQLException
	{
		stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery(query);
		return rs;
	}
	public void closeConn() throws SQLException
	{
		con.close(); 
	}
		
}  
