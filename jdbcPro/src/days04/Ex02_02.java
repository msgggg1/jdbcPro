package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.doit.domain.DeptVO;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author msg
 * @date 2025. 4. 17. -오전 9:26:58
 * @Subject
 * @Content
 */
public class Ex02_02 {

	public static void main(String[] args) {
		// 부서추가
		Connection conn = null;
		CallableStatement cstmt = null;

		String sql = "{call up_insertdept( ?, ?)}";
//		String sql = "{call up_insertdept( pdname => ?)}";
//		String sql = "{call up_insertdept( ploc => ?)}";
		
		// 3 작업
		int deptno;
		String dname = "QC", loc = "SEOUL"; // 입력받은 값 가정
		DeptVO vo = null;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql); // 미리 컴파일
			
			cstmt.setString(1, dname);
			cstmt.setString(2, loc);
		
			int rowCount = cstmt.executeUpdate(); // 결과물 안받아옴. 출력용 매개변수 받아와서 처리
			
			if (rowCount==1) {
				System.out.println("부서추가 성공");
			} else {
				System.out.println("부서추가 실패");
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

	} // main

} // class
/*
 * create or replace procedure up_insertdept
(
    pdname dept.dname%type := null
    , ploc dept.loc%type := null
)
IS
    vdeptno dept.deptno%type;
 
begin
    select NVL(MAX(deptno),0) + 10 INTO vdeptno
    FROm dept;
    
    INSERT INTO dept values(vdeptno, pdname, ploc);
    commit;
end;
 * */