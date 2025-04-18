package days04.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import days04.board.domain.BoardDTO;

public class BoardDAOImpl implements BoardDAO {

	private Connection conn = null; // setter 통해 주입받음
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private BoardDTO dto = null;

	// 생성자 DI, setter DI - 외부에서 만들어서 공통적으로 받아옴
	// 외부에서 객체 만들어질때 주입
	public BoardDAOImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	//setter DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}


	public Connection getConn() {
		return conn;
	}

	// [2] 페이징 처리 O
	@Override
	public List<BoardDTO> select(int currentPage, int numberPerPage) throws SQLException {
		List<BoardDTO> list = null; 
		/*
		String sql =" "
				+ " from tbl_cstVSBoard "
				+ " order by seq DESC";
		 */
		String sql ="SELECT * "
				+ " FROM ( "
				+ "    SELECT rownum no, t.* "
				+ "    FROM ( "
				+ "        SELECT seq, title, writer, email, writedate, readed "
				+ "        from tbl_cstVSBoard "
				+ "        Order by seq desc       "
				+ "        )t "
				+ ")b "
				+ " where no between ? AND ? ";

		long seq;
		String writer, email, title;   
		Date writedate; 
		int readed;

		int totalRecord = getTotalRecords();
		int start = ( currentPage - 1 ) * numberPerPage + 1;
		int end  = start + ( numberPerPage - 1 ) ;
		if (end > totalRecord ) end = totalRecord;

		this.pstmt = conn.prepareStatement(sql); // 오류 처리 X d/t throws (여기서 처리 안함)
		// BETWEEN ? AND ?
		this.pstmt.setInt(1, start);
		this.pstmt.setInt(2, end);
		this.rs = this.pstmt.executeQuery();

		if (this.rs.next()) {
			list = new ArrayList<BoardDTO>();

			do {
				seq = this.rs.getInt("seq");
				title = this.rs.getString("title");
				writer = this.rs.getString("writer");
				email = this.rs.getString("email");
				writedate = this.rs.getDate("writedate");
				readed = this.rs.getInt("readed");

				this.dto = new BoardDTO().builder()
						.seq(seq)
						.title(title)
						.writer(writer)
						.email(email)
						.writedate(writedate)
						.readed(readed)
						.build();

				list.add(dto);

			} while (this.rs.next());
		} // if

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();
		// DB는 닫지 않음

		return list; // 레코드 없다면 null
	}

	// [ 1 ] 페이징 처리x
	@Override
	public List<BoardDTO> select() throws SQLException {
		List<BoardDTO> list = null; //DB 연동해서 채워넣음
		String sql ="select seq, title, writer, email, writedate, readed "
				+ " from tbl_cstVSBoard "
				+ " order by seq DESC";
		long seq;
		String writer, email, title;   
		Date writedate; 
		int readed;

		this.pstmt = conn.prepareStatement(sql); // 오류 처리 X d/t throws (여기서 처리 안함)
		this.rs = this.pstmt.executeQuery();

		if (this.rs.next()) {
			list = new ArrayList<BoardDTO>();

			do {
				seq = this.rs.getInt("seq");
				title = this.rs.getString("title");
				writer = this.rs.getString("writer");
				email = this.rs.getString("email");
				writedate = this.rs.getDate("writedate");
				readed = this.rs.getInt("readed");

				this.dto = new BoardDTO().builder()
						.seq(seq)
						.title(title)
						.writer(writer)
						.email(email)
						.writedate(writedate)
						.readed(readed)
						.build();

				list.add(dto);

			} while (this.rs.next());
		} // if

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();
		// DB는 닫지 않음

		return list; // 레코드 없다면 null
	}

	@Override
	public int insert(BoardDTO dto) throws SQLException {
		// 실제 DB insert 작업
		String sql = "INSERT INTO tbl_cstvsboard "
				+ " (seq, writer, pwd, email, title, tag, content ) "
				+ "VALUES "
				+ " (seq_tblcstvsboard.NEXTVAL, ?, ?, ?, ?, ?, ? ) ";
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		// writer, pwd, email, title, tag, content
		this.pstmt.setString(1, dto.getWriter());
		this.pstmt.setString(2, dto.getPwd());
		this.pstmt.setString(3, dto.getEmail());
		this.pstmt.setString(4, dto.getTitle());
		this.pstmt.setInt(5, dto.getTag());
		this.pstmt.setString(6, dto.getContent());

		rowCount = this.pstmt.executeUpdate();      
		return rowCount;
	}

	@Override
	public int increaseReaded(long seq) throws SQLException {

		String sql = "UPDATE tbl_cstvsboard "
				+ " SET readed = readed + 1 "
				+ " WHERE seq = ? ";

		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		int rowCount = this.pstmt.executeUpdate();
		if (this.pstmt != null)this.pstmt.close();
		return rowCount;
	}

