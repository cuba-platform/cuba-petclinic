update PETCLINIC_VISIT set VISIT_START = current_timestamp where VISIT_START is null ;
alter table PETCLINIC_VISIT alter column VISIT_START set not null ;
update PETCLINIC_VISIT set VISIT_END = current_timestamp where VISIT_END is null ;
alter table PETCLINIC_VISIT alter column VISIT_END set not null ;
