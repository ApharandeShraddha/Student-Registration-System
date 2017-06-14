--------------------------------------------------------
--  File created - Tuesday-December-06-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Trigger TRIG_DELETE_STUDENTS_LOGS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."TRIG_DELETE_STUDENTS_LOGS" 
  after DELETE on students
  for each row
  begin
     insert into logs values(logs_seq.nextval,user,sysdate,'students','delete',:old.B#);
  end;
/
ALTER TRIGGER "SYSTEM"."TRIG_DELETE_STUDENTS_LOGS" ENABLE;
