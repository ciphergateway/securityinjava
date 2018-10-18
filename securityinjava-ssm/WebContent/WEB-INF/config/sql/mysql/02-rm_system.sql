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
   LOCK_DATE            datetime not null,
   primary key (ID)
)
;

ALTER TABLE RM_LOCK ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

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
   ACTION_DATE          datetime not null,
   ACTION_IP            varchar(45) not null,
   ACTION_MODULE        varchar(200) not null,
   ACTION_TYPE          varchar(50) not null comment '$RM_OPERATION_TYPE=操作类型{
            0=新增类,
            1=删除类,
            2=修改类,
            3=查询类,
            9=通用
            }',
   OWNER_ORG_ID         varchar(50),
   USER_ID              BIGINT,
   USER_ID_NAME         varchar(200),
   ACTION_UUID          varchar(50),
   CONTENT              varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_LOG ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Table: RM_LOG_TYPE                                           */
/*==============================================================*/
create table RM_LOG_TYPE
(
   ID                   BIGINT not null,
   BS_KEYWORD           varchar(200) not null,
   NAME                 varchar(200) not null,
   IS_RECORD            char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   IS_ALONE_TABLE       char(1) not null comment '$RM_YES_NOT=是、否{
            0=否,
            1=是
            }',
   TABLE_NAME           varchar(50),
   PATTERN_VALUE        varchar(1000) not null,
   MATCH_PRIORITY       int not null,
   MAX_LOG_SUM          bigint,
   DESCRIPTION          varchar(1000),
   CUSTOM_XML           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_LOG_TYPE ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

/*==============================================================*/
/* Index: IDXU_LOGTYPE_BK                                       */
/*==============================================================*/
create unique index IDXU_LOGTYPE_BK on RM_LOG_TYPE
(
   BS_KEYWORD
);
