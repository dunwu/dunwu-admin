/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.7.31 : Database - dunwu_admin2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dunwu_admin2` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dunwu_admin`;

/*Table structure for table `code_column_config` */

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-字段级别配置';

/*Data for the table `code_column_config` */

insert  into `code_column_config`(`id`,`table_id`,`schema_name`,`table_name`,`field_name`,`comment`,`type`,`java_type`,`key_type`,`not_null`,`property_name`,`label_name`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`form_type`,`list_type`,`query_type`,`sort_type`,`validate_type`,`date_type`,`date_pattern`,`dict_name`,`fill`,`extra`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,NULL,'dunwu_admin','hello','id','ID','bigint(20)','Long','PRI','','id','ID','\0','','','','\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49');
insert  into `code_column_config`(`id`,`table_id`,`schema_name`,`table_name`,`field_name`,`comment`,`type`,`java_type`,`key_type`,`not_null`,`property_name`,`label_name`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`form_type`,`list_type`,`query_type`,`sort_type`,`validate_type`,`date_type`,`date_pattern`,`dict_name`,`fill`,`extra`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,NULL,'dunwu_admin','hello','name','名字','varchar(255)','String','UNI','','name','名字','','','','\0','\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49');
insert  into `code_column_config`(`id`,`table_id`,`schema_name`,`table_name`,`field_name`,`comment`,`type`,`java_type`,`key_type`,`not_null`,`property_name`,`label_name`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`form_type`,`list_type`,`query_type`,`sort_type`,`validate_type`,`date_type`,`date_pattern`,`dict_name`,`fill`,`extra`,`create_by`,`update_by`,`create_time`,`update_time`) values (8,NULL,'dunwu_admin','hello','age','年龄','smallint(3)','Integer','','','age','年龄','','','','\0','\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49');
insert  into `code_column_config`(`id`,`table_id`,`schema_name`,`table_name`,`field_name`,`comment`,`type`,`java_type`,`key_type`,`not_null`,`property_name`,`label_name`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`form_type`,`list_type`,`query_type`,`sort_type`,`validate_type`,`date_type`,`date_pattern`,`dict_name`,`fill`,`extra`,`create_by`,`update_by`,`create_time`,`update_time`) values (9,NULL,'dunwu_admin','hello','avatar','头像','varchar(255)','String','','','avatar','头像','','','','\0','\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49');
insert  into `code_column_config`(`id`,`table_id`,`schema_name`,`table_name`,`field_name`,`comment`,`type`,`java_type`,`key_type`,`not_null`,`property_name`,`label_name`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`form_type`,`list_type`,`query_type`,`sort_type`,`validate_type`,`date_type`,`date_pattern`,`dict_name`,`fill`,`extra`,`create_by`,`update_by`,`create_time`,`update_time`) values (10,NULL,'dunwu_admin','hello','create_time','创建时间','datetime','LocalDateTime','','\0','createTime','创建时间','','','','\0','\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49');

/*Table structure for table `code_database` */

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据库管理';

/*Data for the table `code_database` */

