package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author msg
 * @date 2025. 4. 17. -오전 9:26:58
 * @Subject
 * @Content
 */
public class Ex02_03 {

	public static void main(String[] args) {
		// 부서 삭제
				Connection conn = null;
				CallableStatement cstmt = null;

				String sql = "{call up_deletedept( ? )}";
				
				// 3 작업
				int deptno = 70;
				DeptVO vo = null;
				
				try {
					conn = DBConn.getConnection();
					cstmt = conn.prepareCall(sql); // 미리 컴파일
					
					cstmt.setInt(1, deptno);
				
					int rowCount = cstmt.executeUpdate(); // 결과물 안받아옴. 출력용 매개변수 받아와서 처리
					
					if (rowCount==1) {
						System.out.println("부서삭제 성공");
					} else {
						System.out.println("부서삭제 실패");
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

	}

}

