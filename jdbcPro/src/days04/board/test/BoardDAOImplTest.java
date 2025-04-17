package days04.board.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;

class BoardDAOImplTest {
	
	Connection conn = null;
	BoardDAO dao = null;

	public BoardDAOImplTest() {
		super();
		this.conn = DBConn.getConnection();
		this.dao = new BoardDAOImpl(conn);
	}

	@Test
	void testBoardDAOSelect() {
//		fail("Not yet implemented");
		List<BoardDTO> list = null;
		try {
			list = this.dao.select();
//			list.forEach(dto -> System.out.println(dto));
			list.forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
		System.out.println("end");
	}

}
