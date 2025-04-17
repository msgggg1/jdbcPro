package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author msg
 * @date 2025. 4. 16. -오후 4:39:01
 * @Subject
 * @Content		Statement
 * 				PreparedStatement
 * 				[ CallableStatement ]
 * 				
 * 				[ 회원가입 ]
 * 				아이디 : [kenik] [아이디 중복 체크 버튼]
 * 				아이디를 사용할 수 있다.
 * 				사용중인 아이디입니다. 
 * 
 * 				예) emp 테이블 == 회원테이블 가정
 * 				   empno		아이디 가정
 * 
 * 				저장 프로시저 생성
 */
public class Ex06 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 중복 체크할 ID(empno) 입력");
		int id = scanner.nextInt(); // 7369
		
		// String sql = "{call up_idcheck(?,?)}";
		 String sql = "{call up_idcheck(pid => ?, pcheck => ?)}";
		Connection conn = null;
		CallableStatement cstmt = null; 
		
		 try {
			 conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql) ;
			// IN ?, OUT ?
			cstmt.setInt(1, id); // 파라미터 설정
			cstmt.registerOutParameter(2,Types.INTEGER); // 등록 : INTEGER 값이다~
			cstmt.execute(); // return boolean
//			cstmt.executeQuery(); // return resultset -- 이걸로 처리할거아니ㅏㄹ서 아무거나 상관없음
			
			int check  = cstmt.getInt(2); // out
			
			if (check == 0) {
				System.out.println("사용 가능한 ID(empno) 입니다.");
			} else {
				System.out.println("이미 사용중인 ID(empno) 입니다.");
			} // else
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if( cstmt != null )cstmt.close();
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
			
		}


	} // main

} // class

//create or replace procedure up_idcheck
//(
//    pid IN emp.empno%type -- empno
//    , pcheck OUT number -- 0(사용가능) / 1(사용중)
//)
//IS
//BEGIN
//    SELECT COUNT(*) INTO pcheck
//    FROM emp
//    where empno = pid;
//--EXCEPTION
//END;
//-- Procedure UP_IDCHECK이(가) 컴파일되었습니다.
//-- 반드시 테스트 후 자바에서 사용
//DECLARE 
//    vcheck NUMBER(1);
//BEGIN
//    up_idcheck( 7369, vcheck );
//    DBMS_OUTPUT.PUT_LINE(vcheck);
//END;