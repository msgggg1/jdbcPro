package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.doit.domain.EmpDeptSalgradeVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

public class Ex06 {

	public static void main(String[] args) {
		// empno, ename, hiredate, pay, dname, grade 출력 : 3개 테이블 조인 --대신--> VO 선언
		
		ArrayList<EmpDeptSalgradeVO> list = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select empno, ename, dname, hiredate, sal+NVL(comm,0) pay, grade"
				+ " from emp e left join dept d on e.deptno = d.deptno"
				+ " join salgrade s ON e.sal BETWEEN s.losal AND s.hisal";
		
		int empno;
		String dname;
		String ename;    
		double pay;     
		LocalDateTime hiredate;
		int grade;
		
		EmpDeptSalgradeVO vo = null;
		
		conn = DBConn.getConnection(); // 1, 2
		
		// 3 CRUD
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); 
			
			if (rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt("empno");
					grade = rs.getInt("grade");
					ename = rs.getString("ename");
					dname = rs.getString("dname");    
					pay = rs.getDouble("pay");  
					hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
					
					vo = new EmpDeptSalgradeVO(empno, ename, pay, hiredate, dname, grade);
					list.add(vo);
				} while (rs.next());
				
				dispEmpInfo(list);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {
					rs.close();
					if (stmt != null) stmt.close();
						DBConn.close(); // 4
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

	} // main

	private static void dispEmpInfo(ArrayList<EmpDeptSalgradeVO> list) {
		if (list == null) {
			System.out.println("사원 존재X");
			return;
		} // if
		
		list.forEach(vo -> System.out.println(vo));
		
		
	}

} // class