	@Override
	public BoardDTO view(long seq) throws SQLException {

		this.dto = null;

		String sql = "SELECT seq, title, writer, email, writedate, readed, content "
				+ " FROM tbl_cstvsboard"
				+ " WHERE seq = ? ";

		String writer, email, title, content;   
		Date writedate; 
		int readed;


		this.pstmt = conn.prepareStatement(sql); // 오류 처리 X d/t throws (여기서 처리 안함)
		this.pstmt.setLong(1, seq);
		this.rs = this.pstmt.executeQuery();

		if (this.rs.next()) {

			seq = this.rs.getInt("seq");
			title = this.rs.getString("title");
			writer = this.rs.getString("writer");
			email = this.rs.getString("email");
			writedate = this.rs.getDate("writedate");
			readed = this.rs.getInt("readed");
			content = this.rs.getString("content");

			this.dto = new BoardDTO().builder()
					.seq(seq)
					.title(title)
					.writer(writer)
					.email(email)
					.writedate(writedate)
					.readed(readed)
					.content(content)
					.build();


		} // if

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();

		return dto;
	}

	@Override
	public int delete(long seq) throws SQLException {

		String sql = "DELETE FROM tbl_cstvsboard"
				+ "	WHERE seq = ? ";

		int rowCount = 0 ;

		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();

		if (this.pstmt != null)this.pstmt.close();

		return rowCount;
	}

	@Override
	public int update(BoardDTO dto) throws SQLException {
		// 실제 DB update 작업
		String sql = "UPDATE tbl_cstvsboard "
				+ " SET email = ?, title = ?, content = ?  "
				+ " WHERE seq = ? ";
		int rowCount = 0;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, dto.getEmail());
		this.pstmt.setString(2, dto.getTitle());
		this.pstmt.setString(3, dto.getContent());
		this.pstmt.setLong(4, dto.getSeq());

		rowCount = this.pstmt.executeUpdate();      

		if (this.pstmt != null)this.pstmt.close();

