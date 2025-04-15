package org.doit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalgradeVO {
	
	private int grade;
	private int losal;
	private int hisal;
	
	private int cnt; // 해당 등급에 속한 사원 수 저장
	
}
