-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: dunwu_admin
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

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
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-字段级别配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_column_config`
--

LOCK TABLES `code_column_config` WRITE;
/*!40000 ALTER TABLE `code_column_config` DISABLE KEYS */;
INSERT INTO `code_column_config` VALUES (11,NULL,'dunwu_admin','hello','id','ID','bigint(20)','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49'),(12,NULL,'dunwu_admin','hello','name','名字','varchar(255)','String','UNI',_binary '','name','名字',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49'),(13,NULL,'dunwu_admin','hello','age','年龄','smallint(3)','Integer','',_binary '','age','年龄',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49'),(14,NULL,'dunwu_admin','hello','avatar','头像','varchar(255)','String','',_binary '','avatar','头像',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49'),(15,NULL,'dunwu_admin','hello','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-17 20:44:49','2021-09-17 20:44:49'),(31,NULL,'dunwu_cas','sys_log','id','ID','bigint(20) unsigned','Long','PRI',_binary '','id','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(32,NULL,'dunwu_cas','sys_log','content','日志内容','text','String','',_binary '\0','content','日志内容',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(33,NULL,'dunwu_cas','sys_log','level','日志级别','varchar(10)','String','MUL',_binary '\0','level','日志级别',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(34,NULL,'dunwu_cas','sys_log','exception','异常信息，只有日志级别为ERROR时才有值','text','String','',_binary '\0','exception','异常信息，只有日志级别为ERROR时才有值',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(35,NULL,'dunwu_cas','sys_log','class_name','操作的类名','varchar(255)','String','',_binary '','className','操作的类名',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(36,NULL,'dunwu_cas','sys_log','method_name','操作的方法名','varchar(255)','String','MUL',_binary '','methodName','操作的方法名',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(37,NULL,'dunwu_cas','sys_log','params','被调用方法的参数','text','String','',_binary '\0','params','被调用方法的参数',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(38,NULL,'dunwu_cas','sys_log','operator_id','操作者ID','varchar(255)','String','',_binary '\0','operatorId','操作者ID',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(39,NULL,'dunwu_cas','sys_log','operator_name','操作者用户名','varchar(255)','String','',_binary '\0','operatorName','操作者用户名',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(40,NULL,'dunwu_cas','sys_log','server_ip','服务端IP地址','varchar(255)','String','',_binary '\0','serverIp','服务端IP地址',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(41,NULL,'dunwu_cas','sys_log','client_ip','客户端IP地址','varchar(255)','String','',_binary '\0','clientIp','客户端IP地址',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(42,NULL,'dunwu_cas','sys_log','client_location','客户端地理位置','varchar(255)','String','',_binary '\0','clientLocation','客户端地理位置',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(43,NULL,'dunwu_cas','sys_log','client_device','客户端设备','varchar(255)','String','',_binary '\0','clientDevice','客户端设备',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(44,NULL,'dunwu_cas','sys_log','request_time','HTTP请求的耗时','bigint(20) unsigned','Long','',_binary '\0','requestTime','HTTP请求的耗时',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(45,NULL,'dunwu_cas','sys_log','create_time','日志记录时间','datetime','LocalDateTime','MUL',_binary '','createTime','日志记录时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-09-28 23:13:23','2021-09-28 23:13:23'),(46,NULL,'dunwu_admin','mnt_app','app_id','ID','bigint(20)','Long','PRI',_binary '','appId','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(47,NULL,'dunwu_admin','mnt_app','name','应用名称','varchar(255)','String','',_binary '\0','name','应用名称',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(48,NULL,'dunwu_admin','mnt_app','upload_path','上传目录','varchar(255)','String','',_binary '\0','uploadPath','上传目录',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(49,NULL,'dunwu_admin','mnt_app','deploy_path','部署路径','varchar(255)','String','',_binary '\0','deployPath','部署路径',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(50,NULL,'dunwu_admin','mnt_app','backup_path','备份路径','varchar(255)','String','',_binary '\0','backupPath','备份路径',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(51,NULL,'dunwu_admin','mnt_app','port','应用端口','int(255)','Integer','',_binary '\0','port','应用端口',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(52,NULL,'dunwu_admin','mnt_app','start_script','启动脚本','varchar(4000)','String','',_binary '\0','startScript','启动脚本',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(53,NULL,'dunwu_admin','mnt_app','deploy_script','部署脚本','varchar(4000)','String','',_binary '\0','deployScript','部署脚本',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(54,NULL,'dunwu_admin','mnt_app','create_by','创建者','varchar(255)','String','',_binary '\0','createBy','创建者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(55,NULL,'dunwu_admin','mnt_app','update_by','更新者','varchar(255)','String','',_binary '\0','updateBy','更新者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(56,NULL,'dunwu_admin','mnt_app','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(57,NULL,'dunwu_admin','mnt_app','update_time','更新时间','datetime','LocalDateTime','',_binary '\0','updateTime','更新时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:17:25','2021-10-02 15:17:25'),(58,NULL,'dunwu_admin','mnt_deploy','deploy_id','ID','bigint(20)','Long','PRI',_binary '','deployId','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:18:42','2021-10-02 15:18:42'),(59,NULL,'dunwu_admin','mnt_deploy','app_id','应用编号','bigint(20)','Long','MUL',_binary '\0','appId','应用编号',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:18:42','2021-10-02 15:18:42'),(60,NULL,'dunwu_admin','mnt_deploy','create_by','创建者','varchar(255)','String','',_binary '\0','createBy','创建者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:18:42','2021-10-02 15:18:42'),(61,NULL,'dunwu_admin','mnt_deploy','update_by','更新者','varchar(255)','String','',_binary '\0','updateBy','更新者',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:18:42','2021-10-02 15:18:42'),(62,NULL,'dunwu_admin','mnt_deploy','create_time','创建时间','datetime','LocalDateTime','',_binary '\0','createTime','创建时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:18:42','2021-10-02 15:18:42'),(63,NULL,'dunwu_admin','mnt_deploy','update_time','更新时间','datetime','LocalDateTime','',_binary '\0','updateTime','更新时间',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:18:42','2021-10-02 15:18:42'),(64,NULL,'dunwu_admin','mnt_deploy_history','history_id','ID','varchar(50)','String','PRI',_binary '','historyId','ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:19:32','2021-10-02 15:19:32'),(65,NULL,'dunwu_admin','mnt_deploy_history','app_name','应用名称','varchar(255)','String','',_binary '','appName','应用名称',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:19:32','2021-10-02 15:19:32'),(66,NULL,'dunwu_admin','mnt_deploy_history','deploy_date','部署日期','datetime','LocalDateTime','',_binary '','deployDate','部署日期',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:19:32','2021-10-02 15:19:32'),(67,NULL,'dunwu_admin','mnt_deploy_history','deploy_user','部署用户','varchar(50)','String','',_binary '','deployUser','部署用户',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:19:32','2021-10-02 15:19:32'),(68,NULL,'dunwu_admin','mnt_deploy_history','ip','服务器IP','varchar(20)','String','',_binary '','ip','服务器IP',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:19:32','2021-10-02 15:19:32'),(69,NULL,'dunwu_admin','mnt_deploy_history','deploy_id','部署编号','bigint(20)','Long','',_binary '\0','deployId','部署编号',_binary '',_binary '',_binary '',_binary '\0',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:19:32','2021-10-02 15:19:32'),(70,NULL,'dunwu_admin','mnt_deploy_server','deploy_id','部署ID','bigint(20)','Long','PRI',_binary '','deployId','部署ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:20:28','2021-10-02 15:20:28'),(71,NULL,'dunwu_admin','mnt_deploy_server','server_id','服务ID','bigint(20)','Long','PRI',_binary '','serverId','服务ID',_binary '\0',_binary '',_binary '',_binary '',_binary '\0','Input','Text','EQUALS','asc','string',NULL,'yyyy-MM-dd HH:mm:ss',NULL,NULL,NULL,'admin','admin','2021-10-02 15:20:28','2021-10-02 15:20:28');
/*!40000 ALTER TABLE `code_column_config` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `code_database` WRITE;
/*!40000 ALTER TABLE `code_database` DISABLE KEYS */;
INSERT INTO `code_database` VALUES (1,'dunwu_admin','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_admin','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50'),(2,'dunwu_cas','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_cas?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_cas','admin','admin','2021-08-05 17:26:50','2021-08-05 17:26:50');
/*!40000 ALTER TABLE `code_database` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `code_global_config` WRITE;
/*!40000 ALTER TABLE `code_global_config` DISABLE KEYS */;
INSERT INTO `code_global_config` VALUES (1,_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
/*!40000 ALTER TABLE `code_global_config` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='代码生成-表级别配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_table_config`
--

