--------------------------------------------------------
--  File created - Tuesday-December-06-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Trigger TRIG_ADD_STUDENTS_LOGS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."TRIG_ADD_STUDENTS_LOGS" 
  after INSERT on students
  for each row
  begin
     insert into logs values(logs_seq.nextval,user,sysdate,'students','insert',:new.B#);
  end;
/
ALTER TRIGGER "SYSTEM"."TRIG_ADD_STUDENTS_LOGS" ENABLE;
