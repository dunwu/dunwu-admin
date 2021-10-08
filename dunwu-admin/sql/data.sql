-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: dunwu_admin
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8 ;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cas_dept`
--

DROP TABLE IF EXISTS `cas_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_dept` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) unsigned DEFAULT NULL COMMENT '上级部门ID',
    `name` varchar(255) NOT NULL COMMENT '部门名称',
    `level` int(5) unsigned NOT NULL DEFAULT '1' COMMENT '部门等级',
    `sequence` int(5) unsigned DEFAULT '1' COMMENT '部门顺序',
    `children_num` int(5) unsigned DEFAULT '0' COMMENT '子部门数量',
    `is_disabled` tinyint(1) unsigned NOT NULL COMMENT '状态',
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_dept`
--

INSERT INTO `cas_dept` VALUES (1,0,'研发部',1,1,3,0,NULL,'admin','admin','2020-08-02 14:49:07','2021-10-05 22:39:30');
INSERT INTO `cas_dept` VALUES (2,0,'产品部',1,2,6,0,NULL,'admin','admin','2019-03-25 09:15:32','2021-10-05 22:39:30');
INSERT INTO `cas_dept` VALUES (3,1,'后端开发组',2,1,0,0,NULL,'admin','admin','2019-03-25 09:20:44','2021-10-06 00:50:06');
INSERT INTO `cas_dept` VALUES (4,1,'前端开发组',2,2,0,0,NULL,'admin','admin','2019-03-25 09:52:18','2021-10-05 22:39:30');
INSERT INTO `cas_dept` VALUES (5,1,'测试组',2,3,1,0,NULL,'admin','admin','2019-03-25 11:04:50','2021-10-05 22:39:30');
INSERT INTO `cas_dept` VALUES (6,2,'策划组',2,1,0,0,NULL,'admin','admin','2019-03-25 11:04:53','2021-10-05 22:39:30');
INSERT INTO `cas_dept` VALUES (7,2,'UI设计组',2,2,0,0,NULL,'admin','admin','2020-05-13 22:56:53','2021-10-05 22:39:30');
INSERT INTO `cas_dept` VALUES (28,5,'功能测试组',3,1,0,0,NULL,'admin','admin','2021-10-07 22:42:59','2021-10-07 22:42:59');

--
-- Table structure for table `cas_dept_role_map`
--

DROP TABLE IF EXISTS `cas_dept_role_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_dept_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint(20) NOT NULL,
    `dept_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu` (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_dept_role_map`
--


--
-- Table structure for table `cas_job`
--

