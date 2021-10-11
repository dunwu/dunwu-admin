/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.7.31 : Database - dunwu_admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `cas_dept` */

DROP TABLE IF EXISTS `cas_dept`;

CREATE TABLE `cas_dept` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) unsigned DEFAULT NULL COMMENT '上级部门ID',
    `name` varchar(255) NOT NULL COMMENT '部门名称',
    `level` int(5) unsigned NOT NULL DEFAULT '1' COMMENT '部门等级',
    `sequence` int(5) unsigned DEFAULT '1' COMMENT '部门顺序',
    `children_num` int(5) unsigned DEFAULT '0' COMMENT '子部门数量',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_sequence_pid` (`sequence`,`pid`),
    KEY `idx_name` (`name`),
    KEY `idx_enabled` (`is_disabled`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

/*Data for the table `cas_dept` */

insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,0,'研发部',1,1,3,0,NULL,'admin','admin','2020-08-02 14:49:07','2021-10-05 22:39:30');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,0,'产品部',1,2,6,0,NULL,'admin','admin','2019-03-25 09:15:32','2021-10-05 22:39:30');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,1,'后端开发组',2,1,0,0,NULL,'admin','admin','2019-03-25 09:20:44','2021-10-06 00:50:06');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,1,'前端开发组',2,2,0,0,NULL,'admin','admin','2019-03-25 09:52:18','2021-10-05 22:39:30');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,1,'测试组',2,3,1,0,NULL,'admin','admin','2019-03-25 11:04:50','2021-10-05 22:39:30');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,2,'策划组',2,1,0,0,NULL,'admin','admin','2019-03-25 11:04:53','2021-10-05 22:39:30');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,2,'UI设计组',2,2,0,0,NULL,'admin','admin','2020-05-13 22:56:53','2021-10-05 22:39:30');
insert  into `cas_dept`(`id`,`pid`,`name`,`level`,`sequence`,`children_num`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (28,5,'功能测试组',3,1,0,0,NULL,'admin','admin','2021-10-07 22:42:59','2021-10-07 22:42:59');

/*Table structure for table `cas_dept_job_map` */

DROP TABLE IF EXISTS `cas_dept_job_map`;

CREATE TABLE `cas_dept_job_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id` bigint(20) unsigned NOT NULL COMMENT '部门ID',
    `job_id` bigint(20) unsigned NOT NULL COMMENT '职务ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_dept_job` (`dept_id`,`job_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门职务关联表';

/*Data for the table `cas_dept_job_map` */

insert  into `cas_dept_job_map`(`id`,`dept_id`,`job_id`) values (18,1,1);
insert  into `cas_dept_job_map`(`id`,`dept_id`,`job_id`) values (19,1,2);
insert  into `cas_dept_job_map`(`id`,`dept_id`,`job_id`) values (20,1,3);
insert  into `cas_dept_job_map`(`id`,`dept_id`,`job_id`) values (15,3,1);
insert  into `cas_dept_job_map`(`id`,`dept_id`,`job_id`) values (16,3,2);
insert  into `cas_dept_job_map`(`id`,`dept_id`,`job_id`) values (17,3,3);

/*Table structure for table `cas_dept_role_map` */

DROP TABLE IF EXISTS `cas_dept_role_map`;

CREATE TABLE `cas_dept_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint(20) unsigned NOT NULL,
    `dept_id` bigint(20) unsigned NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_menu` (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';

/*Data for the table `cas_dept_role_map` */

/*Table structure for table `cas_job` */

DROP TABLE IF EXISTS `cas_job`;

CREATE TABLE `cas_job` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '职务名称',
    `type` int(5) unsigned NOT NULL COMMENT '职务类型',
    `level` int(5) unsigned NOT NULL COMMENT '职级',
    `sequence` int(5) unsigned DEFAULT '1' COMMENT '职务顺序',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`,`type`),
    KEY `idx_disabled` (`is_disabled`),
    KEY `idx_sequence` (`sequence`),
    KEY `idx_level` (`level`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='职务表';

/*Data for the table `cas_job` */

insert  into `cas_job`(`id`,`name`,`type`,`level`,`sequence`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'初级工程师',1,1,1,0,'初级工程师','admin','admin','2019-03-29 14:52:28','2021-10-10 14:34:23');
insert  into `cas_job`(`id`,`name`,`type`,`level`,`sequence`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'中级工程师',1,2,2,0,'中级工程师','admin','admin','2019-03-29 14:55:51','2021-10-10 14:34:23');
insert  into `cas_job`(`id`,`name`,`type`,`level`,`sequence`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'高级工程师',1,3,3,0,'高级工程师','admin','admin','2019-03-31 13:39:30','2021-10-10 14:34:23');
insert  into `cas_job`(`id`,`name`,`type`,`level`,`sequence`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,'专家',1,4,4,0,'专家','admin','admin','2019-03-31 13:39:43','2021-10-10 14:34:23');
insert  into `cas_job`(`id`,`name`,`type`,`level`,`sequence`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,'高级专家',1,5,5,0,'高级专家','admin','admin','2021-10-10 08:40:22','2021-10-10 14:34:23');
insert  into `cas_job`(`id`,`name`,`type`,`level`,`sequence`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,'资深专家',1,6,6,0,'资深专家','admin','admin','2021-10-10 08:55:47','2021-10-10 14:34:23');

/*Table structure for table `cas_job_role_map` */

DROP TABLE IF EXISTS `cas_job_role_map`;

CREATE TABLE `cas_job_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id` bigint(20) unsigned NOT NULL COMMENT '岗位ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_job_role` (`job_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统岗位/角色关系表';

/*Data for the table `cas_job_role_map` */

/*Table structure for table `cas_menu` */

DROP TABLE IF EXISTS `cas_menu`;

CREATE TABLE `cas_menu` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) unsigned DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count` int(5) unsigned DEFAULT '0' COMMENT '子菜单数目',
    `type` int(5) unsigned DEFAULT NULL COMMENT '菜单类型',
    `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
    `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
    `component` varchar(255) DEFAULT NULL COMMENT '组件',
    `sequence` int(5) unsigned DEFAULT NULL COMMENT '排序',
    `icon` varchar(255) DEFAULT NULL COMMENT '图标',
    `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
    `is_frame` tinyint(1) unsigned DEFAULT '0' COMMENT '是否外链',
    `is_cached` tinyint(1) unsigned DEFAULT '0' COMMENT '缓存',
    `is_hidden` tinyint(1) unsigned DEFAULT '0' COMMENT '隐藏',
    `permission` varchar(255) DEFAULT NULL COMMENT '权限',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_title` (`title`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `key_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

/*Data for the table `cas_menu` */

insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,NULL,7,0,'权限管理',NULL,NULL,1,'system','cas',0,0,0,NULL,NULL,NULL,'2018-12-18 15:11:29',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,3,1,'用户管理','User','cas/user/index',3,'peoples','user',0,0,0,'cas:user:view',NULL,'admin','2018-12-18 15:14:44','2021-10-05 09:30:02');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,1,3,1,'角色管理','Role','cas/role/index',4,'role','role',0,0,0,'cas:role:view',NULL,'admin','2018-12-18 15:16:07','2021-10-05 09:30:12');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,1,3,1,'菜单管理','Menu','cas/menu/index',5,'menu','menu',0,0,0,'cas:menu:view',NULL,'admin','2018-12-18 15:17:28','2021-10-05 09:30:22');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,NULL,5,0,'系统监控',NULL,NULL,4,'monitor','monitor',0,0,0,NULL,NULL,'admin','2018-12-18 15:17:48','2021-10-05 09:28:00');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,121,0,1,'应用日志','Log','sys/LogList',3,'log','log',0,1,0,'sys:log:view','admin','admin','2018-12-18 15:18:26','2021-10-05 09:56:51');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (9,6,0,1,'SQL监控','Sql','monitor/sql/index',18,'sqlMonitor','druid',0,0,0,NULL,NULL,NULL,'2018-12-18 15:19:34',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (10,NULL,5,0,'组件管理',NULL,NULL,6,'zujian','components',0,0,0,NULL,NULL,'admin','2018-12-19 13:38:16','2021-10-05 09:28:18');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (11,10,0,1,'图标库','Icons','components/icons/index',51,'icon','icon',0,0,0,NULL,NULL,NULL,'2018-12-19 13:38:49',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (14,36,0,1,'邮件工具','Email','tool/email/index',35,'email','email',0,0,0,NULL,NULL,NULL,'2018-12-27 10:13:09','2021-10-05 10:00:36');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (15,10,0,1,'富文本','Editor','components/Editor',52,'fwb','tinymce',0,0,0,NULL,NULL,NULL,'2018-12-27 11:58:25',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (18,36,3,1,'存储管理','Storage','tool/storage/index',34,'qiniu','storage',0,0,0,'tool:storage:view',NULL,NULL,'2018-12-31 11:12:15','2021-10-05 10:01:23');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (21,NULL,2,0,'多级菜单',NULL,'',7,'menu','nested',0,0,0,NULL,NULL,'admin','2019-01-04 16:22:03','2021-10-05 09:28:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (22,21,2,0,'二级菜单1',NULL,'',999,'menu','menu1',0,0,0,NULL,NULL,'admin','2019-01-04 16:23:29','2020-06-21 17:27:20');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (23,21,0,1,'二级菜单2',NULL,'nested/menu2/index',999,'menu','menu2',0,0,0,NULL,NULL,NULL,'2019-01-04 16:23:57',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (24,22,0,1,'三级菜单1','Test','nested/menu1/menu1-1',999,'menu','menu1-1',0,0,0,NULL,NULL,NULL,'2019-01-04 16:24:48',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (27,22,0,1,'三级菜单2',NULL,'nested/menu1/menu1-2',999,'menu','menu1-2',0,0,0,NULL,NULL,NULL,'2019-01-07 17:27:32',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (30,36,0,1,'代码生成','CodeIndex','code/generator/index',32,'dev','code',0,1,0,NULL,NULL,NULL,'2019-01-11 15:45:55',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (33,10,0,1,'Markdown','Markdown','components/MarkDown',53,'markdown','markdown',0,0,0,NULL,NULL,NULL,'2019-03-08 13:46:44',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (34,10,0,1,'Yaml编辑器','YamlEdit','components/YamlEdit',54,'dev','yaml',0,0,0,NULL,NULL,NULL,'2019-03-08 15:49:40',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (35,1,3,1,'部门管理','Dept','cas/dept/index',1,'dept','dept',0,0,0,'cas:dept:view',NULL,'admin','2019-03-25 09:46:00','2021-10-08 21:28:55');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (36,NULL,7,0,'系统工具',NULL,'',3,'sys-tools','tool',0,0,0,NULL,NULL,'admin','2019-03-29 10:57:35','2021-10-05 09:27:37');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (37,1,3,1,'岗位管理','Job','cas/job/index',2,'Steve-Jobs','job',0,0,0,'cas:job:view',NULL,'admin','2019-03-29 13:51:18','2021-10-05 09:29:55');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (38,36,0,1,'接口文档','Swagger','tool/swagger/index',36,'swagger','swagger2',0,0,0,'',NULL,NULL,'2019-03-29 19:57:53','2021-10-05 10:01:23');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (39,121,3,1,'字典管理','Dict','sys/Dict',2,'dictionary','dict',0,1,0,'sys:dict:view','admin','admin','2019-04-10 11:49:04','2021-10-05 09:56:51');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (41,6,0,1,'在线用户','OnlineUser','monitor/online/index',10,'Steve-Jobs','online',0,0,0,NULL,NULL,NULL,'2019-10-26 22:08:43',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (44,2,0,2,'用户新增',NULL,'',2,'','',0,0,0,'cas:user:add',NULL,NULL,'2019-10-29 10:59:46','2021-10-05 09:52:33');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (45,2,0,2,'用户编辑',NULL,'',3,'','',0,0,0,'cas:user:edit',NULL,NULL,'2019-10-29 11:00:08','2021-10-05 09:52:33');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (46,2,0,2,'用户删除',NULL,'',4,'','',0,0,0,'cas:user:del',NULL,NULL,'2019-10-29 11:00:23','2021-10-05 09:52:33');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (48,3,0,2,'角色创建',NULL,'',2,'','',0,0,0,'cas:role:add',NULL,NULL,'2019-10-29 12:45:34','2021-10-05 09:53:15');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (49,3,0,2,'角色修改',NULL,'',3,'','',0,0,0,'cas:role:edit',NULL,NULL,'2019-10-29 12:46:16','2021-10-05 09:53:15');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (50,3,0,2,'角色删除',NULL,'',4,'','',0,0,0,'cas:role:del',NULL,NULL,'2019-10-29 12:46:51','2021-10-05 09:53:15');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (52,5,0,2,'菜单新增',NULL,'',2,'','',0,0,0,'cas:menu:add',NULL,NULL,'2019-10-29 12:55:07','2021-10-05 09:53:51');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (53,5,0,2,'菜单编辑',NULL,'',3,'','',0,0,0,'cas:menu:edit',NULL,NULL,'2019-10-29 12:55:40','2021-10-05 09:53:51');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (54,5,0,2,'菜单删除',NULL,'',4,'','',0,0,0,'cas:menu:del',NULL,NULL,'2019-10-29 12:56:00','2021-10-05 09:53:51');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (56,35,0,2,'部门新增',NULL,'',2,'','',0,0,0,'cas:dept:add','admin','admin','2019-10-29 12:57:09','2021-10-05 09:54:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (57,35,0,2,'部门编辑',NULL,'',3,'','',0,0,0,'cas:dept:edit','admin','admin','2019-10-29 12:57:27','2021-10-05 09:54:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (58,35,0,2,'部门删除',NULL,'',4,'','',0,0,0,'cas:dept:del','admin','admin','2019-10-29 12:57:41','2021-10-05 09:54:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (60,37,0,2,'岗位新增',NULL,'',2,'','',0,0,0,'cas:job:add','admin','admin','2019-10-29 12:58:27','2021-10-05 09:55:05');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (61,37,0,2,'岗位编辑',NULL,'',3,'','',0,0,0,'cas:job:edit','admin','admin','2019-10-29 12:58:45','2021-10-05 09:55:05');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (62,37,0,2,'岗位删除',NULL,'',4,'','',0,0,0,'cas:job:del','admin','admin','2019-10-29 12:59:04','2021-10-05 09:55:05');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (64,39,0,2,'字典新增',NULL,'',2,'','',0,0,0,'sys:dict:add','admin','admin','2019-10-29 13:00:17','2021-10-05 09:57:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (65,39,0,2,'字典编辑',NULL,'',3,'','',0,0,0,'sys:dict:edit','admin','admin','2019-10-29 13:00:42','2021-10-05 09:57:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (66,39,0,2,'字典删除',NULL,'',4,'','',0,0,0,'sys:dict:del','admin','admin','2019-10-29 13:00:59','2021-10-05 09:57:24');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (77,18,0,2,'上传文件',NULL,'',2,'','',0,0,0,'tool:storage:add',NULL,NULL,'2019-10-29 13:09:09','2021-10-05 10:02:11');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (78,18,0,2,'文件编辑',NULL,'',3,'','',0,0,0,'tool:storage:edit',NULL,NULL,'2019-10-29 13:09:22','2021-10-05 10:02:11');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (79,18,0,2,'文件删除',NULL,'',4,'','',0,0,0,'tool:storage:del',NULL,NULL,'2019-10-29 13:09:34','2021-10-05 10:02:11');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (80,6,0,1,'服务监控','ServerMonitor','monitor/server/index',14,'codeConsole','server',0,0,0,'monitor:view',NULL,'admin','2019-11-07 13:06:39','2020-05-04 18:20:50');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (82,36,0,1,'生成配置','GeneratorConfig','code/generator/config',33,'dev','code/config/:dbId/:schemaName/:tableName',0,1,1,'',NULL,NULL,'2019-11-17 20:08:56',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (83,10,0,1,'图表库','Echarts','components/Echarts',50,'chart','echarts',0,1,0,'',NULL,NULL,'2019-11-21 09:04:32',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (90,NULL,5,1,'运维管理','Mnt','',5,'mnt','mnt',0,0,0,NULL,NULL,'admin','2019-11-09 10:31:08','2021-10-05 09:28:11');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (92,90,3,1,'服务器配置','ServerDeploy','mnt/ServerList',23,'server','server',0,0,0,'mnt:server:view',NULL,'admin','2019-11-10 10:29:25','2021-10-03 00:14:18');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (93,90,3,1,'应用配置','App','mnt/AppList',22,'app','app',0,0,0,'mnt:app:view',NULL,'admin','2019-11-10 11:05:16','2021-10-03 00:14:06');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (94,90,3,1,'部署管理','Deploy','mnt/DeployList',24,'deploy','deploy',0,0,0,'deploy:view',NULL,'admin','2019-11-10 15:56:55','2021-10-03 00:19:31');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (97,90,1,1,'部署历史','DeployHistory','mnt/DeployHistoryList',25,'backup','deploy/history',0,0,0,'mnt:deployHistory:view',NULL,'admin','2019-11-10 16:49:44','2021-10-03 00:14:49');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (98,36,3,1,'数据库管理','Database','code/database/index',26,'database','code/database',0,0,0,'database:view',NULL,NULL,'2019-11-10 20:40:04',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (102,97,0,2,'删除',NULL,'',999,'','',0,0,0,'deployHistory:del',NULL,NULL,'2019-11-17 09:32:48',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (103,92,0,2,'服务器新增',NULL,'',999,'','',0,0,0,'serverDeploy:add',NULL,NULL,'2019-11-17 11:08:33',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (104,92,0,2,'服务器编辑',NULL,'',999,'','',0,0,0,'serverDeploy:edit',NULL,NULL,'2019-11-17 11:08:57',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (105,92,0,2,'服务器删除',NULL,'',999,'','',0,0,0,'serverDeploy:del',NULL,NULL,'2019-11-17 11:09:15',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (106,93,0,2,'应用新增',NULL,'',999,'','',0,0,0,'app:add',NULL,NULL,'2019-11-17 11:10:03',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (107,93,0,2,'应用编辑',NULL,'',999,'','',0,0,0,'app:edit',NULL,NULL,'2019-11-17 11:10:28',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (108,93,0,2,'应用删除',NULL,'',999,'','',0,0,0,'app:del',NULL,NULL,'2019-11-17 11:10:55',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (109,94,0,2,'部署新增',NULL,'',999,'','',0,0,0,'deploy:add',NULL,NULL,'2019-11-17 11:11:22',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (110,94,0,2,'部署编辑',NULL,'',999,'','',0,0,0,'deploy:edit',NULL,NULL,'2019-11-17 11:11:41',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (111,94,0,2,'部署删除',NULL,'',999,'','',0,0,0,'deploy:del',NULL,NULL,'2019-11-17 11:12:01',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (112,98,0,2,'数据库新增',NULL,'',999,'','',0,0,0,'database:add',NULL,NULL,'2019-11-17 11:12:43',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (113,98,0,2,'数据库编辑',NULL,'',999,'','',0,0,0,'database:edit',NULL,NULL,'2019-11-17 11:12:58',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (114,98,0,2,'数据库删除',NULL,'',999,'','',0,0,0,'database:del',NULL,NULL,'2019-11-17 11:13:14',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (116,36,0,1,'生成预览','Preview','code/generator/preview',999,'java','code/preview/:dbId/:schemaName/:tableName',0,1,1,NULL,NULL,NULL,'2019-11-26 14:54:36',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (117,NULL,1,0,'项目案例',NULL,NULL,8,'demo','demo',0,0,0,NULL,NULL,'admin','2021-12-19 13:38:16','2021-10-05 09:28:29');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (118,117,0,1,'Hello','Hello','demo/index',50,'','demo/index',0,0,0,'demo:view',NULL,'admin','2021-12-19 13:38:16','2021-09-17 21:22:01');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (119,121,0,1,'全局配置','GlobalConfigList','sys/GlobalConfigList',1,'system1','config',0,1,0,'sys:config:view','admin','admin','2021-10-03 19:00:00','2021-10-05 09:30:37');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`is_frame`,`is_cached`,`is_hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (121,NULL,0,1,'系统管理',NULL,NULL,2,'app','sys',0,0,0,NULL,'admin','admin','2021-10-05 09:15:30','2021-10-05 09:27:14');

/*Table structure for table `cas_role` */

DROP TABLE IF EXISTS `cas_role`;

CREATE TABLE `cas_role` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '编码',
    `name` varchar(255) NOT NULL COMMENT '名称',
    `level` int(5) unsigned DEFAULT NULL COMMENT '角色级别',
    `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色';

/*Data for the table `cas_role` */

insert  into `cas_role`(`id`,`code`,`name`,`level`,`data_scope`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'admin','超级管理员',1,'全部',0,'-',NULL,'admin','2018-11-23 11:04:37','2020-08-06 16:10:24');
insert  into `cas_role`(`id`,`code`,`name`,`level`,`data_scope`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'user','普通用户',2,'本级',0,'-',NULL,'admin','2018-11-23 13:09:06','2020-09-05 10:45:12');

/*Table structure for table `cas_role_menu_map` */

DROP TABLE IF EXISTS `cas_role_menu_map`;

CREATE TABLE `cas_role_menu_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_menu` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=334 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

/*Data for the table `cas_role_menu_map` */

insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (288,1,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (78,1,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (287,2,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (79,2,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (284,3,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (286,5,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (285,6,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (80,6,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (291,7,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (81,7,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (293,9,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (82,9,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (292,10,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (83,10,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (289,11,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (84,11,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (290,14,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (85,14,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (296,15,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (86,15,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (297,18,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (87,19,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (295,21,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (88,21,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (294,22,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (89,22,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (302,23,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (90,23,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (301,24,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (91,24,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (299,27,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (92,27,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (298,28,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (300,30,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (93,30,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (308,33,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (94,33,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (307,34,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (95,34,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (304,35,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (303,36,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (96,36,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (306,37,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (305,38,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (312,39,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (313,41,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (309,44,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (311,45,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (310,46,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (317,48,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (319,49,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (318,50,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (314,52,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (316,53,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (315,54,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (323,56,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (325,57,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (324,58,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (320,60,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (322,61,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (321,62,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (326,64,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (328,65,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (327,66,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (333,73,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (332,74,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (329,75,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (331,77,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (330,78,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (258,79,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (257,80,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (97,80,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (259,82,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (98,82,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (256,83,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (99,83,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (263,90,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (260,92,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (262,93,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (261,94,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (266,97,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (265,98,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (264,102,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (272,103,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (271,104,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (274,105,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (273,106,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (268,107,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (267,108,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (270,109,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (269,110,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (279,111,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (278,112,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (281,113,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (280,114,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (275,116,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (100,116,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (277,117,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (276,118,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (282,119,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (283,121,1);

/*Table structure for table `cas_user` */

DROP TABLE IF EXISTS `cas_user`;

CREATE TABLE `cas_user` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门ID',
    `job_id` bigint(20) unsigned DEFAULT NULL COMMENT '岗位ID',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
    `gender` varchar(2) DEFAULT NULL COMMENT '性别',
    `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
    `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email` (`email`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    KEY `key_enabled` (`is_disabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

/*Data for the table `cas_user` */

insert  into `cas_user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`pwd_reset_time`,`is_disabled`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,1,1,'admin','管理员','男','18888888888','201507802@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa',NULL,0,'admin','admin','2020-05-03 16:38:31','2021-10-07 23:47:10');
insert  into `cas_user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`pwd_reset_time`,`is_disabled`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,2,3,'test','测试','男','15199999999','231@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK',NULL,0,'admin','admin','2020-05-05 11:15:49','2021-10-07 23:47:10');

/*Table structure for table `cas_user_dept_map` */

DROP TABLE IF EXISTS `cas_user_dept_map`;

CREATE TABLE `cas_user_dept_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `dept_id` bigint(20) unsigned NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_dept` (`user_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户部门关联表';

/*Data for the table `cas_user_dept_map` */

/*Table structure for table `cas_user_role_map` */

DROP TABLE IF EXISTS `cas_user_role_map`;

CREATE TABLE `cas_user_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

/*Data for the table `cas_user_role_map` */

insert  into `cas_user_role_map`(`id`,`user_id`,`role_id`) values (1,1,1);
insert  into `cas_user_role_map`(`id`,`user_id`,`role_id`) values (6,2,1);
insert  into `cas_user_role_map`(`id`,`user_id`,`role_id`) values (5,2,2);

/*Table structure for table `code_column_config` */

DROP TABLE IF EXISTS `code_column_config`;

CREATE TABLE `code_column_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id` bigint(20) unsigned DEFAULT NULL COMMENT '所属表的ID',
    `schema_name` varchar(255) NOT NULL COMMENT 'Schema名称',
    `table_name` varchar(255) NOT NULL COMMENT 'Table名称',
    `field_name` varchar(255) NOT NULL COMMENT '字段名称',
    `comment` varchar(255) DEFAULT NULL COMMENT '字段注释',
    `type` varchar(255) DEFAULT NULL COMMENT '字段数据类型',
    `java_type` varchar(255) DEFAULT NULL COMMENT '字段Java类型',
    `key_type` varchar(255) DEFAULT NULL COMMENT '键类型',
    `not_null` bit(1) DEFAULT NULL COMMENT '不允许为空',
    `property_name` varchar(255) DEFAULT NULL COMMENT '字段别名',
    `label_name` varchar(255) DEFAULT NULL COMMENT '字段Label',
    `enable_form` bit(1) DEFAULT NULL COMMENT '允许表单',
    `enable_list` bit(1) DEFAULT NULL COMMENT '允许列表',
    `enable_query` bit(1) DEFAULT NULL COMMENT '允许查询',
    `enable_sort` bit(1) DEFAULT NULL COMMENT '允许排序',
    `enable_validate` bit(1) DEFAULT NULL COMMENT '允许校验',
    `form_type` varchar(255) DEFAULT NULL COMMENT '表单类型',
    `list_type` varchar(255) DEFAULT NULL COMMENT '列表类型',
    `query_type` varchar(255) DEFAULT NULL COMMENT '查询类型',
    `sort_type` varchar(255) DEFAULT NULL COMMENT '排序类型',
    `validate_type` varchar(255) DEFAULT NULL COMMENT '校验类型',
    `date_type` varchar(255) DEFAULT NULL COMMENT '时间类型',
    `date_pattern` varchar(255) DEFAULT NULL COMMENT '时间格式',
    `dict_name` varchar(255) DEFAULT NULL COMMENT '字典名称',
    `fill` varchar(255) DEFAULT NULL COMMENT '@TableField填充属性',
    `extra` varchar(255) DEFAULT NULL COMMENT '扩展属性',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_schema_table_field` (`schema_name`,`table_name`,`field_name`)
) ENGINE=InnoDB AUTO_INCREMENT=340 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-字段级别配置';

/*Data for the table `code_column_config` */

/*Table structure for table `code_database` */

DROP TABLE IF EXISTS `code_database`;

CREATE TABLE `code_database` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '数据库名称',
    `host` varchar(255) NOT NULL COMMENT 'Host',
    `port` int(10) unsigned NOT NULL COMMENT '端口号',
    `jdbc_url` varchar(255) NOT NULL COMMENT 'jdbc地址',
    `username` varchar(255) NOT NULL COMMENT '账号',
    `password` varchar(255) NOT NULL COMMENT '密码',
    `schema_name` varchar(255) DEFAULT NULL COMMENT 'Schema名称',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据库管理';

/*Data for the table `code_database` */

insert  into `code_database`(`id`,`name`,`host`,`port`,`jdbc_url`,`username`,`password`,`schema_name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'dunwu_admin','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_admin','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50');
insert  into `code_database`(`id`,`name`,`host`,`port`,`jdbc_url`,`username`,`password`,`schema_name`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'dunwu_cas','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_cas?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_cas','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50');

/*Table structure for table `code_global_config` */

DROP TABLE IF EXISTS `code_global_config`;

CREATE TABLE `code_global_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `enable_permission` bit(1) DEFAULT NULL COMMENT '开启权限校验',
    `enable_override` bit(1) DEFAULT NULL COMMENT '开启文件覆盖模式',
    `enable_swagger` bit(1) DEFAULT NULL COMMENT '开启Swagger',
    `author` varchar(255) DEFAULT NULL COMMENT '作者',
    `output_dir` varchar(255) DEFAULT NULL COMMENT '输出路径',
    `backend_path` varchar(255) DEFAULT NULL COMMENT '后端代码路径',
    `frontend_path` varchar(255) DEFAULT NULL COMMENT '前端代码路径',
    `package_path` varchar(255) DEFAULT NULL COMMENT '包路径',
    `id_type` varchar(255) DEFAULT NULL COMMENT '主键类型',
    `date_type` varchar(255) DEFAULT NULL COMMENT '时间类型',
    `date_pattern` varchar(255) DEFAULT NULL COMMENT '时间格式化',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-全局配置';

/*Data for the table `code_global_config` */

insert  into `code_global_config`(`id`,`enable_permission`,`enable_override`,`enable_swagger`,`author`,`output_dir`,`backend_path`,`frontend_path`,`package_path`,`id_type`,`date_type`,`date_pattern`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'','','','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');

/*Table structure for table `code_table_config` */

DROP TABLE IF EXISTS `code_table_config`;

CREATE TABLE `code_table_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `db_id` bigint(20) unsigned NOT NULL COMMENT '数据库ID',
    `schema_name` varchar(255) NOT NULL COMMENT 'Schema名称',
    `table_name` varchar(255) NOT NULL COMMENT 'Table名称',
    `comment` varchar(255) DEFAULT NULL COMMENT 'Table注释',
    `enable_permission` bit(1) DEFAULT NULL COMMENT '开启权限校验',
    `enable_override` bit(1) DEFAULT NULL COMMENT '开启文件覆盖模式',
    `enable_swagger` bit(1) DEFAULT NULL COMMENT '开启Swagger2',
    `author` varchar(255) DEFAULT NULL COMMENT '作者',
    `output_dir` varchar(255) DEFAULT NULL COMMENT '输出路径',
    `backend_path` varchar(255) DEFAULT NULL COMMENT '后端代码路径',
    `frontend_path` varchar(255) DEFAULT NULL COMMENT '前端代码路径',
    `package_path` varchar(255) DEFAULT NULL COMMENT '包路径',
    `id_type` varchar(255) DEFAULT NULL COMMENT '主键类型',
    `date_type` varchar(255) DEFAULT NULL COMMENT '时间类型',
    `date_pattern` varchar(255) DEFAULT NULL COMMENT '时间格式',
    `enable_form` bit(1) DEFAULT NULL COMMENT '允许表单',
    `enable_list` bit(1) DEFAULT NULL COMMENT '允许列表',
    `enable_query` bit(1) DEFAULT NULL COMMENT '允许查询',
    `enable_sort` bit(1) DEFAULT NULL COMMENT '允许排序',
    `enable_validate` bit(1) DEFAULT NULL COMMENT '允许校验',
    `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
    `table_prefix` varchar(255) DEFAULT NULL COMMENT '表前缀',
    `api_base_url` varchar(255) DEFAULT NULL COMMENT 'REST接口根路径',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_schema_table` (`schema_name`,`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-表级别配置';

/*Data for the table `code_table_config` */

insert  into `code_table_config`(`id`,`db_id`,`schema_name`,`table_name`,`comment`,`enable_permission`,`enable_override`,`enable_swagger`,`author`,`output_dir`,`backend_path`,`frontend_path`,`package_path`,`id_type`,`date_type`,`date_pattern`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`module_name`,`table_prefix`,`api_base_url`,`create_by`,`update_by`,`create_time`,`update_time`) values (19,1,'dunwu_admin','cas_role','角色','','','','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss','','','','','','cas','cas_','role','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
insert  into `code_table_config`(`id`,`db_id`,`schema_name`,`table_name`,`comment`,`enable_permission`,`enable_override`,`enable_swagger`,`author`,`output_dir`,`backend_path`,`frontend_path`,`package_path`,`id_type`,`date_type`,`date_pattern`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`module_name`,`table_prefix`,`api_base_url`,`create_by`,`update_by`,`create_time`,`update_time`) values (20,1,'dunwu_admin','cas_dept_job_map','角色部门关联','','','','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zp//zproject//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss','','','','','','cas','cas_','DeptJobMap','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');

/*Table structure for table `hello` */

DROP TABLE IF EXISTS `hello`;

CREATE TABLE `hello` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '名字',
    `age` smallint(3) NOT NULL COMMENT '年龄',
    `avatar` varchar(255) NOT NULL COMMENT '头像',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='测试';

/*Data for the table `hello` */

insert  into `hello`(`id`,`name`,`age`,`avatar`,`create_time`) values (1,'fasdfas',12,'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','2021-03-01 21:12:57');
insert  into `hello`(`id`,`name`,`age`,`avatar`,`create_time`) values (2,'abc',11,'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','2021-03-15 21:50:16');

/*Table structure for table `mnt_app` */

DROP TABLE IF EXISTS `mnt_app`;

CREATE TABLE `mnt_app` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) DEFAULT NULL COMMENT '应用名称',
    `upload_path` varchar(255) DEFAULT NULL COMMENT '上传路径',
    `deploy_path` varchar(255) DEFAULT NULL COMMENT '部署路径',
    `backup_path` varchar(255) DEFAULT NULL COMMENT '备份路径',
    `port` int(10) unsigned DEFAULT NULL COMMENT '应用端口',
    `start_script` varchar(4000) DEFAULT NULL COMMENT '启动脚本',
    `deploy_script` varchar(4000) DEFAULT NULL COMMENT '部署脚本',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用配置表';

/*Data for the table `mnt_app` */

insert  into `mnt_app`(`id`,`name`,`upload_path`,`deploy_path`,`backup_path`,`port`,`start_script`,`deploy_script`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'dunwu-mnt','D:\\upload','D:\\deploy','D:\\backup',8080,'1','1','1','admin','admin','2021-10-02 17:39:19','2021-10-02 17:39:19');

/*Table structure for table `mnt_deploy` */

DROP TABLE IF EXISTS `mnt_deploy`;

CREATE TABLE `mnt_deploy` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_id` bigint(20) unsigned DEFAULT NULL COMMENT '应用编号',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署配置表';

/*Data for the table `mnt_deploy` */

insert  into `mnt_deploy`(`id`,`app_id`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,'dunwu 运维服务','admin','admin','2021-10-03 07:11:51','2021-10-03 07:22:05');

/*Table structure for table `mnt_deploy_history` */

DROP TABLE IF EXISTS `mnt_deploy_history`;

CREATE TABLE `mnt_deploy_history` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `deploy_id` bigint(20) unsigned DEFAULT NULL COMMENT '部署编号',
    `app_name` varchar(255) NOT NULL COMMENT '应用名称',
    `deploy_date` datetime NOT NULL COMMENT '部署日期',
    `deploy_user` varchar(50) NOT NULL COMMENT '部署用户',
    `ip` varchar(20) NOT NULL COMMENT '服务器IP',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署历史表';

/*Data for the table `mnt_deploy_history` */

/*Table structure for table `mnt_deploy_server_map` */

DROP TABLE IF EXISTS `mnt_deploy_server_map`;

CREATE TABLE `mnt_deploy_server_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `deploy_id` bigint(20) unsigned NOT NULL COMMENT '部署ID',
    `server_id` bigint(20) unsigned NOT NULL COMMENT '服务ID',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_deploy_server_id` (`deploy_id`,`server_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用和服务关联表';

/*Data for the table `mnt_deploy_server_map` */

insert  into `mnt_deploy_server_map`(`id`,`deploy_id`,`server_id`) values (3,2,1);
insert  into `mnt_deploy_server_map`(`id`,`deploy_id`,`server_id`) values (4,2,2);

/*Table structure for table `mnt_server` */

DROP TABLE IF EXISTS `mnt_server`;

CREATE TABLE `mnt_server` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(100) DEFAULT NULL COMMENT '名称',
    `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
    `port` int(10) unsigned DEFAULT NULL COMMENT '端口',
    `account` varchar(50) DEFAULT NULL COMMENT '账号',
    `password` varchar(100) DEFAULT NULL COMMENT '密码',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_ip` (`ip`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='服务器配置表';

/*Data for the table `mnt_server` */

insert  into `mnt_server`(`id`,`name`,`ip`,`port`,`account`,`password`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'测试服务器一','127.0.0.1',8080,'root','root',NULL,'admin','admin','2021-10-02 19:36:16','2021-10-02 19:36:16');
insert  into `mnt_server`(`id`,`name`,`ip`,`port`,`account`,`password`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'测试服务器二','127.0.0.2',8081,'root','root',NULL,'admin','admin','2021-10-02 19:51:55','2021-10-02 19:51:55');

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '字典编码',
    `name` varchar(255) NOT NULL COMMENT '字典名称',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`code`,`name`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'disabled_status','是否禁用状态',0,'是否禁用状态','admin','admin','2019-10-27 20:31:36','2021-10-10 12:11:37');
insert  into `sys_dict`(`id`,`code`,`name`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'job_type','职务类型',0,NULL,'admin','admin','2021-10-10 12:24:55','2021-10-10 12:27:02');
insert  into `sys_dict`(`id`,`code`,`name`,`is_disabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'job_profession_level','职级',0,NULL,'admin','admin','2021-10-10 14:10:57','2021-10-10 14:10:57');

/*Table structure for table `sys_dict_option` */

DROP TABLE IF EXISTS `sys_dict_option`;

CREATE TABLE `sys_dict_option` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dict_id` bigint(20) unsigned DEFAULT NULL COMMENT '字典id',
    `code` varchar(255) NOT NULL COMMENT '字典选项编码',
    `name` varchar(255) NOT NULL COMMENT '字典选项名称',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_dict_id` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典选项';

/*Data for the table `sys_dict_option` */

insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,1,'false','启用','admin','admin','2019-10-27 20:31:36','2021-10-05 18:48:04');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,'true','禁用','admin','admin','2019-10-27 20:31:36','2021-10-05 18:48:04');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,2,'1','专业岗位','admin','admin','2021-10-10 12:25:46','2021-10-10 12:26:54');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,2,'2','管理岗位','admin','admin','2021-10-10 12:34:36','2021-10-10 12:34:58');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,3,'1','初级','admin','admin','2021-10-10 14:14:12','2021-10-10 14:14:12');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,3,'2','中级','admin','admin','2021-10-10 14:14:19','2021-10-10 14:14:19');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,3,'3','高级','admin','admin','2021-10-10 14:14:25','2021-10-10 14:14:25');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (8,3,'4','专家','admin','admin','2021-10-10 14:14:32','2021-10-10 14:14:32');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (9,3,'5','高级专家','admin','admin','2021-10-10 14:14:45','2021-10-10 14:14:45');
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (10,3,'6','资深专家','admin','admin','2021-10-10 14:15:02','2021-10-10 14:15:02');

/*Table structure for table `sys_global_config` */

DROP TABLE IF EXISTS `sys_global_config`;

CREATE TABLE `sys_global_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_code` varchar(255) DEFAULT NULL COMMENT '应用编码',
    `module_code` varchar(255) DEFAULT NULL COMMENT '模块编码',
    `namespace` varchar(255) DEFAULT NULL COMMENT '命名空间',
    `code` varchar(255) NOT NULL COMMENT '配置项编码',
    `name` varchar(255) NOT NULL COMMENT '配置项配置名称',
    `value` varchar(1000) DEFAULT NULL COMMENT '配置项值',
    `default_value` varchar(1000) DEFAULT NULL COMMENT '配置项默认值',
    `value_type` varchar(50) DEFAULT NULL COMMENT '配置项值类型',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_composite` (`code`,`namespace`,`module_code`,`app_code`),
    KEY `idx_update_time` (`update_time`),
    KEY `idx_is_disabled` (`is_disabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统全局配置表';

/*Data for the table `sys_global_config` */

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `level` varchar(10) DEFAULT NULL COMMENT '日志级别',
    `biz_type` varchar(100) DEFAULT NULL COMMENT '业务类型',
    `message` text COMMENT '日志消息',
    `exception_message` text COMMENT '异常信息，只有日志级别为ERROR时才有值',
    `class_name` varchar(255) NOT NULL COMMENT '操作的类名',
    `method_name` varchar(255) NOT NULL COMMENT '操作的方法名',
    `params` text COMMENT '被调用方法的参数',
    `operate_type` varchar(100) DEFAULT NULL COMMENT '操作类型',
    `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作者ID',
    `operator_name` varchar(100) DEFAULT NULL COMMENT '操作者用户名',
    `server_ip` varchar(100) DEFAULT NULL COMMENT '服务端IP地址',
    `client_ip` varchar(100) DEFAULT NULL COMMENT '客户端IP地址',
    `client_location` varchar(100) DEFAULT NULL COMMENT '客户端地理位置',
    `client_device` varchar(100) DEFAULT NULL COMMENT '客户端设备',
    `request_time` bigint(20) unsigned DEFAULT NULL COMMENT 'HTTP请求的耗时',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_class_method` (`method_name`,`class_name`),
    KEY `idx_level` (`level`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志记录';

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`level`,`biz_type`,`message`,`exception_message`,`class_name`,`method_name`,`params`,`operate_type`,`operator_id`,`operator_name`,`server_ip`,`client_ip`,`client_location`,`client_device`,`request_time`,`create_time`) values (1,'INFO','系统用户','【系统用户】admin更新 cas_user 表中 id = 2 的记录，内容为：UserDto(id=2, deptId=1, jobId=2, username=test, nickname=测试, gender=男, phone=15199999999, email=231@qq.com, avatar=http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png, password=$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK, disabled=false, createBy=admin, updateBy=admin, pwdResetTime=null, createTime=2020-05-05T11:15:49, updateTime=2021-10-07T23:47:10, dept=DeptDto(id=1, pid=0, name=研发部, level=1, sequence=1, childrenNum=3, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=2020-08-02T14:49:07, updateTime=2021-10-05T22:39:30, label=null, hasChildren=false, children=null), job=JobDto(id=2, name=中级工程师, type=1, level=2, sequence=2, disabled=false, note=中级工程师, createBy=admin, updateBy=admin, createTime=2019-03-29T14:55:51, updateTime=2021-10-10T14:34:23, dept=null, roles=null), roles=[RoleDto(id=2, code=null, name=null, level=null, dataScope=null, disabled=null, note=null, createBy=null, updateBy=null, createTime=null, updateTime=null, menus=null, depts=null)], roleCodes=[user], isAdmin=false)',NULL,'io.github.dunwu.module.cas.controller.UserController','edit','{\"gender\":\"男\",\"roles\":[{\"id\":2}],\"deptId\":1,\"updateTime\":1633621630000,\"roleCodes\":[\"user\"],\"avatar\":\"http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png\",\"dept\":{\"level\":1,\"hasChildren\":false,\"pid\":0,\"updateTime\":1633444770000,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1596350947000,\"childrenNum\":3,\"name\":\"研发部\",\"disabled\":false,\"id\":1},\"isAdmin\":false,\"jobId\":2,\"password\":\"$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK\",\"createBy\":\"admin\",\"phone\":\"15199999999\",\"updateBy\":\"admin\",\"createTime\":1588648549000,\"nickname\":\"测试\",\"disabled\":false,\"id\":2,\"job\":{\"note\":\"中级工程师\",\"level\":2,\"updateTime\":1633847663000,\"type\":1,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553842551000,\"name\":\"中级工程师\",\"disabled\":false,\"id\":2},\"email\":\"231@qq.com\",\"username\":\"test\"}','更新',1,'admin','172.22.211.75','127.0.0.1','本机地址','Chrome 91',208,'2021-10-11 17:42:52');
insert  into `sys_log`(`id`,`level`,`biz_type`,`message`,`exception_message`,`class_name`,`method_name`,`params`,`operate_type`,`operator_id`,`operator_name`,`server_ip`,`client_ip`,`client_location`,`client_device`,`request_time`,`create_time`) values (2,'INFO','系统用户','【系统用户】admin更新 cas_user 表中 id = 2 的记录，内容为：UserDto(id=2, deptId=2, jobId=3, username=test, nickname=测试, gender=男, phone=15199999999, email=231@qq.com, avatar=http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png, password=$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK, disabled=false, createBy=admin, updateBy=admin, pwdResetTime=null, createTime=2020-05-05T11:15:49, updateTime=2021-10-07T23:47:10, dept=DeptDto(id=2, pid=0, name=产品部, level=1, sequence=2, childrenNum=6, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=2019-03-25T09:15:32, updateTime=2021-10-05T22:39:30, label=null, hasChildren=false, children=null), job=JobDto(id=3, name=高级工程师, type=1, level=3, sequence=3, disabled=false, note=高级工程师, createBy=admin, updateBy=admin, createTime=2019-03-31T13:39:30, updateTime=2021-10-10T14:34:23, dept=null, roles=null), roles=[RoleDto(id=2, code=null, name=null, level=null, dataScope=null, disabled=null, note=null, createBy=null, updateBy=null, createTime=null, updateTime=null, menus=null, depts=null), RoleDto(id=1, code=null, name=null, level=null, dataScope=null, disabled=null, note=null, createBy=null, updateBy=null, createTime=null, updateTime=null, menus=null, depts=null)], roleCodes=[user], isAdmin=false)',NULL,'io.github.dunwu.module.cas.controller.UserController','edit','{\"gender\":\"男\",\"roles\":[{\"id\":2},{\"id\":1}],\"deptId\":2,\"updateTime\":1633621630000,\"roleCodes\":[\"user\"],\"avatar\":\"http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png\",\"dept\":{\"level\":1,\"hasChildren\":false,\"pid\":0,\"updateTime\":1633444770000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476532000,\"childrenNum\":6,\"name\":\"产品部\",\"disabled\":false,\"id\":2},\"isAdmin\":false,\"jobId\":3,\"password\":\"$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK\",\"createBy\":\"admin\",\"phone\":\"15199999999\",\"updateBy\":\"admin\",\"createTime\":1588648549000,\"nickname\":\"测试\",\"disabled\":false,\"id\":2,\"job\":{\"note\":\"高级工程师\",\"level\":3,\"updateTime\":1633847663000,\"type\":1,\"sequence\":3,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1554010770000,\"name\":\"高级工程师\",\"disabled\":false,\"id\":3},\"email\":\"231@qq.com\",\"username\":\"test\"}','更新',1,'admin','172.22.211.75','127.0.0.1','本机地址','Chrome 91',413,'2021-10-11 20:35:43');

/*Table structure for table `sys_quartz_job` */

DROP TABLE IF EXISTS `sys_quartz_job`;

CREATE TABLE `sys_quartz_job` (
    `job_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name` varchar(255) DEFAULT NULL COMMENT 'Spring Bean名称',
    `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron 表达式',
    `is_pause` bit(1) DEFAULT NULL COMMENT '状态：1暂停、0启用',
    `job_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
    `method_name` varchar(255) DEFAULT NULL COMMENT '方法名称',
    `params` varchar(255) DEFAULT NULL COMMENT '参数',
    `description` varchar(255) DEFAULT NULL COMMENT '备注',
    `person_in_charge` varchar(100) DEFAULT NULL COMMENT '负责人',
    `email` varchar(100) DEFAULT NULL COMMENT '报警邮箱',
    `sub_task` varchar(100) DEFAULT NULL COMMENT '子任务ID',
    `pause_after_failure` bit(1) DEFAULT NULL COMMENT '任务失败后是否暂停',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`job_id`) USING BTREE,
    KEY `key_is_pause` (`is_pause`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定时任务';

/*Data for the table `sys_quartz_job` */

insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'testTask','0/5 * * * * ?','','测试1','run1','test','带参测试，多参使用json','测试',NULL,NULL,NULL,NULL,'admin','2019-08-22 14:08:29','2020-05-24 13:58:33');
insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'testTask','0/5 * * * * ?','','测试','run','','不带参测试','Zheng Jie','','5,6','',NULL,'admin','2019-09-26 16:44:39','2020-05-24 14:48:12');
insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,'Test','0/5 * * * * ?','','任务告警测试','run',NULL,'测试','test','',NULL,'','admin','admin','2020-05-05 20:32:41','2020-05-05 20:36:13');
insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,'testTask','0/5 * * * * ?','','测试3','run2',NULL,'测试3','Zheng Jie','',NULL,'','admin','admin','2020-05-05 20:35:41','2020-05-05 20:36:07');

/*Table structure for table `sys_quartz_log` */

DROP TABLE IF EXISTS `sys_quartz_log`;

CREATE TABLE `sys_quartz_log` (
    `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name` varchar(255) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `cron_expression` varchar(255) DEFAULT NULL,
    `exception_detail` text,
    `is_success` bit(1) DEFAULT NULL,
    `job_name` varchar(255) DEFAULT NULL,
    `method_name` varchar(255) DEFAULT NULL,
    `params` varchar(255) DEFAULT NULL,
    `time` bigint(20) unsigned DEFAULT NULL,
    PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定时任务日志';

/*Data for the table `sys_quartz_log` */

/*Table structure for table `tool_alipay_config` */

DROP TABLE IF EXISTS `tool_alipay_config`;

CREATE TABLE `tool_alipay_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_id` varchar(255) DEFAULT NULL COMMENT '应用ID',
    `charset` varchar(255) DEFAULT NULL COMMENT '编码',
    `format` varchar(255) DEFAULT NULL COMMENT '类型 固定格式json',
    `gateway_url` varchar(255) DEFAULT NULL COMMENT '网关地址',
    `notify_url` varchar(255) DEFAULT NULL COMMENT '异步回调',
    `private_key` text COMMENT '私钥',
    `public_key` text COMMENT '公钥',
    `return_url` varchar(255) DEFAULT NULL COMMENT '回调地址',
    `sign_type` varchar(255) DEFAULT NULL COMMENT '签名方式',
    `sys_service_provider_id` varchar(255) DEFAULT NULL COMMENT '商户号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付宝配置类';

/*Data for the table `tool_alipay_config` */

insert  into `tool_alipay_config`(`id`,`app_id`,`charset`,`format`,`gateway_url`,`notify_url`,`private_key`,`public_key`,`return_url`,`sign_type`,`sys_service_provider_id`) values (1,'2016091700532697','utf-8','JSON','https://openapi.alipaydev.com/gateway.do','http://api.auauz.net/api/aliPay/notify','MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB','http://api.auauz.net/api/aliPay/return','RSA2','2088102176044281');

/*Table structure for table `tool_email_config` */

DROP TABLE IF EXISTS `tool_email_config`;

CREATE TABLE `tool_email_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `from_user` varchar(255) DEFAULT NULL COMMENT '收件人',
    `host` varchar(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
    `pass` varchar(255) DEFAULT NULL COMMENT '密码',
    `port` varchar(255) DEFAULT NULL COMMENT '端口',
    `user` varchar(255) DEFAULT NULL COMMENT '发件者用户名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='邮箱配置';

/*Data for the table `tool_email_config` */

/*Table structure for table `tool_file_content` */

DROP TABLE IF EXISTS `tool_file_content`;

CREATE TABLE `tool_file_content` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `file_name` varchar(128) NOT NULL COMMENT '实际文件名',
    `namespace` varchar(32) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    `tag` varchar(32) DEFAULT '' COMMENT '标签。供业务系统使用',
    `origin_name` varchar(128) NOT NULL COMMENT '源文件名',
    `store_type` varchar(128) DEFAULT '' COMMENT '文件存储服务类型',
    `store_url` varchar(128) DEFAULT '' COMMENT '文件存储路径',
    `content` blob NOT NULL COMMENT '文件内容',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_name` (`file_name`),
    UNIQUE KEY `uk_store_url` (`store_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件内容表';

/*Data for the table `tool_file_content` */

/*Table structure for table `tool_file_info` */

DROP TABLE IF EXISTS `tool_file_info`;

CREATE TABLE `tool_file_info` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `file_name` varchar(255) NOT NULL COMMENT '文件名',
    `namespace` varchar(255) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    `tag` varchar(255) DEFAULT '' COMMENT '标签。供业务系统使用',
    `origin_name` varchar(255) NOT NULL COMMENT '源文件名',
    `size` bigint(20) unsigned NOT NULL COMMENT '文件大小',
    `extension` varchar(32) NOT NULL COMMENT '文件扩展名',
    `content_type` varchar(255) NOT NULL COMMENT '文件实际类型',
    `store_type` varchar(100) DEFAULT '' COMMENT '文件存储服务类型',
    `store_url` varchar(255) DEFAULT '' COMMENT '文件存储路径',
    `access_url` varchar(255) NOT NULL COMMENT '文件访问路径',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_name` (`file_name`),
    UNIQUE KEY `uk_access_url` (`access_url`),
    UNIQUE KEY `uk_keys` (`origin_name`,`tag`,`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件信息表';

/*Data for the table `tool_file_info` */

/*Table structure for table `tool_local_storage` */

DROP TABLE IF EXISTS `tool_local_storage`;

CREATE TABLE `tool_local_storage` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `real_name` varchar(255) DEFAULT NULL COMMENT '文件真实的名称',
    `name` varchar(255) DEFAULT NULL COMMENT '文件名',
    `suffix` varchar(255) DEFAULT NULL COMMENT '后缀',
    `path` varchar(255) DEFAULT NULL COMMENT '路径',
    `type` varchar(255) DEFAULT NULL COMMENT '类型',
    `size` varchar(100) DEFAULT NULL COMMENT '大小',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='本地存储';

/*Data for the table `tool_local_storage` */

/*Table structure for table `tool_qiniu_config` */

DROP TABLE IF EXISTS `tool_qiniu_config`;

CREATE TABLE `tool_qiniu_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `host` varchar(255) NOT NULL COMMENT '外链域名',
    `access_key` text COMMENT 'accessKey',
    `secret_key` text COMMENT 'secretKey',
    `type` varchar(255) DEFAULT NULL COMMENT '空间类型',
    `zone` varchar(255) DEFAULT NULL COMMENT '机房',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='七牛云配置';

/*Data for the table `tool_qiniu_config` */

/*Table structure for table `tool_qiniu_content` */

DROP TABLE IF EXISTS `tool_qiniu_content`;

CREATE TABLE `tool_qiniu_content` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
    `size` varchar(255) DEFAULT NULL COMMENT '文件大小',
    `type` varchar(255) DEFAULT NULL COMMENT '文件类型：私有或公开',
    `url` varchar(255) DEFAULT NULL COMMENT '文件url',
    `suffix` varchar(255) DEFAULT NULL COMMENT '文件后缀',
    `update_time` datetime DEFAULT NULL COMMENT '上传或同步的时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='七牛云文件存储';

/*Data for the table `tool_qiniu_content` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
