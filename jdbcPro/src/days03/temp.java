package days03;

public class temp {

}

private static void 부서검색() {
	// 검색조건 입력 ?  1'b'(부서번호)/2'n'(부서명)/3 'l'(지역명)   "nl"
	// 검색어 입력
	String searchCondition; // 검색 조건
	String searchWord;  // 검색어
	
	System.out.print("> 검색조건, 검색어 입력하세요 ? ");
	searchCondition = scanner.nextLine();
	searchWord = scanner.nextLine();
	
	// 부서조회() 모든 코딩 복사 + 붙이기 
	ResultSet rs = null;
	Statement stmt = null;
	
	String sql = "SELECT * "
			+ "FROM dept "
			+ "WHERE ";
	
	int deptno;
	String dname, loc;
	
	DeptVO vo = null;
	
	// 
	if (searchCondition.equals("b")) {        // 부서번호 검색
		sql += " deptno = "+ searchWord;			
	} else if (searchCondition.equals("n")) { // 부서명 검색
		sql += " REGEXP_LIKE ( dname, '"+searchWord+"', 'i') " ;
	} else if ( searchCondition.equals("l")) { // 지역명 검색
		sql += " REGEXP_LIKE ( loc, '"+searchWord+"', 'i') " ;
	} else if ( searchCondition.equals("nl")) { // 부서명 또는 지역명 검색
		sql += " REGEXP_LIKE ( dname, '"+searchWord+"', 'i') OR REGEXP_LIKE ( loc, '"+searchWord+"', 'i') " ;
	} 
	
	//System.out.println( sql );
	
	try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		if (rs.next()) {
			list = new ArrayList<DeptVO>();
			do {
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				vo = new DeptVO(deptno, dname, loc);
				list.add(vo);
			} while (rs.next());
			
		} // if
		
		부서출력();
		
	} catch (SQLException e) { 
		e.printStackTrace();
	} finally {
		try {
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

