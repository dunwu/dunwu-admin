/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : dunwu_admin

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 24/11/2022 14:22:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cas_dept
-- ----------------------------
DROP TABLE IF EXISTS `cas_dept`;
CREATE TABLE `cas_dept`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `pid` bigint unsigned COMMENT '上级部门ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `level` int unsigned NOT NULL COMMENT '部门等级',
  `sequence` int unsigned COMMENT '部门顺序',
  `children_num` int unsigned COMMENT '子部门数量',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `creator_id` bigint unsigned COMMENT '创建者ID',
  `updater_id` bigint unsigned COMMENT '更新者ID',
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者名称',
  `updater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者名称',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sequence_pid`(`sequence`, `pid`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE,
  INDEX `idx_enabled`(`is_disabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_dept
-- ----------------------------
INSERT INTO `cas_dept` VALUES (1, 0, '研发部', 1, 1, 3, NULL, 0, 1, 1, 'admin', 'admin', '2020-08-02 14:49:07', '2021-10-18 17:23:53');
INSERT INTO `cas_dept` VALUES (2, 0, '产品部', 1, 2, 4, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 09:15:32', '2021-10-18 17:23:42');
INSERT INTO `cas_dept` VALUES (3, 1, '后端开发组', 2, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 09:20:44', '2021-10-13 08:09:12');
INSERT INTO `cas_dept` VALUES (4, 1, '前端开发组', 2, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 09:52:18', '2021-10-13 08:09:12');
INSERT INTO `cas_dept` VALUES (5, 1, '测试组', 2, 3, 2, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 11:04:50', '2021-10-13 08:09:12');
INSERT INTO `cas_dept` VALUES (6, 2, '策划组', 2, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 11:04:53', '2021-10-13 08:09:12');
INSERT INTO `cas_dept` VALUES (7, 2, 'UI设计组', 2, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2020-05-13 22:56:53', '2021-10-13 08:09:12');
INSERT INTO `cas_dept` VALUES (8, 5, '功能测试组', 3, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2021-10-07 22:42:59', '2021-10-17 18:04:14');
INSERT INTO `cas_dept` VALUES (9, 5, '性能测试组', 3, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 09:20:44', '2021-10-17 18:04:14');

-- ----------------------------
-- Table structure for cas_dept_job_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_dept_job_map`;
CREATE TABLE `cas_dept_job_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `dept_id` bigint unsigned NOT NULL COMMENT '部门ID',
  `job_id` bigint unsigned NOT NULL COMMENT '职务ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dept_job`(`dept_id`, `job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门职务关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_dept_job_map
-- ----------------------------
INSERT INTO `cas_dept_job_map` VALUES (18, 1, 1);
INSERT INTO `cas_dept_job_map` VALUES (19, 1, 2);
INSERT INTO `cas_dept_job_map` VALUES (20, 1, 3);
INSERT INTO `cas_dept_job_map` VALUES (21, 1, 4);
INSERT INTO `cas_dept_job_map` VALUES (22, 1, 5);
INSERT INTO `cas_dept_job_map` VALUES (23, 1, 6);
INSERT INTO `cas_dept_job_map` VALUES (24, 2, 1);
INSERT INTO `cas_dept_job_map` VALUES (25, 2, 2);
INSERT INTO `cas_dept_job_map` VALUES (26, 2, 3);
INSERT INTO `cas_dept_job_map` VALUES (15, 3, 1);
INSERT INTO `cas_dept_job_map` VALUES (16, 3, 2);
INSERT INTO `cas_dept_job_map` VALUES (17, 3, 3);

-- ----------------------------
-- Table structure for cas_dept_role_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_dept_role_map`;
CREATE TABLE `cas_dept_role_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `role_id` bigint unsigned NOT NULL,
  `dept_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_menu`(`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cas_job
-- ----------------------------
DROP TABLE IF EXISTS `cas_job`;
CREATE TABLE `cas_job`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职务名称',
  `type` int unsigned NOT NULL COMMENT '职务类型',
  `level` int unsigned NOT NULL COMMENT '职级',
  `sequence` int unsigned COMMENT '职务顺序',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `creator_id` bigint unsigned COMMENT '创建者ID',
  `updater_id` bigint unsigned COMMENT '更新者ID',
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者名称',
  `updater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者名称',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`, `type`) USING BTREE,
  INDEX `idx_disabled`(`is_disabled`) USING BTREE,
  INDEX `idx_sequence`(`sequence`) USING BTREE,
  INDEX `idx_level`(`level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_job
-- ----------------------------
INSERT INTO `cas_job` VALUES (1, '初级工程师', 1, 1, 1, '初级工程师', 0, 1, 1, 'admin', 'admin', '2019-03-29 14:52:28', '2021-10-13 08:09:12');
INSERT INTO `cas_job` VALUES (2, '中级工程师', 1, 2, 2, '中级工程师', 0, 1, 1, 'admin', 'admin', '2019-03-29 14:55:51', '2021-10-13 08:09:12');
INSERT INTO `cas_job` VALUES (3, '高级工程师', 1, 3, 3, '高级工程师', 0, 1, 1, 'admin', 'admin', '2019-03-31 13:39:30', '2021-10-13 08:09:12');
INSERT INTO `cas_job` VALUES (4, '专家', 1, 4, 4, '专家', 0, 1, 1, 'admin', 'admin', '2019-03-31 13:39:43', '2021-10-13 08:09:12');
INSERT INTO `cas_job` VALUES (5, '高级专家', 1, 5, 5, '高级专家', 0, 1, 1, 'admin', 'admin', '2021-10-10 08:40:22', '2021-10-13 08:09:12');
INSERT INTO `cas_job` VALUES (6, '资深专家', 1, 6, 6, '资深专家', 0, 1, 1, 'admin', 'admin', '2021-10-10 08:55:47', '2021-10-13 08:09:12');

-- ----------------------------
-- Table structure for cas_job_role_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_job_role_map`;
CREATE TABLE `cas_job_role_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `job_id` bigint unsigned NOT NULL COMMENT '岗位ID',
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_job_role`(`job_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职务角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cas_menu
-- ----------------------------
DROP TABLE IF EXISTS `cas_menu`;
CREATE TABLE `cas_menu`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `pid` bigint unsigned COMMENT '上级菜单ID',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限表达式',
  `menu_type` int unsigned COMMENT '菜单类型',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件',
  `sequence` int unsigned COMMENT '菜单顺序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接地址',
  `is_frame` tinyint(1) DEFAULT 0 COMMENT '是否外链',
  `is_cached` tinyint(1) DEFAULT 0 COMMENT '缓存',
  `is_hidden` tinyint(1) DEFAULT 0 COMMENT '隐藏',
  `level` int unsigned COMMENT '菜单级别',
  `children_num` int unsigned COMMENT '子菜单数目',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `creator_id` bigint unsigned COMMENT '创建者ID',
  `updater_id` bigint unsigned COMMENT '更新者ID',
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者名称',
  `updater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者名称',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_title`(`name`) USING BTREE,
  UNIQUE INDEX `uk_name`(`code`) USING BTREE,
  INDEX `key_pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_menu
-- ----------------------------
INSERT INTO `cas_menu` VALUES (1, 0, 'Cas', '权限管理', NULL, 0, NULL, 2, 'anq', 'cas', 0, 0, 0, 1, 11, '权限管理系统', 0, 1, 1, 'admin', 'admin', '2018-12-18 15:11:29', '2021-10-19 15:22:31');
INSERT INTO `cas_menu` VALUES (2, 1, 'User', '用户管理', 'cas:user:view', 1, 'cas/user', 3, 'peoples', 'user', 0, 0, 0, 2, 3, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-18 15:14:44', '2021-10-20 00:05:57');
INSERT INTO `cas_menu` VALUES (3, 1, 'Role', '角色管理', 'cas:role:view', 1, 'cas/role', 4, 'role', 'role', 0, 0, 0, 2, 3, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-18 15:16:07', '2021-10-20 00:05:57');
INSERT INTO `cas_menu` VALUES (5, 1, 'Menu', '菜单管理', 'cas:menu:view', 1, 'cas/menu', 5, 'menu', 'menu', 0, 0, 0, 2, 3, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-18 15:17:28', '2021-10-20 00:05:57');
INSERT INTO `cas_menu` VALUES (6, 0, NULL, '系统监控', NULL, 0, NULL, 4, 'monitor', 'monitor', 0, 0, 0, 1, 4, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-18 15:17:48', '2021-10-16 20:22:42');
INSERT INTO `cas_menu` VALUES (7, 121, 'Log', '应用日志', 'sys:log:view', 1, 'sys/LogList', 3, 'log', 'log', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-18 15:18:26', '2021-10-20 00:02:17');
INSERT INTO `cas_menu` VALUES (9, 6, 'Sql', 'SQL监控', NULL, 1, 'monitor/sql/index', 18, 'sqlMonitor', 'druid', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-18 15:19:34', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (10, 0, 'Demo', '项目示例', NULL, 0, NULL, 6, 'zujian', 'demo', 0, 0, 0, 1, 13, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-19 13:38:16', '2021-10-16 20:22:42');
INSERT INTO `cas_menu` VALUES (11, 10, 'Icons', '图标库', NULL, 1, 'demo/icons', 51, 'icon', 'icon', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-19 13:38:49', '2021-10-19 23:56:43');
INSERT INTO `cas_menu` VALUES (14, 36, 'Email', '邮件工具', NULL, 1, 'tool/email/index', 35, 'email', 'email', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-27 10:13:09', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (15, 10, 'Editor', '富文本', NULL, 1, 'demo/Editor', 52, 'fwb', 'tinymce', 0, 0, 0, 3, 0, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-27 11:58:25', '2021-10-19 23:56:36');
INSERT INTO `cas_menu` VALUES (18, 36, 'Storage', '存储管理', 'tool:storage:view', 1, 'tool/storage/index', 34, 'qiniu', 'storage', 0, 0, 0, 1, 3, NULL, 0, 1, 1, 'admin', 'admin', '2018-12-31 11:12:15', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (30, 36, 'CodeIndex', '代码生成', NULL, 1, 'code/generator/index', 32, 'coding', 'code/generator', 0, 1, 0, 2, 7, NULL, 0, 1, 1, 'admin', 'admin', '2019-01-11 15:45:55', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (33, 10, 'Markdown', 'Markdown', NULL, 1, 'demo/MarkDown', 53, 'markdown', 'markdown', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-08 13:46:44', '2021-10-19 23:56:36');
INSERT INTO `cas_menu` VALUES (34, 10, 'YamlEdit', 'Yaml编辑器', NULL, 1, 'demo/YamlEdit', 54, 'dev', 'yaml', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-08 15:49:40', '2021-10-19 23:56:36');
INSERT INTO `cas_menu` VALUES (35, 1, 'Dept', '部门管理', 'cas:dept:view', 1, 'cas/dept', 1, 'dept', 'dept', 0, 0, 0, 2, 3, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-25 09:46:00', '2021-10-19 15:02:12');
INSERT INTO `cas_menu` VALUES (36, 0, 'Tool', '系统工具', NULL, 0, '', 3, 'sys-tools', 'tool', 0, 0, 0, 1, 13, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-29 10:57:35', '2021-10-19 15:26:59');
INSERT INTO `cas_menu` VALUES (37, 1, 'Job', '岗位管理', 'cas:job:view', 1, 'cas/job', 2, 'Steve-Jobs', 'job', 0, 0, 0, 2, 3, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-29 13:51:18', '2021-10-19 15:02:12');
INSERT INTO `cas_menu` VALUES (38, 36, 'Swagger', '接口文档', '', 1, 'tool/swagger/index', 36, 'swagger', 'swagger2', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-03-29 19:57:53', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (39, 121, 'Dict', '数据字典', 'sys:dict:view', 1, 'sys/dict', 2, 'dictionary', 'dict', 0, 0, 0, 2, 5, NULL, 0, 1, 1, 'admin', 'admin', '2019-04-10 11:49:04', '2021-10-20 00:02:17');
INSERT INTO `cas_menu` VALUES (41, 1, 'OnlineUser', '在线用户', 'cas:online:view', 1, 'cas/user/online', 7, 'Steve-Jobs', 'online', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-26 22:08:43', '2021-10-20 00:05:57');
INSERT INTO `cas_menu` VALUES (44, 2, 'UserAdd', '用户新增', 'cas:user:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 10:59:46', '2021-10-19 15:05:16');
INSERT INTO `cas_menu` VALUES (45, 2, 'UserEdit', '用户编辑', 'cas:user:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 11:00:08', '2021-10-19 15:05:16');
INSERT INTO `cas_menu` VALUES (46, 2, 'UserDel', '用户删除', 'cas:user:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 11:00:23', '2021-10-19 15:05:16');
INSERT INTO `cas_menu` VALUES (48, 3, NULL, '角色创建', 'cas:role:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:45:34', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (49, 3, NULL, '角色修改', 'cas:role:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:46:16', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (50, 3, NULL, '角色删除', 'cas:role:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:46:51', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (52, 5, NULL, '菜单新增', 'cas:menu:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:55:07', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (53, 5, NULL, '菜单编辑', 'cas:menu:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:55:40', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (54, 5, NULL, '菜单删除', 'cas:menu:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:56:00', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (56, 35, NULL, '部门新增', 'cas:dept:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:57:09', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (57, 35, NULL, '部门编辑', 'cas:dept:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:57:27', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (58, 35, NULL, '部门删除', 'cas:dept:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:57:41', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (60, 37, NULL, '岗位新增', 'cas:job:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:58:27', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (61, 37, NULL, '岗位编辑', 'cas:job:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:58:45', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (62, 37, NULL, '岗位删除', 'cas:job:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 12:59:04', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (64, 39, NULL, '字典新增', 'sys:dict:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 13:00:17', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (65, 39, NULL, '字典编辑', 'sys:dict:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 13:00:42', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (66, 39, NULL, '字典删除', 'sys:dict:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 13:00:59', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (77, 18, NULL, '上传文件', 'tool:storage:add', 2, '', 2, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 13:09:09', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (78, 18, NULL, '文件编辑', 'tool:storage:edit', 2, '', 3, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 13:09:22', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (79, 18, NULL, '文件删除', 'tool:storage:del', 2, '', 4, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-10-29 13:09:34', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (80, 6, 'ServerMonitor', '服务监控', 'monitor:view', 1, 'monitor/server/index', 14, 'codeConsole', 'server', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-07 13:06:39', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (82, 36, 'GeneratorConfig', '生成配置', '', 1, 'code/generator/config', 33, 'dev', 'code/generator/config/:dbId/:schemaName/:tableName', 0, 1, 1, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 20:08:56', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (83, 10, 'Echarts', '图表库', NULL, 1, 'demo/Echarts', 50, 'chart', 'echarts', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-21 09:04:32', '2021-10-19 23:56:20');
INSERT INTO `cas_menu` VALUES (90, 0, 'Mnt', '运维管理', NULL, 1, '', 5, 'mnt', 'mnt', 0, 0, 0, 1, 5, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-09 10:31:08', '2021-10-16 20:22:42');
INSERT INTO `cas_menu` VALUES (92, 90, 'ServerDeploy', '服务器配置', 'mnt:server:view', 1, 'mnt/ServerList', 23, 'server', 'server', 0, 0, 0, 1, 3, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-10 10:29:25', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (93, 90, 'App', '应用配置', 'mnt:app:view', 1, 'mnt/AppList', 22, 'app', 'app', 0, 0, 0, 1, 3, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-10 11:05:16', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (94, 90, 'Deploy', '部署管理', 'mnt:deploy:view', 1, 'mnt/DeployList', 24, 'deploy', 'deploy', 0, 0, 0, 1, 3, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-10 15:56:55', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (97, 90, 'DeployHistory', '部署历史', 'mnt:deployHistory:view', 1, 'mnt/DeployHistoryList', 25, 'backup', 'deploy/history', 0, 0, 0, 1, 1, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-10 16:49:44', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (98, 36, 'Database', '数据库管理', 'database:view', 1, 'code/database/index', 26, 'database', 'code/database', 0, 0, 0, 1, 3, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-10 20:40:04', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (102, 97, NULL, '删除', 'deployHistory:del', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 09:32:48', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (103, 92, NULL, '服务器新增', 'serverDeploy:add', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:08:33', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (104, 92, NULL, '服务器编辑', 'serverDeploy:edit', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:08:57', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (105, 92, NULL, '服务器删除', 'serverDeploy:del', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:09:15', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (106, 93, NULL, '应用新增', 'app:add', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:10:03', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (107, 93, NULL, '应用编辑', 'app:edit', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:10:28', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (108, 93, NULL, '应用删除', 'app:del', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:10:55', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (109, 94, NULL, '部署新增', 'deploy:add', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:11:22', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (110, 94, NULL, '部署编辑', 'deploy:edit', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:11:41', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (111, 94, NULL, '部署删除', 'deploy:del', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:12:01', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (112, 98, NULL, '数据库新增', 'database:add', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:12:43', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (113, 98, NULL, '数据库编辑', 'database:edit', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:12:58', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (114, 98, NULL, '数据库删除', 'database:del', 2, '', 999, '', '', 0, 0, 0, 1, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-17 11:13:14', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (116, 36, 'Preview', '生成预览', NULL, 1, 'code/generator/preview', 999, 'java', 'code/generator/preview/:dbId/:schemaName/:tableName', 0, 1, 1, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-11-26 14:54:36', '2021-10-12 20:43:18');
INSERT INTO `cas_menu` VALUES (119, 121, 'GlobalConfig', '全局配置', 'sys:config:view', 1, 'sys/GlobalConfigList', 1, 'system1', 'config', 0, 0, 0, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2021-10-03 19:00:00', '2021-10-20 00:02:17');
INSERT INTO `cas_menu` VALUES (121, 0, 'Sys', '系统管理', NULL, 1, NULL, 1, 'app', 'sys', 0, 0, 0, 1, 9, NULL, 0, 1, 1, 'admin', 'admin', '2021-10-05 09:15:30', '2021-10-20 00:03:35');
INSERT INTO `cas_menu` VALUES (122, 1, 'UserCenter', '用户中心', 'cas:user:view', 1, 'cas/user/center', 6, 'user', 'user/center', 0, 0, 1, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2021-10-17 17:48:53', '2021-10-19 15:03:03');
INSERT INTO `cas_menu` VALUES (124, 121, 'DictImportedByEnumForm', '根据Java枚举导入数据字典', 'sys:dict:edit', 1, 'sys/dict/DictImportedByEnumForm', 1, NULL, 'dict/DictImportedByEnumForm', 0, 0, 1, 2, 0, NULL, 0, 1, 1, 'admin', 'admin', '2019-04-10 11:49:04', '2021-10-20 00:02:17');

-- ----------------------------
-- Table structure for cas_permission
-- ----------------------------
DROP TABLE IF EXISTS `cas_permission`;
CREATE TABLE `cas_permission`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `pid` bigint unsigned COMMENT '父权限ID',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限编码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限表达式',
  `resource_id` bigint unsigned COMMENT '资源ID',
  `type` int unsigned COMMENT '权限类型',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `creator_id` bigint unsigned COMMENT '创建者ID',
  `updater_id` bigint unsigned COMMENT '更新者ID',
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者名称',
  `updater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者名称',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cas_permission
-- ----------------------------
INSERT INTO `cas_permission` VALUES (76, NULL, 'Cas', '权限管理', NULL, 1, 1, 0, '权限管理系统', 1, 1, 'admin', 'admin', '2018-12-18 15:11:29', '2021-10-19 15:22:31');
INSERT INTO `cas_permission` VALUES (77, NULL, 'User', '用户管理', 'cas:user:view', 2, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-18 15:14:44', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (78, NULL, 'Role', '角色管理', 'cas:role:view', 3, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-18 15:16:07', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (79, NULL, 'Menu', '菜单管理', 'cas:menu:view', 5, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-18 15:17:28', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (80, NULL, NULL, '系统监控', NULL, 6, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-18 15:17:48', '2021-10-16 20:22:42');
INSERT INTO `cas_permission` VALUES (81, NULL, 'Log', '应用日志', 'sys:log:view', 7, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-18 15:18:26', '2021-10-20 00:02:17');
INSERT INTO `cas_permission` VALUES (82, NULL, 'Sql', 'SQL监控', NULL, 9, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-18 15:19:34', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (83, NULL, 'Demo', '项目示例', NULL, 10, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-19 13:38:16', '2021-10-16 20:22:42');
INSERT INTO `cas_permission` VALUES (84, NULL, 'Icons', '图标库', NULL, 11, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-19 13:38:49', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (85, NULL, 'Email', '邮件工具', NULL, 14, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-27 10:13:09', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (86, NULL, 'Editor', '富文本', NULL, 15, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-27 11:58:25', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (87, NULL, 'Storage', '存储管理', 'tool:storage:view', 18, 1, 0, NULL, 1, 1, 'admin', 'admin', '2018-12-31 11:12:15', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (93, NULL, 'CodeIndex', '代码生成', NULL, 30, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-01-11 15:45:55', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (94, NULL, 'Markdown', 'Markdown', NULL, 33, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-03-08 13:46:44', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (95, NULL, 'YamlEdit', 'Yaml编辑器', NULL, 34, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-03-08 15:49:40', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (96, NULL, 'Dept', '部门管理', 'cas:dept:view', 35, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-03-25 09:46:00', '2021-10-19 15:02:12');
INSERT INTO `cas_permission` VALUES (97, NULL, NULL, '系统工具', NULL, 36, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-03-29 10:57:35', '2021-10-16 20:22:42');
INSERT INTO `cas_permission` VALUES (98, NULL, 'Job', '岗位管理', 'cas:job:view', 37, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-03-29 13:51:18', '2021-10-19 15:02:12');
INSERT INTO `cas_permission` VALUES (99, NULL, 'Swagger', '接口文档', '', 38, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-03-29 19:57:53', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (100, NULL, 'Dict', '数据字典', 'sys:dict:view', 39, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-04-10 11:49:04', '2021-10-20 00:02:17');
INSERT INTO `cas_permission` VALUES (101, NULL, 'OnlineUser', '在线用户', 'cas:online:view', 41, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-26 22:08:43', '2021-10-20 00:05:57');
INSERT INTO `cas_permission` VALUES (102, NULL, NULL, '用户新增', 'cas:user:add', 44, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 10:59:46', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (103, NULL, NULL, '用户编辑', 'cas:user:edit', 45, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 11:00:08', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (104, NULL, NULL, '用户删除', 'cas:user:del', 46, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 11:00:23', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (105, NULL, NULL, '角色创建', 'cas:role:add', 48, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:45:34', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (106, NULL, NULL, '角色修改', 'cas:role:edit', 49, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:46:16', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (107, NULL, NULL, '角色删除', 'cas:role:del', 50, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:46:51', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (108, NULL, NULL, '菜单新增', 'cas:menu:add', 52, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:55:07', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (109, NULL, NULL, '菜单编辑', 'cas:menu:edit', 53, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:55:40', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (110, NULL, NULL, '菜单删除', 'cas:menu:del', 54, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:56:00', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (111, NULL, NULL, '部门新增', 'cas:dept:add', 56, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:57:09', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (112, NULL, NULL, '部门编辑', 'cas:dept:edit', 57, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:57:27', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (113, NULL, NULL, '部门删除', 'cas:dept:del', 58, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:57:41', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (114, NULL, NULL, '岗位新增', 'cas:job:add', 60, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:58:27', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (115, NULL, NULL, '岗位编辑', 'cas:job:edit', 61, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:58:45', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (116, NULL, NULL, '岗位删除', 'cas:job:del', 62, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 12:59:04', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (117, NULL, NULL, '字典新增', 'sys:dict:add', 64, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 13:00:17', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (118, NULL, NULL, '字典编辑', 'sys:dict:edit', 65, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 13:00:42', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (119, NULL, NULL, '字典删除', 'sys:dict:del', 66, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 13:00:59', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (120, NULL, NULL, '上传文件', 'tool:storage:add', 77, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 13:09:09', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (121, NULL, NULL, '文件编辑', 'tool:storage:edit', 78, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 13:09:22', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (122, NULL, NULL, '文件删除', 'tool:storage:del', 79, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-10-29 13:09:34', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (123, NULL, 'ServerMonitor', '服务监控', 'monitor:view', 80, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-07 13:06:39', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (124, NULL, 'GeneratorConfig', '生成配置', '', 82, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 20:08:56', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (125, NULL, 'Echarts', '图表库', NULL, 83, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-21 09:04:32', '2021-10-19 23:57:32');
INSERT INTO `cas_permission` VALUES (126, NULL, 'Mnt', '运维管理', NULL, 90, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-09 10:31:08', '2021-10-16 20:22:42');
INSERT INTO `cas_permission` VALUES (127, NULL, 'ServerDeploy', '服务器配置', 'mnt:server:view', 92, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-10 10:29:25', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (128, NULL, 'App', '应用配置', 'mnt:app:view', 93, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-10 11:05:16', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (129, NULL, 'Deploy', '部署管理', 'mnt:deploy:view', 94, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-10 15:56:55', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (130, NULL, 'DeployHistory', '部署历史', 'mnt:deployHistory:view', 97, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-10 16:49:44', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (131, NULL, 'Database', '数据库管理', 'database:view', 98, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-10 20:40:04', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (132, NULL, NULL, '删除', 'deployHistory:del', 102, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 09:32:48', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (133, NULL, NULL, '服务器新增', 'serverDeploy:add', 103, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:08:33', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (134, NULL, NULL, '服务器编辑', 'serverDeploy:edit', 104, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:08:57', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (135, NULL, NULL, '服务器删除', 'serverDeploy:del', 105, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:09:15', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (136, NULL, NULL, '应用新增', 'app:add', 106, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:10:03', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (137, NULL, NULL, '应用编辑', 'app:edit', 107, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:10:28', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (138, NULL, NULL, '应用删除', 'app:del', 108, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:10:55', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (139, NULL, NULL, '部署新增', 'deploy:add', 109, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:11:22', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (140, NULL, NULL, '部署编辑', 'deploy:edit', 110, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:11:41', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (141, NULL, NULL, '部署删除', 'deploy:del', 111, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:12:01', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (142, NULL, NULL, '数据库新增', 'database:add', 112, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:12:43', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (143, NULL, NULL, '数据库编辑', 'database:edit', 113, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:12:58', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (144, NULL, NULL, '数据库删除', 'database:del', 114, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-17 11:13:14', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (145, NULL, 'Preview', '生成预览', NULL, 116, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-11-26 14:54:36', '2021-10-12 20:43:18');
INSERT INTO `cas_permission` VALUES (148, NULL, 'GlobalConfig', '全局配置', 'sys:config:view', 119, 1, 0, NULL, 1, 1, 'admin', 'admin', '2021-10-03 19:00:00', '2021-10-19 23:57:51');
INSERT INTO `cas_permission` VALUES (149, NULL, NULL, '系统管理', NULL, 121, 1, 0, NULL, 1, 1, 'admin', 'admin', '2021-10-05 09:15:30', '2021-10-16 20:22:42');
INSERT INTO `cas_permission` VALUES (150, NULL, 'UserCenter', '用户中心', 'cas:user:view', 122, 1, 0, NULL, 1, 1, 'admin', 'admin', '2021-10-17 17:48:53', '2021-10-17 22:18:46');
INSERT INTO `cas_permission` VALUES (152, NULL, 'DictImportedByEnumForm', '根据Java枚举导入数据字典', 'sys:dict:edit', 124, 1, 0, NULL, 1, 1, 'admin', 'admin', '2019-04-10 11:49:04', '2021-10-20 00:02:17');

-- ----------------------------
-- Table structure for cas_role
-- ----------------------------
DROP TABLE IF EXISTS `cas_role`;
CREATE TABLE `cas_role`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `level` int unsigned COMMENT '角色级别',
  `data_scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据权限',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `creator_id` bigint unsigned COMMENT '创建者ID',
  `updater_id` bigint unsigned COMMENT '更新者ID',
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者名称',
  `updater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者名称',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_role
-- ----------------------------
INSERT INTO `cas_role` VALUES (1, 'admin', '超级管理员', 1, '全部', '-', 0, 1, 1, 'admin', 'admin', '2018-11-23 11:04:37', '2021-10-13 08:09:12');
INSERT INTO `cas_role` VALUES (2, 'user', '普通用户', 2, '本级', '-', 0, 1, 1, 'admin', 'admin', '2018-11-23 13:09:06', '2021-10-13 08:09:12');
INSERT INTO `cas_role` VALUES (3, 'guest', 'guest', 3, '本级', NULL, 0, 1, 1, 'admin', 'admin', '2021-10-17 20:52:08', '2021-10-17 20:52:08');

-- ----------------------------
-- Table structure for cas_role_menu_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_role_menu_map`;
CREATE TABLE `cas_role_menu_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `menu_id` bigint unsigned NOT NULL COMMENT '菜单ID',
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_menu`(`menu_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 333 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_role_menu_map
-- ----------------------------
INSERT INTO `cas_role_menu_map` VALUES (288, 1, 1);
INSERT INTO `cas_role_menu_map` VALUES (78, 1, 2);
INSERT INTO `cas_role_menu_map` VALUES (287, 2, 1);
INSERT INTO `cas_role_menu_map` VALUES (79, 2, 2);
INSERT INTO `cas_role_menu_map` VALUES (284, 3, 1);
INSERT INTO `cas_role_menu_map` VALUES (286, 5, 1);
INSERT INTO `cas_role_menu_map` VALUES (285, 6, 1);
INSERT INTO `cas_role_menu_map` VALUES (80, 6, 2);
INSERT INTO `cas_role_menu_map` VALUES (291, 7, 1);
INSERT INTO `cas_role_menu_map` VALUES (81, 7, 2);
INSERT INTO `cas_role_menu_map` VALUES (293, 9, 1);
INSERT INTO `cas_role_menu_map` VALUES (82, 9, 2);
INSERT INTO `cas_role_menu_map` VALUES (292, 10, 1);
INSERT INTO `cas_role_menu_map` VALUES (83, 10, 2);
INSERT INTO `cas_role_menu_map` VALUES (289, 11, 1);
INSERT INTO `cas_role_menu_map` VALUES (84, 11, 2);
INSERT INTO `cas_role_menu_map` VALUES (290, 14, 1);
INSERT INTO `cas_role_menu_map` VALUES (85, 14, 2);
INSERT INTO `cas_role_menu_map` VALUES (296, 15, 1);
INSERT INTO `cas_role_menu_map` VALUES (86, 15, 2);
INSERT INTO `cas_role_menu_map` VALUES (297, 18, 1);
INSERT INTO `cas_role_menu_map` VALUES (87, 19, 2);
INSERT INTO `cas_role_menu_map` VALUES (295, 21, 1);
INSERT INTO `cas_role_menu_map` VALUES (88, 21, 2);
INSERT INTO `cas_role_menu_map` VALUES (294, 22, 1);
INSERT INTO `cas_role_menu_map` VALUES (89, 22, 2);
INSERT INTO `cas_role_menu_map` VALUES (302, 23, 1);
INSERT INTO `cas_role_menu_map` VALUES (90, 23, 2);
INSERT INTO `cas_role_menu_map` VALUES (301, 24, 1);
INSERT INTO `cas_role_menu_map` VALUES (91, 24, 2);
INSERT INTO `cas_role_menu_map` VALUES (299, 27, 1);
INSERT INTO `cas_role_menu_map` VALUES (92, 27, 2);
INSERT INTO `cas_role_menu_map` VALUES (298, 28, 1);
INSERT INTO `cas_role_menu_map` VALUES (300, 30, 1);
INSERT INTO `cas_role_menu_map` VALUES (93, 30, 2);
INSERT INTO `cas_role_menu_map` VALUES (308, 33, 1);
INSERT INTO `cas_role_menu_map` VALUES (94, 33, 2);
INSERT INTO `cas_role_menu_map` VALUES (307, 34, 1);
INSERT INTO `cas_role_menu_map` VALUES (95, 34, 2);
INSERT INTO `cas_role_menu_map` VALUES (304, 35, 1);
INSERT INTO `cas_role_menu_map` VALUES (303, 36, 1);
INSERT INTO `cas_role_menu_map` VALUES (96, 36, 2);
INSERT INTO `cas_role_menu_map` VALUES (306, 37, 1);
INSERT INTO `cas_role_menu_map` VALUES (305, 38, 1);
INSERT INTO `cas_role_menu_map` VALUES (312, 39, 1);
INSERT INTO `cas_role_menu_map` VALUES (313, 41, 1);
INSERT INTO `cas_role_menu_map` VALUES (309, 44, 1);
INSERT INTO `cas_role_menu_map` VALUES (311, 45, 1);
INSERT INTO `cas_role_menu_map` VALUES (310, 46, 1);
INSERT INTO `cas_role_menu_map` VALUES (317, 48, 1);
INSERT INTO `cas_role_menu_map` VALUES (319, 49, 1);
INSERT INTO `cas_role_menu_map` VALUES (318, 50, 1);
INSERT INTO `cas_role_menu_map` VALUES (314, 52, 1);
INSERT INTO `cas_role_menu_map` VALUES (316, 53, 1);
INSERT INTO `cas_role_menu_map` VALUES (315, 54, 1);
INSERT INTO `cas_role_menu_map` VALUES (323, 56, 1);
INSERT INTO `cas_role_menu_map` VALUES (325, 57, 1);
INSERT INTO `cas_role_menu_map` VALUES (324, 58, 1);
INSERT INTO `cas_role_menu_map` VALUES (320, 60, 1);
INSERT INTO `cas_role_menu_map` VALUES (322, 61, 1);
INSERT INTO `cas_role_menu_map` VALUES (321, 62, 1);
INSERT INTO `cas_role_menu_map` VALUES (326, 64, 1);
INSERT INTO `cas_role_menu_map` VALUES (328, 65, 1);
INSERT INTO `cas_role_menu_map` VALUES (327, 66, 1);
INSERT INTO `cas_role_menu_map` VALUES (333, 73, 1);
INSERT INTO `cas_role_menu_map` VALUES (332, 74, 1);
INSERT INTO `cas_role_menu_map` VALUES (329, 75, 1);
INSERT INTO `cas_role_menu_map` VALUES (331, 77, 1);
INSERT INTO `cas_role_menu_map` VALUES (330, 78, 1);
INSERT INTO `cas_role_menu_map` VALUES (258, 79, 1);
INSERT INTO `cas_role_menu_map` VALUES (257, 80, 1);
INSERT INTO `cas_role_menu_map` VALUES (97, 80, 2);
INSERT INTO `cas_role_menu_map` VALUES (259, 82, 1);
INSERT INTO `cas_role_menu_map` VALUES (98, 82, 2);
INSERT INTO `cas_role_menu_map` VALUES (256, 83, 1);
INSERT INTO `cas_role_menu_map` VALUES (99, 83, 2);
INSERT INTO `cas_role_menu_map` VALUES (263, 90, 1);
INSERT INTO `cas_role_menu_map` VALUES (260, 92, 1);
INSERT INTO `cas_role_menu_map` VALUES (262, 93, 1);
INSERT INTO `cas_role_menu_map` VALUES (261, 94, 1);
INSERT INTO `cas_role_menu_map` VALUES (266, 97, 1);
INSERT INTO `cas_role_menu_map` VALUES (265, 98, 1);
INSERT INTO `cas_role_menu_map` VALUES (264, 102, 1);
INSERT INTO `cas_role_menu_map` VALUES (272, 103, 1);
INSERT INTO `cas_role_menu_map` VALUES (271, 104, 1);
INSERT INTO `cas_role_menu_map` VALUES (274, 105, 1);
INSERT INTO `cas_role_menu_map` VALUES (273, 106, 1);
INSERT INTO `cas_role_menu_map` VALUES (268, 107, 1);
INSERT INTO `cas_role_menu_map` VALUES (267, 108, 1);
INSERT INTO `cas_role_menu_map` VALUES (270, 109, 1);
INSERT INTO `cas_role_menu_map` VALUES (269, 110, 1);
INSERT INTO `cas_role_menu_map` VALUES (279, 111, 1);
INSERT INTO `cas_role_menu_map` VALUES (278, 112, 1);
INSERT INTO `cas_role_menu_map` VALUES (281, 113, 1);
INSERT INTO `cas_role_menu_map` VALUES (280, 114, 1);
INSERT INTO `cas_role_menu_map` VALUES (275, 116, 1);
INSERT INTO `cas_role_menu_map` VALUES (100, 116, 2);
INSERT INTO `cas_role_menu_map` VALUES (277, 117, 1);
INSERT INTO `cas_role_menu_map` VALUES (276, 118, 1);
INSERT INTO `cas_role_menu_map` VALUES (282, 119, 1);
INSERT INTO `cas_role_menu_map` VALUES (283, 121, 1);

-- ----------------------------
-- Table structure for cas_role_permission_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_role_permission_map`;
CREATE TABLE `cas_role_permission_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  `permission_id` bigint unsigned NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_permission`(`permission_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 444 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_role_permission_map
-- ----------------------------
INSERT INTO `cas_role_permission_map` VALUES (375, 1, 76);
INSERT INTO `cas_role_permission_map` VALUES (376, 1, 77);
INSERT INTO `cas_role_permission_map` VALUES (377, 1, 78);
INSERT INTO `cas_role_permission_map` VALUES (378, 1, 79);
INSERT INTO `cas_role_permission_map` VALUES (379, 1, 80);
INSERT INTO `cas_role_permission_map` VALUES (380, 1, 81);
INSERT INTO `cas_role_permission_map` VALUES (381, 1, 82);
INSERT INTO `cas_role_permission_map` VALUES (382, 1, 83);
INSERT INTO `cas_role_permission_map` VALUES (226, 2, 83);
INSERT INTO `cas_role_permission_map` VALUES (383, 1, 84);
INSERT INTO `cas_role_permission_map` VALUES (227, 2, 84);
INSERT INTO `cas_role_permission_map` VALUES (384, 1, 85);
INSERT INTO `cas_role_permission_map` VALUES (385, 1, 86);
INSERT INTO `cas_role_permission_map` VALUES (228, 2, 86);
INSERT INTO `cas_role_permission_map` VALUES (386, 1, 87);
INSERT INTO `cas_role_permission_map` VALUES (229, 2, 88);
INSERT INTO `cas_role_permission_map` VALUES (230, 2, 89);
INSERT INTO `cas_role_permission_map` VALUES (231, 2, 90);
INSERT INTO `cas_role_permission_map` VALUES (232, 2, 91);
INSERT INTO `cas_role_permission_map` VALUES (233, 2, 92);
INSERT INTO `cas_role_permission_map` VALUES (387, 1, 93);
INSERT INTO `cas_role_permission_map` VALUES (388, 1, 94);
INSERT INTO `cas_role_permission_map` VALUES (234, 2, 94);
INSERT INTO `cas_role_permission_map` VALUES (389, 1, 95);
INSERT INTO `cas_role_permission_map` VALUES (235, 2, 95);
INSERT INTO `cas_role_permission_map` VALUES (390, 1, 96);
INSERT INTO `cas_role_permission_map` VALUES (391, 1, 97);
INSERT INTO `cas_role_permission_map` VALUES (392, 1, 98);
INSERT INTO `cas_role_permission_map` VALUES (393, 1, 99);
INSERT INTO `cas_role_permission_map` VALUES (394, 1, 100);
INSERT INTO `cas_role_permission_map` VALUES (395, 1, 101);
INSERT INTO `cas_role_permission_map` VALUES (396, 1, 102);
INSERT INTO `cas_role_permission_map` VALUES (397, 1, 103);
INSERT INTO `cas_role_permission_map` VALUES (398, 1, 104);
INSERT INTO `cas_role_permission_map` VALUES (399, 1, 105);
INSERT INTO `cas_role_permission_map` VALUES (400, 1, 106);
INSERT INTO `cas_role_permission_map` VALUES (401, 1, 107);
INSERT INTO `cas_role_permission_map` VALUES (402, 1, 108);
INSERT INTO `cas_role_permission_map` VALUES (403, 1, 109);
INSERT INTO `cas_role_permission_map` VALUES (404, 1, 110);
INSERT INTO `cas_role_permission_map` VALUES (405, 1, 111);
INSERT INTO `cas_role_permission_map` VALUES (406, 1, 112);
INSERT INTO `cas_role_permission_map` VALUES (407, 1, 113);
INSERT INTO `cas_role_permission_map` VALUES (408, 1, 114);
INSERT INTO `cas_role_permission_map` VALUES (409, 1, 115);
INSERT INTO `cas_role_permission_map` VALUES (410, 1, 116);
INSERT INTO `cas_role_permission_map` VALUES (411, 1, 117);
INSERT INTO `cas_role_permission_map` VALUES (412, 1, 118);
INSERT INTO `cas_role_permission_map` VALUES (413, 1, 119);
INSERT INTO `cas_role_permission_map` VALUES (414, 1, 120);
INSERT INTO `cas_role_permission_map` VALUES (415, 1, 121);
INSERT INTO `cas_role_permission_map` VALUES (416, 1, 122);
INSERT INTO `cas_role_permission_map` VALUES (417, 1, 123);
INSERT INTO `cas_role_permission_map` VALUES (418, 1, 124);
INSERT INTO `cas_role_permission_map` VALUES (419, 1, 125);
INSERT INTO `cas_role_permission_map` VALUES (236, 2, 125);
INSERT INTO `cas_role_permission_map` VALUES (420, 1, 126);
INSERT INTO `cas_role_permission_map` VALUES (421, 1, 127);
INSERT INTO `cas_role_permission_map` VALUES (422, 1, 128);
INSERT INTO `cas_role_permission_map` VALUES (423, 1, 129);
INSERT INTO `cas_role_permission_map` VALUES (424, 1, 130);
INSERT INTO `cas_role_permission_map` VALUES (425, 1, 131);
INSERT INTO `cas_role_permission_map` VALUES (426, 1, 132);
INSERT INTO `cas_role_permission_map` VALUES (427, 1, 133);
INSERT INTO `cas_role_permission_map` VALUES (428, 1, 134);
INSERT INTO `cas_role_permission_map` VALUES (429, 1, 135);
INSERT INTO `cas_role_permission_map` VALUES (430, 1, 136);
INSERT INTO `cas_role_permission_map` VALUES (431, 1, 137);
INSERT INTO `cas_role_permission_map` VALUES (432, 1, 138);
INSERT INTO `cas_role_permission_map` VALUES (433, 1, 139);
INSERT INTO `cas_role_permission_map` VALUES (434, 1, 140);
INSERT INTO `cas_role_permission_map` VALUES (435, 1, 141);
INSERT INTO `cas_role_permission_map` VALUES (436, 1, 142);
INSERT INTO `cas_role_permission_map` VALUES (437, 1, 143);
INSERT INTO `cas_role_permission_map` VALUES (438, 1, 144);
INSERT INTO `cas_role_permission_map` VALUES (439, 1, 145);
INSERT INTO `cas_role_permission_map` VALUES (440, 1, 148);
INSERT INTO `cas_role_permission_map` VALUES (441, 1, 149);
INSERT INTO `cas_role_permission_map` VALUES (442, 1, 150);
INSERT INTO `cas_role_permission_map` VALUES (443, 1, 151);
INSERT INTO `cas_role_permission_map` VALUES (444, 1, 152);

-- ----------------------------
-- Table structure for cas_user
-- ----------------------------
DROP TABLE IF EXISTS `cas_user`;
CREATE TABLE `cas_user`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `dept_id` bigint unsigned COMMENT '部门ID',
  `job_id` bigint unsigned COMMENT '岗位ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像地址',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `pwd_reset_time` datetime(0) DEFAULT NULL COMMENT '修改密码的时间',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `creator_id` bigint unsigned COMMENT '创建者ID',
  `updater_id` bigint unsigned COMMENT '更新者ID',
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者名称',
  `updater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者名称',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_email`(`email`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  INDEX `key_enabled`(`is_disabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_user
-- ----------------------------
INSERT INTO `cas_user` VALUES (1, 1, 6, 'admin', '管理员', '男', '18888888888', '201507802@qq.com', 'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png', '$2a$10$/wOEzFwYkE.gGyp9Aor.yeZVm.pvRAfqPgsHeEN4GC/kKuwr6NWUa', NULL, 0, 1, 1, 'admin', 'admin', '2020-05-03 16:38:31', '2021-10-18 10:15:47');
INSERT INTO `cas_user` VALUES (2, 2, 3, 'test', '测试', '男', '15199999999', '231@qq.com', 'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png', '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', NULL, 0, 1, 1, 'admin', 'admin', '2020-05-05 11:15:49', '2021-10-13 08:09:12');

-- ----------------------------
-- Table structure for cas_user_dept_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_user_dept_map`;
CREATE TABLE `cas_user_dept_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `dept_id` bigint unsigned NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_dept`(`user_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户部门关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cas_user_role_map
-- ----------------------------
DROP TABLE IF EXISTS `cas_user_role_map`;
CREATE TABLE `cas_user_role_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `role_id` bigint unsigned NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cas_user_role_map
-- ----------------------------
INSERT INTO `cas_user_role_map` VALUES (9, 1, 1);
INSERT INTO `cas_user_role_map` VALUES (7, 2, 2);

-- ----------------------------
-- Table structure for code_column_config
-- ----------------------------
DROP TABLE IF EXISTS `code_column_config`;
CREATE TABLE `code_column_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `db_id` bigint unsigned NOT NULL COMMENT '数据库ID',
  `table_id` bigint unsigned COMMENT '所属表的ID',
  `schema_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Schema名称',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Table名称',
  `field_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段名称',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段注释',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段数据类型',
  `java_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段Java类型',
  `key_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '键类型',
  `not_null` bit(1) DEFAULT NULL COMMENT '不允许为空',
  `property_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段别名',
  `label_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段Label',
  `enable_form` bit(1) DEFAULT NULL COMMENT '允许表单',
  `enable_list` bit(1) DEFAULT NULL COMMENT '允许列表',
  `enable_query` bit(1) DEFAULT NULL COMMENT '允许查询',
  `enable_sort` bit(1) DEFAULT NULL COMMENT '允许排序',
  `enable_validate` bit(1) DEFAULT NULL COMMENT '允许校验',
  `form_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表单类型',
  `list_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '列表类型',
  `query_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '查询类型',
  `sort_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序类型',
  `validate_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '校验类型',
  `date_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间类型',
  `date_pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间格式',
  `dict_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典编码',
  `fill` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '@TableField填充属性',
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '扩展属性',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_schema_table_field`(`schema_name`, `table_name`, `field_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 682 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成-字段级别配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of code_column_config
-- ----------------------------
INSERT INTO `code_column_config` VALUES (622, 1, 33, 'dunwu_admin', 'sys_dict', 'id', 'ID', 'bigint unsigned', 'Long', 'PRI', b'1', 'id', 'ID', b'0', b'1', b'1', b'1', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (623, 1, 33, 'dunwu_admin', 'sys_dict', 'code', '字典编码', 'varchar(255)', 'String', '', b'1', 'code', '字典编码', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (624, 1, 33, 'dunwu_admin', 'sys_dict', 'name', '字典名称', 'varchar(255)', 'String', '', b'1', 'name', '字典名称', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (625, 1, 33, 'dunwu_admin', 'sys_dict', 'note', '备注', 'varchar(255)', 'String', '', b'0', 'note', '备注', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (626, 1, 33, 'dunwu_admin', 'sys_dict', 'is_disabled', '是否禁用', 'tinyint(1)', 'Boolean', '', b'1', 'isDisabled', '是否禁用：1 表示禁用；0 表示启用', b'1', b'1', b'1', b'0', b'0', 'Select', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', 'disabled_status', NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (627, 1, 33, 'dunwu_admin', 'sys_dict', 'create_by', '创建者', 'varchar(255)', 'String', '', b'0', 'createBy', '创建者', b'0', b'0', b'0', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (628, 1, 33, 'dunwu_admin', 'sys_dict', 'update_by', '更新者', 'varchar(255)', 'String', '', b'0', 'updateBy', '更新者', b'0', b'0', b'0', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (629, 1, 33, 'dunwu_admin', 'sys_dict', 'create_time', '创建时间', 'datetime', 'LocalDateTime', '', b'0', 'createTime', '创建时间', b'0', b'0', b'0', b'0', b'0', 'DateTimePicker', 'Date', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (630, 1, 33, 'dunwu_admin', 'sys_dict', 'update_time', '更新时间', 'datetime', 'LocalDateTime', '', b'0', 'updateTime', '更新时间', b'0', b'0', b'0', b'0', b'0', 'DateTimePicker', 'Date', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-02-14 15:53:04', '2022-02-14 15:53:04');
INSERT INTO `code_column_config` VALUES (657, 1, 36, 'dunwu_admin', 'cas_job', 'id', 'ID', 'bigint unsigned', 'Long', 'PRI', b'1', 'id', 'ID', b'0', b'1', b'1', b'1', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (658, 1, 36, 'dunwu_admin', 'cas_job', 'name', '职务名称', 'varchar(255)', 'String', 'MUL', b'1', 'name', '职务名称', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (659, 1, 36, 'dunwu_admin', 'cas_job', 'type', '职务类型', 'int unsigned', 'Integer', '', b'1', 'type', '职务类型', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (660, 1, 36, 'dunwu_admin', 'cas_job', 'level', '职级', 'int unsigned', 'Integer', 'MUL', b'1', 'level', '职级', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (661, 1, 36, 'dunwu_admin', 'cas_job', 'sequence', '职务顺序', 'int unsigned', 'Integer', 'MUL', b'0', 'sequence', '职务顺序', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (662, 1, 36, 'dunwu_admin', 'cas_job', 'note', '备注', 'varchar(255)', 'String', '', b'0', 'note', '备注', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (663, 1, 36, 'dunwu_admin', 'cas_job', 'is_disabled', '是否禁用：1 表示禁用；0 表示启用', 'tinyint(1)', 'Boolean', 'MUL', b'1', 'isDisabled', '是否禁用：1 表示禁用；0 表示启用', b'1', b'1', b'1', b'0', b'0', 'Select', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', 'disabled_status', NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (664, 1, 36, 'dunwu_admin', 'cas_job', 'creator_id', '创建者ID', 'bigint unsigned', 'Long', '', b'0', 'creatorId', '创建者ID', b'0', b'0', b'0', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (665, 1, 36, 'dunwu_admin', 'cas_job', 'updater_id', '更新者ID', 'bigint unsigned', 'Long', '', b'0', 'updaterId', '更新者ID', b'0', b'0', b'0', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (666, 1, 36, 'dunwu_admin', 'cas_job', 'creator_name', '创建者名称', 'varchar(255)', 'String', '', b'0', 'creatorName', '创建者名称', b'0', b'0', b'0', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (667, 1, 36, 'dunwu_admin', 'cas_job', 'updater_name', '更新者名称', 'varchar(255)', 'String', '', b'0', 'updaterName', '更新者名称', b'0', b'0', b'0', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (668, 1, 36, 'dunwu_admin', 'cas_job', 'create_time', '创建时间', 'datetime', 'LocalDateTime', '', b'0', 'createTime', '创建时间', b'0', b'0', b'0', b'0', b'0', 'DateTimePicker', 'Date', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (669, 1, 36, 'dunwu_admin', 'cas_job', 'update_time', '更新时间', 'datetime', 'LocalDateTime', '', b'0', 'updateTime', '更新时间', b'0', b'0', b'0', b'0', b'0', 'DateTimePicker', 'Date', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-03-30 14:10:32', '2022-03-30 14:10:32');
INSERT INTO `code_column_config` VALUES (670, 1, 37, 'dunwu_admin', 'cas_role', 'id', 'ID', 'bigint unsigned', 'Long', 'PRI', b'1', 'id', 'ID', b'0', b'1', b'1', b'1', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (671, 1, 37, 'dunwu_admin', 'cas_role', 'code', '角色编码', 'varchar(255)', 'String', 'UNI', b'1', 'code', '角色编码', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (672, 1, 37, 'dunwu_admin', 'cas_role', 'name', '角色名称', 'varchar(255)', 'String', 'MUL', b'1', 'name', '角色名称', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (673, 1, 37, 'dunwu_admin', 'cas_role', 'level', '角色级别', 'int unsigned', 'Integer', '', b'0', 'level', '角色级别', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (674, 1, 37, 'dunwu_admin', 'cas_role', 'data_scope', '数据权限', 'varchar(255)', 'String', '', b'0', 'dataScope', '数据权限', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (675, 1, 37, 'dunwu_admin', 'cas_role', 'note', '备注', 'varchar(255)', 'String', '', b'0', 'note', '备注', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (676, 1, 37, 'dunwu_admin', 'cas_role', 'is_disabled', '是否禁用：1 表示禁用；0 表示启用', 'tinyint(1)', 'Boolean', '', b'1', 'isDisabled', '是否禁用：1 表示禁用；0 表示启用', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (677, 1, 37, 'dunwu_admin', 'cas_role', 'creator_id', '创建者ID', 'bigint unsigned', 'Long', '', b'0', 'creatorId', '创建者ID', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (678, 1, 37, 'dunwu_admin', 'cas_role', 'updater_id', '更新者ID', 'bigint unsigned', 'Long', '', b'0', 'updaterId', '更新者ID', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (679, 1, 37, 'dunwu_admin', 'cas_role', 'creator_name', '创建者名称', 'varchar(255)', 'String', '', b'0', 'creatorName', '创建者名称', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (680, 1, 37, 'dunwu_admin', 'cas_role', 'updater_name', '更新者名称', 'varchar(255)', 'String', '', b'0', 'updaterName', '更新者名称', b'1', b'1', b'1', b'0', b'0', 'Input', 'Text', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (681, 1, 37, 'dunwu_admin', 'cas_role', 'create_time', '创建时间', 'datetime', 'LocalDateTime', '', b'0', 'createTime', '创建时间', b'1', b'1', b'1', b'0', b'0', 'DateTimePicker', 'Date', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');
INSERT INTO `code_column_config` VALUES (682, 1, 37, 'dunwu_admin', 'cas_role', 'update_time', '更新时间', 'datetime', 'LocalDateTime', '', b'0', 'updateTime', '更新时间', b'1', b'1', b'1', b'0', b'0', 'DateTimePicker', 'Date', 'EQUALS', 'asc', 'string', NULL, 'yyyy-MM-dd HH:mm:ss', NULL, NULL, NULL, 'admin', 'admin', '2022-08-30 20:30:39', '2022-08-30 20:30:39');

-- ----------------------------
-- Table structure for code_database
-- ----------------------------
DROP TABLE IF EXISTS `code_database`;
CREATE TABLE `code_database`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库名称',
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Host',
  `port` int unsigned NOT NULL COMMENT '端口号',
  `jdbc_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'jdbc地址',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `schema_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Schema名称',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据库管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of code_database
-- ----------------------------
INSERT INTO `code_database` VALUES (1, 'dunwu_admin 开发数据库', 'localhost', 3306, 'jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false', 'root', 'root', 'dunwu_admin', 'admin', 'admin', '2021-08-05 17:26:50', '2022-01-30 09:11:51');
INSERT INTO `code_database` VALUES (2, 'dunwu_cas', 'localhost', 3306, 'jdbc:mysql://localhost:3306/dunwu_cas?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false', 'root', 'root', 'dunwu_cas', 'admin', 'admin', '2021-08-05 17:26:50', '2021-08-05 17:26:50');

-- ----------------------------
-- Table structure for code_global_config
-- ----------------------------
DROP TABLE IF EXISTS `code_global_config`;
CREATE TABLE `code_global_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `enable_permission` tinyint(1) DEFAULT NULL COMMENT '开启权限校验',
  `enable_override` tinyint(1) DEFAULT NULL COMMENT '开启文件覆盖模式',
  `enable_swagger` tinyint(1) DEFAULT NULL COMMENT '开启Swagger',
  `enable_easy_excel` tinyint(1) DEFAULT NULL COMMENT '开启EasyExcel',
  `enable_log` tinyint(1) DEFAULT NULL COMMENT '开启操作日志',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作者',
  `output_dir` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '输出路径',
  `backend_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '后端代码路径',
  `frontend_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '前端代码路径',
  `package_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '包路径',
  `id_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键类型',
  `date_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间类型',
  `date_pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间格式化',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成-全局配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of code_global_config
-- ----------------------------
INSERT INTO `code_global_config` VALUES (1, 1, 1, 1, 1, 1, '<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'io.github.dunwu.module.sys', 'AUTO', 'TIME_PACK', 'yyyy-MM-dd HH:mm:ss', 'admin', 'admin', '2021-09-17 20:44:16', '2022-01-28 10:06:32');

-- ----------------------------
-- Table structure for code_table_config
-- ----------------------------
DROP TABLE IF EXISTS `code_table_config`;
CREATE TABLE `code_table_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `db_id` bigint unsigned NOT NULL COMMENT '数据库ID',
  `schema_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Schema名称',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Table名称',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Table注释',
  `enable_permission` tinyint(1) DEFAULT NULL COMMENT '开启权限校验',
  `enable_override` tinyint(1) DEFAULT NULL COMMENT '开启文件覆盖模式',
  `enable_swagger` tinyint(1) DEFAULT NULL COMMENT '开启Swagger2',
  `enable_easy_excel` tinyint(1) DEFAULT NULL COMMENT '开启EasyExcel',
  `enable_log` tinyint(1) DEFAULT NULL COMMENT '开启操作日志',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作者',
  `output_dir` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '输出路径',
  `backend_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '后端代码路径',
  `frontend_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '前端代码路径',
  `package_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '包路径',
  `id_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主键类型',
  `date_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间类型',
  `date_pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间格式',
  `enable_form` tinyint(1) DEFAULT NULL COMMENT '允许表单',
  `enable_list` tinyint(1) DEFAULT NULL COMMENT '允许列表',
  `enable_query` tinyint(1) DEFAULT NULL COMMENT '允许查询',
  `enable_sort` tinyint(1) DEFAULT NULL COMMENT '允许排序',
  `enable_validate` tinyint(1) DEFAULT NULL COMMENT '允许校验',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模块名称',
  `table_prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表前缀',
  `api_base_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'REST接口根路径',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_schema_table`(`schema_name`, `table_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成-表级别配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of code_table_config
-- ----------------------------
INSERT INTO `code_table_config` VALUES (33, 1, 'dunwu_admin', 'sys_dict', '数据字典', 1, 1, 1, 1, 1, '<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>', 'D:\\Codes\\zp\\zproject\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'io.github.dunwu.module', 'AUTO', 'TIME_PACK', 'yyyy-MM-dd HH:mm:ss', 1, 1, 1, 1, 1, 'sys', 'sys_', 'dict', 'admin', 'admin', '2021-09-17 20:44:16', '2021-09-17 20:44:16');
INSERT INTO `code_table_config` VALUES (35, 1, 'dunwu_admin', 'sys_dict_option', '数据字典选项', 1, 1, 1, 1, 1, '<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>', 'D:\\Codes\\zp\\zproject\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'io.github.dunwu.module', 'AUTO', 'TIME_PACK', 'yyyy-MM-dd HH:mm:ss', 1, 1, 1, 1, 1, 'sys', 'sys', 'dict/option', 'admin', 'admin', '2021-09-17 20:44:16', '2022-01-28 10:06:32');
INSERT INTO `code_table_config` VALUES (36, 1, 'dunwu_admin', 'cas_job', '职务表', 1, 1, 1, 1, 0, '<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'io.github.dunwu.module.sys', 'AUTO', 'TIME_PACK', 'yyyy-MM-dd HH:mm:ss', 1, 1, 1, 1, 1, 'cas', 'cas_', 'job', 'admin', 'admin', '2021-09-17 20:44:16', '2022-01-28 10:06:32');
INSERT INTO `code_table_config` VALUES (37, 1, 'dunwu_admin', 'cas_role', '角色表', 1, 1, 1, 1, 1, '<a href=\"mailto:forbreak@163.com\">Zhang Peng</a>', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'D:\\Codes\\zp\\zproject\\dunwu\\dunwu-boot-admin\\dunwu-admin\\dunwu-admin-modules\\dunwu-admin-module-common', 'io.github.dunwu.module.sys2', 'AUTO', 'TIME_PACK', 'yyyy-MM-dd HH:mm:ss', 1, 1, 1, 1, 1, 'cas', 'cas_', 'role', 'admin', 'admin', '2021-09-17 20:44:16', '2022-01-28 10:06:32');

-- ----------------------------
-- Table structure for hello
-- ----------------------------
DROP TABLE IF EXISTS `hello`;
CREATE TABLE `hello`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `age` smallint(0) NOT NULL COMMENT '年龄',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hello
-- ----------------------------
INSERT INTO `hello` VALUES (1, 'fasdfas', 12, 'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png', '2021-03-01 21:12:57');
INSERT INTO `hello` VALUES (2, 'abc', 11, 'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png', '2021-03-15 21:50:16');

-- ----------------------------
-- Table structure for mnt_app
-- ----------------------------
DROP TABLE IF EXISTS `mnt_app`;
CREATE TABLE `mnt_app`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '应用名称',
  `upload_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上传路径',
  `deploy_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部署路径',
  `backup_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备份路径',
  `port` int unsigned COMMENT '应用端口',
  `start_script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '启动脚本',
  `deploy_script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部署脚本',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mnt_app
-- ----------------------------
INSERT INTO `mnt_app` VALUES (1, 'dunwu-mnt', 'D:\\upload', 'D:\\deploy', 'D:\\backup', 8080, '1', '1', '1', 'admin', 'admin', '2021-10-02 17:39:19', '2021-10-02 17:39:19');

-- ----------------------------
-- Table structure for mnt_deploy
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy`;
CREATE TABLE `mnt_deploy`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `app_id` bigint unsigned COMMENT '应用编号',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app_id`(`app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部署配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mnt_deploy
-- ----------------------------
INSERT INTO `mnt_deploy` VALUES (2, 1, 'dunwu 运维服务', 'admin', 'admin', '2021-10-03 07:11:51', '2021-10-03 07:22:05');

-- ----------------------------
-- Table structure for mnt_deploy_history
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_history`;
CREATE TABLE `mnt_deploy_history`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `deploy_id` bigint unsigned COMMENT '部署编号',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名称',
  `deploy_date` datetime(0) NOT NULL COMMENT '部署日期',
  `deploy_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部署用户',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务器IP',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部署历史表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mnt_deploy_server_map
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_server_map`;
CREATE TABLE `mnt_deploy_server_map`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `deploy_id` bigint unsigned NOT NULL COMMENT '部署ID',
  `server_id` bigint unsigned NOT NULL COMMENT '服务ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_deploy_server_id`(`deploy_id`, `server_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用和服务关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mnt_deploy_server_map
-- ----------------------------
INSERT INTO `mnt_deploy_server_map` VALUES (3, 2, 1);
INSERT INTO `mnt_deploy_server_map` VALUES (4, 2, 2);

-- ----------------------------
-- Table structure for mnt_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_server`;
CREATE TABLE `mnt_server`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `port` int unsigned COMMENT '端口',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ip`(`ip`) USING BTREE,
  INDEX `idx_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务器配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mnt_server
-- ----------------------------
INSERT INTO `mnt_server` VALUES (1, '测试服务器一', '127.0.0.1', 8080, 'root', 'root', NULL, 'admin', 'admin', '2021-10-02 19:36:16', '2021-10-02 19:36:16');
INSERT INTO `mnt_server` VALUES (2, '测试服务器二', '127.0.0.2', 8081, 'root', 'root', NULL, 'admin', 'admin', '2021-10-02 19:51:55', '2021-10-02 19:51:55');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'disabled_status', '是否启用', '是否启用状态', 0, 'admin', 'admin', '2019-10-27 20:31:36', '2021-10-10 12:11:37');
INSERT INTO `sys_dict` VALUES (2, 'job_type', '职务类型', NULL, 0, 'admin', 'admin', '2021-10-10 12:24:55', '2021-10-10 12:27:02');
INSERT INTO `sys_dict` VALUES (3, 'job_profession_level', '职级', '职级', 0, 'admin', 'admin', '2021-10-10 14:10:57', '2021-10-10 14:10:57');

-- ----------------------------
-- Table structure for sys_dict_option
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_option`;
CREATE TABLE `sys_dict_option`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `dict_id` bigint unsigned COMMENT '字典id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典选项编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典选项名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典选项值',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_disabled` tinyint(1) DEFAULT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_dict_option`(`dict_id`, `code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典选项' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_option
-- ----------------------------
INSERT INTO `sys_dict_option` VALUES (1, 1, 'ENABLE', '启用', 'false', '否', 0, 'admin', 'admin', '2019-10-27 20:31:36', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (2, 1, 'DISABLE', '禁用', 'true', '是', 0, 'admin', 'admin', '2019-10-27 20:31:36', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (3, 2, 'PROFESSION', '专业岗位', '1', NULL, 0, 'admin', 'admin', '2021-10-10 12:25:46', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (4, 2, 'MANAGEMENT', '管理岗位', '2', NULL, 0, 'admin', 'admin', '2021-10-10 12:34:36', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (5, 3, 'LOW', '初级', '1', NULL, 0, 'admin', 'admin', '2021-10-10 14:14:12', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (6, 3, 'MEDIUM', '中级', '2', NULL, 0, 'admin', 'admin', '2021-10-10 14:14:19', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (7, 3, 'HIGH', '高级', '3', NULL, 0, 'admin', 'admin', '2021-10-10 14:14:25', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (8, 3, 'EXPERT', '专家', '4', NULL, 0, 'admin', 'admin', '2021-10-10 14:14:32', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (9, 3, 'ADVANCED_EXPERT', '高级专家', '5', NULL, 0, 'admin', 'admin', '2021-10-10 14:14:45', '2022-01-22 21:13:25');
INSERT INTO `sys_dict_option` VALUES (10, 3, 'SENIOR_EXPERT', '资深专家', '6', NULL, 0, 'admin', 'admin', '2021-10-10 14:15:02', '2022-01-22 21:13:25');

-- ----------------------------
-- Table structure for sys_global_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_global_config`;
CREATE TABLE `sys_global_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `app_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '应用编码',
  `module_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模块编码',
  `namespace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '命名空间',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置项编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置项配置名称',
  `value` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置项值',
  `default_value` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置项默认值',
  `value_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置项值类型',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用：1 表示禁用；0 表示启用',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_composite`(`code`, `namespace`, `module_code`, `app_code`) USING BTREE,
  INDEX `idx_update_time`(`update_time`) USING BTREE,
  INDEX `idx_is_disabled`(`is_disabled`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统全局配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '应用名',
  `biz_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务编码',
  `biz_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '业务类型',
  `is_success` tinyint(1) NOT NULL COMMENT '是否成功：0.失败；1.成功',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '基本信息',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '详情信息',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '异常信息',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类名',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '方法名',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '方法参数',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作类型',
  `operator_id` bigint unsigned COMMENT '操作者ID',
  `operator_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作者用户名',
  `server_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '服务端IP地址',
  `client_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户端IP地址',
  `client_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户端地理位置',
  `client_device` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户端设备',
  `request_time` bigint unsigned COMMENT '操作耗时',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_biz`(`biz_no`, `biz_type`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 165 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job`  (
  `job_id` bigint unsigned NOT NULL COMMENT 'ID',
  `bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Spring Bean名称',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'cron 表达式',
  `is_pause` bit(1) DEFAULT NULL COMMENT '状态：1暂停、0启用',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务名称',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法名称',
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '参数',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `person_in_charge` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '报警邮箱',
  `sub_task` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '子任务ID',
  `pause_after_failure` bit(1) DEFAULT NULL COMMENT '任务失败后是否暂停',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `key_is_pause`(`is_pause`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
INSERT INTO `sys_quartz_job` VALUES (2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '测试', NULL, NULL, NULL, NULL, 'admin', '2019-08-22 14:08:29', '2020-05-24 13:58:33');
INSERT INTO `sys_quartz_job` VALUES (3, 'testTask', '0/5 * * * * ?', b'1', '测试', 'run', '', '不带参测试', 'Zheng Jie', '', '5,6', b'1', NULL, 'admin', '2019-09-26 16:44:39', '2020-05-24 14:48:12');
INSERT INTO `sys_quartz_job` VALUES (5, 'Test', '0/5 * * * * ?', b'1', '任务告警测试', 'run', NULL, '测试', 'test', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job` VALUES (6, 'testTask', '0/5 * * * * ?', b'1', '测试3', 'run2', NULL, '测试3', 'Zheng Jie', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');

-- ----------------------------
-- Table structure for sys_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_log`;
CREATE TABLE `sys_quartz_log`  (
  `log_id` bigint unsigned NOT NULL COMMENT 'ID',
  `bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `is_success` bit(1) DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `time` bigint unsigned,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_alipay_config`;
CREATE TABLE `tool_alipay_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '应用ID',
  `charset` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型 固定格式json',
  `gateway_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '网关地址',
  `notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '异步回调',
  `private_key` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '私钥',
  `public_key` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '公钥',
  `return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回调地址',
  `sign_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '签名方式',
  `sys_service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付宝配置类' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tool_alipay_config
-- ----------------------------
INSERT INTO `tool_alipay_config` VALUES (1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do', 'http://api.auauz.net/api/aliPay/notify', 'MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB', 'http://api.auauz.net/api/aliPay/return', 'RSA2', '2088102176044281');

-- ----------------------------
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_email_config`;
CREATE TABLE `tool_email_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `from_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收件人',
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '端口',
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发件者用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮箱配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_file_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_file_content`;
CREATE TABLE `tool_file_content`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实际文件名',
  `namespace` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
  `tag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '标签。供业务系统使用',
  `origin_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '源文件名',
  `store_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件存储服务类型',
  `store_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件存储路径',
  `content` blob NOT NULL COMMENT '文件内容',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_file_name`(`file_name`) USING BTREE,
  UNIQUE INDEX `uk_store_url`(`store_url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件内容表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_file_info
-- ----------------------------
DROP TABLE IF EXISTS `tool_file_info`;
CREATE TABLE `tool_file_info`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名',
  `namespace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '标签。供业务系统使用',
  `origin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '源文件名',
  `size` bigint unsigned NOT NULL COMMENT '文件大小',
  `extension` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件扩展名',
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件实际类型',
  `store_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件存储服务类型',
  `store_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文件存储路径',
  `access_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件访问路径',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_file_name`(`file_name`) USING BTREE,
  UNIQUE INDEX `uk_access_url`(`access_url`) USING BTREE,
  UNIQUE INDEX `uk_keys`(`origin_name`, `tag`, `namespace`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_local_storage
-- ----------------------------
DROP TABLE IF EXISTS `tool_local_storage`;
CREATE TABLE `tool_local_storage`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件真实的名称',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '后缀',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  `size` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '大小',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '本地存储' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_config`;
CREATE TABLE `tool_qiniu_config`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Bucket 识别符',
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外链域名',
  `access_key` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'accessKey',
  `secret_key` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'secretKey',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '空间类型',
  `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '机房',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '七牛云配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tool_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_content`;
CREATE TABLE `tool_qiniu_content`  (
  `id` bigint unsigned NOT NULL COMMENT 'ID',
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Bucket 识别符',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名称',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件大小',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件类型：私有或公开',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件url',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件后缀',
  `update_time` datetime(0) DEFAULT NULL COMMENT '上传或同步的时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '七牛云文件存储' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
