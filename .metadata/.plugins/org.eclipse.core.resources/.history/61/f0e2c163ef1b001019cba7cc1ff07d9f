package days04.board.persistence;

import java.sql.SQLException;
import java.util.List;

import days04.board.domain.BoardDTO;

public interface BoardDAO {
		
		// 1. 글 목록 조회 + 페이징 처리 X
		// public abstract 생략
//		BoardDTO [] select()
		List<BoardDTO> select() throws SQLException; // List가 확장성 더 좋은 코딩 / 관리자가 알아야함 -> 일시킨 사람에게 올림

		// 2. 새글 쓰기
		int insert(BoardDTO dto) throws SQLException;
		
		
}
