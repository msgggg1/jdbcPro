package days02;

import org.doit.domain.EmpVO;

/**
 * @author msg
 * @date 2025. 4. 15.
 *
 */
public class Ex02 {

	public static void main(String[] args) {
//		EmpVO emp1 = new EmpVO();
//		EmpVO emp2 = new EmpVO(1111, "홍길동"); // 생성자 없음 --> 일일히 오버로딩
		
		EmpVO emp = new EmpVO().builder().empno(1111).ename("홍길동").build(); // @builder때문에 보임
		// 일일히 생성자 오버로딩 할 필요 없음
		System.out.println(emp.toString());
	}

}
