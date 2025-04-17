package days04.board.test;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

class BoardServiceTest {

	BoardService boardService ;

	public BoardServiceTest() {
		super();
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn); // DI - conn
		this.boardService = new BoardService(dao); // DI - dao
	}



	@Test
	void test() {
		// fail("Not yet implemented");
		List<BoardDTO> list = null;
		try {
			list = this.boardService.selectService();
			//list.forEach(dto -> System.out.println(dto));
			list.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
		System.out.println("end");
	}

}
