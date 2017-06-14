# Student-Registration-System (JAVA , PL/SQL)
An application to perform typical student registration tasks in a university.

Schema Used for Project:

Students(B#, firstname, lastname, status, gpa, email, bdate, deptname)
Courses(dept_code, course#, title)
Course_credit(course#, credits)
Classes(classid, dept_code, course#, sect#, year, semester, limit, class_size)
Enrollments(B#, classid, lgrade)
Grades(lgrade, ngrade)
Prerequisites(dept_code, course#, pre_dept_code, pre_course#)
Logs(logid, who, time, table_name, operation, key_value)

Steps to run Project:

1. Execute PL/SQL code which is inside PL_SQL Source code folder in following order
   1. Project3_declaration.sql
   2. Project3.sql
   3. logs_seq.sql
   4. all triggers

2. Registration is a java code. Go to this folder through terminal

3. Now run following command to compile java code
		javac *.java

4. Execute following command
		java Entry
		
5. This will open window which have all functionality.
    1: Display table names
    2: Show classes where Student is enrolled
    3: Dependent courses
    4: List students enroll for classid(display classid and course title
    5: Enroll Student in to Class
    6: Drop Student enrollment
    7: Delete Student
