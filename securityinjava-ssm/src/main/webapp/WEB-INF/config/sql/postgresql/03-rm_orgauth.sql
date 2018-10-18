/*==============================================================*/
/* DBMS name:      MySQL 5.0_qb                                 */
/* Created on:     2011-05-11 22:53:24                          */
/*==============================================================*/


/*
drop table if exists RM_USER;

drop table if exists RM_USER_ONLINE_RECORD;
*/


/*==============================================================*/
/* Table: RM_USER                                               */
/*==============================================================*/
create table RM_USER
(
   ID                   BIGINT not null,
   NAME                 varchar(200) not null,
   LOCK_STATUS          char(1) not null ,
   LOGIN_ID             varchar(200) not null,
   PASSWORD             varchar(200),
   AUTHEN_TYPE          varchar(200),
   ORGANIZATION_ID      varchar(200),
   EMPLOYEE_ID          varchar(50),
   EMAIL                varchar(200),
   ADMIN_TYPE           varchar(2) ,
   DESCRIPTION          varchar(1000),
   AGENT_STATUS         char(1) ,
   LOGIN_STATUS         char(1),
   LAST_LOGIN_DATE      timestamp(0),
   LAST_LOGIN_IP        varchar(50),
   LOGIN_SUM            bigint,
   LAST_CUSTOM_CSS      varchar(200),
   IS_AFFIX             char(1),
   FUNCTION_PERMISSION  varchar(4000) ,
   DATA_PERMISSION      varchar(4000) ,
   CUSTOM1              varchar(200),
   CUSTOM2              varchar(200),
   CUSTOM3              varchar(200),
   CUSTOM4              varchar(200),
   CUSTOM5              varchar(200),
   CUSTOM_XML           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_USER 
ADD COLUMN USABLE_STATUS CHAR(1) DEFAULT '1',
ADD COLUMN MODIFY_DATE  timestamp(0),
ADD COLUMN MODIFY_IP VARCHAR(45),
ADD COLUMN MODIFY_USER_ID BIGINT
;

/*==============================================================*/
/* Index: IDXU_USER_LOGINID                                     */
/*==============================================================*/
create unique index IDXU_USER_LOGINID on RM_USER
(
   LOGIN_ID
);


/*==============================================================*/
/* Table: RM_USER_ONLINE_RECORD                                 */
/*==============================================================*/
create table RM_USER_ONLINE_RECORD
(
   ID                   BIGINT not null,
   USER_ID              BIGINT not null,
   LOGIN_TIME           timestamp(0) not null,
   CLUSTER_NODE_ID      varchar(50),
   LOGIN_SIGN           varchar(200),
   LOGIN_IP             varchar(50),
   LOGIN_UUID           varchar(50),
   LOGOUT_TIME          timestamp(0),
   LOGOUT_TYPE          varchar(50) ,
   ONLINE_TIME          bigint,
   LAST_OPERATION       varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_USER_ONLINE_RECORD 
ADD COLUMN USABLE_STATUS CHAR(1) DEFAULT '1',
ADD COLUMN MODIFY_DATE  timestamp(0),
ADD COLUMN MODIFY_IP VARCHAR(45),
ADD COLUMN MODIFY_USER_ID BIGINT
;

alter table RM_USER_ONLINE_RECORD add constraint FK_REFERM_USER_ONLINERECORD foreign key (USER_ID)
      references RM_USER (ID) on delete restrict on update restrict;

INSERT INTO RM_USER (ID, NAME, LOCK_STATUS, LOGIN_ID, PASSWORD, AUTHEN_TYPE, ORGANIZATION_ID, EMPLOYEE_ID, EMAIL, ADMIN_TYPE, DESCRIPTION, AGENT_STATUS, LOGIN_STATUS, LAST_LOGIN_DATE, LAST_LOGIN_IP, LOGIN_SUM, LAST_CUSTOM_CSS, IS_AFFIX, FUNCTION_PERMISSION, DATA_PERMISSION, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5, CUSTOM_XML, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID)
VALUES (1000201100000000001,'ADMIN','1','admin','111111',NULL,'',NULL,'admin@quickbundle.org','3','',NULL,'1','2018-10-04 23:09:43','0:0:0:0:0:0:0:1',82,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2011-07-14 00:32:16','0:0:0:0:0:0:0:1',NULL);
