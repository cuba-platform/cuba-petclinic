alter table PETCLINIC_PET add constraint FK_PETCLINIC_PET_TYPE foreign key (TYPE_ID) references PETCLINIC_PET_TYPE(ID);
create index IDX_PETCLINIC_PET_TYPE on PETCLINIC_PET (TYPE_ID);
