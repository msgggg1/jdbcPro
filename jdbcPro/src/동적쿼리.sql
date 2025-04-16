select grade, losal, hisal, count(*) cnt
From salgrade s join emp e on e.sal BETWEEN losal and hisal
group by grade, losal, hisal
Order by grade ASC; 

--
select d.deptno, d.dname, empno, ename, sal
from dept d right join emp e ON d.deptno = e.deptno
                    join salgrade s ON e.sal BETWEEN losal and hisal
where grade = 1;

-- 
select *
from dept;

select *
from dept
where deptno > 0; -- f10 -> 성능좋게 하기 위해 (index)

--
delete from dept
where deptno in (10, 30, 50);
rollback;

SELECT * FROM dept
WHERE dname LIKE('%A%') OR loc LIKE('%A%');

select *
from emp e left Join dept d On e.deptno = d.deptno;

select *
from emp 
where ename LIKE ('%AR%');

select *
from emp;

select *
from dept;


1. 부서번호 검색
select *
from dept
where deptno IN (10,20);
where deptno = 20;
2. 부서명 검색
select *
from dept
where regexp_LIKE(dname, 'sa' , 'i');
where dname LIKE '%SA%';
3. 지역명 검색
select *
from dept
where regexp_LIKE(loc, 'se' , 'i');
where dname LIKE '%SE%';

select * from dept where regexp_LIKE(dname, 's' , 'i') OR regexp_LIKE(loc, 's' , 'i'); -- 쿼리 확인

DESC emp;

-- 회원테이블에서 아이디 중복체크하는 저장 프로시저 선언
-- ( emp 테이블  empno(아이디) )
create or replace procedure up_idcheck
(
    pid IN emp.empno%type -- empno
    , pcheck OUT number -- 0(사용가능) / 1(사용중)
)
IS
BEGIN
    SELECT COUNT(*) INTO pcheck
    FROM emp
    where empno = pid;
--EXCEPTION
END;
-- Procedure UP_IDCHECK이(가) 컴파일되었습니다.
-- 반드시 테스트 후 자바에서 사용
DECLARE 
    vcheck NUMBER(1);
BEGIN
    up_idcheck( 7369, vcheck );
    DBMS_OUTPUT.PUT_LINE(vcheck);
END;


create or replace procedure up_login
(
    pid IN emp.empno%type
    , ppw IN emp.ename%type
    , ptcheck out number
)
IS
    vcheck number;
    ppwcheck number;
    
BEGIN
    SELECT count(*) INTO vcheck
    FROM emp
    where empno = pid;
    
    SELECT count(*) INTO ppwcheck
    FROm emp
    where empno = pid AND ename = ppw;
    
    IF vcheck = 1 AND ppwcheck = 0 then ptcheck := 0;
    ELSIF vcheck = 1 AND ppwcheck = 1 then ptcheck := 1;
    ELSIF vcheck = 0 then ptcheck := -1;
    END IF;
END;

DECLARE 
    vcheck NUMBER(1);
BEGIN
    up_login( 9999, 'Ssdf', vcheck);
    DBMS_OUTPUT.PUT_LINE(vcheck);
END;

select *
from emp;













