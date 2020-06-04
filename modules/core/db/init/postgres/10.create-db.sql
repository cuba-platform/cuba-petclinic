-- begin PETCLINIC_VISIT
create table PETCLINIC_VISIT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ varchar(50) not null,
    ASSIGNED_NURSE_ID uuid,
    PET_ID uuid not null,
    VISIT_START timestamp not null,
    VISIT_END timestamp not null,
    DESCRIPTION varchar(4000),
    TREATMENT_STATUS varchar(50),
    --
    primary key (ID)
)^
-- end PETCLINIC_VISIT
-- begin PETCLINIC_VETERINARIAN
create table PETCLINIC_VETERINARIAN (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    FIRST_NAME varchar(255) not null,
    LAST_NAME varchar(255),
    --
    primary key (ID)
)^
-- end PETCLINIC_VETERINARIAN
-- begin PETCLINIC_OWNER
create table PETCLINIC_OWNER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    FIRST_NAME varchar(255) not null,
    LAST_NAME varchar(255),
    --
    ADDRESS varchar(255) not null,
    CITY varchar(255) not null,
    EMAIL varchar(255),
    TELEPHONE varchar(255),
    --
    primary key (ID)
)^
-- end PETCLINIC_OWNER
-- begin PETCLINIC_PET
create table PETCLINIC_PET (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    NAME varchar(255),
    --
    IDENTIFICATION_NUMBER varchar(255) not null,
    BIRTH_DATE date,
    TYPE_ID uuid,
    OWNER_ID uuid,
    --
    primary key (ID)
)^
-- end PETCLINIC_PET
-- begin PETCLINIC_PET_TYPE
create table PETCLINIC_PET_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    NAME varchar(255),
    --
    COLOR varchar(255),
    --
    primary key (ID)
)^
-- end PETCLINIC_PET_TYPE
-- begin PETCLINIC_SPECIALTY
create table PETCLINIC_SPECIALTY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end PETCLINIC_SPECIALTY
-- begin PETCLINIC_VETERINARIAN_SPECIALTY_LINK
create table PETCLINIC_VETERINARIAN_SPECIALTY_LINK (
    VET_ID uuid,
    SPECIALTY_ID uuid,
    primary key (VET_ID, SPECIALTY_ID)
)^
-- end PETCLINIC_VETERINARIAN_SPECIALTY_LINK
