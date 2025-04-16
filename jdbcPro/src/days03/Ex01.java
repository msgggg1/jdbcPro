package days03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.EmpVO;

import com.util.DBConn;

public class Ex01 {

	public static void main(String[] args) {
//		[문제] emp 테이블을 사용해서 
//	       1. 부서명으로 검색    f
//	       2. 사원명으로 검색    n
//	       3. 사원번호로 검색    p 
//	       4. 잡(job) 으로 검색  j
//	       5. 입사일자로 검색    h
//	7521	W[AR]D	SALESMAN	7698	81/02/22	1250	500	30


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select *"
				+ " from emp "
				+ " where ";
		
		String condition = "";
		String field = "";
		String keyword = "";
		
		int empno ;
		String ename;
		String job;
		int mgr;
		LocalDateTime hiredate;
		double sal, comm;
		int deptno;
		
		ArrayList<EmpVO> list = null;
		EmpVO vo = null;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 검색 기준 입력");
		condition = scanner.nextLine();
		System.out.println("> 검색어 입력");
		keyword = scanner.nextLine();
		
		if (condition.equals("f")) {
			sql += "deptno LIKE ('%" + keyword + "%')";
		} else if (condition.equals("n")){
			sql += "ename LIKE ('%" + keyword + "%')";
		} else if(condition.equals("p")) {
			sql += "empno LIKE ('%" + keyword + "%')";
		} else if (condition.equals("j")) {
			sql += "job LIKE ('%" + keyword + "%')";
		} else if (condition.equals("h")) {
			sql += "hiredate LIKE ('%" + keyword + "%')";
		} 
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno") ;
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					vo = new EmpVO(empno, mgr, deptno, ename, job, sal, comm, hiredate);
					list.add(vo);					
				} while (rs.next());
				
			} // if
			
			dispEmpInfo(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	} // main

	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		if (list == null) {
			System.out.println("사원 정보가 없습니다.");
		} 
		
		list.forEach(vo -> System.out.println(vo));
		
	}

} // class
