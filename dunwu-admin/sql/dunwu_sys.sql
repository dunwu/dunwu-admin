-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: dunwu_admin
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @`old_character_set_client` = @@`character_set_client` */;
/*!40101 SET @`old_character_set_results` = @@`character_set_results` */;
/*!40101 SET @`old_collation_connection` = @@`collation_connection` */;
SET NAMES utf8;
/*!40014 SET @`old_unique_checks` = @@`unique_checks`, UNIQUE_CHECKS = 0 */;
/*!40014 SET @`old_foreign_key_checks` = @@`foreign_key_checks`, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @`old_sql_mode` = @@`sql_mode`, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @`old_sql_notes` = @@`sql_notes`, SQL_NOTES = 0 */;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `sys_dict` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        VARCHAR(255)        NOT NULL COMMENT '字典编码',
    `name`        VARCHAR(255)        NOT NULL COMMENT '字典名称',
    `is_disabled` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='数据字典';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_dict`
--

INSERT INTO `sys_dict`
VALUES (1, 'disabled_status', '是否禁用状态', 0, '是否禁用状态', 'admin', 'admin', '2019-10-27 20:31:36', '2021-10-10 12:11:37');
INSERT INTO `sys_dict`
VALUES (2, 'job_type', '职务类型', 0, NULL, 'admin', 'admin', '2021-10-10 12:24:55', '2021-10-10 12:27:02');
INSERT INTO `sys_dict`
VALUES (3, 'job_profession_level', '职级', 0, NULL, 'admin', 'admin', '2021-10-10 14:10:57', '2021-10-10 14:10:57');

--
-- Table structure for table `sys_dict_option`
--

DROP TABLE IF EXISTS `sys_dict_option`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `sys_dict_option` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dict_id`     BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '字典id',
    `code`        VARCHAR(255)        NOT NULL COMMENT '字典选项编码',
    `name`        VARCHAR(255)        NOT NULL COMMENT '字典选项名称',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_dict_id`(`dict_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='数据字典选项';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_dict_option`
--

INSERT INTO `sys_dict_option`
VALUES (1, 1, 'false', '启用', 'admin', 'admin', '2019-10-27 20:31:36', '2021-10-05 18:48:04');
INSERT INTO `sys_dict_option`
VALUES (2, 1, 'true', '禁用', 'admin', 'admin', '2019-10-27 20:31:36', '2021-10-05 18:48:04');
INSERT INTO `sys_dict_option`
VALUES (3, 2, '1', '专业岗位', 'admin', 'admin', '2021-10-10 12:25:46', '2021-10-10 12:26:54');
INSERT INTO `sys_dict_option`
VALUES (4, 2, '2', '管理岗位', 'admin', 'admin', '2021-10-10 12:34:36', '2021-10-10 12:34:58');
INSERT INTO `sys_dict_option`
VALUES (5, 3, '1', '初级', 'admin', 'admin', '2021-10-10 14:14:12', '2021-10-10 14:14:12');
INSERT INTO `sys_dict_option`
VALUES (6, 3, '2', '中级', 'admin', 'admin', '2021-10-10 14:14:19', '2021-10-10 14:14:19');
INSERT INTO `sys_dict_option`
VALUES (7, 3, '3', '高级', 'admin', 'admin', '2021-10-10 14:14:25', '2021-10-10 14:14:25');
INSERT INTO `sys_dict_option`
VALUES (8, 3, '4', '专家', 'admin', 'admin', '2021-10-10 14:14:32', '2021-10-10 14:14:32');
INSERT INTO `sys_dict_option`
VALUES (9, 3, '5', '高级专家', 'admin', 'admin', '2021-10-10 14:14:45', '2021-10-10 14:14:45');
INSERT INTO `sys_dict_option`
VALUES (10, 3, '6', '资深专家', 'admin', 'admin', '2021-10-10 14:15:02', '2021-10-10 14:15:02');

--
-- Table structure for table `sys_global_config`
--

DROP TABLE IF EXISTS `sys_global_config`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `sys_global_config` (
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_code`      VARCHAR(255)  DEFAULT NULL COMMENT '应用编码',
    `module_code`   VARCHAR(255)  DEFAULT NULL COMMENT '模块编码',
    `namespace`     VARCHAR(255)  DEFAULT NULL COMMENT '命名空间',
    `code`          VARCHAR(255)        NOT NULL COMMENT '配置项编码',
    `name`          VARCHAR(255)        NOT NULL COMMENT '配置项配置名称',
    `value`         VARCHAR(1000) DEFAULT NULL COMMENT '配置项值',
    `default_value` VARCHAR(1000) DEFAULT NULL COMMENT '配置项默认值',
    `value_type`    VARCHAR(50)   DEFAULT NULL COMMENT '配置项值类型',
    `is_disabled`   TINYINT(1) UNSIGNED NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
    `note`          VARCHAR(255)  DEFAULT NULL COMMENT '备注',
    `create_by`     VARCHAR(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`     VARCHAR(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`   DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_composite`(`code`, `namespace`, `module_code`, `app_code`),
    KEY `idx_update_time`(`update_time`),
    KEY `idx_is_disabled`(`is_disabled`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统全局配置表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_global_config`
--


--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `sys_log` (
    `id`                BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `level`             VARCHAR(10)                  DEFAULT NULL COMMENT '日志级别',
    `biz_type`          VARCHAR(100)                 DEFAULT NULL COMMENT '业务类型',
    `message`           TEXT COMMENT '日志消息',
    `exception_message` TEXT COMMENT '异常信息，只有日志级别为ERROR时才有值',
    `class_name`        VARCHAR(255)        NOT NULL COMMENT '操作的类名',
    `method_name`       VARCHAR(255)        NOT NULL COMMENT '操作的方法名',
    `params`            TEXT COMMENT '被调用方法的参数',
    `operate_type`      VARCHAR(100)                 DEFAULT NULL COMMENT '操作类型',
    `operator_id`       BIGINT(20) UNSIGNED          DEFAULT NULL COMMENT '操作者ID',
    `operator_name`     VARCHAR(100)                 DEFAULT NULL COMMENT '操作者用户名',
    `server_ip`         VARCHAR(100)                 DEFAULT NULL COMMENT '服务端IP地址',
    `client_ip`         VARCHAR(100)                 DEFAULT NULL COMMENT '客户端IP地址',
    `client_location`   VARCHAR(100)                 DEFAULT NULL COMMENT '客户端地理位置',
    `client_device`     VARCHAR(100)                 DEFAULT NULL COMMENT '客户端设备',
    `request_time`      BIGINT(20) UNSIGNED          DEFAULT NULL COMMENT 'HTTP请求的耗时',
    `create_time`       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_class_method`(`method_name`, `class_name`),
    KEY `idx_level`(`level`),
    KEY `idx_create_time`(`create_time`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统日志记录';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_log`
--


--
-- Table structure for table `sys_quartz_job`
--

DROP TABLE IF EXISTS `sys_quartz_job`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `sys_quartz_job` (
    `job_id`              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name`           VARCHAR(255) DEFAULT NULL COMMENT 'Spring Bean名称',
    `cron_expression`     VARCHAR(255) DEFAULT NULL COMMENT 'cron 表达式',
    `is_pause`            BIT(1)       DEFAULT NULL COMMENT '状态：1暂停、0启用',
    `job_name`            VARCHAR(255) DEFAULT NULL COMMENT '任务名称',
    `method_name`         VARCHAR(255) DEFAULT NULL COMMENT '方法名称',
    `params`              VARCHAR(255) DEFAULT NULL COMMENT '参数',
    `description`         VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `person_in_charge`    VARCHAR(100) DEFAULT NULL COMMENT '负责人',
    `email`               VARCHAR(100) DEFAULT NULL COMMENT '报警邮箱',
    `sub_task`            VARCHAR(100) DEFAULT NULL COMMENT '子任务ID',
    `pause_after_failure` BIT(1)       DEFAULT NULL COMMENT '任务失败后是否暂停',
    `create_by`           VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`           VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time`         DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`job_id`) USING BTREE,
    KEY `key_is_pause`(`is_pause`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='定时任务';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_quartz_job`
--

INSERT INTO `sys_quartz_job`
VALUES (2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '测试', NULL, NULL, NULL,
        NULL, 'admin', '2019-08-22 14:08:29', '2020-05-24 13:58:33');
INSERT INTO `sys_quartz_job`
VALUES (3, 'testTask', '0/5 * * * * ?', b'1', '测试', 'run', '', '不带参测试', 'Zheng Jie', '', '5,6', b'1',
        NULL, 'admin', '2019-09-26 16:44:39', '2020-05-24 14:48:12');
INSERT INTO `sys_quartz_job`
VALUES (5, 'Test', '0/5 * * * * ?', b'1', '任务告警测试', 'run', NULL, '测试', 'test', '', NULL, b'1',
        'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job`
VALUES (6, 'testTask', '0/5 * * * * ?', b'1', '测试3', 'run2', NULL, '测试3', 'Zheng Jie', '', NULL, b'1',
        'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');

--
-- Table structure for table `sys_quartz_log`
--

DROP TABLE IF EXISTS `sys_quartz_log`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `sys_quartz_log` (
    `log_id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name`        VARCHAR(255)        DEFAULT NULL,
    `create_time`      DATETIME            DEFAULT NULL,
    `cron_expression`  VARCHAR(255)        DEFAULT NULL,
    `exception_detail` TEXT,
    `is_success`       BIT(1)              DEFAULT NULL,
    `job_name`         VARCHAR(255)        DEFAULT NULL,
    `method_name`      VARCHAR(255)        DEFAULT NULL,
    `params`           VARCHAR(255)        DEFAULT NULL,
    `time`             BIGINT(20) UNSIGNED DEFAULT NULL,
    PRIMARY KEY (`log_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='定时任务日志';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_quartz_log`
--


/*!40101 SET SQL_MODE = @`old_sql_mode` */;
/*!40014 SET FOREIGN_KEY_CHECKS = @`old_foreign_key_checks` */;
/*!40014 SET UNIQUE_CHECKS = @`old_unique_checks` */;
/*!40101 SET CHARACTER_SET_CLIENT = @`old_character_set_client` */;
/*!40101 SET CHARACTER_SET_RESULTS = @`old_character_set_results` */;
/*!40101 SET COLLATION_CONNECTION = @`old_collation_connection` */;
/*!40111 SET SQL_NOTES = @`old_sql_notes` */;

-- Dump completed
