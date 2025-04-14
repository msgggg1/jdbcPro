package days01;

public class Ex01 {

	public static void main(String[] args) {
		
		// 1. JDBCClass 폴더 생성
		// 2. 자바설정
		// 3. jdbcPro 프로젝트 생성
		System.out.println("hello world");
		
		// jdbc ->'인터페이스' (엔진 생각)
		// oraexe에 JDBC드라이버 내장
		
		// [ Connection 순서 ]
//		1. Class.forName()으로  JDBC드라이버 로딩 -- 메모리 상에 올림
//		2. DriverManager.getConnection()	->  Connection 객체 생성
//		3. 하고자 하는 작업
//		4. Connection close() -> 연결 종료

	}

}
