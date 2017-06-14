--------------------------------------------------------
--  File created - Tuesday-December-06-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Trigger TRIG_INSERT_ENROLLMRNTS_LOGS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."TRIG_INSERT_ENROLLMRNTS_LOGS" 
  after INSERT on enrollments
  for each row
  begin
     update classes set CLASSES.CLASS_SIZE = CLASSES.CLASS_SIZE+1 where CLASSID= :new.classid;
     insert into logs values(logs_seq.nextval,user,sysdate,'enrollments','insert',:new.B# || ',' ||:new.classid);
  end;
/
ALTER TRIGGER "SYSTEM"."TRIG_INSERT_ENROLLMRNTS_LOGS" ENABLE;
