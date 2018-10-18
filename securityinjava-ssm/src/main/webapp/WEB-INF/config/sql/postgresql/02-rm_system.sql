/*==============================================================*/
/* DBMS name:      MySQL 5.0_qb                                 */
/* Created on:     2011-05-11 23:12:07                          */
/*==============================================================*/


/* drop index IDXU_LOCK_BK_CONTENT on RM_LOCK */;
/*
drop table if exists RM_LOCK;

drop table if exists RM_LOG;
*/
/* drop index IDXU_LOGTYPE_BK on RM_LOG_TYPE */;
/*
drop table if exists RM_LOG_TYPE;
*/

/*==============================================================*/
/* Table: RM_LOCK                                               */
/*==============================================================*/
create table RM_LOCK
(
   ID                   BIGINT not null,
   USER_ID              varchar(50),
   BS_KEYWORD           varchar(200) not null,
   LOCK_CONTENT         varchar(200) not null,
   LOCK_DATE            timestamp(0) not null,
   primary key (ID)
)
;

ALTER TABLE RM_LOCK 
ADD COLUMN USABLE_STATUS CHAR(1) DEFAULT '1',
ADD COLUMN MODIFY_DATE  timestamp(0),
ADD COLUMN MODIFY_IP VARCHAR(45),
ADD COLUMN MODIFY_USER_ID BIGINT
;

/*==============================================================*/
/* Index: IDXU_LOCK_BK_CONTENT                                  */
/*==============================================================*/
create unique index IDXU_LOCK_BK_CONTENT on RM_LOCK
(
   BS_KEYWORD,
   LOCK_CONTENT
);

/*==============================================================*/
/* Table: RM_LOG                                                */
/*==============================================================*/
create table RM_LOG
(
   ID                   BIGINT not null,
   LOG_TYPE_ID          BIGINT not null,
   ACTION_DATE          timestamp(0) not null,
   ACTION_IP            varchar(45) not null,
   ACTION_MODULE        varchar(200) not null,
   ACTION_TYPE          varchar(50) not null ,
   OWNER_ORG_ID         varchar(50),
   USER_ID              BIGINT,
   USER_ID_NAME         varchar(200),
   ACTION_UUID          varchar(50),
   CONTENT              varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_LOG 
ADD COLUMN USABLE_STATUS CHAR(1) DEFAULT '1',
ADD COLUMN MODIFY_DATE  timestamp(0),
ADD COLUMN MODIFY_IP VARCHAR(45),
ADD COLUMN MODIFY_USER_ID BIGINT
;

/*==============================================================*/
/* Table: RM_LOG_TYPE                                           */
/*==============================================================*/
create table RM_LOG_TYPE
(
   ID                   BIGINT not null,
   BS_KEYWORD           varchar(200) not null,
   NAME                 varchar(200) not null,
   IS_RECORD            char(1) not null ,
   IS_ALONE_TABLE       char(1) not null ,
   TABLE_NAME           varchar(50),
   PATTERN_VALUE        varchar(1000) not null,
   MATCH_PRIORITY       int not null,
   MAX_LOG_SUM          bigint,
   DESCRIPTION          varchar(1000),
   CUSTOM_XML           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_LOG_TYPE 
ADD COLUMN USABLE_STATUS CHAR(1) DEFAULT '1',
ADD COLUMN MODIFY_DATE  timestamp(0),
ADD COLUMN MODIFY_IP VARCHAR(45),
ADD COLUMN MODIFY_USER_ID BIGINT
;

/*==============================================================*/
/* Index: IDXU_LOGTYPE_BK                                       */
/*==============================================================*/
create unique index IDXU_LOGTYPE_BK on RM_LOG_TYPE
(
   BS_KEYWORD
);
