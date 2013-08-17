package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcUtil {

	static{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException e){
			
		}
	}
	public static Connection getConnection(){
		Connection con = null;
		try{
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "java", "1111");
			con.setAutoCommit(false);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close(Connection con){
		try{
			con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt){
		try{
			stmt.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs){
		try{
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void commit(Connection con){
		try{
			con.commit();
		}
		catch(Exception e){
			System.out.println("성공 예외");
			e.getMessage();
			}
	}
	public static void rollback(Connection con){
		try{
			con.rollback();
		}
		catch(Exception e){
			System.out.println("예외");
			e.getMessage();
		}
	}
}