		return rowCount;
	}

	@Override
	public List<BoardDTO> search(String condition, String keyword) throws SQLException {
		List<BoardDTO> list = null; //DB 연동해서 채워넣음
		String sql ="select seq, title, writer, email, writedate, readed "
				+ " from tbl_cstVSBoard "
				+ " WHERE";
		// WHERE 조건절 추가
		// 제목("t") 내용 ("c") 작성자("w") 제목+내용("tc")
		switch (condition) {	
		case "t":
			sql += " REGEXP_LIKE(title , ? , 'i')"; // 칼럼명, 테이블명 ? 사용 불가
			break;
		case "c":	
			sql += " REGEXP_LIKE(content , ? , 'i')";
			break;
		case "w":	
			sql += " REGEXP_LIKE(writer , ? , 'i')";
			break;
		case "tc":	
			sql += " REGEXP_LIKE(title , ? , 'i') OR REGEXP_LIKE(content , ? , 'i')";
			break;
		}

		sql += "order by seq DESC";

		long seq;
		String writer, email, title;   
		Date writedate; 
		int readed;

		this.pstmt = conn.prepareStatement(sql); // 오류 처리 X d/t throws (여기서 처리 안함)
		this.pstmt.setString(1, keyword);
		if (condition.equals("tc")) this.pstmt.setString(2, keyword);
		this.rs = this.pstmt.executeQuery();


		if (this.rs.next()) {
			list = new ArrayList<BoardDTO>();
			do {
				seq = this.rs.getInt("seq");
				title = this.rs.getString("title");
				writer = this.rs.getString("writer");
				email = this.rs.getString("email");
				writedate = this.rs.getDate("writedate");
				readed = this.rs.getInt("readed");

				this.dto = new BoardDTO().builder()
						.seq(seq)
						.title(title)
						.writer(writer)
						.email(email)
						.writedate(writedate)
						.readed(readed)
						.build();

				list.add(dto);

			} while (this.rs.next());
		} // if

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();
		// DB는 닫지 않음

		return list; // 레코드 없다면 null
	}

	@Override
	public int getTotalRecords() throws SQLException {
		String sql = "SELECT COUNT(*) "
				+ " FROM tbl_cstvsboard";
		int totalRecord = 0 ;
		this.pstmt = this.conn.prepareStatement(sql);
		this.rs = this.pstmt.executeQuery();
		if (rs.next()) totalRecord = this.rs.getInt(1);

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();

		return totalRecord;
	}

	@Override
	public int getTotalPages(int numberPerPage) throws SQLException {
		String sql = "SELECT CEIL(COUNT(*)/?) "
				+ " FROM tbl_cstvsboard";
		int totalPages = 0 ;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, numberPerPage);
		this.rs = this.pstmt.executeQuery();
		if (rs.next()) totalPages = this.rs.getInt(1);

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();

		return totalPages;
	}

	// 페이징 처리 O
	@Override
	public List<BoardDTO> search(String condition, String keyword, int currentPage, int numberPerPage)
			throws SQLException {
		List<BoardDTO> list = null; //DB 연동해서 채워넣음

		String sql = 
				"SELECT * "
						+ "FROM ( "
						+ "    SELECT ROWNUM no, t.* "
						+ "    FROM ( "
						+ "        SELECT seq, title, writer, email, writedate, readed "
						+ "        FROM tbl_cstVSBoard ";
		// 검색조건에 맞는 WHERE절 추가.S
		switch (condition) {
		case "t":
			sql += " WHERE REGEXP_LIKE(title, ?, 'i')  "; 
			break;
		case "w":
			sql += " WHERE REGEXP_LIKE(writer, ?, 'i')  "; 
			break;
		case "c":
			sql += " WHERE REGEXP_LIKE(content, ?, 'i')  "; 
			break;
		case "tc":
			sql += " WHERE REGEXP_LIKE(title, ?, 'i')  OR REGEXP_LIKE(content, ?, 'i') "; 
			break; 
		}      
		// 검색조건에 맞는 WHERE절 추가.E      
		sql     += "        ORDER BY seq DESC "
				+ "    ) t  "
				+ ")b  "
				+ "WHERE no BETWEEN ? AND ?";

		long seq;
		String writer, email, title;   
		Date writedate; 
		int readed;

		int totalRecord = getTotalRecords(condition, keyword);
		int start = ( currentPage - 1 ) * numberPerPage + 1;
		int end  = start + ( numberPerPage - 1 ) ;
		if (end > totalRecord ) end = totalRecord;

		this.pstmt = conn.prepareStatement(sql); // 오류 처리 X d/t throws (여기서 처리 안함)
		this.pstmt.setString(1, keyword);
		if (condition.equals("tc")) {
			this.pstmt.setString(2, keyword);
			this.pstmt.setInt(3, start);
			this.pstmt.setInt(4, end);
		} else {
			this.pstmt.setInt(2, start);
			this.pstmt.setInt(3, end);
		}
		this.rs = this.pstmt.executeQuery();


		if (this.rs.next()) {
			list = new ArrayList<BoardDTO>();
			do {
				seq = this.rs.getInt("seq");
				title = this.rs.getString("title");
				writer = this.rs.getString("writer");
				email = this.rs.getString("email");
				writedate = this.rs.getDate("writedate");
				readed = this.rs.getInt("readed");

				this.dto = new BoardDTO().builder()
						.seq(seq)
						.title(title)
						.writer(writer)
						.email(email)
						.writedate(writedate)
						.readed(readed)
						.build();

				list.add(dto);

			} while (this.rs.next());
		} // if

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();
		// DB는 닫지 않음

		return list; // 레코드 없다면 null
	}

	@Override
	public int getTotalRecords(String condition, String keyword) throws SQLException {
		String sql = "SELECT COUNT(*) "
				+ " FROM tbl_cstvsboard"
				+ " WHERE ";
		// WHERE 조건절 추가
		// 제목("t") 내용 ("c") 작성자("w") 제목+내용("tc")
		switch (condition) {	
		case "t":
			sql += " REGEXP_LIKE(title , ? , 'i')"; // 칼럼명, 테이블명 ? 사용 불가
			break;
		case "c":	
			sql += " REGEXP_LIKE(content , ? , 'i')";
			break;
		case "w":	
			sql += " REGEXP_LIKE(writer , ? , 'i')";
			break;
		case "tc":	
			sql += " REGEXP_LIKE(title , ? , 'i') OR REGEXP_LIKE(content , ? , 'i')";
			break;
		}

		int totalRecord = 0 ;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, keyword);
		if (condition.equals("tc")) this.pstmt.setString(2, keyword);

		this.rs = this.pstmt.executeQuery();
		if (rs.next()) totalRecord = this.rs.getInt(1);

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();

		return totalRecord;
	}

	@Override
	public int getTotalPages(int numberPerPage, String condition, String keyword) throws SQLException {
		String sql = "SELECT CEIL(COUNT(*)/?) "
				+ " FROM tbl_cstvsboard"
				+ " WHERE ";
		// WHERE 조건절 추가
		// 제목("t") 내용 ("c") 작성자("w") 제목+내용("tc")
		switch (condition) {	
		case "t":
			sql += " REGEXP_LIKE(title , ? , 'i')"; // 칼럼명, 테이블명 ? 사용 불가
			break;
		case "c":	
			sql += " REGEXP_LIKE(content , ? , 'i')";
			break;
		case "w":	
			sql += " REGEXP_LIKE(writer , ? , 'i')";
			break;
		case "tc":	
			sql += " REGEXP_LIKE(title , ? , 'i') OR REGEXP_LIKE(content , ? , 'i')";
			break;
		}

		int totalPages = 0 ;
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, numberPerPage);
		this.pstmt.setString(2, keyword);
		
		if (condition.equals("tc")) this.pstmt.setString(3, keyword); 
		
		this.rs = this.pstmt.executeQuery();
		if (rs.next()) totalPages = this.rs.getInt(1);

		if (this.rs != null) this.rs.close();
		if (this.pstmt != null)this.pstmt.close();

		return totalPages;
	}




} // class