DROP TABLE IF EXISTS `cas_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_job` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '岗位名称',
    `sequence` int(5) DEFAULT NULL COMMENT '排序',
    `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
    `enabled` bit(1) NOT NULL COMMENT '岗位状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`),
    KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='岗位';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_job`
--

INSERT INTO `cas_job` VALUES (1,'人事专员',3,NULL,_binary '',NULL,'admin','admin','2019-03-29 14:52:28',NULL);
INSERT INTO `cas_job` VALUES (2,'产品经理',4,NULL,_binary '',NULL,'admin','admin','2019-03-29 14:55:51',NULL);
INSERT INTO `cas_job` VALUES (3,'全栈开发',2,NULL,_binary '',NULL,'admin','admin','2019-03-31 13:39:30','2020-05-05 11:33:43');
INSERT INTO `cas_job` VALUES (4,'软件测试',5,NULL,_binary '',NULL,'admin','admin','2019-03-31 13:39:43','2020-05-10 19:56:26');

--
-- Table structure for table `cas_job_role_map`
--

DROP TABLE IF EXISTS `cas_job_role_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_job_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id` bigint(20) NOT NULL COMMENT '岗位ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_job_role` (`job_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统岗位/角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_job_role_map`
--


--
-- Table structure for table `cas_menu`
--

DROP TABLE IF EXISTS `cas_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count` int(5) DEFAULT '0' COMMENT '子菜单数目',
    `type` int(11) DEFAULT NULL COMMENT '菜单类型',
    `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
    `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
    `component` varchar(255) DEFAULT NULL COMMENT '组件',
    `sequence` int(5) DEFAULT NULL COMMENT '排序',
    `icon` varchar(255) DEFAULT NULL COMMENT '图标',
    `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
    `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
    `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
    `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_menu`
--

INSERT INTO `cas_menu` VALUES (1,NULL,7,0,'权限管理',NULL,NULL,1,'system','cas',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-18 15:11:29',NULL);
INSERT INTO `cas_menu` VALUES (2,1,3,1,'用户管理','User','cas/user/index',3,'peoples','user',_binary '\0',_binary '\0',_binary '\0','cas:user:view',NULL,'admin','2018-12-18 15:14:44','2021-10-05 09:30:02');
INSERT INTO `cas_menu` VALUES (3,1,3,1,'角色管理','Role','cas/role/index',4,'role','role',_binary '\0',_binary '\0',_binary '\0','cas:role:view',NULL,'admin','2018-12-18 15:16:07','2021-10-05 09:30:12');
INSERT INTO `cas_menu` VALUES (5,1,3,1,'菜单管理','Menu','cas/menu/index',5,'menu','menu',_binary '\0',_binary '\0',_binary '\0','cas:menu:view',NULL,'admin','2018-12-18 15:17:28','2021-10-05 09:30:22');
INSERT INTO `cas_menu` VALUES (6,NULL,5,0,'系统监控',NULL,NULL,4,'monitor','monitor',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2018-12-18 15:17:48','2021-10-05 09:28:00');
INSERT INTO `cas_menu` VALUES (7,121,0,1,'应用日志','Log','sys/LogList',3,'log','log',_binary '\0',_binary '',_binary '\0','sys:log:view','admin','admin','2018-12-18 15:18:26','2021-10-05 09:56:51');
INSERT INTO `cas_menu` VALUES (9,6,0,1,'SQL监控','Sql','monitor/sql/index',18,'sqlMonitor','druid',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-18 15:19:34',NULL);
INSERT INTO `cas_menu` VALUES (10,NULL,5,0,'组件管理',NULL,NULL,6,'zujian','components',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2018-12-19 13:38:16','2021-10-05 09:28:18');
INSERT INTO `cas_menu` VALUES (11,10,0,1,'图标库','Icons','components/icons/index',51,'icon','icon',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-19 13:38:49',NULL);
INSERT INTO `cas_menu` VALUES (14,36,0,1,'邮件工具','Email','tool/email/index',35,'email','email',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-27 10:13:09','2021-10-05 10:00:36');
INSERT INTO `cas_menu` VALUES (15,10,0,1,'富文本','Editor','components/Editor',52,'fwb','tinymce',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-27 11:58:25',NULL);
INSERT INTO `cas_menu` VALUES (18,36,3,1,'存储管理','Storage','tool/storage/index',34,'qiniu','storage',_binary '\0',_binary '\0',_binary '\0','tool:storage:view',NULL,NULL,'2018-12-31 11:12:15','2021-10-05 10:01:23');
INSERT INTO `cas_menu` VALUES (21,NULL,2,0,'多级菜单',NULL,'',7,'menu','nested',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2019-01-04 16:22:03','2021-10-05 09:28:24');
INSERT INTO `cas_menu` VALUES (22,21,2,0,'二级菜单1',NULL,'',999,'menu','menu1',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2019-01-04 16:23:29','2020-06-21 17:27:20');
INSERT INTO `cas_menu` VALUES (23,21,0,1,'二级菜单2',NULL,'nested/menu2/index',999,'menu','menu2',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-01-04 16:23:57',NULL);
INSERT INTO `cas_menu` VALUES (24,22,0,1,'三级菜单1','Test','nested/menu1/menu1-1',999,'menu','menu1-1',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-01-04 16:24:48',NULL);
INSERT INTO `cas_menu` VALUES (27,22,0,1,'三级菜单2',NULL,'nested/menu1/menu1-2',999,'menu','menu1-2',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-01-07 17:27:32',NULL);
INSERT INTO `cas_menu` VALUES (30,36,0,1,'代码生成','CodeIndex','code/generator/index',32,'dev','code',_binary '\0',_binary '',_binary '\0',NULL,NULL,NULL,'2019-01-11 15:45:55',NULL);
INSERT INTO `cas_menu` VALUES (33,10,0,1,'Markdown','Markdown','components/MarkDown',53,'markdown','markdown',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-03-08 13:46:44',NULL);
INSERT INTO `cas_menu` VALUES (34,10,0,1,'Yaml编辑器','YamlEdit','components/YamlEdit',54,'dev','yaml',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-03-08 15:49:40',NULL);
INSERT INTO `cas_menu` VALUES (35,1,3,1,'部门管理','Dept','cas/dept/index',1,'dept','dept',_binary '\0',_binary '\0',_binary '\0','cas:dept:view',NULL,'admin','2019-03-25 09:46:00','2021-10-08 21:28:55');
INSERT INTO `cas_menu` VALUES (36,NULL,7,0,'系统工具',NULL,'',3,'sys-tools','tool',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2019-03-29 10:57:35','2021-10-05 09:27:37');
INSERT INTO `cas_menu` VALUES (37,1,3,1,'岗位管理','Job','cas/job/index',2,'Steve-Jobs','job',_binary '\0',_binary '\0',_binary '\0','cas:job:view',NULL,'admin','2019-03-29 13:51:18','2021-10-05 09:29:55');
INSERT INTO `cas_menu` VALUES (38,36,0,1,'接口文档','Swagger','tool/swagger/index',36,'swagger','swagger2',_binary '\0',_binary '\0',_binary '\0','',NULL,NULL,'2019-03-29 19:57:53','2021-10-05 10:01:23');
INSERT INTO `cas_menu` VALUES (39,121,3,1,'字典管理','Dict','sys/Dict',2,'dictionary','dict',_binary '\0',_binary '',_binary '\0','sys:dict:view','admin','admin','2019-04-10 11:49:04','2021-10-05 09:56:51');
INSERT INTO `cas_menu` VALUES (41,6,0,1,'在线用户','OnlineUser','monitor/online/index',10,'Steve-Jobs','online',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-10-26 22:08:43',NULL);
INSERT INTO `cas_menu` VALUES (44,2,0,2,'用户新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','cas:user:add',NULL,NULL,'2019-10-29 10:59:46','2021-10-05 09:52:33');
INSERT INTO `cas_menu` VALUES (45,2,0,2,'用户编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','cas:user:edit',NULL,NULL,'2019-10-29 11:00:08','2021-10-05 09:52:33');
INSERT INTO `cas_menu` VALUES (46,2,0,2,'用户删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','cas:user:del',NULL,NULL,'2019-10-29 11:00:23','2021-10-05 09:52:33');
INSERT INTO `cas_menu` VALUES (48,3,0,2,'角色创建',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','cas:role:add',NULL,NULL,'2019-10-29 12:45:34','2021-10-05 09:53:15');
INSERT INTO `cas_menu` VALUES (49,3,0,2,'角色修改',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','cas:role:edit',NULL,NULL,'2019-10-29 12:46:16','2021-10-05 09:53:15');
INSERT INTO `cas_menu` VALUES (50,3,0,2,'角色删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','cas:role:del',NULL,NULL,'2019-10-29 12:46:51','2021-10-05 09:53:15');
INSERT INTO `cas_menu` VALUES (52,5,0,2,'菜单新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','cas:menu:add',NULL,NULL,'2019-10-29 12:55:07','2021-10-05 09:53:51');
INSERT INTO `cas_menu` VALUES (53,5,0,2,'菜单编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','cas:menu:edit',NULL,NULL,'2019-10-29 12:55:40','2021-10-05 09:53:51');
INSERT INTO `cas_menu` VALUES (54,5,0,2,'菜单删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','cas:menu:del',NULL,NULL,'2019-10-29 12:56:00','2021-10-05 09:53:51');
INSERT INTO `cas_menu` VALUES (56,35,0,2,'部门新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','cas:dept:add','admin','admin','2019-10-29 12:57:09','2021-10-05 09:54:24');
INSERT INTO `cas_menu` VALUES (57,35,0,2,'部门编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','cas:dept:edit','admin','admin','2019-10-29 12:57:27','2021-10-05 09:54:24');
INSERT INTO `cas_menu` VALUES (58,35,0,2,'部门删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','cas:dept:del','admin','admin','2019-10-29 12:57:41','2021-10-05 09:54:24');
INSERT INTO `cas_menu` VALUES (60,37,0,2,'岗位新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','cas:job:add','admin','admin','2019-10-29 12:58:27','2021-10-05 09:55:05');
INSERT INTO `cas_menu` VALUES (61,37,0,2,'岗位编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','cas:job:edit','admin','admin','2019-10-29 12:58:45','2021-10-05 09:55:05');
INSERT INTO `cas_menu` VALUES (62,37,0,2,'岗位删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','cas:job:del','admin','admin','2019-10-29 12:59:04','2021-10-05 09:55:05');
INSERT INTO `cas_menu` VALUES (64,39,0,2,'字典新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','sys:dict:add','admin','admin','2019-10-29 13:00:17','2021-10-05 09:57:24');
INSERT INTO `cas_menu` VALUES (65,39,0,2,'字典编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','sys:dict:edit','admin','admin','2019-10-29 13:00:42','2021-10-05 09:57:24');
INSERT INTO `cas_menu` VALUES (66,39,0,2,'字典删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','sys:dict:del','admin','admin','2019-10-29 13:00:59','2021-10-05 09:57:24');
INSERT INTO `cas_menu` VALUES (77,18,0,2,'上传文件',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','tool:storage:add',NULL,NULL,'2019-10-29 13:09:09','2021-10-05 10:02:11');
INSERT INTO `cas_menu` VALUES (78,18,0,2,'文件编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','tool:storage:edit',NULL,NULL,'2019-10-29 13:09:22','2021-10-05 10:02:11');
INSERT INTO `cas_menu` VALUES (79,18,0,2,'文件删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','tool:storage:del',NULL,NULL,'2019-10-29 13:09:34','2021-10-05 10:02:11');
INSERT INTO `cas_menu` VALUES (80,6,0,1,'服务监控','ServerMonitor','monitor/server/index',14,'codeConsole','server',_binary '\0',_binary '\0',_binary '\0','monitor:view',NULL,'admin','2019-11-07 13:06:39','2020-05-04 18:20:50');
INSERT INTO `cas_menu` VALUES (82,36,0,1,'生成配置','GeneratorConfig','code/generator/config',33,'dev','code/config/:dbId/:schemaName/:tableName',_binary '\0',_binary '',_binary '','',NULL,NULL,'2019-11-17 20:08:56',NULL);
INSERT INTO `cas_menu` VALUES (83,10,0,1,'图表库','Echarts','components/Echarts',50,'chart','echarts',_binary '\0',_binary '',_binary '\0','',NULL,NULL,'2019-11-21 09:04:32',NULL);
INSERT INTO `cas_menu` VALUES (90,NULL,5,1,'运维管理','Mnt','',5,'mnt','mnt',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2019-11-09 10:31:08','2021-10-05 09:28:11');
INSERT INTO `cas_menu` VALUES (92,90,3,1,'服务器配置','ServerDeploy','mnt/ServerList',23,'server','server',_binary '\0',_binary '\0',_binary '\0','mnt:server:view',NULL,'admin','2019-11-10 10:29:25','2021-10-03 00:14:18');
INSERT INTO `cas_menu` VALUES (93,90,3,1,'应用配置','App','mnt/AppList',22,'app','app',_binary '\0',_binary '\0',_binary '\0','mnt:app:view',NULL,'admin','2019-11-10 11:05:16','2021-10-03 00:14:06');
INSERT INTO `cas_menu` VALUES (94,90,3,1,'部署管理','Deploy','mnt/DeployList',24,'deploy','deploy',_binary '\0',_binary '\0',_binary '\0','deploy:view',NULL,'admin','2019-11-10 15:56:55','2021-10-03 00:19:31');
INSERT INTO `cas_menu` VALUES (97,90,1,1,'部署历史','DeployHistory','mnt/DeployHistoryList',25,'backup','deploy/history',_binary '\0',_binary '\0',_binary '\0','mnt:deployHistory:view',NULL,'admin','2019-11-10 16:49:44','2021-10-03 00:14:49');
INSERT INTO `cas_menu` VALUES (98,36,3,1,'数据库管理','Database','code/database/index',26,'database','code/database',_binary '\0',_binary '\0',_binary '\0','database:view',NULL,NULL,'2019-11-10 20:40:04',NULL);
INSERT INTO `cas_menu` VALUES (102,97,0,2,'删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deployHistory:del',NULL,NULL,'2019-11-17 09:32:48',NULL);
INSERT INTO `cas_menu` VALUES (103,92,0,2,'服务器新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','serverDeploy:add',NULL,NULL,'2019-11-17 11:08:33',NULL);
INSERT INTO `cas_menu` VALUES (104,92,0,2,'服务器编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','serverDeploy:edit',NULL,NULL,'2019-11-17 11:08:57',NULL);
INSERT INTO `cas_menu` VALUES (105,92,0,2,'服务器删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','serverDeploy:del',NULL,NULL,'2019-11-17 11:09:15',NULL);
INSERT INTO `cas_menu` VALUES (106,93,0,2,'应用新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','app:add',NULL,NULL,'2019-11-17 11:10:03',NULL);
INSERT INTO `cas_menu` VALUES (107,93,0,2,'应用编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','app:edit',NULL,NULL,'2019-11-17 11:10:28',NULL);
INSERT INTO `cas_menu` VALUES (108,93,0,2,'应用删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','app:del',NULL,NULL,'2019-11-17 11:10:55',NULL);
INSERT INTO `cas_menu` VALUES (109,94,0,2,'部署新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deploy:add',NULL,NULL,'2019-11-17 11:11:22',NULL);
INSERT INTO `cas_menu` VALUES (110,94,0,2,'部署编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deploy:edit',NULL,NULL,'2019-11-17 11:11:41',NULL);
INSERT INTO `cas_menu` VALUES (111,94,0,2,'部署删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deploy:del',NULL,NULL,'2019-11-17 11:12:01',NULL);
INSERT INTO `cas_menu` VALUES (112,98,0,2,'数据库新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','database:add',NULL,NULL,'2019-11-17 11:12:43',NULL);
INSERT INTO `cas_menu` VALUES (113,98,0,2,'数据库编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','database:edit',NULL,NULL,'2019-11-17 11:12:58',NULL);
INSERT INTO `cas_menu` VALUES (114,98,0,2,'数据库删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','database:del',NULL,NULL,'2019-11-17 11:13:14',NULL);
INSERT INTO `cas_menu` VALUES (116,36,0,1,'生成预览','Preview','code/generator/preview',999,'java','code/preview/:dbId/:schemaName/:tableName',_binary '\0',_binary '',_binary '',NULL,NULL,NULL,'2019-11-26 14:54:36',NULL);
INSERT INTO `cas_menu` VALUES (117,NULL,1,0,'项目案例',NULL,NULL,8,'demo','demo',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2021-12-19 13:38:16','2021-10-05 09:28:29');
INSERT INTO `cas_menu` VALUES (118,117,0,1,'Hello','Hello','demo/index',50,'','demo/index',_binary '\0',_binary '\0',_binary '\0','demo:view',NULL,'admin','2021-12-19 13:38:16','2021-09-17 21:22:01');
INSERT INTO `cas_menu` VALUES (119,121,0,1,'全局配置','GlobalConfigList','sys/GlobalConfigList',1,'system1','config',_binary '\0',_binary '',_binary '\0','sys:config:view','admin','admin','2021-10-03 19:00:00','2021-10-05 09:30:37');
INSERT INTO `cas_menu` VALUES (121,NULL,0,1,'系统管理',NULL,NULL,2,'app','sys',_binary '\0',_binary '\0',_binary '\0',NULL,'admin','admin','2021-10-05 09:15:30','2021-10-05 09:27:14');

--
-- Table structure for table `cas_role`
--

DROP TABLE IF EXISTS `cas_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_role` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '编码',
    `name` varchar(255) NOT NULL COMMENT '名称',
    `level` int(255) DEFAULT NULL COMMENT '角色级别',
    `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
    `enabled` bit(1) NOT NULL COMMENT '岗位状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_role`
--

INSERT INTO `cas_role` VALUES (1,'admin','超级管理员',1,'全部',_binary '','-',NULL,'admin','2018-11-23 11:04:37','2020-08-06 16:10:24');
INSERT INTO `cas_role` VALUES (2,'user','普通用户',2,'本级',_binary '','-',NULL,'admin','2018-11-23 13:09:06','2020-09-05 10:45:12');

--
-- Table structure for table `cas_role_menu_map`
--

DROP TABLE IF EXISTS `cas_role_menu_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_role_menu_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=334 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_role_menu_map`
--

INSERT INTO `cas_role_menu_map` VALUES (288,1,1);
INSERT INTO `cas_role_menu_map` VALUES (78,1,2);
INSERT INTO `cas_role_menu_map` VALUES (287,2,1);
INSERT INTO `cas_role_menu_map` VALUES (79,2,2);
INSERT INTO `cas_role_menu_map` VALUES (284,3,1);
INSERT INTO `cas_role_menu_map` VALUES (286,5,1);
INSERT INTO `cas_role_menu_map` VALUES (285,6,1);
INSERT INTO `cas_role_menu_map` VALUES (80,6,2);
INSERT INTO `cas_role_menu_map` VALUES (291,7,1);
INSERT INTO `cas_role_menu_map` VALUES (81,7,2);
INSERT INTO `cas_role_menu_map` VALUES (293,9,1);
INSERT INTO `cas_role_menu_map` VALUES (82,9,2);
INSERT INTO `cas_role_menu_map` VALUES (292,10,1);
INSERT INTO `cas_role_menu_map` VALUES (83,10,2);
INSERT INTO `cas_role_menu_map` VALUES (289,11,1);
INSERT INTO `cas_role_menu_map` VALUES (84,11,2);
INSERT INTO `cas_role_menu_map` VALUES (290,14,1);
INSERT INTO `cas_role_menu_map` VALUES (85,14,2);
INSERT INTO `cas_role_menu_map` VALUES (296,15,1);
INSERT INTO `cas_role_menu_map` VALUES (86,15,2);
INSERT INTO `cas_role_menu_map` VALUES (297,18,1);
INSERT INTO `cas_role_menu_map` VALUES (87,19,2);
INSERT INTO `cas_role_menu_map` VALUES (295,21,1);
INSERT INTO `cas_role_menu_map` VALUES (88,21,2);
INSERT INTO `cas_role_menu_map` VALUES (294,22,1);
INSERT INTO `cas_role_menu_map` VALUES (89,22,2);
INSERT INTO `cas_role_menu_map` VALUES (302,23,1);
INSERT INTO `cas_role_menu_map` VALUES (90,23,2);
INSERT INTO `cas_role_menu_map` VALUES (301,24,1);
INSERT INTO `cas_role_menu_map` VALUES (91,24,2);
INSERT INTO `cas_role_menu_map` VALUES (299,27,1);
INSERT INTO `cas_role_menu_map` VALUES (92,27,2);
INSERT INTO `cas_role_menu_map` VALUES (298,28,1);
INSERT INTO `cas_role_menu_map` VALUES (300,30,1);
INSERT INTO `cas_role_menu_map` VALUES (93,30,2);
INSERT INTO `cas_role_menu_map` VALUES (308,33,1);
INSERT INTO `cas_role_menu_map` VALUES (94,33,2);
INSERT INTO `cas_role_menu_map` VALUES (307,34,1);
INSERT INTO `cas_role_menu_map` VALUES (95,34,2);
INSERT INTO `cas_role_menu_map` VALUES (304,35,1);
INSERT INTO `cas_role_menu_map` VALUES (303,36,1);
INSERT INTO `cas_role_menu_map` VALUES (96,36,2);
INSERT INTO `cas_role_menu_map` VALUES (306,37,1);
INSERT INTO `cas_role_menu_map` VALUES (305,38,1);
INSERT INTO `cas_role_menu_map` VALUES (312,39,1);
INSERT INTO `cas_role_menu_map` VALUES (313,41,1);
INSERT INTO `cas_role_menu_map` VALUES (309,44,1);
INSERT INTO `cas_role_menu_map` VALUES (311,45,1);
INSERT INTO `cas_role_menu_map` VALUES (310,46,1);
INSERT INTO `cas_role_menu_map` VALUES (317,48,1);
INSERT INTO `cas_role_menu_map` VALUES (319,49,1);
INSERT INTO `cas_role_menu_map` VALUES (318,50,1);
INSERT INTO `cas_role_menu_map` VALUES (314,52,1);
INSERT INTO `cas_role_menu_map` VALUES (316,53,1);
INSERT INTO `cas_role_menu_map` VALUES (315,54,1);
INSERT INTO `cas_role_menu_map` VALUES (323,56,1);
INSERT INTO `cas_role_menu_map` VALUES (325,57,1);
INSERT INTO `cas_role_menu_map` VALUES (324,58,1);
INSERT INTO `cas_role_menu_map` VALUES (320,60,1);
INSERT INTO `cas_role_menu_map` VALUES (322,61,1);
INSERT INTO `cas_role_menu_map` VALUES (321,62,1);
INSERT INTO `cas_role_menu_map` VALUES (326,64,1);
INSERT INTO `cas_role_menu_map` VALUES (328,65,1);
INSERT INTO `cas_role_menu_map` VALUES (327,66,1);
INSERT INTO `cas_role_menu_map` VALUES (333,73,1);
INSERT INTO `cas_role_menu_map` VALUES (332,74,1);
INSERT INTO `cas_role_menu_map` VALUES (329,75,1);
INSERT INTO `cas_role_menu_map` VALUES (331,77,1);
INSERT INTO `cas_role_menu_map` VALUES (330,78,1);
INSERT INTO `cas_role_menu_map` VALUES (258,79,1);
INSERT INTO `cas_role_menu_map` VALUES (257,80,1);
INSERT INTO `cas_role_menu_map` VALUES (97,80,2);
INSERT INTO `cas_role_menu_map` VALUES (259,82,1);
INSERT INTO `cas_role_menu_map` VALUES (98,82,2);
INSERT INTO `cas_role_menu_map` VALUES (256,83,1);
INSERT INTO `cas_role_menu_map` VALUES (99,83,2);
INSERT INTO `cas_role_menu_map` VALUES (263,90,1);
INSERT INTO `cas_role_menu_map` VALUES (260,92,1);
INSERT INTO `cas_role_menu_map` VALUES (262,93,1);
INSERT INTO `cas_role_menu_map` VALUES (261,94,1);
INSERT INTO `cas_role_menu_map` VALUES (266,97,1);
INSERT INTO `cas_role_menu_map` VALUES (265,98,1);
INSERT INTO `cas_role_menu_map` VALUES (264,102,1);
INSERT INTO `cas_role_menu_map` VALUES (272,103,1);
INSERT INTO `cas_role_menu_map` VALUES (271,104,1);
INSERT INTO `cas_role_menu_map` VALUES (274,105,1);
INSERT INTO `cas_role_menu_map` VALUES (273,106,1);
INSERT INTO `cas_role_menu_map` VALUES (268,107,1);
INSERT INTO `cas_role_menu_map` VALUES (267,108,1);
INSERT INTO `cas_role_menu_map` VALUES (270,109,1);
INSERT INTO `cas_role_menu_map` VALUES (269,110,1);
INSERT INTO `cas_role_menu_map` VALUES (279,111,1);
INSERT INTO `cas_role_menu_map` VALUES (278,112,1);
INSERT INTO `cas_role_menu_map` VALUES (281,113,1);
INSERT INTO `cas_role_menu_map` VALUES (280,114,1);
INSERT INTO `cas_role_menu_map` VALUES (275,116,1);
INSERT INTO `cas_role_menu_map` VALUES (100,116,2);
INSERT INTO `cas_role_menu_map` VALUES (277,117,1);
INSERT INTO `cas_role_menu_map` VALUES (276,118,1);
INSERT INTO `cas_role_menu_map` VALUES (282,119,1);
INSERT INTO `cas_role_menu_map` VALUES (283,121,1);

--
-- Table structure for table `cas_user`
--

DROP TABLE IF EXISTS `cas_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
    `is_admin` bit(1) DEFAULT b'0' COMMENT '是否为admin账号',
    `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
    `is_disabled` tinyint(1) unsigned DEFAULT NULL COMMENT '是否禁用：1禁用；0启用',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新着',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email` (`email`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    KEY `key_enabled` (`is_disabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_user`
--

INSERT INTO `cas_user` VALUES (1,1,1,'admin','管理员','男','18888888888','201507802@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa',_binary '\0',NULL,0,'admin','admin','2020-05-03 16:38:31','2021-10-07 23:47:10');
INSERT INTO `cas_user` VALUES (2,1,2,'test','测试','男','15199999999','231@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK',_binary '\0',NULL,0,'admin','admin','2020-05-05 11:15:49','2021-10-07 23:47:10');

--
-- Table structure for table `cas_user_dept_map`
--

DROP TABLE IF EXISTS `cas_user_dept_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_user_dept_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_dept` (`user_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_user_dept_map`
--


--
-- Table structure for table `cas_user_role_map`
--

DROP TABLE IF EXISTS `cas_user_role_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `cas_user_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cas_user_role_map`
--

INSERT INTO `cas_user_role_map` VALUES (1,1,1);
INSERT INTO `cas_user_role_map` VALUES (3,2,2);

--
-- Table structure for table `code_column_config`
--

DROP TABLE IF EXISTS `code_column_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `code_column_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id` bigint(20) DEFAULT NULL COMMENT '所属表的ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-字段级别配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_column_config`
--

INSERT INTO `code_column_config` VALUES (217,NULL,'dunwu_admin','sys_global_config','id','ID','bigint(20) unsigned','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (218,NULL,'dunwu_admin','sys_global_config','app_code','应用编码','varchar(255)','String','',_binary '\0','appCode','应用编码',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (219,NULL,'dunwu_admin','sys_global_config','module_code','模块编码','varchar(255)','String','',_binary '\0','moduleCode','模块编码',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (220,NULL,'dunwu_admin','sys_global_config','namespace','命名空间','varchar(255)','String','',_binary '\0','namespace','命名空间',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (221,NULL,'dunwu_admin','sys_global_config','code','配置项编码','varchar(255)','String','MUL',_binary '','code','配置项编码',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (222,NULL,'dunwu_admin','sys_global_config','name','配置项配置名称','varchar(255)','String','',_binary '','name','配置项配置名称',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (223,NULL,'dunwu_admin','sys_global_config','value','配置项值','varchar(1000)','String','',_binary '\0','value','配置项值',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (224,NULL,'dunwu_admin','sys_global_config','default_value','配置项默认值','varchar(1000)','String','',_binary '\0','defaultValue','配置项默认值',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (225,NULL,'dunwu_admin','sys_global_config','value_type','配置项值类型','varchar(50)','String','',_binary '\0','valueType','配置项值类型',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (226,NULL,'dunwu_admin','sys_global_config','is_disabled','是否禁用：1 表示禁用；0 表示启用','tinyint(1) unsigned','Boolean','MUL',_binary '','isDisabled','是否禁用：1 表示禁用；0 表示启用',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (227,NULL,'dunwu_admin','sys_global_config','note','备注','varchar(255)','String','',_binary '\0','note','备注',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (228,NULL,'dunwu_admin','sys_global_config','create_by','创建者','varchar(255)','String','',_binary '\0','createBy','创建者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (229,NULL,'dunwu_admin','sys_global_config','update_by','更新者','varchar(255)','String','',_binary '\0','updateBy','更新者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (230,NULL,'dunwu_admin','sys_global_config','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (231,NULL,'dunwu_admin','sys_global_config','update_time','更新时间','datetime','LocalDateTime','MUL',_binary '\0','updateTime','更新时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 18:53:31','2021-10-03 18:53:31');
INSERT INTO `code_column_config` VALUES (247,NULL,'dunwu_admin','tool_file_info','id','ID','bigint(20) unsigned','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (248,NULL,'dunwu_admin','tool_file_info','file_name','文件名','varchar(255)','String','UNI',_binary '','fileName','文件名',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (249,NULL,'dunwu_admin','tool_file_info','namespace','命名空间。一般对应业务系统','varchar(255)','String','',_binary '\0','namespace','命名空间。一般对应业务系统',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (250,NULL,'dunwu_admin','tool_file_info','tag','标签。供业务系统使用','varchar(255)','String','',_binary '\0','tag','标签。供业务系统使用',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (251,NULL,'dunwu_admin','tool_file_info','origin_name','源文件名','varchar(255)','String','MUL',_binary '','originName','源文件名',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (252,NULL,'dunwu_admin','tool_file_info','size','文件大小','bigint(20) unsigned','Long','',_binary '','size','文件大小',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (253,NULL,'dunwu_admin','tool_file_info','extension','文件扩展名','varchar(32)','String','',_binary '','extension','文件扩展名',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (254,NULL,'dunwu_admin','tool_file_info','content_type','文件实际类型','varchar(32)','String','',_binary '','contentType','文件实际类型',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (255,NULL,'dunwu_admin','tool_file_info','store_type','文件存储服务类型','varchar(128)','String','',_binary '\0','storeType','文件存储服务类型',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (256,NULL,'dunwu_admin','tool_file_info','store_url','文件存储路径','varchar(128)','String','',_binary '\0','storeUrl','文件存储路径',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (257,NULL,'dunwu_admin','tool_file_info','access_url','文件访问路径','varchar(160)','String','UNI',_binary '','accessUrl','文件访问路径',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (258,NULL,'dunwu_admin','tool_file_info','create_by','创建者','varchar(255)','String','',_binary '\0','createBy','创建者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (259,NULL,'dunwu_admin','tool_file_info','update_by','更新者','varchar(255)','String','',_binary '\0','updateBy','更新者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (260,NULL,'dunwu_admin','tool_file_info','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (261,NULL,'dunwu_admin','tool_file_info','update_time','更新时间','datetime','LocalDateTime','',_binary '\0','updateTime','更新时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:24:14','2021-10-03 21:24:14');
INSERT INTO `code_column_config` VALUES (262,NULL,'dunwu_admin','tool_file_content','id','ID','varchar(32)','String','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (263,NULL,'dunwu_admin','tool_file_content','file_name','实际文件名','varchar(128)','String','UNI',_binary '','fileName','实际文件名',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (264,NULL,'dunwu_admin','tool_file_content','namespace','命名空间。一般对应业务系统','varchar(32)','String','',_binary '\0','namespace','命名空间。一般对应业务系统',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (265,NULL,'dunwu_admin','tool_file_content','tag','标签。供业务系统使用','varchar(32)','String','',_binary '\0','tag','标签。供业务系统使用',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (266,NULL,'dunwu_admin','tool_file_content','origin_name','源文件名','varchar(128)','String','',_binary '','originName','源文件名',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (267,NULL,'dunwu_admin','tool_file_content','store_type','文件存储服务类型','varchar(128)','String','',_binary '\0','storeType','文件存储服务类型',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (268,NULL,'dunwu_admin','tool_file_content','store_url','文件存储路径','varchar(128)','String','UNI',_binary '\0','storeUrl','文件存储路径',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (269,NULL,'dunwu_admin','tool_file_content','content','文件内容','blob','Blob','',_binary '','content','文件内容',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-03 21:26:13','2021-10-03 21:26:13');
INSERT INTO `code_column_config` VALUES (281,NULL,'dunwu_admin','cas_dept','id','ID','bigint(20) unsigned','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (282,NULL,'dunwu_admin','cas_dept','pid','上级部门ID','bigint(20) unsigned','Long','',_binary '\0','pid','上级部门ID',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (283,NULL,'dunwu_admin','cas_dept','name','部门名称','varchar(255)','String','MUL',_binary '','name','部门名称',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (284,NULL,'dunwu_admin','cas_dept','sequence','部门顺序','int(5) unsigned','Integer','MUL',_binary '\0','sequence','部门顺序',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (285,NULL,'dunwu_admin','cas_dept','children_num','子部门数量','int(5) unsigned','Integer','',_binary '\0','childrenNum','子部门数量',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (286,NULL,'dunwu_admin','cas_dept','is_disabled','状态','tinyint(1) unsigned','Boolean','MUL',_binary '','isDisabled','状态',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (287,NULL,'dunwu_admin','cas_dept','note','备注','varchar(255)','String','',_binary '\0','note','备注',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (288,NULL,'dunwu_admin','cas_dept','create_by','创建者','varchar(255)','String','',_binary '\0','createBy','创建者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (289,NULL,'dunwu_admin','cas_dept','update_by','更新者','varchar(255)','String','',_binary '\0','updateBy','更新者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (290,NULL,'dunwu_admin','cas_dept','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (291,NULL,'dunwu_admin','cas_dept','update_time','更新时间','datetime','LocalDateTime','',_binary '\0','updateTime','更新时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-05 18:16:16','2021-10-05 18:16:16');
INSERT INTO `code_column_config` VALUES (292,NULL,'dunwu_admin','cas_user_dept_map','id','ID','bigint(20) unsigned','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:29:40','2021-10-07 19:29:40');
INSERT INTO `code_column_config` VALUES (293,NULL,'dunwu_admin','cas_user_dept_map','user_id','用户ID','bigint(20)','Long','MUL',_binary '','userId','用户ID',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:29:40','2021-10-07 19:29:40');
INSERT INTO `code_column_config` VALUES (294,NULL,'dunwu_admin','cas_user_dept_map','dept_id','部门ID','bigint(20)','Long','',_binary '','deptId','部门ID',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:29:40','2021-10-07 19:29:40');
INSERT INTO `code_column_config` VALUES (295,NULL,'dunwu_admin','cas_user','id','ID','bigint(20)','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (296,NULL,'dunwu_admin','cas_user','dept_id','部门ID','bigint(20)','Long','MUL',_binary '\0','deptId','部门ID',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (297,NULL,'dunwu_admin','cas_user','job_id','岗位ID','bigint(20)','Long','',_binary '\0','jobId','岗位ID',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (298,NULL,'dunwu_admin','cas_user','username','用户名','varchar(255)','String','UNI',_binary '\0','username','用户名',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (299,NULL,'dunwu_admin','cas_user','nickname','昵称','varchar(255)','String','',_binary '\0','nickname','昵称',_binary '',_binary '',_binary '',_binary '\0',_binary '','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (300,NULL,'dunwu_admin','cas_user','gender','性别','varchar(2)','String','',_binary '\0','gender','性别',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (301,NULL,'dunwu_admin','cas_user','phone','手机号码','varchar(255)','String','',_binary '\0','phone','手机号码',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (302,NULL,'dunwu_admin','cas_user','email','邮箱','varchar(255)','String','UNI',_binary '\0','email','邮箱',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (303,NULL,'dunwu_admin','cas_user','avatar','头像地址','varchar(255)','String','MUL',_binary '\0','avatar','头像地址',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (304,NULL,'dunwu_admin','cas_user','password','密码','varchar(255)','String','',_binary '\0','password','密码',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (305,NULL,'dunwu_admin','cas_user','is_admin','是否为admin账号','bit(1)','Boolean','',_binary '\0','isAdmin','是否为admin账号',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (306,NULL,'dunwu_admin','cas_user','enabled','状态：1启用、0禁用','bigint(20)','Long','MUL',_binary '\0','enabled','状态：1启用、0禁用',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (307,NULL,'dunwu_admin','cas_user','create_by','创建者','varchar(255)','String','',_binary '\0','createBy','创建者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (308,NULL,'dunwu_admin','cas_user','update_by','更新着','varchar(255)','String','',_binary '\0','updateBy','更新着',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (309,NULL,'dunwu_admin','cas_user','pwd_reset_time','修改密码的时间','datetime','LocalDateTime','',_binary '\0','pwdResetTime','修改密码的时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (310,NULL,'dunwu_admin','cas_user','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');
INSERT INTO `code_column_config` VALUES (311,NULL,'dunwu_admin','cas_user','update_time','更新时间','datetime','LocalDateTime','',_binary '\0','updateTime','更新时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-07 19:33:11','2021-10-07 19:33:11');

--
-- Table structure for table `code_database`
--

DROP TABLE IF EXISTS `code_database`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `code_database` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '数据库名称',
    `host` varchar(255) NOT NULL COMMENT 'Host',
    `port` int(10) NOT NULL COMMENT '端口号',
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_database`
--

INSERT INTO `code_database` VALUES (1,'dunwu_admin','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_admin','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50');
INSERT INTO `code_database` VALUES (2,'dunwu_cas','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_cas?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_cas','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50');

--
-- Table structure for table `code_global_config`
--

DROP TABLE IF EXISTS `code_global_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `code_global_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_global_config`
--

INSERT INTO `code_global_config` VALUES (1,_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');

--
-- Table structure for table `code_table_config`
--

DROP TABLE IF EXISTS `code_table_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `code_table_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `db_id` bigint(20) NOT NULL COMMENT '数据库ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-表级别配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_table_config`
--

INSERT INTO `code_table_config` VALUES (1,1,'dunwu_admin','hello','测试',_binary '\0',_binary '',_binary '','zp','D://',NULL,NULL,'io.github.dunwu.module','NONE','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','demo',NULL,'hello','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (2,2,'dunwu_cas','sys_log','系统日志记录',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','sys','sys_','log','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (3,1,'dunwu_admin','mnt_app','应用管理',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','app','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (4,1,'dunwu_admin','mnt_deploy','部署管理',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','deploy','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (5,1,'dunwu_admin','mnt_deploy_history','部署历史管理',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','deploy/history','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (6,1,'dunwu_admin','mnt_deploy_server','应用与服务器关联',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','deploy/server','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (7,1,'dunwu_admin','mnt_server','服务器配置表',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','server','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (8,1,'dunwu_admin','mnt_deploy_server_map','应用和服务关联表',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt2','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','DeployServerMap','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (9,1,'dunwu_admin','tool_alipay_config','支付宝配置类',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','tool','tool_','alipay','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (10,1,'dunwu_admin','sys_dict','数据字典',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','sys','sys_','dict','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (11,1,'dunwu_admin','sys_dict_option','数据字典详情',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','sys','sys_','dict/option','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (12,1,'dunwu_admin','sys_global_config','系统全局配置表',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-common','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','sys','sys_','config','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (13,1,'dunwu_admin','tool_file_info','文件信息表',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','tool','tool_','file','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (14,1,'dunwu_admin','tool_file_content','文件内容表',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-tool','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','tool','tool_','file/content','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (15,1,'dunwu_admin','cas_dept','部门',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','cas','cas_','dept','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (16,1,'dunwu_admin','cas_user_dept_map','用户部门关联表',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','cas','cas_','UserDeptMap','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (17,1,'dunwu_admin','cas_user','系统用户',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-security','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','cas','cas_','user','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');

--
-- Table structure for table `hello`
--

DROP TABLE IF EXISTS `hello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `hello` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '名字',
    `age` smallint(3) NOT NULL COMMENT '年龄',
    `avatar` varchar(255) NOT NULL COMMENT '头像',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='测试';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hello`
--

INSERT INTO `hello` VALUES (1,'fasdfas',12,'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','2021-03-01 21:12:57');
INSERT INTO `hello` VALUES (2,'abc',11,'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','2021-03-15 21:50:16');

--
-- Table structure for table `mnt_app`
--

DROP TABLE IF EXISTS `mnt_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_app`
--

INSERT INTO `mnt_app` VALUES (1,'dunwu-mnt','D:\\upload','D:\\deploy','D:\\backup',8080,'1','1','1','admin','admin','2021-10-02 17:39:19','2021-10-02 17:39:19');

--
-- Table structure for table `mnt_deploy`
--

DROP TABLE IF EXISTS `mnt_deploy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_deploy`
--

INSERT INTO `mnt_deploy` VALUES (2,1,'dunwu 运维服务','admin','admin','2021-10-03 07:11:51','2021-10-03 07:22:05');

--
-- Table structure for table `mnt_deploy_history`
--

DROP TABLE IF EXISTS `mnt_deploy_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_deploy_history` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `deploy_id` bigint(20) unsigned DEFAULT NULL COMMENT '部署编号',
    `app_name` varchar(255) NOT NULL COMMENT '应用名称',
    `deploy_date` datetime NOT NULL COMMENT '部署日期',
    `deploy_user` varchar(50) NOT NULL COMMENT '部署用户',
    `ip` varchar(20) NOT NULL COMMENT '服务器IP',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_deploy_history`
--


--
-- Table structure for table `mnt_deploy_server_map`
--

DROP TABLE IF EXISTS `mnt_deploy_server_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_deploy_server_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `deploy_id` bigint(20) unsigned NOT NULL COMMENT '部署ID',
    `server_id` bigint(20) unsigned NOT NULL COMMENT '服务ID',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_deploy_server_id` (`deploy_id`,`server_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用和服务关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_deploy_server_map`
--

INSERT INTO `mnt_deploy_server_map` VALUES (3,2,1);
INSERT INTO `mnt_deploy_server_map` VALUES (4,2,2);

--
-- Table structure for table `mnt_server`
--

DROP TABLE IF EXISTS `mnt_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_server`
--

INSERT INTO `mnt_server` VALUES (1,'测试服务器一','127.0.0.1',8080,'root','root',NULL,'admin','admin','2021-10-02 19:36:16','2021-10-02 19:36:16');
INSERT INTO `mnt_server` VALUES (2,'测试服务器二','127.0.0.2',8081,'root','root',NULL,'admin','admin','2021-10-02 19:51:55','2021-10-02 19:51:55');

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

INSERT INTO `sys_dict` VALUES (1,'disabled_status','是否停用状态',0,'是否停用状态','admin','admin','2019-10-27 20:31:36','2021-10-05 18:46:03');
INSERT INTO `sys_dict` VALUES (2,'dept_status','部门状态',0,'部门状态','admin','admin','2019-10-27 20:31:36','2021-10-03 18:37:45');
INSERT INTO `sys_dict` VALUES (3,'job_status','岗位状态',0,'岗位状态','admin',NULL,'2019-10-27 20:31:36',NULL);

--
-- Table structure for table `sys_dict_option`
--

DROP TABLE IF EXISTS `sys_dict_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_option`
--

INSERT INTO `sys_dict_option` VALUES (1,1,'false','启用','admin','admin','2019-10-27 20:31:36','2021-10-05 18:48:04');
INSERT INTO `sys_dict_option` VALUES (2,1,'true','禁用','admin','admin','2019-10-27 20:31:36','2021-10-05 18:48:04');
INSERT INTO `sys_dict_option` VALUES (3,2,'true','启用','admin',NULL,NULL,NULL);
INSERT INTO `sys_dict_option` VALUES (4,2,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);
INSERT INTO `sys_dict_option` VALUES (5,3,'true','启用','admin',NULL,NULL,NULL);
INSERT INTO `sys_dict_option` VALUES (6,3,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);

--
-- Table structure for table `sys_global_config`
--

DROP TABLE IF EXISTS `sys_global_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统全局配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_global_config`
--

INSERT INTO `sys_global_config` VALUES (1,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','appId','应用ID','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (2,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','publicKey','支付宝公钥','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (3,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','privateKey','支付宝私钥','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (4,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','signType','RSA2','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (5,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','gatewayUrl','支付宝开放安全地址','https://openapi.alipaydev.com/gateway.do','https://openapi.alipaydev.com/gateway.do','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (6,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','charset','编码','utf-8','utf-8','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (7,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','notifyUrl','异步通知地址','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (8,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','returnUrl','订单完成后返回的页面','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (9,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','format','序列化类型','JSON','JSON','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');
INSERT INTO `sys_global_config` VALUES (10,'dunwu-admin','dunwu-admin-module-tool','/tool/alipay','merchantId','商户号','','','String',0,NULL,'admin','admin','2021-10-03 19:23:27','2021-10-03 19:23:27');

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

INSERT INTO `sys_log` VALUES (1,'INFO','','admin更新一条 SysMenu 记录',NULL,'io.github.dunwu.module.cas.controller.SysMenuController','edit','{\"cache\":false,\"hidden\":false,\"icon\":\"sys-tools\",\"weight\":30,\"pid\":0,\"title\":\"系统工具\",\"type\":0,\"path\":\"tool\",\"component\":\"\",\"updateBy\":\"admin\",\"iFrame\":false,\"id\":36}','',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',134,'2021-10-03 22:45:01');
INSERT INTO `sys_log` VALUES (2,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败','\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'\n; Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',232,'2021-10-04 08:50:55');
INSERT INTO `sys_log` VALUES (3,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败','\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'\n; Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'dunwu/image/nullfile.separatorpng\' for key \'uk_access_url\'','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',30,'2021-10-04 08:51:02');
INSERT INTO `sys_log` VALUES (4,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',136,'2021-10-04 09:08:22');
INSERT INTO `sys_log` VALUES (5,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',12262,'2021-10-04 10:12:40');
INSERT INTO `sys_log` VALUES (6,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',0,'2021-10-04 10:36:34');
INSERT INTO `sys_log` VALUES (7,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',0,'2021-10-04 10:38:42');
INSERT INTO `sys_log` VALUES (8,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileContentMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileContentMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_content (`file_name`, `namespace`, `tag`, `origin_name`, `store_type`, `store_url`, `content`) VALUES (?, ?, ?, ?, ?, ?, ?)\r\n### Cause: java.sql.SQLException: Field \'id\' doesn\'t have a default value\n; Field \'id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'id\' doesn\'t have a default value','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',10678,'2021-10-04 10:42:27');
INSERT INTO `sys_log` VALUES (9,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',59,'2021-10-04 10:43:25');
INSERT INTO `sys_log` VALUES (10,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',1,'2021-10-04 10:51:55');
INSERT INTO `sys_log` VALUES (11,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorpng\' for key \'uk_access_url\'\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorpng\' for key \'uk_access_url\'\n; Duplicate entry \'//file.separatorpng\' for key \'uk_access_url\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorpng\' for key \'uk_access_url\'','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',198,'2021-10-04 10:52:27');
INSERT INTO `sys_log` VALUES (12,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',102,'2021-10-04 10:52:54');
INSERT INTO `sys_log` VALUES (13,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'\n; Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',83,'2021-10-04 10:54:40');
INSERT INTO `sys_log` VALUES (14,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'\n; Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'//file.separatorjpg\' for key \'uk_access_url\'','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',51,'2021-10-04 10:55:08');
INSERT INTO `sys_log` VALUES (15,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',149485,'2021-10-04 10:59:37');
INSERT INTO `sys_log` VALUES (16,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',40,'2021-10-04 10:59:46');
INSERT INTO `sys_log` VALUES (17,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',86662,'2021-10-04 11:01:23');
INSERT INTO `sys_log` VALUES (18,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',160,'2021-10-04 11:03:15');
INSERT INTO `sys_log` VALUES (19,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',276,'2021-10-04 11:06:04');
INSERT INTO `sys_log` VALUES (20,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error querying database.  Cause: java.sql.SQLException: connection closed\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileContentMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileContentMapper.selectById\r\n### The error occurred while executing a query\r\n### Cause: java.sql.SQLException: connection closed\n; uncategorized SQLException; SQL state [null]; error code [0]; connection closed; nested exception is java.sql.SQLException: connection closed','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',201297,'2021-10-04 11:09:39');
INSERT INTO `sys_log` VALUES (21,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',13849,'2021-10-04 11:10:10');
INSERT INTO `sys_log` VALUES (22,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',9943,'2021-10-04 11:11:57');
INSERT INTO `sys_log` VALUES (23,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\n; Data truncation: Data too long for column \'content_type\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',12781,'2021-10-04 11:12:27');
INSERT INTO `sys_log` VALUES (24,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\n; Data truncation: Data too long for column \'content_type\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',4712,'2021-10-04 11:13:23');
INSERT INTO `sys_log` VALUES (25,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\n; Data truncation: Data too long for column \'content_type\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',6792,'2021-10-04 11:13:48');
INSERT INTO `sys_log` VALUES (26,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\n; Data truncation: Data too long for column \'content_type\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',13,'2021-10-04 11:15:04');
INSERT INTO `sys_log` VALUES (27,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：失败','\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\r\n### The error may exist in io/github/dunwu/module/tool/fs/dao/mapper/FileInfoMapper.java (best guess)\r\n### The error may involve io.github.dunwu.module.tool.fs.dao.mapper.FileInfoMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tool_file_info (`file_name`, `namespace`, `tag`, `origin_name`, `size`, `extension`, `content_type`, `store_type`, `store_url`, `access_url`, `create_by`, `update_by`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1\n; Data truncation: Data too long for column \'content_type\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'content_type\' at row 1','io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',9939,'2021-10-04 11:15:41');
INSERT INTO `sys_log` VALUES (28,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',55,'2021-10-04 11:17:02');
INSERT INTO `sys_log` VALUES (29,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',273,'2021-10-04 11:21:20');
INSERT INTO `sys_log` VALUES (30,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',66,'2021-10-04 11:22:29');
INSERT INTO `sys_log` VALUES (31,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',98,'2021-10-04 11:37:29');
INSERT INTO `sys_log` VALUES (32,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',1,'2021-10-04 11:56:13');
INSERT INTO `sys_log` VALUES (33,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',0,'2021-10-04 11:57:37');
INSERT INTO `sys_log` VALUES (34,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',1,'2021-10-04 12:00:57');
INSERT INTO `sys_log` VALUES (35,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',0,'2021-10-04 12:03:26');
INSERT INTO `sys_log` VALUES (36,'ERROR','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：null失败',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',1,'2021-10-04 12:06:40');
INSERT INTO `sys_log` VALUES (37,'INFO','文件信息表','【文件信息表】admin向 tool_file_info 表中添加一条记录，内容为：test',NULL,'io.github.dunwu.module.tool.fs.controller.FileController','upload','','添加',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 94',120,'2021-10-04 12:10:24');
INSERT INTO `sys_log` VALUES (38,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":1,\"updateBy\":\"admin\",\"name\":\"华南分部\",\"id\":7}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',29,'2021-10-05 15:11:24');
INSERT INTO `sys_log` VALUES (39,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":2,\"updateBy\":\"admin\",\"name\":\"华北分部\",\"id\":8}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',26,'2021-10-05 15:11:29');
INSERT INTO `sys_log` VALUES (40,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":1,\"updateBy\":\"admin\",\"name\":\"少林\",\"id\":7}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',58,'2021-10-05 15:13:56');
INSERT INTO `sys_log` VALUES (41,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":2,\"updateBy\":\"admin\",\"name\":\"武当\",\"id\":8}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',31,'2021-10-05 15:14:03');
INSERT INTO `sys_log` VALUES (42,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":1,\"updateBy\":\"admin\",\"name\":\"研发部\",\"id\":7}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',38,'2021-10-05 15:38:28');
INSERT INTO `sys_log` VALUES (43,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":2,\"updateBy\":\"admin\",\"name\":\"运营部\",\"id\":8}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',23,'2021-10-05 15:38:43');
INSERT INTO `sys_log` VALUES (44,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":2,\"updateBy\":\"admin\",\"name\":\"市场部\",\"id\":8}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',59,'2021-10-05 15:47:21');
INSERT INTO `sys_log` VALUES (45,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":7,\"enabled\":true,\"subCount\":1,\"sequence\":3,\"updateBy\":\"admin\",\"name\":\"服务端开发组\",\"id\":2}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',88,'2021-10-05 15:50:42');
INSERT INTO `sys_log` VALUES (46,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":7,\"enabled\":true,\"subCount\":0,\"sequence\":4,\"updateBy\":\"admin\",\"name\":\"前端开发组\",\"id\":5}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',11,'2021-10-05 15:50:52');
INSERT INTO `sys_log` VALUES (47,'INFO','','admin根据 ID 更新一条 Dept 记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":7,\"enabled\":true,\"subCount\":1,\"sequence\":3,\"updateBy\":\"admin\",\"name\":\"后端开发组\",\"id\":2}','',1,'admin','192.168.251.199','127.0.0.1','本机地址','Chrome 94',97,'2021-10-05 15:51:05');
INSERT INTO `sys_log` VALUES (48,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 8 的记录，内容为：Dept(id=8, pid=0, name=市场部, sequence=2, childrenNum=2, disabled=true, note=null, createBy=admin, updateBy=admin, createTime=2019-03-25T11:04:53, updateTime=2021-10-05T15:47:21)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"updateTime\":1633420041000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553483093000,\"childrenNum\":2,\"name\":\"市场部\",\"disabled\":true,\"id\":8}','更新',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',60,'2021-10-05 19:37:05');
INSERT INTO `sys_log` VALUES (49,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 8 的记录，内容为：Dept(id=8, pid=0, name=市场部, sequence=2, childrenNum=2, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=2019-03-25T11:04:53, updateTime=2021-10-05T15:47:21)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"updateTime\":1633420041000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553483093000,\"childrenNum\":2,\"name\":\"市场部\",\"disabled\":false,\"id\":8}','更新',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',35,'2021-10-05 19:40:02');
INSERT INTO `sys_log` VALUES (50,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=18, pid=0, name=市场部, level=1, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":1,\"pid\":0,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"市场部\",\"disabled\":false,\"id\":18}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',130,'2021-10-05 22:50:01');
INSERT INTO `sys_log` VALUES (51,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=19, pid=18, name=销售部, level=2, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":2,\"pid\":18,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"销售部\",\"disabled\":false,\"id\":19}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',27,'2021-10-05 22:50:47');
INSERT INTO `sys_log` VALUES (52,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=20, pid=19, name=销售培训组, level=3, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":3,\"pid\":19,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"销售培训组\",\"disabled\":false,\"id\":20}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',29,'2021-10-05 22:51:20');
INSERT INTO `sys_log` VALUES (53,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=21, pid=0, name=A, level=1, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":1,\"pid\":0,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"A\",\"disabled\":false,\"id\":21}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',79,'2021-10-05 23:11:40');
INSERT INTO `sys_log` VALUES (54,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=22, pid=21, name=A-A, level=2, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":2,\"pid\":21,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"A-A\",\"disabled\":false,\"id\":22}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',107,'2021-10-05 23:11:54');
INSERT INTO `sys_log` VALUES (55,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=23, pid=0, name=A-B, level=1, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":1,\"pid\":0,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"A-B\",\"disabled\":false,\"id\":23}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',47,'2021-10-05 23:12:14');
INSERT INTO `sys_log` VALUES (56,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 23 的记录，内容为：Dept(id=23, pid=0, name=A-B, level=1, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=2021-10-05T23:12:14, updateTime=2021-10-05T23:12:14)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"level\":1,\"pid\":0,\"updateTime\":1633446734000,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1633446734000,\"childrenNum\":0,\"name\":\"A-B\",\"disabled\":false,\"id\":23}','更新',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',6,'2021-10-05 23:12:45');
INSERT INTO `sys_log` VALUES (57,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=24, pid=21, name=A-B, level=2, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":2,\"pid\":21,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"A-B\",\"disabled\":false,\"id\":24}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',40,'2021-10-05 23:14:01');
INSERT INTO `sys_log` VALUES (58,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=25, pid=22, name=A-A-A, level=3, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":3,\"pid\":22,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"A-A-A\",\"disabled\":false,\"id\":25}','添加',1,'admin','192.168.96.199','127.0.0.1','本机地址','Chrome 94',70,'2021-10-05 23:14:17');
INSERT INTO `sys_log` VALUES (59,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=26, pid=3, name=test, level=3, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":3,\"pid\":3,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"test\",\"disabled\":false,\"id\":26}','添加',1,'admin','127.0.0.1','127.0.0.1','本机地址','Chrome 94',143,'2021-10-06 00:41:38');
INSERT INTO `sys_log` VALUES (60,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=27, pid=3, name=test, level=3, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":3,\"pid\":3,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"test\",\"disabled\":false,\"id\":27}','添加',1,'admin','127.0.0.1','127.0.0.1','本机地址','Chrome 94',151,'2021-10-06 00:50:51');
INSERT INTO `sys_log` VALUES (61,'INFO','部门','【部门】admin向 cas_dept 表中添加一条记录，内容为：Dept(id=28, pid=5, name=功能测试组, level=3, sequence=1, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=null, updateTime=null)',NULL,'io.github.dunwu.module.cas.controller.DeptController','add','{\"level\":3,\"pid\":5,\"sequence\":1,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"childrenNum\":0,\"name\":\"功能测试组\",\"disabled\":false,\"id\":28}','添加',1,'admin','192.168.1.11','127.0.0.1','本机地址','Chrome 94',101,'2021-10-07 22:43:00');
INSERT INTO `sys_log` VALUES (62,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 7 的记录，内容为：Dept(id=7, pid=2, name=UI设计组, level=2, sequence=2, childrenNum=0, disabled=true, note=null, createBy=admin, updateBy=admin, createTime=2020-05-13T22:56:53, updateTime=2021-10-05T22:39:30)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"level\":2,\"pid\":2,\"updateTime\":1633444770000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1589381813000,\"childrenNum\":0,\"name\":\"UI设计组\",\"disabled\":true,\"id\":7}','更新',1,'admin','192.168.1.11','127.0.0.1','本机地址','Chrome 94',54,'2021-10-08 22:00:05');
INSERT INTO `sys_log` VALUES (63,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 7 的记录，内容为：Dept(id=7, pid=2, name=UI设计组, level=2, sequence=2, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=2020-05-13T22:56:53, updateTime=2021-10-05T22:39:30)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"level\":2,\"pid\":2,\"updateTime\":1633444770000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1589381813000,\"childrenNum\":0,\"name\":\"UI设计组\",\"disabled\":false,\"id\":7}','更新',1,'admin','192.168.1.11','127.0.0.1','本机地址','Chrome 94',67,'2021-10-08 22:01:30');
INSERT INTO `sys_log` VALUES (64,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 7 的记录，内容为：Dept(id=7, pid=2, name=UI设计组, level=2, sequence=2, childrenNum=0, disabled=true, note=null, createBy=admin, updateBy=admin, createTime=2020-05-13T22:56:53, updateTime=2021-10-05T22:39:30)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"level\":2,\"pid\":2,\"updateTime\":1633444770000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1589381813000,\"childrenNum\":0,\"name\":\"UI设计组\",\"disabled\":true,\"id\":7}','更新',1,'admin','192.168.1.11','127.0.0.1','本机地址','Chrome 94',29,'2021-10-08 22:01:34');
INSERT INTO `sys_log` VALUES (65,'INFO','部门','【部门】admin更新 cas_dept 表中 id = 7 的记录，内容为：Dept(id=7, pid=2, name=UI设计组, level=2, sequence=2, childrenNum=0, disabled=false, note=null, createBy=admin, updateBy=admin, createTime=2020-05-13T22:56:53, updateTime=2021-10-05T22:39:30)',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"level\":2,\"pid\":2,\"updateTime\":1633444770000,\"sequence\":2,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1589381813000,\"childrenNum\":0,\"name\":\"UI设计组\",\"disabled\":false,\"id\":7}','更新',1,'admin','192.168.1.11','127.0.0.1','本机地址','Chrome 94',33,'2021-10-08 22:01:37');

--
-- Table structure for table `sys_quartz_job`
--

DROP TABLE IF EXISTS `sys_quartz_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_quartz_job` (
    `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_quartz_job`
--

INSERT INTO `sys_quartz_job` VALUES (2,'testTask','0/5 * * * * ?',_binary '','测试1','run1','test','带参测试，多参使用json','测试',NULL,NULL,NULL,NULL,'admin','2019-08-22 14:08:29','2020-05-24 13:58:33');
INSERT INTO `sys_quartz_job` VALUES (3,'testTask','0/5 * * * * ?',_binary '','测试','run','','不带参测试','Zheng Jie','','5,6',_binary '',NULL,'admin','2019-09-26 16:44:39','2020-05-24 14:48:12');
INSERT INTO `sys_quartz_job` VALUES (5,'Test','0/5 * * * * ?',_binary '','任务告警测试','run',NULL,'测试','test','',NULL,_binary '','admin','admin','2020-05-05 20:32:41','2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job` VALUES (6,'testTask','0/5 * * * * ?',_binary '','测试3','run2',NULL,'测试3','Zheng Jie','',NULL,_binary '','admin','admin','2020-05-05 20:35:41','2020-05-05 20:36:07');

--
-- Table structure for table `sys_quartz_log`
--

DROP TABLE IF EXISTS `sys_quartz_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_quartz_log` (
    `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name` varchar(255) DEFAULT NULL,
    `create_time` datetime DEFAULT NULL,
    `cron_expression` varchar(255) DEFAULT NULL,
    `exception_detail` text,
    `is_success` bit(1) DEFAULT NULL,
    `job_name` varchar(255) DEFAULT NULL,
    `method_name` varchar(255) DEFAULT NULL,
    `params` varchar(255) DEFAULT NULL,
    `time` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='定时任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_quartz_log`
--


--
-- Table structure for table `tool_alipay_config`
--

DROP TABLE IF EXISTS `tool_alipay_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_alipay_config`
--

INSERT INTO `tool_alipay_config` VALUES (1,'2016091700532697','utf-8','JSON','https://openapi.alipaydev.com/gateway.do','http://api.auauz.net/api/aliPay/notify','MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB','http://api.auauz.net/api/aliPay/return','RSA2','2088102176044281');

--
-- Table structure for table `tool_email_config`
--

DROP TABLE IF EXISTS `tool_email_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `tool_email_config` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `from_user` varchar(255) DEFAULT NULL COMMENT '收件人',
    `host` varchar(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
    `pass` varchar(255) DEFAULT NULL COMMENT '密码',
    `port` varchar(255) DEFAULT NULL COMMENT '端口',
    `user` varchar(255) DEFAULT NULL COMMENT '发件者用户名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='邮箱配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_email_config`
--


--
-- Table structure for table `tool_file_content`
--

DROP TABLE IF EXISTS `tool_file_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件内容表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_file_content`
--


--
-- Table structure for table `tool_file_info`
--

DROP TABLE IF EXISTS `tool_file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_file_info`
--


--
-- Table structure for table `tool_local_storage`
--

DROP TABLE IF EXISTS `tool_local_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_local_storage`
--


--
-- Table structure for table `tool_qiniu_config`
--

DROP TABLE IF EXISTS `tool_qiniu_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_qiniu_config`
--


--
-- Table structure for table `tool_qiniu_content`
--

DROP TABLE IF EXISTS `tool_qiniu_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_qiniu_content`
--


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
