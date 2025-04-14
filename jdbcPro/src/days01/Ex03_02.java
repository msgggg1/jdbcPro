package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.doit.domain.DeptVO;

import oracle.jdbc.driver.OracleDriver;

/**
 * @author user
 * 			ResultSet rs
 * 			while(rs.next()){ 
 * 					부서정보를 ArrayList 컬렉션 객체에 저장
 * 				 }
 * 
 * 			// ArrayList 객체를 출력하는 메서드 통해서 출력
 *
 */
public class Ex03_02 {
	public static void main(String[] args) {
		
String className = "oracle.jdbc.driver.OracleDriver";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		String sql = "SELECT *"
					+ " FROM dept";
		
		Connection conn = null; // finally에서 close 하기 위해 
		//java.sql
		Statement stmt = null;
		ResultSet rs = null; // select 결과물 담는 객체
		
		int deptno ;
		String dname, loc;
		
		DeptVO vo = null; // 하나의 부서정보를 저장할 VO객체
		ArrayList<DeptVO> list = null; // 처음부터 생성x. 읽어오는게 하나라도 있으면 그때 생성
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			// 3. CRUD
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select DQL문 사용
			
			if (rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc);
					list.add(vo);
				} while (rs.next());
			} //if
				
			// 다른 레이어 계층에 list 전달 -> 출력
			dispDeptInfo(list);
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

	private static void dispDeptInfo(ArrayList<DeptVO> list) {
//		if (list.isEmpty()) {
		if (list == null) {
			System.out.println("> SELECT 부서가 없다.");
			return;
		}
		
		Iterator<DeptVO> ir = list.iterator();
		while (ir.hasNext()) {
			DeptVO vo = ir.next();
			System.out.println(vo);
			
		}
	}

} // class
