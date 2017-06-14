--------------------------------------------------------
--  File created - Tuesday-December-06-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package Body PROJECT3
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "SYSTEM"."PROJECT3" as
function get_students
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from students order by B#;
    return rc;
end;

function get_prerequisites
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from prerequisites;
    return rc;
end;

function get_courses
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from courses;
    return rc;
end;

function get_logs
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from logs;
    return rc;
end;

function get_grades
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from grades;
    return rc;
end;

function get_enrollments
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from enrollments;
    return rc;
end;

function get_courseCredit
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from course_credit;
    return rc;
end;

function get_classes
    return ref_cursor is
    rc ref_cursor;
begin
    open rc for
    select * from classes;
    return rc;
end;

--Drop student enrollment
PROCEDURE dropStudentEnrollment (v_B# STUDENTS.B#%type, v_classid CLASSES.CLASSID%type) as
  studentExists number;
  enrollmentExists number;
  classidExists number;
  checkPrerequisite number;
  temp_classSize CLASSES.CLASS_SIZE%type;
  classSize number;
  temp_B# STUDENTS.B#%type;
  temp_classid CLASSES.CLASSID%type;
  enroll_classid ENROLLMENTS.CLASSID%type;
  status boolean;
  raise_prerequisite exception;
  v_prerequisite CLASSES.COURSE#%type;
 
 	BEGIN
  studentExists := 0;
  enrollmentExists := 0;
  classSize :=0;
  classidExists := 0;
  checkPrerequisite :=0;
  status :=false;
  
     --check B# is valid or not
    BEGIN
      select B# into temp_B#  from students where students.B# = v_B#;
      EXCEPTION
      WHEN no_data_found  THEN studentExists := 1;
      if(studentExists = 1) then RAISE_APPLICATION_ERROR(-20000, 'The B# is invalid.');
      return;
       end if;
    END;
    
    --check class is valid or not
    BEGIN
          select classid into temp_classid from classes where classes.CLASSID = v_classid;
          EXCEPTION
          WHEN no_data_found THEN classidExists := 1;
          if(classidExists = 1) then RAISE_APPLICATION_ERROR(-20000, 'The classid is invalid.');
          return;
          end if;
    END;    

--check enrollment		
    BEGIN
      select classid into enroll_classid from enrollments where enrollments.B# = v_B# and enrollments.classid = v_classid;
      EXCEPTION
      WHEN no_data_found THEN enrollmentExists := 1;
       if(enrollmentExists = 1) then RAISE_APPLICATION_ERROR(-20000, 'This student is not enrolled in any classes.');
       return;
      end if;
    END;
    
    
 ---Check Prerequisites
      Begin
        select course# into v_prerequisite from classes where classes.classid= v_classid;
        BEGIN
        
        
        
      select count(*) into checkPrerequisite
        FROM
        COURSES 
      WHERE COURSE# 
      IN
    (SELECT t1.COURSE# 
    FROM PREREQUISITES  t1
    LEFT JOIN PREREQUISITES t2 
    ON 
    t2.COURSE# = t1.PRE_COURSE#
      START WITH t1.PRE_COURSE# =v_prerequisite
      CONNECT BY PRIOR t1.COURSE# = t1.PRE_COURSE#);
                                                                             
        if(checkPrerequisite > 0) then raise raise_prerequisite;
        end if;
        EXCEPTION 
        when raise_prerequisite then 
        RAISE_APPLICATION_ERROR(-20000, 'The drop is not permitted because another class uses it as a prerequisite.');
        END;
        
      end;
 
		--deletion from enrollment table
	begin	
      delete from enrollments where  B# = v_B# and CLASSID = v_classid;return;
      commit;
      dbms_output.put_line('Deleted Successfully');
     
     --check for further enrollment existance
     BEGIN
      select classid into enroll_classid from enrollments where enrollments.B# = v_B# and enrollments.classid = v_classid;
      EXCEPTION
      WHEN no_data_found THEN enrollmentExists := 1;
       if(enrollmentExists = 1) then RAISE_APPLICATION_ERROR(-20000, 'This student is not enrolled in any classes.');
       return;
      end if;
    END;
     
   ----check for class size  
     
        BEGIN
          select CLASS_SIZE into temp_classSize from classes where classid = v_classid;
          Exception
          when no_data_found then classSize :=1;
          if(classSize = 1) THEN RAISE_APPLICATION_ERROR(-20000,'The class now has no students.');
          return;
          END IF;
        END;
  end;
end;

 --Procedure to delete student 
PROCEDURE deleteStudent (v_B# Students.B#%type) AS
   studentExists number;
   temp_B# STUDENTS.B#%type;
   --check B# is valid or not
  Begin
    BEGIN
      select B# into temp_B#  from students where students.B# = v_B#;
      EXCEPTION
      WHEN no_data_found  THEN studentExists := 1;
      if(studentExists = 1) then RAISE_APPLICATION_ERROR(-20000, 'The B# is invalid.');
      return;
       end if;
    END;
  ----delete Student   
    BEGIN
      delete from students where B# = v_B#;
      commit;
      dbms_output.put_line('Student Deleted Successfully.');
    END;

end;

---------Procedure to list classes of students-----------------------------------------
PROCEDURE list_classstudents(v_classid IN classes.classid%TYPE, v_class OUT SYS_REFCURSOR, v_student OUT SYS_REFCURSOR) IS
    count_class number;
    counter number;
    v_title Courses.title%TYPE;
    v_B# Students.B#%TYPE;
    v_firstname Students.lastname%TYPE;
    invalid_classid exception;
    not_found_student exception;
  BEGIN
  
    SELECT COUNT(*)
    INTO counter
    FROM Classes cs JOIN Courses cr ON cs.dept_code = cr.dept_code 
    and cs.course# = cr.course#
    WHERE classid = v_classid;
    IF counter = 0 THEN
      raise invalid_classid;
    ELSE
        OPEN v_class FOR
        SELECT title 
        FROM Classes cs JOIN Courses cr ON cs.dept_code = cr.dept_code 
        and cs.course# = cr.course#
        WHERE classid = v_classid;
            
      SELECT count(*)
      INTO counter
      FROM Enrollments er JOIN Students s ON er.B# = s.B# 
      JOIN Classes cs ON er.classid = cs.classid
      WHERE cs.classid = v_classid;
      
  --------check existence of student---------    
      IF counter = 0 THEN
        raise not_found_student;
      ELSE
        OPEN v_student FOR
        SELECT s.B#, s.firstname 
        FROM Enrollments er JOIN Students s ON er.B# = s.B# 
        JOIN Classes cs ON er.classid = cs.classid
        WHERE cs.classid = v_classid;
        
      END IF;
    END IF;
    EXCEPTION
      WHEN invalid_classid THEN
        RAISE_APPLICATION_ERROR(-20000, 'The classid is invalid.');
        --dbms_output.put_line('The classid is invalid.');
      WHEN not_found_student THEN 
        RAISE_APPLICATION_ERROR(-20000, 'No student has enrolled in the class.');
        --dbms_output.put_line('No student has enrolled in the class.');
      WHEN OTHERS THEN 
        RAISE_APPLICATION_ERROR(-20000, 'Error while executing procedure.');
        --dbms_output.put_line('Error while executing procedure');

END list_classstudents;

----------Procedure to enroll student in to class --------------------
PROCEDURE enroll_student(in_B# IN Students.B#%TYPE, in_classid IN Classes.classid%TYPE) IS

  counter number;
  v_year Classes.year%TYPE;
  v_semester Classes.semester%TYPE;
  v_dept_code Classes.dept_code%TYPE;
  v_course# Classes.course#%TYPE;
  invalid_student exception;
  invalid_class exception;
  class_full exception;
  already_enroll exception;
  overload_classes exception;
  unsatisfied_prerequisites exception;
  
  BEGIN
  
    SELECT count(*)
    INTO counter
    FROM Students
    WHERE in_B# = Students.B#;
    IF counter=0 THEN
      raise invalid_student;
    ELSE
    
      SELECT count(*)
      INTO counter
      FROM Classes
      WHERE in_classid = CLasses.classid;
      IF counter=0 THEN
        raise invalid_class;
      ELSE
      
        SELECT count(*)
        INTO counter
        FROM Classes
        WHERE in_classid = CLasses.classid;
        IF counter=0 THEN
          raise invalid_class;
        ELSE
    
          SELECT count(*)
          INTO counter
          FROM Classes
          WHERE in_classid = CLasses.classid AND CLasses.CLASS_SIZE >= CLasses.LIMIT;
          IF counter>0 THEN
            raise class_full;
          ELSE
          
            SELECT count(*)
            INTO counter
            FROM Enrollments er
            WHERE in_classid = er.classid AND in_B#= er.B#;
            IF counter>0 THEN
              raise already_enroll;
            ELSE
    ---check total number of courses to which student is enrolled in one semester os same year-----------
              SELECT year, semester 
              INTO v_year, v_semester
              FROM Classes
              WHERE classid = in_classid;
              SELECT count(*)
              INTO counter
              FROM Enrollments
              WHERE B# = in_B# and classid IN (SELECT classid FROM Classes WHERE year = v_year and semester = v_semester);
              IF counter > 3 THEN 
                  raise overload_classes;
              ELSE
                IF counter = 3 THEN
                    dbms_output.put_line('You are overloaded'); 
                END IF;
              
    
                SELECT count(*)
                INTO counter
                FROM Enrollments
                WHERE lgrade IN ('A', 'A-', 'B','B+', 'B-', 'C') and classid = (SELECT classid 
                                        FROM Classes 
                                        WHERE (dept_code, course#) = (SELECT pre_dept_code, pre_course#
                                                FROM Prerequisites
                                                WHERE dept_code = v_dept_code 
                                            and course# = v_course#));
                          IF counter <> 0 THEN
                            raise unsatisfied_prerequisites; 
                          ELSE
                            INSERT INTO Enrollments(B#, classid, lgrade) VALUES (in_B#, in_classid, NULL);
                            COMMIT; 
                          END IF;
                        END IF;
                      END IF;
                    END IF;
                  END IF;
                END IF;
              END IF;
  EXCEPTION
      WHEN invalid_student THEN
        RAISE_APPLICATION_ERROR(-20000, 'The B# is invalid.');
        --dbms_output.put_line('The B# is invalid');
      WHEN invalid_class THEN 
        RAISE_APPLICATION_ERROR(-20000, 'The classid is invalid.');
        --dbms_output.put_line('The classid is invalid');
      WHEN class_full THEN 
        RAISE_APPLICATION_ERROR(-20000, 'The class is full.');
        --dbms_output.put_line('The class is full');
      WHEN already_enroll THEN
        RAISE_APPLICATION_ERROR(-20000, 'The student is already in the class.');
        --dbms_output.put_line('The student is already in the class');
      WHEN overload_classes THEN
        RAISE_APPLICATION_ERROR(-20000, 'Students cannot be enrolled in more than four classes in the same semester.');
        --dbms_output.put_line('Students cannot be enrolled in more than four classes in the same semester');
      WHEN unsatisfied_prerequisites THEN
        RAISE_APPLICATION_ERROR(-20000, 'Prerequisite not satisfied.');
        --dbms_output.put_line('Prerequisite not satisfied');
    
                  
  
END enroll_student;


---------------------------------show Student classes----------------
PROCEDURE showStudentClasses(v_B# IN STUDENTS.B#%type, v_classes OUT SYS_REFCURSOR)
   AS
   --local declarations
   classexist char(5);
   Bnumber char(4);
   lettergrade varchar2(2);
   
   no_of_classes number;
   
   no_class_found number;
   no_student_found number;
 --  no_class_found EXCEPTION;
 --  no_student_found EXCEPTION;
   temp number;
   BEGIN
   classexist:= 0;
   Bnumber:=0;
   lettergrade:=null;
   no_class_found:=0;
   
    -- TO CHECK IF STUDENT EXIST  
   BEGIN
   select B# into Bnumber from Students where v_B#=B#; 
   EXCEPTION
   WHEN NO_DATA_FOUND 
   THEN 
   no_student_found:=1; 
   IF (no_student_found=1)
   THEN
   RAISE_APPLICATION_ERROR(-20000,'The B# is invalid.');
   return;
   END IF;
   END;
   
    --to check if the student is enrolled in any class 
   BEGIN  
   select 1 into temp from dual where exists (select 1 from enrollments where v_B#=B#); 
   EXCEPTION
   WHEN NO_DATA_FOUND 
   THEN no_class_found:=1;
   IF (no_class_found=1)
   THEN
   RAISE_APPLICATION_ERROR(-20000, 'The student has not taken any course.');
   RETURN;
   END IF;
   END;
   
  
    
   BEGIN
    open v_classes for
    select CL.CLASSID,CL.DEPT_CODE,CL.COURSE#,CL.SECT#,CL.YEAR,CL.SEMESTER,G.LGRADE,G.NGRADE 
    FROM
    CLASSES CL
    JOIN
    ENROLLMENTS E
    ON CL.CLASSID = E.CLASSID AND E.B#=v_B#
    left JOIN
    GRADES G
    ON G.LGRADE = E.LGRADE;
    
    --return rc;
    END;   
  END;


--------------------------------------------------------------------

-----------------------get Dependent-----------------------------
PROCEDURE GET_DEPENDENTCOURSES(v_pre_deptcode IN PREREQUISITES.PRE_DEPT_CODE%type, v_pre_course# IN PREREQUISITES.PRE_COURSE#%type, rc OUT SYS_REFCURSOR)
AS
--local declaration
pre_coursenum number(3);
pre_deptcodename varchar2(4);
--pre_deptcodename_count number
pre_coursenum_count number(3);
pre_coursenum_not_found number(3);
temp number(3);

BEGIN
pre_coursenum := 0;
pre_coursenum_count:=0;
temp:=0;

BEGIN
select 1
into
temp
from
PREREQUISITES 
where 
v_pre_course#=pre_course# and v_pre_deptcode=PRE_DEPT_CODE;

EXCEPTION
WHEN NO_DATA_FOUND 
THEN 
pre_coursenum_not_found:=1;
IF (pre_coursenum_not_found=1)
THEN
RAISE_APPLICATION_ERROR(-20000, 'No prerequisite found.');
RETURN;
END IF;
END;


BEGIN
open rc for
select CONCAT(DEPT_CODE,COURSE#)
FROM
COURSES 
WHERE COURSE# 
IN
(SELECT t1.COURSE# 
FROM PREREQUISITES  t1
LEFT JOIN PREREQUISITES t2 
ON 
t2.COURSE# = t1.PRE_COURSE#
START WITH t1.PRE_COURSE# = v_pre_course# 
CONNECT BY PRIOR t1.COURSE# = t1.PRE_COURSE#);
END;   
END;
-------------------------------------------------------------------------

end;

/
