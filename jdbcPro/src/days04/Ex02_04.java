package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author msg
 * @date 2025. 4. 17. -오전 9:26:58
 * @Subject
 * @Content
 */
public class Ex02_04 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("부서번호 입력 ? ");
		int deptno = scanner.nextInt();
		System.out.printf("[%d]\n", deptno); 
		scanner.nextLine();

		System.out.print("부서명 입력 ? ");
		String dname = scanner.nextLine();
		System.out.printf("[%s]\n", dname);      
		//System.out.println( dname.equals("")); // 입력여부 체크

		System.out.print("지역명 입력 ? ");
		String loc = scanner.nextLine();
		System.out.printf("[%s]\n", loc);      
		//System.out.println( loc.equals("")); // 입력여부 체크

		Connection conn = null;
		CallableStatement cstmt = null;
		
//		String sql = "{call up_updatedept( ? , ? , ? )}";
//		String sql = "{call up_updatedept( ? , ? )}";
		String sql = null;
		
		/* 칼럼 수 많아지면
		if (dname.equals("")) {
			sql = "{call up_updatedept( ? , ploc=> ? )}";
		} else if (loc.equals("")) {
			sql = "{call up_updatedept( ? , pdname=> ? )}";
		} else {
			sql = "{call up_updatedept( ? , ? , ? )}";
		}
		*/
		
		sql = "{call up_updatedept( pdeptno => ? ";
		if (!dname.trim().equals("")) sql += ", pdname => ?";
		if (!loc.trim().equals("")) sql += ", ploc => ?";
		sql += ")}";
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql); // 미리 컴파일

			cstmt.setInt(1, deptno);
			if (!dname.trim().equals("")) cstmt.setString(2, dname);
			else 						  cstmt.setString(2, loc);
			
			if (!dname.trim().equals("") && !loc.trim().equals("")) {
				cstmt.setString(3, loc);				
			} // if

			int rowCount = cstmt.executeUpdate(); 

			if (rowCount==1) {
				System.out.println("부서 정보 수정 성공");
			} else {
				System.out.println("부서 정보 수정 실패");
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally { 
			try {
				if( cstmt != null ) cstmt.close();
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		
		/*

		// 부서 수정
		// 수정할 부서 번호 입력 -> 부서명, 지역명만 각각 수정, 둘다 수정

		Scanner scanner = new Scanner(System.in);
		System.out.println("수정할 부서 번호, 부서명, 지역명 입력");

		Connection conn = null;
		CallableStatement cstmt = null;

		String sql = "{call up_updatedept( ? , ? , ? )}";

		// 3 작업
		int deptno = scanner.nextInt();
		String dname = scanner.next();
		String loc = scanner.next();

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql); // 미리 컴파일

			cstmt.setInt(1, deptno);
			cstmt.setString(2, dname);
			cstmt.setString(3, loc);

			int rowCount = cstmt.executeUpdate(); 

			if (rowCount==1) {
				System.out.println("부서 정보 수정 성공");
			} else {
				System.out.println("부서 정보 수정 실패");
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally { 
			try {
				if( cstmt != null ) cstmt.close();
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		 */


	}

}

/* 프로시저
 * create or replace procedure up_updatedept
(
    pdeptno dept.deptno%type 
    ,pdname dept.dname%type := null
    ,ploc dept.loc%type := null
)
IS 
    vdname dept.dname%type;
    vloc dept.loc%type ;
begin
    UPDATE dept
    SET dname = NVL(pdname, dname), loc = NVL(ploc, loc)
    WHERE deptno = pdeptno;
    commit;
end;*/
