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

----[나]
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
------

create or replace procedure up_login
(
      pid IN emp.empno%type
    , ppwd IN emp.ename%type
    , pcheck out number -- 0(성공) 1(ID존재,pwdX), -1(ID존재X)
)
IS
    vpwd emp.ename%type;
BEGIN
    SELECT count(*) INTO pcheck -- 1/0
    FROM emp
    where empno = pid;
    
    IF pcheck = 1 THEN -- ID 존재
         SELECT ename INTO vpwd -- 1/0
         FROM emp
         where empno = pid;
         
         IF ppwd = vpwd THEN pcheck := 0;
         -- ELSE X 이미 1
         END IF;
    ELSE --ID 존재X
        pcheck := -1;
    END IF;
--EXCEPTION
END;

DECLARE
    vcheck number;
BEGIN
    up_login(7369, 'SMITH', vcheck);
    DBMS_OUTPUT.PUT_LINE(vcheck);
END;


---- 커서에 담아서 커서를 리턴
create or replace procedure up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS
begin
    OPEN pdeptcursor FOR
    select *
    from dept;
--exception
end;


-- 
create or replace procedure up_insertdept
(
    pdname dept.dname%type := null
    , ploc dept.loc%type := null
)
IS
    vdeptno dept.deptno%type;
 
begin
    select NVL(MAX(deptno),0) + 10 INTO vdeptno
    FROm dept;
    
    INSERT INTO dept values(vdeptno, pdname, ploc);
    commit;
end;

select *
from dept;
---
create or replace procedure up_deletedept
(
    pdeptno dept.deptno%type 
)
IS 
begin
    
    DELETE FROM dept 
    where deptno = pdeptno;
    commit;
end;
--[나]

create or replace procedure up_updatedept
(
    pdeptno dept.deptno%type 
    ,pdname IN OUT dept.dname%type
    ,ploc IN OUT dept.loc%type
)
IS 
    vdname dept.dname%type;
    vloc dept.loc%type ;
begin
    select dname, loc INTO vdname, vloc
    from dept
    where deptno = pdeptno;

    IF pdname IS NULL THEN pdname := vdname;
    END IF;
    IF ploc IS NULL THEN ploc := vloc;
    END IF;
    
    UPDATE dept
    SET dname = pdname, loc = ploc
    WHERE deptno = pdeptno;
end;

---
create or replace procedure up_updatedept
(
    pdeptno dept.deptno%type 
    ,pdname dept.dname%type := null
    ,ploc dept.loc%type := null
)
IS 
    vdname dept.dname%type;
    vloc dept.loc%type ;
begin
    UPDATE dept
    SET dname = NVL(pdname, dname), loc = NVL(ploc, loc)
    WHERE deptno = pdeptno;
    commit;
end;

SELECT *
from dept;

----
create sequence seq_tblcstVSBoard
nocache;

create table tbl_cstVSBoard (
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

-- 더미 데이터 생성.
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

--
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
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          