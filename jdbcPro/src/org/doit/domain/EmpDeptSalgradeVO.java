package org.doit.domain;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EmpDeptSalgradeVO {// ArrayList<EmpVO>  출력.. / EmpDTO와 나중엔 구분안하고 사용
	
//	private EmpVO evo;
	private int empno; 
	private String ename ;
	private double pay;
	private LocalDateTime hiredate;
	private String dname ;
	private int deptno;

//	private EmpVO evo;
//	private DeptVO dvo;
	// 이렇게 선언할 수도 있다. 
	
	
	
}
