--------------------------------------------------------
--  File created - Tuesday-December-06-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package PROJECT3
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "SYSTEM"."PROJECT3" as
	type ref_cursor is ref cursor;

	function get_students
	return ref_cursor;

	function get_prerequisites
	return ref_cursor;

	function get_logs
	return ref_cursor;

	function get_grades
	return ref_cursor;

	function get_enrollments
	return ref_cursor;

	function get_courses
	return ref_cursor;
  
  function get_courseCredit
	return ref_cursor;

	function get_classes
	return ref_cursor;
  
  PROCEDURE DROPSTUDENTENROLLMENT(v_B# STUDENTS.B#%type,v_classid CLASSES.CLASSID%type);
  
  PROCEDURE deleteStudent(v_B# Students.B#%type);
  
  PROCEDURE list_classstudents(v_classid IN classes.classid%TYPE, v_class OUT SYS_REFCURSOR, v_student OUT SYS_REFCURSOR);
  
  PROCEDURE enroll_student(in_B# IN Students.B#%TYPE, in_classid IN Classes.classid%TYPE);
  
  PROCEDURE showStudentClasses(v_B# IN STUDENTS.B#%type,v_classes OUT SYS_REFCURSOR);
  
  PROCEDURE GET_DEPENDENTCOURSES(v_pre_deptcode IN PREREQUISITES.PRE_DEPT_CODE%type,v_pre_course# IN PREREQUISITES.PRE_COURSE#%type, rc OUT SYS_REFCURSOR);

end;

/
