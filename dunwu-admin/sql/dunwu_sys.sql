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
    ROW_FORMAT = COMPACT
    COMMENT ='数据字典';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_dict`
--

INSERT INTO `sys_dict`
VALUES (1, 'user_status', '用户状态', 0, '用户状态', 'admin', 'admin', '2019-10-27 20:31:36', '2021-10-03 18:25:58'),
(2, 'dept_status', '部门状态', 0, '部门状态', 'admin', 'admin', '2019-10-27 20:31:36', '2021-10-03 18:37:45'),
(3, 'job_status', '岗位状态', 0, '岗位状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);

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
    ROW_FORMAT = COMPACT
    COMMENT ='数据字典详情';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_dict_option`
--

INSERT INTO `sys_dict_option`
VALUES (1, 1, 'true', '激活', 'admin', NULL, '2019-10-27 20:31:36', NULL),
    (2, 1, 'false', '禁用', 'admin', NULL, NULL, NULL),
    (3, 2, 'true', '启用', 'admin', NULL, NULL, NULL),
    (4, 2, 'false', '停用', 'admin', NULL, '2019-10-27 20:31:36', NULL),
    (5, 3, 'true', '启用', 'admin', NULL, NULL, NULL),
    (6, 3, 'false', '停用', 'admin', NULL, '2019-10-27 20:31:36', NULL);

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
    ROW_FORMAT = COMPACT
    COMMENT ='系统全局配置表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_global_config`
--

INSERT INTO `sys_global_config`
VALUES (1, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'appId', '应用ID', '', '', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (2, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'publicKey', '支付宝公钥', '', '', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (3, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'privateKey', '支付宝私钥', '', '', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (4, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'signType', 'RSA2', '', '', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (5, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'gatewayUrl', '支付宝开放安全地址',
        'https://openapi.alipaydev.com/gateway.do', 'https://openapi.alipaydev.com/gateway.do', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (6, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'charset', '编码', 'utf-8', 'utf-8', 'String', 0,
        NULL, 'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (7, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'notifyUrl', '异步通知地址', '', '', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (8, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'returnUrl', '订单完成后返回的页面', '', '', 'String', 0,
        NULL, 'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (9, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'format', '序列化类型', 'JSON', 'JSON', 'String', 0,
        NULL, 'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');
INSERT INTO `sys_global_config`
VALUES (10, 'dunwu-admin', 'dunwu-admin-module-tool', '/tool/alipay', 'merchantId', '商户号', '', '', 'String', 0, NULL,
        'admin', 'admin', '2021-10-03 19:23:27', '2021-10-03 19:23:27');

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
    ROW_FORMAT = COMPACT
    COMMENT ='系统日志记录';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `sys_log`
--

/*!40101 SET SQL_MODE = @`old_sql_mode` */;
/*!40014 SET FOREIGN_KEY_CHECKS = @`old_foreign_key_checks` */;
/*!40014 SET UNIQUE_CHECKS = @`old_unique_checks` */;
/*!40101 SET CHARACTER_SET_CLIENT = @`old_character_set_client` */;
/*!40101 SET CHARACTER_SET_RESULTS = @`old_character_set_results` */;
/*!40101 SET COLLATION_CONNECTION = @`old_collation_connection` */;
/*!40111 SET SQL_NOTES = @`old_sql_notes` */;

-- Dump completed
