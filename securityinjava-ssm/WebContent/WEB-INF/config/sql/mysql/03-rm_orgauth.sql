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
   LOCK_STATUS          char(1) not null comment '$RM_LOCK_STATUS=激活、锁定状态{
            0=锁定,
            1=激活状态
            }',
   LOGIN_ID             varchar(200) not null,
   PASSWORD             varchar(200),
   AUTHEN_TYPE          varchar(200),
   ORGANIZATION_ID      varchar(200),
   EMPLOYEE_ID          varchar(50),
   EMAIL                varchar(200),
   ADMIN_TYPE           varchar(2) comment '$RM_ADMIN_TYPE=用户权限类型{
            0=临时用户,
            1=前台用户,
            2=普通用户,
            9=超级管理员(admin,一般用于数据初始化)
            }',
   DESCRIPTION          varchar(1000),
   AGENT_STATUS         char(1) comment '$RM_AGENT_STATUS=代理状态{
            0=未代理,
            1=已代理
            }',
   LOGIN_STATUS         char(1),
   LAST_LOGIN_DATE      datetime,
   LAST_LOGIN_IP        varchar(50),
   LOGIN_SUM            bigint,
   LAST_CUSTOM_CSS      varchar(200),
   IS_AFFIX             char(1),
   FUNCTION_PERMISSION  varchar(4000) comment '逗号分隔',
   DATA_PERMISSION      varchar(4000) comment '逗号分隔',
   CUSTOM1              varchar(200),
   CUSTOM2              varchar(200),
   CUSTOM3              varchar(200),
   CUSTOM4              varchar(200),
   CUSTOM5              varchar(200),
   CUSTOM_XML           varchar(4000),
   primary key (ID)
)
;

ALTER TABLE RM_USER ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

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
   LOGIN_TIME           datetime not null,
   CLUSTER_NODE_ID      varchar(50),
   LOGIN_SIGN           varchar(200),
   LOGIN_IP             varchar(50),
   LOGIN_UUID           varchar(50),
   LOGOUT_TIME          datetime,
   LOGOUT_TYPE          varchar(50) comment '$RM_LOGOUT_TYPE=注销类型{
            1=正常注销,
            2=超时退出,
            3=被强制登录替换,
            4=被管理员强制退出
            }',
   ONLINE_TIME          bigint,
   LAST_OPERATION       varchar(1000),
   primary key (ID)
)
;

ALTER TABLE RM_USER_ONLINE_RECORD ADD (
USABLE_STATUS CHAR(1) DEFAULT '1',
MODIFY_DATE DATETIME,
MODIFY_IP VARCHAR(45),
MODIFY_USER_ID BIGINT
);

alter table RM_USER_ONLINE_RECORD add constraint FK_REFERM_USER_ONLINERECORD foreign key (USER_ID)
      references RM_USER (ID) on delete restrict on update restrict;

INSERT INTO `RM_USER` (`ID`, `NAME`, `LOCK_STATUS`, `LOGIN_ID`, `PASSWORD`, `AUTHEN_TYPE`, `ORGANIZATION_ID`, `EMPLOYEE_ID`, `EMAIL`, `ADMIN_TYPE`, `DESCRIPTION`, `AGENT_STATUS`, `LOGIN_STATUS`, `LAST_LOGIN_DATE`, `LAST_LOGIN_IP`, `LOGIN_SUM`, `LAST_CUSTOM_CSS`, `IS_AFFIX`, `FUNCTION_PERMISSION`, `DATA_PERMISSION`, `CUSTOM1`, `CUSTOM2`, `CUSTOM3`, `CUSTOM4`, `CUSTOM5`, `CUSTOM_XML`, `USABLE_STATUS`, `MODIFY_DATE`, `MODIFY_IP`, `MODIFY_USER_ID`)
VALUES (1000201100000000001,'ADMIN','1','admin','111111',NULL,'',NULL,'admin@quickbundle.org','3','',NULL,'1','2018-10-04 23:09:43','0:0:0:0:0:0:0:1',82,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','2011-07-14 00:32:16','0:0:0:0:0:0:0:1',NULL);
