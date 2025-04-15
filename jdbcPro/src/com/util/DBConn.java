package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.doit.domain.EmpVO;

// 싱글톤(singleton)
public class DBConn {

	private static Connection conn = null;

	private DBConn() {
		// 외부에서 new연산자로 객체생성 불가

	}

	public static synchronized Connection getConnection() {
		if (conn == null) {

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String password = "tiger";
			String className = "oracle.jdbc.driver.OracleDriver";

			try {
				Class.forName(className);
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // if

		return conn;
	}
	
	
	public static synchronized Connection getConnection(String url,String user, String password) { // 오버로딩(중복함수)
		if (conn == null) {
			String className = "oracle.jdbc.driver.OracleDriver";

			try {
				Class.forName(className);
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // if

		return conn;
	}

	
	

	public static void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn = null;  // 초기화 **** -> 다음에 호출해도 안열림
	}
}
