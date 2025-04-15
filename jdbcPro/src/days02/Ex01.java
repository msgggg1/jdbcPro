package days02;

import java.sql.Connection;

import com.util.DBConn;

/**
 * @author msg
 * @date 2025. 4. 15.
 *
 */
public class Ex01 {

	public static void main(String[] args) {
		// 1,2,4 반복적 -> class library
		// com.util.DBConn.java : Connection
		Connection conn = null;
		
		for (int i = 0; i < 5; i++) {
			
			conn = DBConn.getConnection();			
			System.out.println(conn);
//			oracle.jdbc.driver.T4CConnection@6b419da 뒤 코드값 동일 -> 싱글톤
//			oracle.jdbc.driver.T4CConnection@6b419da
//			oracle.jdbc.driver.T4CConnection@6b419da
//			oracle.jdbc.driver.T4CConnection@6b419da
//			oracle.jdbc.driver.T4CConnection@6b419da
		}
		

		// conn.close(); XXXXXXX
		DBConn.close(); // 싱글톤 객체 사용할거라서 
	} // main

} // class
