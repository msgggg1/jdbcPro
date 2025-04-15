package days02;

import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

public class Ex04 {
	public static void main(String[] args) throws SQLException {
		
		// DB 연동 다른 클래스 사용
		 OracleDataSource ods = new OracleDataSource();
		 ods.setURL("jdbc:oracle:thin:scott/tiger@localhost:1521:xe");
		 // ods.setUser(null)
		 Connection conn = ods.getConnection();
		 // Create Oracle DatabaseMetaData object
		 DatabaseMetaData meta = conn.getMetaData();
		 // gets driver info:
		 System.out.println("JDBC driver version is " + meta.getDriverVersion());
	
		 
	}
}
