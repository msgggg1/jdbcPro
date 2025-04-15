package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * @author msg
 * @date 2025. 4. 15. -오전 11:24:18
 * @Subject
 * @Content 1. 부서 정보 출력
 * 			2. 부서를 선택하세요 ? 10 엔터
 * 			3. 해당 부서의 사원들 정보를 출력
 */
public class Ex05 {
	  public static void main(String[] args) {
		  
		  String sql = "SELECT *"
					+ " FROM dept";
		
		Connection conn = null; // finally에서 close 하기 위해 
		Statement stmt = null;
		ResultSet rs = null; // select 결과물 담는 객체
		
		int deptno ;
		String dname, loc;
		
		DeptVO dvo = null; // 하나의 부서정보를 저장할 VO객체
		ArrayList<DeptVO> dlist = null; // 처음부터 생성x. 읽어오는게 하나라도 있으면 그때 생성
		
		try {
			
			conn = DBConn.getConnection();
			// 3. CRUD
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select DQL문 사용
			
			if (rs.next()) {
				dlist = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					dvo = new DeptVO(deptno, dname, loc);
					dlist.add(dvo);
				} while (rs.next());
			} //if
				
			// 다른 레이어 계층에 list 전달 -> 출력
			dispDeptInfo(dlist);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); 
				stmt.close(); 
				//System.out.println(conn.isClosed()); // true
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 부서 번호 입력");
		deptno = scanner.nextInt(); // 유효성 검사
		
		// 해당 부서원들 출력
		ArrayList<EmpVO> elist = null;
		
		sql = "select *"
		   + " from emp"
		   + " WHERE deptno = " + deptno; // 날짜, 문자열일때 홑따옴표 앞뒤
		
		int empno, mgr;
		String ename, job;    
		double sal, comm;     
		LocalDateTime hiredate;
		
		EmpVO evo = null;
		
		conn = DBConn.getConnection(); // 1, 2
		
		// 3 CRUD
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select dql문 // 개수, select 개수 알 수 없음. next()뿐
//			리턴값 int : 영향받은 레코드 수 stmt.executeUpdate(sql); insert, update, delete DML 
			
			if (rs.next()) {
				elist = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					mgr = rs.getInt("mgr");
					deptno = rs.getInt("deptno");
					ename = rs.getString("ename");
					job = rs.getString("job");    
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");     
					hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
					
					evo = new EmpVO(empno, mgr, deptno, ename, job, sal, comm, hiredate);
					elist.add(evo);
				} while (rs.next());
				
			}
			dispEmpInfo(elist);
			
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
		
		
	}// main
	
	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		if (list == null) {
			System.out.println("사원 존재X");
			return;
		} // if
		
		// 1. iterator 반복자 사용해서 출력
		// 2. 람다식 사용해서 출력
		list.forEach(vo -> System.out.println(vo));
	}

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
}
		
	/*
	public static void main(String[] args) {
		
ArrayList<EmpVO> list = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select *"
				  + " from emp";
		
		int empno, mgr, deptno;
		String ename, job;    
		double sal, comm;     
		LocalDateTime hiredate;
		
		EmpVO vo = null;
		
		conn = DBConn.getConnection(); // 1, 2
		
		// 3 CRUD
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // select dql문 // 개수, select 개수 알 수 없음. next()뿐
//			리턴값 int : 영향받은 레코드 수 stmt.executeUpdate(sql); insert, update, delete DML 
			
			if (rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					mgr = rs.getInt("mgr");
					deptno = rs.getInt("deptno");
					ename = rs.getString("ename");
					job = rs.getString("job");    
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");     
					hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
					
					vo = new EmpVO(empno, mgr, deptno, ename, job, sal, comm, hiredate);
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
		
		Scanner sc = new Scanner(System.in);
		System.out.print("부서를 선택하세요: ");
		deptno = sc.nextInt();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDeptno() == deptno) {
				System.out.println(list.get(i));
			} // if
		} // for
		
	} // main
	
	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		if (list == null) {
			System.out.println("사원 존재X");
			return;
		} // if

		list.forEach(vo -> System.out.println(vo));
		
		
	}
	*/

 // class