insert  into `code_database`(`id`,`name`,`host`,`port`,`jdbc_url`,`username`,`password`,`schema_name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'dunwu_admin','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_admin','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50');

/*Table structure for table `code_global_config` */

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

/*Data for the table `code_global_config` */

insert  into `code_global_config`(`id`,`enable_permission`,`enable_override`,`enable_swagger`,`author`,`output_dir`,`backend_path`,`frontend_path`,`package_path`,`id_type`,`date_type`,`date_pattern`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'\0','','','zp','D://',NULL,NULL,NULL,'NONE','TIME_PACK','yyyy-MM-dd HH:mm:ss','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');

/*Table structure for table `code_table_config` */

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-表级别配置';

/*Data for the table `code_table_config` */

insert  into `code_table_config`(`id`,`db_id`,`schema_name`,`table_name`,`comment`,`enable_permission`,`enable_override`,`enable_swagger`,`author`,`output_dir`,`backend_path`,`frontend_path`,`package_path`,`id_type`,`date_type`,`date_pattern`,`enable_form`,`enable_list`,`enable_query`,`enable_sort`,`enable_validate`,`module_name`,`table_prefix`,`api_base_url`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,1,'dunwu_admin','hello','测试','\0','','','zp','D://',NULL,NULL,'io.github.dunwu.module','NONE','TIME_PACK','yyyy-MM-dd HH:mm:ss','','','','','','demo',NULL,'hello','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');

/*Table structure for table `file_record` */

CREATE TABLE `file_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `filename` varchar(255) NOT NULL COMMENT '文件名',
    `namespace` varchar(255) DEFAULT '' COMMENT '命名空间',
    `tag` varchar(255) DEFAULT '' COMMENT '标签',
    `origin_name` varchar(255) NOT NULL COMMENT '源文件名',
    `size` bigint(20) unsigned DEFAULT '0' COMMENT '文件大小',
    `extension` varchar(255) DEFAULT '' COMMENT '文件扩展名',
    `content_type` varchar(255) DEFAULT '' COMMENT '文件实际类型',
    `store_type` varchar(255) NOT NULL COMMENT '文件存储类型',
    `url` varchar(255) NOT NULL COMMENT '文件存储路径',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_filename` (`filename`),
    UNIQUE KEY `uk_keys` (`origin_name`,`tag`,`namespace`),
    KEY `key_code` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件记录';

/*Data for the table `file_record` */

/*Table structure for table `hello` */

CREATE TABLE `hello` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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

CREATE TABLE `mnt_app` (
    `app_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) DEFAULT NULL COMMENT '应用名称',
    `upload_path` varchar(255) DEFAULT NULL COMMENT '上传目录',
    `deploy_path` varchar(255) DEFAULT NULL COMMENT '部署路径',
    `backup_path` varchar(255) DEFAULT NULL COMMENT '备份路径',
    `port` int(255) DEFAULT NULL COMMENT '应用端口',
    `start_script` varchar(4000) DEFAULT NULL COMMENT '启动脚本',
    `deploy_script` varchar(4000) DEFAULT NULL COMMENT '部署脚本',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用管理';

/*Data for the table `mnt_app` */

/*Table structure for table `mnt_database` */

CREATE TABLE `mnt_database` (
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据库管理';

/*Data for the table `mnt_database` */

insert  into `mnt_database`(`id`,`name`,`host`,`port`,`jdbc_url`,`username`,`password`,`schema_name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'dunwu_admin','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_admin','admin','admin','2021-03-12 19:16:03','2021-03-12 19:16:03');

/*Table structure for table `mnt_deploy` */

CREATE TABLE `mnt_deploy` (
    `deploy_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_id` bigint(20) DEFAULT NULL COMMENT '应用编号',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`deploy_id`) USING BTREE,
    KEY `fk6sy157pseoxx4fmcqr1vnvvhy` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署管理';

/*Data for the table `mnt_deploy` */

/*Table structure for table `mnt_deploy_history` */

CREATE TABLE `mnt_deploy_history` (
    `history_id` varchar(50) NOT NULL COMMENT 'ID',
    `app_name` varchar(255) NOT NULL COMMENT '应用名称',
    `deploy_date` datetime NOT NULL COMMENT '部署日期',
    `deploy_user` varchar(50) NOT NULL COMMENT '部署用户',
    `ip` varchar(20) NOT NULL COMMENT '服务器IP',
    `deploy_id` bigint(20) DEFAULT NULL COMMENT '部署编号',
    PRIMARY KEY (`history_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署历史管理';

/*Data for the table `mnt_deploy_history` */

/*Table structure for table `mnt_deploy_server` */

CREATE TABLE `mnt_deploy_server` (
    `deploy_id` bigint(20) NOT NULL COMMENT '部署ID',
    `server_id` bigint(20) NOT NULL COMMENT '服务ID',
    PRIMARY KEY (`deploy_id`,`server_id`) USING BTREE,
    KEY `fkeaaha7jew9a02b3bk9ghols53` (`server_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用与服务器关联';

/*Data for the table `mnt_deploy_server` */

/*Table structure for table `mnt_server` */

CREATE TABLE `mnt_server` (
    `server_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `account` varchar(50) DEFAULT NULL COMMENT '账号',
    `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
    `name` varchar(100) DEFAULT NULL COMMENT '名称',
    `password` varchar(100) DEFAULT NULL COMMENT '密码',
    `port` int(11) DEFAULT NULL COMMENT '端口',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`server_id`) USING BTREE,
    KEY `idx_ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='服务器管理';

/*Data for the table `mnt_server` */

/*Table structure for table `sys_dept` */

CREATE TABLE `sys_dept` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) DEFAULT '0' COMMENT '上级部门',
    `sub_count` int(5) DEFAULT '0' COMMENT '子部门数目',
    `name` varchar(255) NOT NULL COMMENT '名称',
    `weight` int(5) DEFAULT '999' COMMENT '排序',
    `enabled` bit(1) NOT NULL COMMENT '状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_pid` (`pid`),
    KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,7,1,'研发部',3,'',NULL,'admin','admin','2019-03-25 09:15:32','2020-08-02 14:48:47');
insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,7,0,'运维部',4,'',NULL,'admin','admin','2019-03-25 09:20:44','2020-05-17 14:27:27');
insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,8,0,'测试部',6,'',NULL,'admin','admin','2019-03-25 09:52:18','2020-06-08 11:59:21');
insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,0,2,'华南分部',0,'',NULL,'admin','admin','2019-03-25 11:04:50','2020-06-08 12:08:56');
insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (8,0,2,'华北分部',1,'',NULL,'admin','admin','2019-03-25 11:04:53','2020-05-14 12:54:00');
insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (15,8,0,'UI部门',7,'',NULL,'admin','admin','2020-05-13 22:56:53','2020-05-14 12:54:13');
insert  into `sys_dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (17,2,0,'研发一组',999,'',NULL,'admin','admin','2020-08-02 14:49:07','2020-08-02 14:49:07');

/*Table structure for table `sys_dict` */

CREATE TABLE `sys_dict` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '字典编码',
    `name` varchar(255) NOT NULL COMMENT '字典名称',
    `enabled` bit(1) NOT NULL COMMENT '状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'user_status','用户状态','','用户状态','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'dept_status','部门状态','','部门状态','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'job_status','岗位状态','','岗位状态','admin',NULL,'2019-10-27 20:31:36',NULL);

/*Table structure for table `sys_dict_option` */

CREATE TABLE `sys_dict_option` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
    `code` varchar(255) NOT NULL COMMENT '字典选项编码',
    `name` varchar(255) NOT NULL COMMENT '字典选项名称',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_dict_id` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

