package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Ex04_04 {
	public static void main(String[] args) {
	
		String className = "oracle.jdbc.driver.OracleDriver";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		String sql = "SELECT *"
					+ " FROM emp";
		
		Connection conn = null; // finally에서 close 하기 위해 
		Statement stmt = null;
		ResultSet rs = null; // select 결과물 담는 객체
		
		int empno, mgr, deptno ;
		String ename, job;
		double sal, comm;
		
		 //LocalDate hiredate
		LocalDateTime hiredate;
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			
			stmt = conn.createStatement();
			//stmt.executeUpdate(sql); insert, update, delete DML 문 사용 시
			rs = stmt.executeQuery(sql); // select DQL문 사용

			while (rs.next()) {
				empno = rs.getInt("empno");
				mgr = rs.getInt("mgr");
				deptno = rs.getInt("deptno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				// java.sql.Date 리턴 자료형 
				/* 1. Date -> LpcalDate 변환 코딩
				hiredate = rs.getDate("hiredate");
				*/
				
				//2. 
				hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm"); // null -> 자동으로 0으로 찍힘 / 자바 null값 -> 객체여야 함. 
				System.out.printf("%d\t%s\t%s\t%d\t%tF\t%.2f\t%.2f\t%d\n", empno, ename, job, mgr
																	, hiredate // %s -> 객체.toString으로 출력
																	, sal, comm, deptno);
			}
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // 꼭, 이 위치에 
				stmt.close(); // 일꾼도 close 하는게 좋음. 
				conn.close();
				//System.out.println(conn.isClosed()); // true
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

	} // main
			
	
//	public static void main(String[] args) {
//		/*
//7369   SMITH   CLERK   7902   1980-12-17   800.00   0.00   20
//7499   ALLEN   SALESMAN   7698   1981-02-20   1600.00   300.00   30
//7521   WARD   SALESMAN   7698   1981-02-22   1250.00   500.00   30
//7566   JONES   MANAGER   7839   1981-04-02   2850.00   0.00   20
//7654   MARTIN   SALESMAN   7698   1981-09-28   1250.00   1400.00   30
//7698   BLAKE   MANAGER   7839   1981-05-01   2850.00   0.00   30
//7782   CLARK   MANAGER   7839   1981-06-09   2450.00   0.00   10
//7839   KING   PRESIDENT   0   1981-11-17   5000.00   0.00   0
//7844   TURNER   SALESMAN   7698   1981-09-08   1500.00   0.00   30
//7900   JAMES   CLERK   7698   1981-12-03   950.00   0.00   30
//7902   FORD   ANALYST   7566   1981-12-03   3000.00   0.00   20
//7934   MILLER   CLERK   7782   1982-01-23   1300.00   0.00   10
//		 */
//		
//			
//		String className = "oracle.jdbc.driver.OracleDriver";
//		
//		String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		String user = "scott";
//		String password = "tiger";
//		
//		String sql = "SELECT *"
//					+ " FROM emp";
//		
//		Connection conn = null; // finally에서 close 하기 위해 
//		//java.sql
//		Statement stmt = null;
//		ResultSet rs = null; // select 결과물 담는 객체
//		
//		int empno, mgr, deptno ;
//		String ename, job, hiredate;
//		double sal, comm;
//		
//		try {
//			Class.forName(className);
//			conn = DriverManager.getConnection(url, user, password);
//			// 3. CRUD
//			
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//			
//			while (rs.next()) {
//				empno = rs.getInt("empno");
//				mgr = rs.getInt("mgr");
//				deptno = rs.getInt("deptno");
//				ename = rs.getString("ename");
//				job = rs.getString("job");
//				hiredate = rs.getString("hiredate");
//				sal = rs.getDouble("sal");
//				comm = rs.getDouble("comm");
//				System.out.printf("%d\t%s\t%s\t%d\t%s\t%f\t%f\t%d\n", empno, ename, job, mgr, hiredate, sal, comm, deptno);
//			}
//			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			
//		}
//		
//	} /// main


} // class
