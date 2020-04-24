alter table PETCLINIC_VISIT add column TYPE_ varchar(50) ^
update PETCLINIC_VISIT set TYPE_ = 'REGULAR_CHECKUP' where TYPE_ is null ;
alter table PETCLINIC_VISIT alter column TYPE_ set not null ;
