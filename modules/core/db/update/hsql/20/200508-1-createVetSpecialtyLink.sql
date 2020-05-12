create table PETCLINIC_VET_SPECIALTY_LINK (
    VET_ID varchar(36) not null,
    SPECIALTY_ID varchar(36) not null,
    primary key (VET_ID, SPECIALTY_ID)
);
