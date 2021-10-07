-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: dunwu_admin
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @`old_character_set_client` = @@`character_set_client` */;
/*!40101 SET @`old_character_set_results` = @@`character_set_results` */;
/*!40101 SET @`old_collation_connection` = @@`collation_connection` */;
SET NAMES utf8;
/*!40103 SET @`old_time_zone` = @@`time_zone` */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @`old_unique_checks` = @@`unique_checks`, UNIQUE_CHECKS = 0 */;
/*!40014 SET @`old_foreign_key_checks` = @@`foreign_key_checks`, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @`old_sql_mode` = @@`sql_mode`, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @`old_sql_notes` = @@`sql_notes`, SQL_NOTES = 0 */;

--
-- Table structure for table `mnt_app`
--

DROP TABLE IF EXISTS `mnt_app`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `mnt_app` (
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`          VARCHAR(255)     DEFAULT NULL COMMENT '应用名称',
    `upload_path`   VARCHAR(255)     DEFAULT NULL COMMENT '上传路径',
    `deploy_path`   VARCHAR(255)     DEFAULT NULL COMMENT '部署路径',
    `backup_path`   VARCHAR(255)     DEFAULT NULL COMMENT '备份路径',
    `port`          INT(10) UNSIGNED DEFAULT NULL COMMENT '应用端口',
    `start_script`  VARCHAR(4000)    DEFAULT NULL COMMENT '启动脚本',
    `deploy_script` VARCHAR(4000)    DEFAULT NULL COMMENT '部署脚本',
    `note`          VARCHAR(255)     DEFAULT NULL COMMENT '备注',
    `create_by`     VARCHAR(255)     DEFAULT NULL COMMENT '创建者',
    `update_by`     VARCHAR(255)     DEFAULT NULL COMMENT '更新者',
    `create_time`   DATETIME         DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='应用配置表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Table structure for table `mnt_deploy`
--

DROP TABLE IF EXISTS `mnt_deploy`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `mnt_deploy` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_id`      BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '应用编号',
    `note`        VARCHAR(255)        DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_app_id`(`app_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='部署配置表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Table structure for table `mnt_deploy_history`
--

DROP TABLE IF EXISTS `mnt_deploy_history`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `mnt_deploy_history` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `deploy_id`   BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '部署编号',
    `app_name`    VARCHAR(255)        NOT NULL COMMENT '应用名称',
    `deploy_date` DATETIME            NOT NULL COMMENT '部署日期',
    `deploy_user` VARCHAR(50)         NOT NULL COMMENT '部署用户',
    `ip`          VARCHAR(20)         NOT NULL COMMENT '服务器IP',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='部署历史表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Table structure for table `mnt_deploy_server_map`
--

DROP TABLE IF EXISTS `mnt_deploy_server_map`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `mnt_deploy_server_map` (
    `id`        BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `deploy_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '部署ID',
    `server_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '服务ID',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_deploy_server_id`(`deploy_id`, `server_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='应用和服务关联表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Table structure for table `mnt_server`
--

DROP TABLE IF EXISTS `mnt_server`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `mnt_server` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(100)     DEFAULT NULL COMMENT '名称',
    `ip`          VARCHAR(20)      DEFAULT NULL COMMENT 'IP地址',
    `port`        INT(10) UNSIGNED DEFAULT NULL COMMENT '端口',
    `account`     VARCHAR(50)      DEFAULT NULL COMMENT '账号',
    `password`    VARCHAR(100)     DEFAULT NULL COMMENT '密码',
    `note`        VARCHAR(255)     DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255)     DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)     DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME         DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_ip`(`ip`),
    KEY `idx_name`(`name`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='服务器配置表';
/*!40101 SET character_set_client = @`saved_cs_client` */;
/*!40103 SET TIME_ZONE = @`old_time_zone` */;

/*!40101 SET SQL_MODE = @`old_sql_mode` */;
/*!40014 SET FOREIGN_KEY_CHECKS = @`old_foreign_key_checks` */;
/*!40014 SET UNIQUE_CHECKS = @`old_unique_checks` */;
/*!40101 SET CHARACTER_SET_CLIENT = @`old_character_set_client` */;
/*!40101 SET CHARACTER_SET_RESULTS = @`old_character_set_results` */;
/*!40101 SET COLLATION_CONNECTION = @`old_collation_connection` */;
/*!40111 SET SQL_NOTES = @`old_sql_notes` */;

-- Dump completed on 2021-10-03 11:21:35
