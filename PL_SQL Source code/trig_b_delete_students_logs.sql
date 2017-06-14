--------------------------------------------------------
--  File created - Tuesday-December-06-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Trigger TRIG_B_DELETE_STUDENTS_LOGS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."TRIG_B_DELETE_STUDENTS_LOGS" 
   before DELETE on students
  for each row
  begin
    delete from enrollments where enrollments.B# =:old.B#;
    
  end;
/
ALTER TRIGGER "SYSTEM"."TRIG_B_DELETE_STUDENTS_LOGS" ENABLE;