LOCK TABLES `code_table_config` WRITE;
/*!40000 ALTER TABLE `code_table_config` DISABLE KEYS */;
INSERT INTO `code_table_config` VALUES (1,1,'dunwu_admin','hello','测试',_binary '\0',_binary '',_binary '','zp','D://',NULL,NULL,'io.github.dunwu.module','NONE','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','demo',NULL,'hello','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16'),(2,2,'dunwu_cas','sys_log','系统日志记录',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','sys','sys_','log','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16'),(3,1,'dunwu_admin','mnt_app','应用配置',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','app','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16'),(4,1,'dunwu_admin','mnt_deploy','部署管理',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','deploy','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16'),(5,1,'dunwu_admin','mnt_deploy_history','部署历史管理',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','deploy/history','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16'),(6,1,'dunwu_admin','mnt_deploy_server','应用与服务器关联',_binary '',_binary '',_binary '','<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>','D://Codes//zpproj//dunwu-security//cas-demo//cas-security-common','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','D://Codes//zpproj//dunwu-boot-admin//dunwu-admin//dunwu-admin-modules//dunwu-admin-module-mnt','io.github.dunwu.module','AUTO','TIME_PACK','yyyy-MM-dd HH:mm:ss',_binary '',_binary '',_binary '',_binary '',_binary '','mnt','mnt_','deploy/server','admin','admin','2021-09-17 20:44:16','2021-09-17 20:44:16');
/*!40000 ALTER TABLE `code_table_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_record`
--

DROP TABLE IF EXISTS `file_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_record`
--

LOCK TABLES `file_record` WRITE;
/*!40000 ALTER TABLE `file_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_record` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `hello` WRITE;
/*!40000 ALTER TABLE `hello` DISABLE KEYS */;
INSERT INTO `hello` VALUES (1,'fasdfas',12,'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','2021-03-01 21:12:57'),(2,'abc',11,'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','2021-03-15 21:50:16');
/*!40000 ALTER TABLE `hello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnt_app`
--

DROP TABLE IF EXISTS `mnt_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_app` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_app`
--

LOCK TABLES `mnt_app` WRITE;
/*!40000 ALTER TABLE `mnt_app` DISABLE KEYS */;
/*!40000 ALTER TABLE `mnt_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnt_database`
--

DROP TABLE IF EXISTS `mnt_database`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_database`
--

LOCK TABLES `mnt_database` WRITE;
/*!40000 ALTER TABLE `mnt_database` DISABLE KEYS */;
INSERT INTO `mnt_database` VALUES (1,'dunwu_admin','localhost',3306,'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false','root','root','dunwu_admin','admin','admin','2021-03-12 19:16:03','2021-03-12 19:16:03');
/*!40000 ALTER TABLE `mnt_database` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnt_deploy`
--

DROP TABLE IF EXISTS `mnt_deploy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_deploy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` bigint(20) DEFAULT NULL COMMENT '应用编号',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk6sy157pseoxx4fmcqr1vnvvhy` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_deploy`
--

LOCK TABLES `mnt_deploy` WRITE;
/*!40000 ALTER TABLE `mnt_deploy` DISABLE KEYS */;
/*!40000 ALTER TABLE `mnt_deploy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnt_deploy_history`
--

DROP TABLE IF EXISTS `mnt_deploy_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_deploy_history` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `app_name` varchar(255) NOT NULL COMMENT '应用名称',
  `deploy_date` datetime NOT NULL COMMENT '部署日期',
  `deploy_user` varchar(50) NOT NULL COMMENT '部署用户',
  `ip` varchar(20) NOT NULL COMMENT '服务器IP',
  `deploy_id` bigint(20) DEFAULT NULL COMMENT '部署编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部署历史管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_deploy_history`
--

LOCK TABLES `mnt_deploy_history` WRITE;
/*!40000 ALTER TABLE `mnt_deploy_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `mnt_deploy_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnt_deploy_server_map`
--

DROP TABLE IF EXISTS `mnt_deploy_server_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_deploy_server_map` (
  `deploy_id` bigint(20) NOT NULL COMMENT '部署ID',
  `server_id` bigint(20) NOT NULL COMMENT '服务ID',
  PRIMARY KEY (`deploy_id`,`server_id`) USING BTREE,
  KEY `fkeaaha7jew9a02b3bk9ghols53` (`server_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='应用与服务器关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_deploy_server_map`
--

LOCK TABLES `mnt_deploy_server_map` WRITE;
/*!40000 ALTER TABLE `mnt_deploy_server_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `mnt_deploy_server_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mnt_server`
--

DROP TABLE IF EXISTS `mnt_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mnt_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `port` int(11) DEFAULT NULL COMMENT '端口',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='服务器管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mnt_server`
--

LOCK TABLES `mnt_server` WRITE;
/*!40000 ALTER TABLE `mnt_server` DISABLE KEYS */;
/*!40000 ALTER TABLE `mnt_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (2,7,1,'研发部',3,_binary '',NULL,'admin','admin','2019-03-25 09:15:32','2020-08-02 14:48:47'),(5,7,0,'运维部',4,_binary '',NULL,'admin','admin','2019-03-25 09:20:44','2020-05-17 14:27:27'),(6,8,0,'测试部',6,_binary '',NULL,'admin','admin','2019-03-25 09:52:18','2020-06-08 11:59:21'),(7,0,2,'华南分部',0,_binary '',NULL,'admin','admin','2019-03-25 11:04:50','2020-06-08 12:08:56'),(8,0,2,'华北分部',1,_binary '',NULL,'admin','admin','2019-03-25 11:04:53','2020-05-14 12:54:00'),(15,8,0,'UI部门',7,_binary '',NULL,'admin','admin','2020-05-13 22:56:53','2020-05-14 12:54:13'),(17,2,0,'研发一组',999,_binary '',NULL,'admin','admin','2020-08-02 14:49:07','2020-08-02 14:49:07');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'user_status','用户状态',_binary '','用户状态','admin',NULL,'2019-10-27 20:31:36',NULL),(2,'dept_status','部门状态',_binary '','部门状态','admin',NULL,'2019-10-27 20:31:36',NULL),(3,'job_status','岗位状态',_binary '','岗位状态','admin',NULL,'2019-10-27 20:31:36',NULL);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_option`
--

DROP TABLE IF EXISTS `sys_dict_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_option`
--

LOCK TABLES `sys_dict_option` WRITE;
/*!40000 ALTER TABLE `sys_dict_option` DISABLE KEYS */;
INSERT INTO `sys_dict_option` VALUES (1,1,'true','激活','admin',NULL,'2019-10-27 20:31:36',NULL),(2,1,'false','禁用','admin',NULL,NULL,NULL),(3,2,'true','启用','admin',NULL,NULL,NULL),(4,2,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL),(5,3,'true','启用','admin',NULL,NULL,NULL),(6,3,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);
/*!40000 ALTER TABLE `sys_dict_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'人事专员',3,NULL,_binary '',NULL,'admin','admin','2019-03-29 14:52:28',NULL),(2,'产品经理',4,NULL,_binary '',NULL,'admin','admin','2019-03-29 14:55:51',NULL),(3,'全栈开发',2,NULL,_binary '',NULL,'admin','admin','2019-03-31 13:39:30','2020-05-05 11:33:43'),(4,'软件测试',5,NULL,_binary '',NULL,'admin','admin','2019-03-31 13:39:43','2020-05-10 19:56:26');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_role`
--

DROP TABLE IF EXISTS `sys_job_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_job_role` (`job_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统岗位/角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_role`
--

LOCK TABLES `sys_job_role` WRITE;
/*!40000 ALTER TABLE `sys_job_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_role` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (3,'INFO','','【】更新一条 SysDept 记录',NULL,'io.github.dunwu.module.cas.controller.SysDeptController','edit','{\"hasChildren\":true,\"weight\":0,\"pid\":0,\"updateTime\":1591589336000,\"label\":\"华南分部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553483090000,\"children\":[{\"hasChildren\":true,\"weight\":3,\"pid\":7,\"updateTime\":1596350927000,\"label\":\"研发部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476532000,\"children\":[{\"hasChildren\":false,\"weight\":999,\"pid\":2,\"updateTime\":1596350947000,\"label\":\"研发一组\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1596350947000,\"name\":\"研发一组\",\"id\":17}],\"name\":\"研发部\",\"id\":2},{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}],\"name\":\"华南分部\",\"id\":7}','',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 93',19,'2021-09-29 23:26:22'),(4,'INFO','','admin更新一条 SysDept 记录',NULL,'io.github.dunwu.module.cas.controller.SysDeptController','edit','{\"hasChildren\":true,\"weight\":0,\"pid\":0,\"updateTime\":1591589336000,\"label\":\"华南分部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553483090000,\"children\":[{\"hasChildren\":true,\"weight\":3,\"pid\":7,\"updateTime\":1596350927000,\"label\":\"研发部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476532000,\"children\":[{\"hasChildren\":false,\"weight\":999,\"pid\":2,\"updateTime\":1596350947000,\"label\":\"研发一组\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1596350947000,\"name\":\"研发一组\",\"id\":17}],\"name\":\"研发部\",\"id\":2},{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}],\"name\":\"华南分部\",\"id\":7}','',1,'admin','192.168.1.4','127.0.0.1','本机地址','Chrome 93',18,'2021-09-29 23:36:47');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,NULL,7,0,'系统管理',NULL,NULL,1,'system','system',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-18 15:11:29',NULL),(2,1,3,1,'用户管理','User','system/user/index',2,'peoples','user',_binary '\0',_binary '\0',_binary '\0','user:view',NULL,NULL,'2018-12-18 15:14:44',NULL),(3,1,3,1,'角色管理','Role','system/role/index',3,'role','role',_binary '\0',_binary '\0',_binary '\0','roles:view',NULL,NULL,'2018-12-18 15:16:07',NULL),(5,1,3,1,'菜单管理','Menu','system/menu/index',5,'menu','menu',_binary '\0',_binary '\0',_binary '\0','menu:view',NULL,NULL,'2018-12-18 15:17:28',NULL),(6,NULL,5,0,'系统监控',NULL,NULL,10,'monitor','monitor',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-18 15:17:48',NULL),(7,6,0,1,'应用日志','Log','monitor/log/index',11,'log','log',_binary '\0',_binary '',_binary '\0',NULL,NULL,'admin','2018-12-18 15:18:26','2020-06-06 13:11:57'),(9,6,0,1,'SQL监控','Sql','monitor/sql/index',18,'sqlMonitor','druid',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-18 15:19:34',NULL),(10,NULL,5,0,'组件管理',NULL,NULL,50,'zujian','components',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-19 13:38:16',NULL),(11,10,0,1,'图标库','Icons','components/icons/index',51,'icon','icon',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-19 13:38:49',NULL),(14,36,0,1,'邮件工具','Email','tools/email/index',35,'email','email',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-27 10:13:09',NULL),(15,10,0,1,'富文本','Editor','components/Editor',52,'fwb','tinymce',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-27 11:58:25',NULL),(18,36,3,1,'存储管理','Storage','tools/storage/index',34,'qiniu','storage',_binary '\0',_binary '\0',_binary '\0','storage:view',NULL,NULL,'2018-12-31 11:12:15',NULL),(19,36,0,1,'支付宝工具','AliPay','tools/aliPay/index',37,'alipay','aliPay',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2018-12-31 14:52:38',NULL),(21,NULL,2,0,'多级菜单',NULL,'',900,'menu','nested',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2019-01-04 16:22:03','2020-06-21 17:27:35'),(22,21,2,0,'二级菜单1',NULL,'',999,'menu','menu1',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,'admin','2019-01-04 16:23:29','2020-06-21 17:27:20'),(23,21,0,1,'二级菜单2',NULL,'nested/menu2/index',999,'menu','menu2',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-01-04 16:23:57',NULL),(24,22,0,1,'三级菜单1','Test','nested/menu1/menu1-1',999,'menu','menu1-1',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-01-04 16:24:48',NULL),(27,22,0,1,'三级菜单2',NULL,'nested/menu1/menu1-2',999,'menu','menu1-2',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-01-07 17:27:32',NULL),(28,1,3,1,'任务调度','Timing','system/timing/index',999,'timing','timing',_binary '\0',_binary '\0',_binary '\0','timing:view',NULL,NULL,'2019-01-07 20:34:40',NULL),(30,36,0,1,'代码生成','CodeIndex','code/generator/index',32,'dev','code',_binary '\0',_binary '',_binary '\0',NULL,NULL,NULL,'2019-01-11 15:45:55',NULL),(33,10,0,1,'Markdown','Markdown','components/MarkDown',53,'markdown','markdown',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-03-08 13:46:44',NULL),(34,10,0,1,'Yaml编辑器','YamlEdit','components/YamlEdit',54,'dev','yaml',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-03-08 15:49:40',NULL),(35,1,3,1,'部门管理','Dept','system/dept/index',6,'dept','dept',_binary '\0',_binary '\0',_binary '\0','dept:view',NULL,NULL,'2019-03-25 09:46:00',NULL),(36,NULL,7,0,'系统工具',NULL,'',30,'sys-tools','sys-tools',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-03-29 10:57:35',NULL),(37,1,3,1,'岗位管理','Job','system/job/index',7,'Steve-Jobs','job',_binary '\0',_binary '\0',_binary '\0','job:view',NULL,NULL,'2019-03-29 13:51:18',NULL),(38,36,0,1,'接口文档','Swagger','tools/swagger/index',36,'swagger','swagger2',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-03-29 19:57:53',NULL),(39,1,3,1,'字典管理','Dict','system/dict/index',8,'dictionary','dict',_binary '\0',_binary '\0',_binary '\0','dict:view',NULL,NULL,'2019-04-10 11:49:04',NULL),(41,6,0,1,'在线用户','OnlineUser','monitor/online/index',10,'Steve-Jobs','online',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-10-26 22:08:43',NULL),(44,2,0,2,'用户新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','user:add',NULL,NULL,'2019-10-29 10:59:46',NULL),(45,2,0,2,'用户编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','user:edit',NULL,NULL,'2019-10-29 11:00:08',NULL),(46,2,0,2,'用户删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','user:del',NULL,NULL,'2019-10-29 11:00:23',NULL),(48,3,0,2,'角色创建',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','roles:add',NULL,NULL,'2019-10-29 12:45:34',NULL),(49,3,0,2,'角色修改',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','roles:edit',NULL,NULL,'2019-10-29 12:46:16',NULL),(50,3,0,2,'角色删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','roles:del',NULL,NULL,'2019-10-29 12:46:51',NULL),(52,5,0,2,'菜单新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','menu:add',NULL,NULL,'2019-10-29 12:55:07',NULL),(53,5,0,2,'菜单编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','menu:edit',NULL,NULL,'2019-10-29 12:55:40',NULL),(54,5,0,2,'菜单删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','menu:del',NULL,NULL,'2019-10-29 12:56:00',NULL),(56,35,0,2,'部门新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','dept:add',NULL,NULL,'2019-10-29 12:57:09',NULL),(57,35,0,2,'部门编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','dept:edit',NULL,NULL,'2019-10-29 12:57:27',NULL),(58,35,0,2,'部门删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','dept:del',NULL,NULL,'2019-10-29 12:57:41',NULL),(60,37,0,2,'岗位新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','job:add',NULL,NULL,'2019-10-29 12:58:27',NULL),(61,37,0,2,'岗位编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','job:edit',NULL,NULL,'2019-10-29 12:58:45',NULL),(62,37,0,2,'岗位删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','job:del',NULL,NULL,'2019-10-29 12:59:04',NULL),(64,39,0,2,'字典新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','dict:add',NULL,NULL,'2019-10-29 13:00:17',NULL),(65,39,0,2,'字典编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','dict:edit',NULL,NULL,'2019-10-29 13:00:42',NULL),(66,39,0,2,'字典删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','dict:del',NULL,NULL,'2019-10-29 13:00:59',NULL),(73,28,0,2,'任务新增',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','timing:add',NULL,NULL,'2019-10-29 13:07:28',NULL),(74,28,0,2,'任务编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','timing:edit',NULL,NULL,'2019-10-29 13:07:41',NULL),(75,28,0,2,'任务删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','timing:del',NULL,NULL,'2019-10-29 13:07:54',NULL),(77,18,0,2,'上传文件',NULL,'',2,'','',_binary '\0',_binary '\0',_binary '\0','storage:add',NULL,NULL,'2019-10-29 13:09:09',NULL),(78,18,0,2,'文件编辑',NULL,'',3,'','',_binary '\0',_binary '\0',_binary '\0','storage:edit',NULL,NULL,'2019-10-29 13:09:22',NULL),(79,18,0,2,'文件删除',NULL,'',4,'','',_binary '\0',_binary '\0',_binary '\0','storage:del',NULL,NULL,'2019-10-29 13:09:34',NULL),(80,6,0,1,'服务监控','ServerMonitor','monitor/server/index',14,'codeConsole','server',_binary '\0',_binary '\0',_binary '\0','monitor:view',NULL,'admin','2019-11-07 13:06:39','2020-05-04 18:20:50'),(82,36,0,1,'生成配置','GeneratorConfig','code/generator/config',33,'dev','code/config/:dbId/:schemaName/:tableName',_binary '\0',_binary '',_binary '','',NULL,NULL,'2019-11-17 20:08:56',NULL),(83,10,0,1,'图表库','Echarts','components/Echarts',50,'chart','echarts',_binary '\0',_binary '',_binary '\0','',NULL,NULL,'2019-11-21 09:04:32',NULL),(90,NULL,5,1,'运维管理','Mnt','',20,'mnt','mnt',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2019-11-09 10:31:08',NULL),(92,90,3,1,'服务器','ServerDeploy','mnt/server/index',22,'server','mnt/serverDeploy',_binary '\0',_binary '\0',_binary '\0','serverDeploy:view',NULL,NULL,'2019-11-10 10:29:25',NULL),(93,90,3,1,'应用配置','App','mnt/app/index',23,'app','mnt/app',_binary '\0',_binary '\0',_binary '\0','app:view',NULL,NULL,'2019-11-10 11:05:16',NULL),(94,90,3,1,'部署管理','Deploy','mnt/deploy/index',24,'deploy','mnt/deploy',_binary '\0',_binary '\0',_binary '\0','deploy:view',NULL,NULL,'2019-11-10 15:56:55',NULL),(97,90,1,1,'部署备份','DeployHistory','mnt/deployHistory/index',25,'backup','mnt/deployHistory',_binary '\0',_binary '\0',_binary '\0','deployHistory:view',NULL,NULL,'2019-11-10 16:49:44',NULL),(98,36,3,1,'数据库管理','Database','code/database/index',26,'database','code/database',_binary '\0',_binary '\0',_binary '\0','database:view',NULL,NULL,'2019-11-10 20:40:04',NULL),(102,97,0,2,'删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deployHistory:del',NULL,NULL,'2019-11-17 09:32:48',NULL),(103,92,0,2,'服务器新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','serverDeploy:add',NULL,NULL,'2019-11-17 11:08:33',NULL),(104,92,0,2,'服务器编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','serverDeploy:edit',NULL,NULL,'2019-11-17 11:08:57',NULL),(105,92,0,2,'服务器删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','serverDeploy:del',NULL,NULL,'2019-11-17 11:09:15',NULL),(106,93,0,2,'应用新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','app:add',NULL,NULL,'2019-11-17 11:10:03',NULL),(107,93,0,2,'应用编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','app:edit',NULL,NULL,'2019-11-17 11:10:28',NULL),(108,93,0,2,'应用删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','app:del',NULL,NULL,'2019-11-17 11:10:55',NULL),(109,94,0,2,'部署新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deploy:add',NULL,NULL,'2019-11-17 11:11:22',NULL),(110,94,0,2,'部署编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deploy:edit',NULL,NULL,'2019-11-17 11:11:41',NULL),(111,94,0,2,'部署删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','deploy:del',NULL,NULL,'2019-11-17 11:12:01',NULL),(112,98,0,2,'数据库新增',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','database:add',NULL,NULL,'2019-11-17 11:12:43',NULL),(113,98,0,2,'数据库编辑',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','database:edit',NULL,NULL,'2019-11-17 11:12:58',NULL),(114,98,0,2,'数据库删除',NULL,'',999,'','',_binary '\0',_binary '\0',_binary '\0','database:del',NULL,NULL,'2019-11-17 11:13:14',NULL),(116,36,0,1,'生成预览','Preview','code/generator/preview',999,'java','code/preview/:dbId/:schemaName/:tableName',_binary '\0',_binary '',_binary '',NULL,NULL,NULL,'2019-11-26 14:54:36',NULL),(117,NULL,1,0,'项目案例',NULL,NULL,50,'demo','demo',_binary '\0',_binary '\0',_binary '\0',NULL,NULL,NULL,'2021-12-19 13:38:16','2021-09-17 21:16:42'),(118,117,0,1,'Hello','Hello','demo/index',50,'','demo/index',_binary '\0',_binary '\0',_binary '\0','demo:view',NULL,'admin','2021-12-19 13:38:16','2021-09-17 21:22:01');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `sys_quartz_job` WRITE;
/*!40000 ALTER TABLE `sys_quartz_job` DISABLE KEYS */;
INSERT INTO `sys_quartz_job` VALUES (2,'testTask','0/5 * * * * ?',_binary '','测试1','run1','test','带参测试，多参使用json','测试',NULL,NULL,NULL,NULL,'admin','2019-08-22 14:08:29','2020-05-24 13:58:33'),(3,'testTask','0/5 * * * * ?',_binary '','测试','run','','不带参测试','Zheng Jie','','5,6',_binary '',NULL,'admin','2019-09-26 16:44:39','2020-05-24 14:48:12'),(5,'Test','0/5 * * * * ?',_binary '','任务告警测试','run',NULL,'测试','test','',NULL,_binary '','admin','admin','2020-05-05 20:32:41','2020-05-05 20:36:13'),(6,'testTask','0/5 * * * * ?',_binary '','测试3','run2',NULL,'测试3','Zheng Jie','',NULL,_binary '','admin','admin','2020-05-05 20:35:41','2020-05-05 20:36:07');
/*!40000 ALTER TABLE `sys_quartz_job` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `sys_quartz_log` WRITE;
/*!40000 ALTER TABLE `sys_quartz_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_quartz_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员',1,'全部',_binary '','-',NULL,'admin','2018-11-23 11:04:37','2020-08-06 16:10:24'),(2,'普通用户',2,'本级',_binary '','-',NULL,'admin','2018-11-23 13:09:06','2020-09-05 10:45:12');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_roles_depts`
--

DROP TABLE IF EXISTS `sys_roles_depts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_roles_depts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_role_menu` (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_roles_depts`
--

LOCK TABLES `sys_roles_depts` WRITE;
/*!40000 ALTER TABLE `sys_roles_depts` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_roles_depts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_roles_menus`
--

DROP TABLE IF EXISTS `sys_roles_menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_roles_menus` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_role_menu` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_roles_menus`
--

LOCK TABLES `sys_roles_menus` WRITE;
/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
INSERT INTO `sys_roles_menus` VALUES (131,1,1),(78,1,2),(130,2,1),(79,2,2),(127,3,1),(129,5,1),(128,6,1),(80,6,2),(134,7,1),(81,7,2),(136,9,1),(82,9,2),(135,10,1),(83,10,2),(132,11,1),(84,11,2),(133,14,1),(85,14,2),(140,15,1),(86,15,2),(141,18,1),(137,19,1),(87,19,2),(139,21,1),(88,21,2),(138,22,1),(89,22,2),(146,23,1),(90,23,2),(145,24,1),(91,24,2),(143,27,1),(92,27,2),(142,28,1),(144,30,1),(93,30,2),(152,33,1),(94,33,2),(151,34,1),(95,34,2),(148,35,1),(147,36,1),(96,36,2),(150,37,1),(149,38,1),(156,39,1),(157,41,1),(153,44,1),(155,45,1),(154,46,1),(161,48,1),(163,49,1),(162,50,1),(158,52,1),(160,53,1),(159,54,1),(167,56,1),(169,57,1),(168,58,1),(164,60,1),(166,61,1),(165,62,1),(170,64,1),(172,65,1),(171,66,1),(177,73,1),(176,74,1),(173,75,1),(175,77,1),(174,78,1),(103,79,1),(102,80,1),(97,80,2),(104,82,1),(98,82,2),(101,83,1),(99,83,2),(108,90,1),(105,92,1),(107,93,1),(106,94,1),(111,97,1),(110,98,1),(109,102,1),(117,103,1),(116,104,1),(119,105,1),(118,106,1),(113,107,1),(112,108,1),(115,109,1),(114,110,1),(124,111,1),(123,112,1),(126,113,1),(125,114,1),(120,116,1),(100,116,2),(122,117,1),(121,118,1);
/*!40000 ALTER TABLE `sys_roles_menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,2,1,'admin','管理员','男','18888888888','201507802@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa',_binary '',1,NULL,'admin','2020-05-03 16:38:31','2018-08-23 09:11:56','2020-09-05 10:43:31'),(2,2,2,'test','测试','男','15199999999','231@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK',_binary '\0',1,'admin','admin',NULL,'2020-05-05 11:15:49','2020-09-05 10:43:38');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_users_roles`
--

DROP TABLE IF EXISTS `sys_users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_users_roles` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_users_roles`
--

LOCK TABLES `sys_users_roles` WRITE;
/*!40000 ALTER TABLE `sys_users_roles` DISABLE KEYS */;
INSERT INTO `sys_users_roles` VALUES (1,1,1),(3,2,2);
/*!40000 ALTER TABLE `sys_users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_alipay_config`
--

DROP TABLE IF EXISTS `tool_alipay_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_alipay_config`
--

LOCK TABLES `tool_alipay_config` WRITE;
/*!40000 ALTER TABLE `tool_alipay_config` DISABLE KEYS */;
INSERT INTO `tool_alipay_config` VALUES (1,'2016091700532697','utf-8','JSON','https://openapi.alipaydev.com/gateway.do','http://api.auauz.net/api/aliPay/notify','MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB','http://api.auauz.net/api/aliPay/return','RSA2','2088102176044281');
/*!40000 ALTER TABLE `tool_alipay_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_email_config`
--

DROP TABLE IF EXISTS `tool_email_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tool_email_config` (
  `config_id` bigint(20) NOT NULL COMMENT 'ID',
  `from_user` varchar(255) DEFAULT NULL COMMENT '收件人',
  `host` varchar(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `pass` varchar(255) DEFAULT NULL COMMENT '密码',
  `port` varchar(255) DEFAULT NULL COMMENT '端口',
  `user` varchar(255) DEFAULT NULL COMMENT '发件者用户名',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='邮箱配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_email_config`
--

LOCK TABLES `tool_email_config` WRITE;
/*!40000 ALTER TABLE `tool_email_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_email_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_local_storage`
--

DROP TABLE IF EXISTS `tool_local_storage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_local_storage`
--

LOCK TABLES `tool_local_storage` WRITE;
/*!40000 ALTER TABLE `tool_local_storage` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_local_storage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_qiniu_config`
--

DROP TABLE IF EXISTS `tool_qiniu_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_qiniu_config`
--

LOCK TABLES `tool_qiniu_config` WRITE;
/*!40000 ALTER TABLE `tool_qiniu_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_qiniu_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_qiniu_content`
--

DROP TABLE IF EXISTS `tool_qiniu_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_qiniu_content`
--

LOCK TABLES `tool_qiniu_content` WRITE;
/*!40000 ALTER TABLE `tool_qiniu_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_qiniu_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-02 15:46:47
