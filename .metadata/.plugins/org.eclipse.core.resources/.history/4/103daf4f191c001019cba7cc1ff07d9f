package days04;

import java.sql.Connection;
import java.util.List;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 * @author msg
 * @date 2025. 4. 17. -오전 11:17:13
 * @Subject
 * @Content ***게시판 만들기***
 */
public class Ex04 {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService boardService = new BoardService(dao);
		BoardController boardController = new BoardController(boardService);
		boardController.boardStart();
		
		/*
		 * [게시판의 새글쓰기]
		 * 1. BoardDAO 인터페이스에 추상메서드 추가
		 * 2. BoardDAOImpl 클래스에서 추가된 추상메서드 오버라이딩
		 * 3. BoardService 클래스에서 오버라이딩된 함수 호출하는 서비스 메서드 추가
		 * 4. BoardController에 새글쓰기() 메서드에서 새글 입력 -> BoardService -> BoardDAO -> Oracle DB INSERT
		 * 5. ㄴ 글목록페이지로 이동. 새글이 보이는지 눈에 보여야함. 
		 * 
		 * 
		 * */
		
		/*
		 * [ 게시판 MVC 패턴으로 구현 ]
		 * 오래걸리지만 유지보수가 쉬움
		 * 
		 * 1. days04.board.domain 패키지 선언 - DTO, VO 넣을 것 : 데이터 전달 객체(data transfer object)
		 * 2. days04.board.persistence 패키지 선언 - DAO (DB Access object)
		 * 					ㄴ 끈기, 고집, 영속, 지속성 -> DB에 저장해야 영속성
		 * 3. days04.board.service 패키지 선언 // 회원가입 -> 축하문구, 포인트 지급 등. / 항상 서비스 거침 -> 확장성 위해서
		 * 4. days04.board.controller 패키지 선언 // 모든것들 제어
		 * 5. 
		 * 
		 * [오라클 게시판 Table ] <-DTO -> BoardDAOImpl 구현 <- DTO -> BoardService 구현 <- DTO -> BoardController 구현 <-> 사용자 게시글 목록 요청
		 * 						   List<boardDTO>select()             List<boardDTO> selectService                 뷰로 만들어서 돌려줌
		 * 
		 *  // 단위 테스트 할 필요성
		 * 
		 * 
		 * 
		 * */
		
		 
		
	} // main

} // class
/*
 * create table tbl_cstVSBoard (
  seq NUMBER not null primary key,
  writer varchar2 (20) not null ,
  pwd varchar2 (20) not null ,
  email varchar2 (100),
  title varchar2 (200) not null ,
  writedate date default sysdate,
  readed number default 0,
  tag number(1) not null , -- 0 or 1
  content CLOB null
);
*/
/*
BEGIN
For i IN 1..150 LOOP
 INSERT into tbl_cstVSBoard 
  ( seq,  writer, pwd, email, title, tag,  content)
  values(seq_tblcstVSBoard.nextval, '홍길동' || MOD(i,10), '1234'
            ,'홍길동' || MOD(i,10)||'@sist.co.kr'
            , '더미'|| i
            , 0
            , '더미'|| i
            );
 END LOOP;
END;

select *
from tbl_cstVSBoard;

commit;



   BEGIN
            UPDATE tbl_cstVSBoard
            SET writer = '권태정'
            WHERE MOD(seq, 15) = 4;
            COMMIT;
         END;
         --
          BEGIN
             UPDATE tbl_cstVSBoard
             SET title = '게시판 구현'
             WHERE MOD(seq, 15) IN ( 3, 5, 8 );
             COMMIT;
          END;
 */