/*Data for the table `sys_dict_option` */

insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,1,'true','激活','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,'false','禁用','admin',NULL,NULL,NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,2,'true','启用','admin',NULL,NULL,NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,2,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,3,'true','启用','admin',NULL,NULL,NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,3,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);

/*Table structure for table `sys_job` */

CREATE TABLE `sys_job` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '岗位名称',
    `weight` int(5) DEFAULT NULL COMMENT '排序',
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

/*Data for the table `sys_job` */

insert  into `sys_job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'人事专员',3,NULL,'',NULL,'admin','admin','2019-03-29 14:52:28',NULL);
insert  into `sys_job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'产品经理',4,NULL,'',NULL,'admin','admin','2019-03-29 14:55:51',NULL);
insert  into `sys_job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'全栈开发',2,NULL,'',NULL,'admin','admin','2019-03-31 13:39:30','2020-05-05 11:33:43');
insert  into `sys_job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,'软件测试',5,NULL,'',NULL,'admin','admin','2019-03-31 13:39:43','2020-05-10 19:56:26');

/*Table structure for table `sys_job_role` */

CREATE TABLE `sys_job_role` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id` bigint(20) NOT NULL COMMENT '岗位ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_job_role` (`job_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统岗位/角色关系表';

/*Data for the table `sys_job_role` */

/*Table structure for table `sys_log` */

CREATE TABLE `sys_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `description` varchar(255) DEFAULT NULL COMMENT '日志描述信息',
    `level` varchar(10) DEFAULT NULL COMMENT '日志级别',
    `exception` text COMMENT '异常信息，只有日志级别为ERROR时才有值',
    `method` varchar(255) DEFAULT NULL COMMENT '被调用方法的名称',
    `params` text COMMENT '被调用方法的参数',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `request_ip` varchar(255) DEFAULT NULL COMMENT 'HTTP请求的IP地址',
    `request_location` varchar(255) DEFAULT NULL COMMENT 'HTTP请求的地理地址',
    `request_browser` varchar(255) DEFAULT NULL COMMENT 'HTTP请求的浏览器',
    `request_time` bigint(20) DEFAULT NULL COMMENT 'HTTP请求的耗时',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志记录';

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (1,'更新一条 SysUser 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysUserController.edit()','{\"gender\":\"男\",\"roles\":[{\"id\":2}],\"deptId\":2,\"updateTime\":1599273818000,\"avatar\":\"http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png\",\"dept\":{\"hasChildren\":false,\"weight\":3,\"pid\":7,\"updateTime\":1596350927000,\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476532000,\"name\":\"研发部\",\"id\":2},\"isAdmin\":false,\"enabled\":true,\"jobId\":2,\"password\":\"$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK\",\"createBy\":\"admin\",\"phone\":\"15199999999\",\"updateBy\":\"admin\",\"createTime\":1588648549000,\"name\":\"test\",\"nickname\":\"测试\",\"id\":2,\"job\":{\"weight\":4,\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553842551000,\"name\":\"产品经理\",\"id\":2},\"email\":\"231@qq.com\",\"username\":\"test\"}','admin','127.0.0.1','本机地址','Chrome 91',142,'2021-08-05 17:44:29');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (2,'更新一条 SysDept 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysDeptController.edit()','{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":false,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}','admin','127.0.0.1','本机地址','Chrome 91',52,'2021-08-05 17:45:07');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (3,'更新一条 SysDept 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysDeptController.edit()','{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}','admin','127.0.0.1','本机地址','Chrome 91',49,'2021-08-05 17:45:10');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (4,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":0,\"path\":\"demo/HelloList\",\"component\":\"demo/HelloList\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',252,'2021-09-17 20:54:28');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (5,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":0,\"path\":\"demo/index\",\"component\":\"demo/HelloList\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',67,'2021-09-17 20:55:11');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (6,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',48,'2021-09-17 20:57:51');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (7,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',8,'2021-09-17 20:59:44');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (8,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',22,'2021-09-17 21:09:23');

/*Table structure for table `sys_menu` */

CREATE TABLE `sys_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count` int(5) DEFAULT '0' COMMENT '子菜单数目',
    `type` int(11) DEFAULT NULL COMMENT '菜单类型',
    `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
    `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
    `component` varchar(255) DEFAULT NULL COMMENT '组件',
    `weight` int(5) DEFAULT NULL COMMENT '排序',
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
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,NULL,7,0,'系统管理',NULL,NULL,1,'system','system','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:11:29',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,3,1,'用户管理','User','system/user/index',2,'peoples','user','\0','\0','\0','user:view',NULL,NULL,'2018-12-18 15:14:44',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,1,3,1,'角色管理','Role','system/role/index',3,'role','role','\0','\0','\0','roles:view',NULL,NULL,'2018-12-18 15:16:07',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,1,3,1,'菜单管理','Menu','system/menu/index',5,'menu','menu','\0','\0','\0','menu:view',NULL,NULL,'2018-12-18 15:17:28',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,NULL,5,0,'系统监控',NULL,NULL,10,'monitor','monitor','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:17:48',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,6,0,1,'应用日志','Log','monitor/log/index',11,'log','log','\0','','\0',NULL,NULL,'admin','2018-12-18 15:18:26','2020-06-06 13:11:57');
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (9,6,0,1,'SQL监控','Sql','monitor/sql/index',18,'sqlMonitor','druid','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:19:34',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (10,NULL,5,0,'组件管理',NULL,NULL,50,'zujian','components','\0','\0','\0',NULL,NULL,NULL,'2018-12-19 13:38:16',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (11,10,0,1,'图标库','Icons','components/icons/index',51,'icon','icon','\0','\0','\0',NULL,NULL,NULL,'2018-12-19 13:38:49',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (14,36,0,1,'邮件工具','Email','tools/email/index',35,'email','email','\0','\0','\0',NULL,NULL,NULL,'2018-12-27 10:13:09',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (15,10,0,1,'富文本','Editor','components/Editor',52,'fwb','tinymce','\0','\0','\0',NULL,NULL,NULL,'2018-12-27 11:58:25',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (18,36,3,1,'存储管理','Storage','tools/storage/index',34,'qiniu','storage','\0','\0','\0','storage:view',NULL,NULL,'2018-12-31 11:12:15',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (19,36,0,1,'支付宝工具','AliPay','tools/aliPay/index',37,'alipay','aliPay','\0','\0','\0',NULL,NULL,NULL,'2018-12-31 14:52:38',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (21,NULL,2,0,'多级菜单',NULL,'',900,'menu','nested','\0','\0','\0',NULL,NULL,'admin','2019-01-04 16:22:03','2020-06-21 17:27:35');
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (22,21,2,0,'二级菜单1',NULL,'',999,'menu','menu1','\0','\0','\0',NULL,NULL,'admin','2019-01-04 16:23:29','2020-06-21 17:27:20');
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (23,21,0,1,'二级菜单2',NULL,'nested/menu2/index',999,'menu','menu2','\0','\0','\0',NULL,NULL,NULL,'2019-01-04 16:23:57',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (24,22,0,1,'三级菜单1','Test','nested/menu1/menu1-1',999,'menu','menu1-1','\0','\0','\0',NULL,NULL,NULL,'2019-01-04 16:24:48',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (27,22,0,1,'三级菜单2',NULL,'nested/menu1/menu1-2',999,'menu','menu1-2','\0','\0','\0',NULL,NULL,NULL,'2019-01-07 17:27:32',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (28,1,3,1,'任务调度','Timing','system/timing/index',999,'timing','timing','\0','\0','\0','timing:view',NULL,NULL,'2019-01-07 20:34:40',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (30,36,0,1,'代码生成','CodeIndex','code/generator/index',32,'dev','code','\0','','\0',NULL,NULL,NULL,'2019-01-11 15:45:55',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (33,10,0,1,'Markdown','Markdown','components/MarkDown',53,'markdown','markdown','\0','\0','\0',NULL,NULL,NULL,'2019-03-08 13:46:44',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (34,10,0,1,'Yaml编辑器','YamlEdit','components/YamlEdit',54,'dev','yaml','\0','\0','\0',NULL,NULL,NULL,'2019-03-08 15:49:40',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (35,1,3,1,'部门管理','Dept','system/dept/index',6,'dept','dept','\0','\0','\0','dept:view',NULL,NULL,'2019-03-25 09:46:00',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (36,NULL,7,0,'系统工具',NULL,'',30,'sys-tools','sys-tools','\0','\0','\0',NULL,NULL,NULL,'2019-03-29 10:57:35',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (37,1,3,1,'岗位管理','Job','system/job/index',7,'Steve-Jobs','job','\0','\0','\0','job:view',NULL,NULL,'2019-03-29 13:51:18',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (38,36,0,1,'接口文档','Swagger','tools/swagger/index',36,'swagger','swagger2','\0','\0','\0',NULL,NULL,NULL,'2019-03-29 19:57:53',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (39,1,3,1,'字典管理','Dict','system/dict/index',8,'dictionary','dict','\0','\0','\0','dict:view',NULL,NULL,'2019-04-10 11:49:04',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (41,6,0,1,'在线用户','OnlineUser','monitor/online/index',10,'Steve-Jobs','online','\0','\0','\0',NULL,NULL,NULL,'2019-10-26 22:08:43',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (44,2,0,2,'用户新增',NULL,'',2,'','','\0','\0','\0','user:add',NULL,NULL,'2019-10-29 10:59:46',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (45,2,0,2,'用户编辑',NULL,'',3,'','','\0','\0','\0','user:edit',NULL,NULL,'2019-10-29 11:00:08',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (46,2,0,2,'用户删除',NULL,'',4,'','','\0','\0','\0','user:del',NULL,NULL,'2019-10-29 11:00:23',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (48,3,0,2,'角色创建',NULL,'',2,'','','\0','\0','\0','roles:add',NULL,NULL,'2019-10-29 12:45:34',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (49,3,0,2,'角色修改',NULL,'',3,'','','\0','\0','\0','roles:edit',NULL,NULL,'2019-10-29 12:46:16',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (50,3,0,2,'角色删除',NULL,'',4,'','','\0','\0','\0','roles:del',NULL,NULL,'2019-10-29 12:46:51',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (52,5,0,2,'菜单新增',NULL,'',2,'','','\0','\0','\0','menu:add',NULL,NULL,'2019-10-29 12:55:07',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (53,5,0,2,'菜单编辑',NULL,'',3,'','','\0','\0','\0','menu:edit',NULL,NULL,'2019-10-29 12:55:40',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (54,5,0,2,'菜单删除',NULL,'',4,'','','\0','\0','\0','menu:del',NULL,NULL,'2019-10-29 12:56:00',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (56,35,0,2,'部门新增',NULL,'',2,'','','\0','\0','\0','dept:add',NULL,NULL,'2019-10-29 12:57:09',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (57,35,0,2,'部门编辑',NULL,'',3,'','','\0','\0','\0','dept:edit',NULL,NULL,'2019-10-29 12:57:27',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (58,35,0,2,'部门删除',NULL,'',4,'','','\0','\0','\0','dept:del',NULL,NULL,'2019-10-29 12:57:41',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (60,37,0,2,'岗位新增',NULL,'',2,'','','\0','\0','\0','job:add',NULL,NULL,'2019-10-29 12:58:27',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (61,37,0,2,'岗位编辑',NULL,'',3,'','','\0','\0','\0','job:edit',NULL,NULL,'2019-10-29 12:58:45',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (62,37,0,2,'岗位删除',NULL,'',4,'','','\0','\0','\0','job:del',NULL,NULL,'2019-10-29 12:59:04',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (64,39,0,2,'字典新增',NULL,'',2,'','','\0','\0','\0','dict:add',NULL,NULL,'2019-10-29 13:00:17',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (65,39,0,2,'字典编辑',NULL,'',3,'','','\0','\0','\0','dict:edit',NULL,NULL,'2019-10-29 13:00:42',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (66,39,0,2,'字典删除',NULL,'',4,'','','\0','\0','\0','dict:del',NULL,NULL,'2019-10-29 13:00:59',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (73,28,0,2,'任务新增',NULL,'',2,'','','\0','\0','\0','timing:add',NULL,NULL,'2019-10-29 13:07:28',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (74,28,0,2,'任务编辑',NULL,'',3,'','','\0','\0','\0','timing:edit',NULL,NULL,'2019-10-29 13:07:41',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (75,28,0,2,'任务删除',NULL,'',4,'','','\0','\0','\0','timing:del',NULL,NULL,'2019-10-29 13:07:54',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (77,18,0,2,'上传文件',NULL,'',2,'','','\0','\0','\0','storage:add',NULL,NULL,'2019-10-29 13:09:09',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (78,18,0,2,'文件编辑',NULL,'',3,'','','\0','\0','\0','storage:edit',NULL,NULL,'2019-10-29 13:09:22',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (79,18,0,2,'文件删除',NULL,'',4,'','','\0','\0','\0','storage:del',NULL,NULL,'2019-10-29 13:09:34',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (80,6,0,1,'服务监控','ServerMonitor','monitor/server/index',14,'codeConsole','server','\0','\0','\0','monitor:view',NULL,'admin','2019-11-07 13:06:39','2020-05-04 18:20:50');
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (82,36,0,1,'生成配置','GeneratorConfig','code/generator/config',33,'dev','code/config/:dbId/:schemaName/:tableName','\0','','','',NULL,NULL,'2019-11-17 20:08:56',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (83,10,0,1,'图表库','Echarts','components/Echarts',50,'chart','echarts','\0','','\0','',NULL,NULL,'2019-11-21 09:04:32',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (90,NULL,5,1,'运维管理','Mnt','',20,'mnt','mnt','\0','\0','\0',NULL,NULL,NULL,'2019-11-09 10:31:08',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (92,90,3,1,'服务器','ServerDeploy','mnt/server/index',22,'server','mnt/serverDeploy','\0','\0','\0','serverDeploy:view',NULL,NULL,'2019-11-10 10:29:25',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (93,90,3,1,'应用管理','App','mnt/app/index',23,'app','mnt/app','\0','\0','\0','app:view',NULL,NULL,'2019-11-10 11:05:16',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (94,90,3,1,'部署管理','Deploy','mnt/deploy/index',24,'deploy','mnt/deploy','\0','\0','\0','deploy:view',NULL,NULL,'2019-11-10 15:56:55',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (97,90,1,1,'部署备份','DeployHistory','mnt/deployHistory/index',25,'backup','mnt/deployHistory','\0','\0','\0','deployHistory:view',NULL,NULL,'2019-11-10 16:49:44',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (98,36,3,1,'数据库管理','Database','code/database/index',26,'database','code/database','\0','\0','\0','database:view',NULL,NULL,'2019-11-10 20:40:04',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (102,97,0,2,'删除',NULL,'',999,'','','\0','\0','\0','deployHistory:del',NULL,NULL,'2019-11-17 09:32:48',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (103,92,0,2,'服务器新增',NULL,'',999,'','','\0','\0','\0','serverDeploy:add',NULL,NULL,'2019-11-17 11:08:33',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (104,92,0,2,'服务器编辑',NULL,'',999,'','','\0','\0','\0','serverDeploy:edit',NULL,NULL,'2019-11-17 11:08:57',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (105,92,0,2,'服务器删除',NULL,'',999,'','','\0','\0','\0','serverDeploy:del',NULL,NULL,'2019-11-17 11:09:15',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (106,93,0,2,'应用新增',NULL,'',999,'','','\0','\0','\0','app:add',NULL,NULL,'2019-11-17 11:10:03',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (107,93,0,2,'应用编辑',NULL,'',999,'','','\0','\0','\0','app:edit',NULL,NULL,'2019-11-17 11:10:28',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (108,93,0,2,'应用删除',NULL,'',999,'','','\0','\0','\0','app:del',NULL,NULL,'2019-11-17 11:10:55',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (109,94,0,2,'部署新增',NULL,'',999,'','','\0','\0','\0','deploy:add',NULL,NULL,'2019-11-17 11:11:22',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (110,94,0,2,'部署编辑',NULL,'',999,'','','\0','\0','\0','deploy:edit',NULL,NULL,'2019-11-17 11:11:41',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (111,94,0,2,'部署删除',NULL,'',999,'','','\0','\0','\0','deploy:del',NULL,NULL,'2019-11-17 11:12:01',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (112,98,0,2,'数据库新增',NULL,'',999,'','','\0','\0','\0','database:add',NULL,NULL,'2019-11-17 11:12:43',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (113,98,0,2,'数据库编辑',NULL,'',999,'','','\0','\0','\0','database:edit',NULL,NULL,'2019-11-17 11:12:58',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (114,98,0,2,'数据库删除',NULL,'',999,'','','\0','\0','\0','database:del',NULL,NULL,'2019-11-17 11:13:14',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (116,36,0,1,'生成预览','Preview','code/generator/preview',999,'java','code/preview/:dbId/:schemaName/:tableName','\0','','',NULL,NULL,NULL,'2019-11-26 14:54:36',NULL);
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (117,NULL,1,0,'项目案例',NULL,NULL,50,'demo','demo','\0','\0','\0',NULL,NULL,NULL,'2021-12-19 13:38:16','2021-09-17 21:16:42');
insert  into `sys_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (118,117,0,1,'Hello','Hello','demo/index',50,'','demo/index','\0','\0','\0','demo:view',NULL,'admin','2021-12-19 13:38:16','2021-09-17 21:22:01');

/*Table structure for table `sys_quartz_job` */

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

/*Data for the table `sys_quartz_job` */

insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'testTask','0/5 * * * * ?','','测试1','run1','test','带参测试，多参使用json','测试',NULL,NULL,NULL,NULL,'admin','2019-08-22 14:08:29','2020-05-24 13:58:33');
insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'testTask','0/5 * * * * ?','','测试','run','','不带参测试','Zheng Jie','','5,6','',NULL,'admin','2019-09-26 16:44:39','2020-05-24 14:48:12');
insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,'Test','0/5 * * * * ?','','任务告警测试','run',NULL,'测试','test','',NULL,'','admin','admin','2020-05-05 20:32:41','2020-05-05 20:36:13');
insert  into `sys_quartz_job`(`job_id`,`bean_name`,`cron_expression`,`is_pause`,`job_name`,`method_name`,`params`,`description`,`person_in_charge`,`email`,`sub_task`,`pause_after_failure`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,'testTask','0/5 * * * * ?','','测试3','run2',NULL,'测试3','Zheng Jie','',NULL,'','admin','admin','2020-05-05 20:35:41','2020-05-05 20:36:07');

/*Table structure for table `sys_quartz_log` */

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

/*Data for the table `sys_quartz_log` */

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
    UNIQUE KEY `uk_name` (`name`),
    KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`level`,`data_scope`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'超级管理员',1,'全部','','-',NULL,'admin','2018-11-23 11:04:37','2020-08-06 16:10:24');
insert  into `sys_role`(`id`,`name`,`level`,`data_scope`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'普通用户',2,'本级','','-',NULL,'admin','2018-11-23 13:09:06','2020-09-05 10:45:12');

/*Table structure for table `sys_roles_depts` */

CREATE TABLE `sys_roles_depts` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint(20) NOT NULL,
    `dept_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu` (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';

/*Data for the table `sys_roles_depts` */

/*Table structure for table `sys_roles_menus` */

CREATE TABLE `sys_roles_menus` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

/*Data for the table `sys_roles_menus` */

insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (131,1,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (78,1,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (130,2,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (79,2,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (127,3,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (129,5,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (128,6,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (80,6,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (134,7,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (81,7,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (136,9,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (82,9,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (135,10,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (83,10,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (132,11,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (84,11,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (133,14,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (85,14,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (140,15,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (86,15,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (141,18,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (137,19,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (87,19,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (139,21,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (88,21,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (138,22,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (89,22,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (146,23,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (90,23,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (145,24,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (91,24,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (143,27,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (92,27,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (142,28,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (144,30,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (93,30,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (152,33,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (94,33,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (151,34,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (95,34,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (148,35,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (147,36,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (96,36,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (150,37,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (149,38,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (156,39,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (157,41,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (153,44,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (155,45,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (154,46,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (161,48,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (163,49,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (162,50,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (158,52,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (160,53,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (159,54,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (167,56,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (169,57,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (168,58,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (164,60,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (166,61,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (165,62,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (170,64,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (172,65,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (171,66,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (177,73,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (176,74,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (173,75,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (175,77,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (174,78,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (103,79,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (102,80,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (97,80,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (104,82,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (98,82,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (101,83,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (99,83,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (108,90,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (105,92,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (107,93,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (106,94,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (111,97,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (110,98,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (109,102,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (117,103,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (116,104,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (119,105,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (118,106,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (113,107,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (112,108,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (115,109,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (114,110,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (124,111,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (123,112,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (126,113,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (125,114,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (120,116,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (100,116,2);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (122,117,1);
insert  into `sys_roles_menus`(`id`,`menu_id`,`role_id`) values (121,118,1);

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
    `job_id` bigint(20) DEFAULT NULL COMMENT '岗位ID',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
    `gender` varchar(2) DEFAULT NULL COMMENT '性别',
    `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
    `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    `is_admin` bit(1) DEFAULT b'0' COMMENT '是否为admin账号',
    `enabled` bigint(20) DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新着',
    `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email` (`email`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    KEY `key_dept_id` (`dept_id`) USING BTREE,
    KEY `key_avatar` (`avatar`) USING BTREE,
    KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`is_admin`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (1,2,1,'admin','管理员','男','18888888888','201507802@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa','',1,NULL,'admin','2020-05-03 16:38:31','2018-08-23 09:11:56','2020-09-05 10:43:31');
insert  into `sys_user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`is_admin`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (2,2,2,'test','测试','男','15199999999','231@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK','\0',1,'admin','admin',NULL,'2020-05-05 11:15:49','2020-09-05 10:43:38');

/*Table structure for table `sys_users_roles` */

CREATE TABLE `sys_users_roles` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

/*Data for the table `sys_users_roles` */

insert  into `sys_users_roles`(`id`,`user_id`,`role_id`) values (1,1,1);
insert  into `sys_users_roles`(`id`,`user_id`,`role_id`) values (3,2,2);

/*Table structure for table `tool_alipay_config` */

CREATE TABLE `tool_alipay_config` (
    `config_id` bigint(20) NOT NULL COMMENT 'ID',
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
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付宝配置类';

/*Data for the table `tool_alipay_config` */

insert  into `tool_alipay_config`(`config_id`,`app_id`,`charset`,`format`,`gateway_url`,`notify_url`,`private_key`,`public_key`,`return_url`,`sign_type`,`sys_service_provider_id`) values (1,'2016091700532697','utf-8','JSON','https://openapi.alipaydev.com/gateway.do','http://api.auauz.net/api/aliPay/notify','MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB','http://api.auauz.net/api/aliPay/return','RSA2','2088102176044281');

/*Table structure for table `tool_email_config` */

CREATE TABLE `tool_email_config` (
    `config_id` bigint(20) NOT NULL COMMENT 'ID',
    `from_user` varchar(255) DEFAULT NULL COMMENT '收件人',
    `host` varchar(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
    `pass` varchar(255) DEFAULT NULL COMMENT '密码',
    `port` varchar(255) DEFAULT NULL COMMENT '端口',
    `user` varchar(255) DEFAULT NULL COMMENT '发件者用户名',
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='邮箱配置';

/*Data for the table `tool_email_config` */

/*Table structure for table `tool_local_storage` */

CREATE TABLE `tool_local_storage` (
    `storage_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
    PRIMARY KEY (`storage_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='本地存储';

/*Data for the table `tool_local_storage` */

/*Table structure for table `tool_qiniu_config` */

CREATE TABLE `tool_qiniu_config` (
    `config_id` bigint(20) NOT NULL COMMENT 'ID',
    `access_key` text COMMENT 'accessKey',
    `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `host` varchar(255) NOT NULL COMMENT '外链域名',
    `secret_key` text COMMENT 'secretKey',
    `type` varchar(255) DEFAULT NULL COMMENT '空间类型',
    `zone` varchar(255) DEFAULT NULL COMMENT '机房',
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='七牛云配置';

/*Data for the table `tool_qiniu_config` */

/*Table structure for table `tool_qiniu_content` */

CREATE TABLE `tool_qiniu_content` (
    `content_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
    `size` varchar(255) DEFAULT NULL COMMENT '文件大小',
    `type` varchar(255) DEFAULT NULL COMMENT '文件类型：私有或公开',
    `url` varchar(255) DEFAULT NULL COMMENT '文件url',
    `suffix` varchar(255) DEFAULT NULL COMMENT '文件后缀',
    `update_time` datetime DEFAULT NULL COMMENT '上传或同步的时间',
    PRIMARY KEY (`content_id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='七牛云文件存储';

/*Data for the table `tool_qiniu_content` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
