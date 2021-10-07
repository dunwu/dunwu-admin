--
-- Table structure for table `cas_dept`
--

DROP TABLE IF EXISTS `cas_dept`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_dept` (
    `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`          BIGINT(20) UNSIGNED          DEFAULT NULL COMMENT '上级部门ID',
    `name`         VARCHAR(255)        NOT NULL COMMENT '部门名称',
    `level`        INT(5) UNSIGNED     NOT NULL DEFAULT '1' COMMENT '部门等级',
    `sequence`     INT(5) UNSIGNED              DEFAULT '1' COMMENT '部门顺序',
    `children_num` INT(5) UNSIGNED              DEFAULT '0' COMMENT '子部门数量',
    `is_disabled`  TINYINT(1) UNSIGNED NOT NULL COMMENT '状态',
    `note`         VARCHAR(255)                 DEFAULT NULL COMMENT '备注',
    `create_by`    VARCHAR(255)                 DEFAULT NULL COMMENT '创建者',
    `update_by`    VARCHAR(255)                 DEFAULT NULL COMMENT '更新者',
    `create_time`  DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_sequence_pid`(`sequence`, `pid`),
    KEY `idx_name`(`name`),
    KEY `idx_enabled`(`is_disabled`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='部门';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_dept`
--

INSERT INTO `cas_dept`
VALUES (1, 0, '研发部', 1, 1, 3, 0, NULL, 'admin', 'admin', '2020-08-02 14:49:07', '2021-10-05 22:39:30');
INSERT INTO `cas_dept`
VALUES (2, 0, '产品部', 1, 2, 2, 0, NULL, 'admin', 'admin', '2019-03-25 09:15:32', '2021-10-05 22:39:30');
INSERT INTO `cas_dept`
VALUES (3, 1, '后端开发组', 2, 1, 0, 0, NULL, 'admin', 'admin', '2019-03-25 09:20:44', '2021-10-06 00:50:06');
INSERT INTO `cas_dept`
VALUES (4, 1, '前端开发组', 2, 2, 0, 0, NULL, 'admin', 'admin', '2019-03-25 09:52:18', '2021-10-05 22:39:30');
INSERT INTO `cas_dept`
VALUES (5, 1, '测试组', 2, 3, 0, 0, NULL, 'admin', 'admin', '2019-03-25 11:04:50', '2021-10-05 22:39:30');
INSERT INTO `cas_dept`
VALUES (6, 2, '策划组', 2, 1, 0, 0, NULL, 'admin', 'admin', '2019-03-25 11:04:53', '2021-10-05 22:39:30');
INSERT INTO `cas_dept`
VALUES (7, 2, 'UI设计组', 2, 2, 0, 0, NULL, 'admin', 'admin', '2020-05-13 22:56:53', '2021-10-05 22:39:30');

--
-- Table structure for table `cas_dept_role_map`
--

DROP TABLE IF EXISTS `cas_dept_role_map`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_dept_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` BIGINT(20)          NOT NULL,
    `dept_id` BIGINT(20)          NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu`(`role_id`, `dept_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色部门关联';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_dept_role_map`
--


--
-- Table structure for table `cas_job`
--

DROP TABLE IF EXISTS `cas_job`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_job` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '岗位名称',
    `sequence`    INT(5)       DEFAULT NULL COMMENT '排序',
    `dept_id`     BIGINT(20)   DEFAULT NULL COMMENT '部门ID',
    `enabled`     BIT(1)       NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name`(`name`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='岗位';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_job`
--

INSERT INTO `cas_job`
VALUES (1, '人事专员', 3, NULL, `_binary` '', NULL, 'admin', 'admin', '2019-03-29 14:52:28', NULL);
INSERT INTO `cas_job`
VALUES (2, '产品经理', 4, NULL, `_binary` '', NULL, 'admin', 'admin', '2019-03-29 14:55:51', NULL);
INSERT INTO `cas_job`
VALUES (3, '全栈开发', 2, NULL, `_binary` '', NULL, 'admin', 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `cas_job`
VALUES (4, '软件测试', 5, NULL, `_binary` '', NULL, 'admin', 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');

--
-- Table structure for table `cas_job_role_map`
--

DROP TABLE IF EXISTS `cas_job_role_map`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_job_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id`  BIGINT(20)          NOT NULL COMMENT '岗位ID',
    `role_id` BIGINT(20)          NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_job_role`(`job_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统岗位/角色关系表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_job_role_map`
--


--
-- Table structure for table `cas_menu`
--

DROP TABLE IF EXISTS `cas_menu`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_menu` (
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20)   DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count`   INT(5)       DEFAULT '0' COMMENT '子菜单数目',
    `type`        INT(11)      DEFAULT NULL COMMENT '菜单类型',
    `title`       VARCHAR(255) DEFAULT NULL COMMENT '菜单标题',
    `name`        VARCHAR(255) DEFAULT NULL COMMENT '组件名称',
    `component`   VARCHAR(255) DEFAULT NULL COMMENT '组件',
    `sequence`    INT(5)       DEFAULT NULL COMMENT '排序',
    `icon`        VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `path`        VARCHAR(255) DEFAULT NULL COMMENT '链接地址',
    `i_frame`     BIT(1)       DEFAULT NULL COMMENT '是否外链',
    `cache`       BIT(1)       DEFAULT b'0' COMMENT '缓存',
    `hidden`      BIT(1)       DEFAULT b'0' COMMENT '隐藏',
    `permission`  VARCHAR(255) DEFAULT NULL COMMENT '权限',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_title`(`title`),
    UNIQUE KEY `uk_name`(`name`),
    KEY `key_pid`(`pid`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 120
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统菜单';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_menu`
--

INSERT INTO `cas_menu`
VALUES (1, NULL, 7, 0, '权限管理', NULL, NULL, 1, 'system', 'cas', `_binary` '\0', `_binary` '\0', `_binary` '\0', NULL,
        NULL, NULL, '2018-12-18 15:11:29', NULL);
INSERT INTO `cas_menu`
VALUES (2, 1, 3, 1, '用户管理', 'User', 'cas/user/index', 2, 'peoples', 'user', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'user:view', NULL, NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO `cas_menu`
VALUES (3, 1, 3, 1, '角色管理', 'Role', 'cas/role/index', 3, 'role', 'role', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        'roles:view', NULL, NULL, '2018-12-18 15:16:07', NULL);
INSERT INTO `cas_menu`
VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'cas/menu/index', 5, 'menu', 'menu', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        'menu:view', NULL, NULL, '2018-12-18 15:17:28', NULL);
INSERT INTO `cas_menu`
VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        NULL, NULL, NULL, '2018-12-18 15:17:48', NULL);
INSERT INTO `cas_menu`
VALUES (7, 1, 0, 1, '应用日志', 'Log', 'sys/LogList', 11, 'log', 'log', `_binary` '\0', `_binary` '', `_binary` '\0',
        'sys:log:view', NULL, 'admin', '2018-12-18 15:18:26', '2021-10-03 19:01:01');
INSERT INTO `cas_menu`
VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2018-12-18 15:19:34', NULL);
INSERT INTO `cas_menu`
VALUES (10, NULL, 5, 0, '组件管理', NULL, NULL, 50, 'zujian', 'components', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        NULL, NULL, NULL, '2018-12-19 13:38:16', NULL);
INSERT INTO `cas_menu`
VALUES (11, 10, 0, 1, '图标库', 'Icons', 'components/icons/index', 51, 'icon', 'icon', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2018-12-19 13:38:49', NULL);
INSERT INTO `cas_menu`
VALUES (14, 36, 0, 1, '邮件工具', 'Email', 'tools/email/index', 35, 'email', 'email', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2018-12-27 10:13:09', NULL);
INSERT INTO `cas_menu`
VALUES (15, 10, 0, 1, '富文本', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2018-12-27 11:58:25', NULL);
INSERT INTO `cas_menu`
VALUES (18, 36, 3, 1, '存储管理', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'storage:view', NULL, NULL, '2018-12-31 11:12:15', NULL);
INSERT INTO `cas_menu`
VALUES (21, NULL, 2, 0, '多级菜单', NULL, '', 900, 'menu', 'nested', `_binary` '\0', `_binary` '\0', `_binary` '\0', NULL,
        NULL, 'admin', '2019-01-04 16:22:03', '2020-06-21 17:27:35');
INSERT INTO `cas_menu`
VALUES (22, 21, 2, 0, '二级菜单1', NULL, '', 999, 'menu', 'menu1', `_binary` '\0', `_binary` '\0', `_binary` '\0', NULL,
        NULL, 'admin', '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO `cas_menu`
VALUES (23, 21, 0, 1, '二级菜单2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2019-01-04 16:23:57', NULL);
INSERT INTO `cas_menu`
VALUES (24, 22, 0, 1, '三级菜单1', 'Test', 'nested/menu1/menu1-1', 999, 'menu', 'menu1-1', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2019-01-04 16:24:48', NULL);
INSERT INTO `cas_menu`
VALUES (27, 22, 0, 1, '三级菜单2', NULL, 'nested/menu1/menu1-2', 999, 'menu', 'menu1-2', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2019-01-07 17:27:32', NULL);
INSERT INTO `cas_menu`
VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'cas/timing/index', 999, 'timing', 'timing', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'timing:view', NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO `cas_menu`
VALUES (30, 36, 0, 1, '代码生成', 'CodeIndex', 'code/generator/index', 32, 'dev', 'code', `_binary` '\0', `_binary` '',
        `_binary` '\0', NULL, NULL, NULL, '2019-01-11 15:45:55', NULL);
INSERT INTO `cas_menu`
VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', `_binary` '\0',
        `_binary` '\0', `_binary` '\0', NULL, NULL, NULL, '2019-03-08 13:46:44', NULL);
INSERT INTO `cas_menu`
VALUES (34, 10, 0, 1, 'Yaml编辑器', 'YamlEdit', 'components/YamlEdit', 54, 'dev', 'yaml', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', NULL, NULL, NULL, '2019-03-08 15:49:40', NULL);
INSERT INTO `cas_menu`
VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'cas/dept/index', 6, 'dept', 'dept', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'dept:view', NULL, NULL, '2019-03-25 09:46:00', NULL);
INSERT INTO `cas_menu`
VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'tool', `_binary` '\0', `_binary` '\0', `_binary` '\0', NULL,
        NULL, 'admin', '2019-03-29 10:57:35', '2021-10-03 22:50:33');
INSERT INTO `cas_menu`
VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'cas/job/index', 7, 'Steve-Jobs', 'job', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'job:view', NULL, NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO `cas_menu`
VALUES (38, 36, 0, 1, '接口文档', 'Swagger', 'tools/swagger/index', 36, 'swagger', 'swagger2', `_binary` '\0',
        `_binary` '\0', `_binary` '\0', NULL, NULL, NULL, '2019-03-29 19:57:53', NULL);
INSERT INTO `cas_menu`
VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'sys/Dict', 8, 'dictionary', 'dict', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'sys:dict:view', NULL, 'admin', '2019-04-10 11:49:04', '2021-10-03 19:00:41');
INSERT INTO `cas_menu`
VALUES (41, 6, 0, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', `_binary` '\0',
        `_binary` '\0', `_binary` '\0', NULL, NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `cas_menu`
VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'user:add', NULL,
        NULL, '2019-10-29 10:59:46', NULL);
INSERT INTO `cas_menu`
VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'user:edit', NULL,
        NULL, '2019-10-29 11:00:08', NULL);
INSERT INTO `cas_menu`
VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'user:del', NULL,
        NULL, '2019-10-29 11:00:23', NULL);
INSERT INTO `cas_menu`
VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'roles:add', NULL,
        NULL, '2019-10-29 12:45:34', NULL);
INSERT INTO `cas_menu`
VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'roles:edit', NULL,
        NULL, '2019-10-29 12:46:16', NULL);
INSERT INTO `cas_menu`
VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'roles:del', NULL,
        NULL, '2019-10-29 12:46:51', NULL);
INSERT INTO `cas_menu`
VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'menu:add', NULL,
        NULL, '2019-10-29 12:55:07', NULL);
INSERT INTO `cas_menu`
VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'menu:edit', NULL,
        NULL, '2019-10-29 12:55:40', NULL);
INSERT INTO `cas_menu`
VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'menu:del', NULL,
        NULL, '2019-10-29 12:56:00', NULL);
INSERT INTO `cas_menu`
VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'dept:add', NULL,
        NULL, '2019-10-29 12:57:09', NULL);
INSERT INTO `cas_menu`
VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'dept:edit', NULL,
        NULL, '2019-10-29 12:57:27', NULL);
INSERT INTO `cas_menu`
VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'dept:del', NULL,
        NULL, '2019-10-29 12:57:41', NULL);
INSERT INTO `cas_menu`
VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'job:add', NULL,
        NULL, '2019-10-29 12:58:27', NULL);
INSERT INTO `cas_menu`
VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'job:edit', NULL,
        NULL, '2019-10-29 12:58:45', NULL);
INSERT INTO `cas_menu`
VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'job:del', NULL,
        NULL, '2019-10-29 12:59:04', NULL);
INSERT INTO `cas_menu`
VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'dict:add', NULL,
        NULL, '2019-10-29 13:00:17', NULL);
INSERT INTO `cas_menu`
VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'dict:edit', NULL,
        NULL, '2019-10-29 13:00:42', NULL);
INSERT INTO `cas_menu`
VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'dict:del', NULL,
        NULL, '2019-10-29 13:00:59', NULL);
INSERT INTO `cas_menu`
VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'timing:add', NULL,
        NULL, '2019-10-29 13:07:28', NULL);
INSERT INTO `cas_menu`
VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'timing:edit', NULL,
        NULL, '2019-10-29 13:07:41', NULL);
INSERT INTO `cas_menu`
VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'timing:del', NULL,
        NULL, '2019-10-29 13:07:54', NULL);
INSERT INTO `cas_menu`
VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'storage:add', NULL,
        NULL, '2019-10-29 13:09:09', NULL);
INSERT INTO `cas_menu`
VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'storage:edit', NULL,
        NULL, '2019-10-29 13:09:22', NULL);
INSERT INTO `cas_menu`
VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'storage:del', NULL,
        NULL, '2019-10-29 13:09:34', NULL);
INSERT INTO `cas_menu`
VALUES (80, 6, 0, 1, '服务监控', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', `_binary` '\0',
        `_binary` '\0', `_binary` '\0', 'monitor:view', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO `cas_menu`
VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'code/generator/config', 33, 'dev',
        'code/config/:dbId/:schemaName/:tableName', `_binary` '\0', `_binary` '', `_binary` '', '', NULL, NULL,
        '2019-11-17 20:08:56', NULL);
INSERT INTO `cas_menu`
VALUES (83, 10, 0, 1, '图表库', 'Echarts', 'components/Echarts', 50, 'chart', 'echarts', `_binary` '\0', `_binary` '',
        `_binary` '\0', '', NULL, NULL, '2019-11-21 09:04:32', NULL);
INSERT INTO `cas_menu`
VALUES (90, NULL, 5, 1, '运维管理', 'Mnt', '', 20, 'mnt', 'mnt', `_binary` '\0', `_binary` '\0', `_binary` '\0', NULL, NULL,
        NULL, '2019-11-09 10:31:08', NULL);
INSERT INTO `cas_menu`
VALUES (92, 90, 3, 1, '服务器配置', 'ServerDeploy', 'mnt/ServerList', 23, 'server', 'server', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'mnt:server:view', NULL, 'admin', '2019-11-10 10:29:25', '2021-10-03 00:14:18');
INSERT INTO `cas_menu`
VALUES (93, 90, 3, 1, '应用配置', 'App', 'mnt/AppList', 22, 'app', 'app', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        'mnt:app:view', NULL, 'admin', '2019-11-10 11:05:16', '2021-10-03 00:14:06');
INSERT INTO `cas_menu`
VALUES (94, 90, 3, 1, '部署管理', 'Deploy', 'mnt/DeployList', 24, 'deploy', 'deploy', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'deploy:view', NULL, 'admin', '2019-11-10 15:56:55', '2021-10-03 00:19:31');
INSERT INTO `cas_menu`
VALUES (97, 90, 1, 1, '部署历史', 'DeployHistory', 'mnt/DeployHistoryList', 25, 'backup', 'deploy/history', `_binary` '\0',
        `_binary` '\0', `_binary` '\0', 'mnt:deployHistory:view', NULL, 'admin', '2019-11-10 16:49:44',
        '2021-10-03 00:14:49');
INSERT INTO `cas_menu`
VALUES (98, 36, 3, 1, '数据库管理', 'Database', 'code/database/index', 26, 'database', 'code/database', `_binary` '\0',
        `_binary` '\0', `_binary` '\0', 'database:view', NULL, NULL, '2019-11-10 20:40:04', NULL);
INSERT INTO `cas_menu`
VALUES (102, 97, 0, 2, '删除', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'deployHistory:del',
        NULL, NULL, '2019-11-17 09:32:48', NULL);
INSERT INTO `cas_menu`
VALUES (103, 92, 0, 2, '服务器新增', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        'serverDeploy:add', NULL, NULL, '2019-11-17 11:08:33', NULL);
INSERT INTO `cas_menu`
VALUES (104, 92, 0, 2, '服务器编辑', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        'serverDeploy:edit', NULL, NULL, '2019-11-17 11:08:57', NULL);
INSERT INTO `cas_menu`
VALUES (105, 92, 0, 2, '服务器删除', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0',
        'serverDeploy:del', NULL, NULL, '2019-11-17 11:09:15', NULL);
INSERT INTO `cas_menu`
VALUES (106, 93, 0, 2, '应用新增', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'app:add', NULL,
        NULL, '2019-11-17 11:10:03', NULL);
INSERT INTO `cas_menu`
VALUES (107, 93, 0, 2, '应用编辑', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'app:edit', NULL,
        NULL, '2019-11-17 11:10:28', NULL);
INSERT INTO `cas_menu`
VALUES (108, 93, 0, 2, '应用删除', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'app:del', NULL,
        NULL, '2019-11-17 11:10:55', NULL);
INSERT INTO `cas_menu`
VALUES (109, 94, 0, 2, '部署新增', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'deploy:add',
        NULL, NULL, '2019-11-17 11:11:22', NULL);
INSERT INTO `cas_menu`
VALUES (110, 94, 0, 2, '部署编辑', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'deploy:edit',
        NULL, NULL, '2019-11-17 11:11:41', NULL);
INSERT INTO `cas_menu`
VALUES (111, 94, 0, 2, '部署删除', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'deploy:del',
        NULL, NULL, '2019-11-17 11:12:01', NULL);
INSERT INTO `cas_menu`
VALUES (112, 98, 0, 2, '数据库新增', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'database:add',
        NULL, NULL, '2019-11-17 11:12:43', NULL);
INSERT INTO `cas_menu`
VALUES (113, 98, 0, 2, '数据库编辑', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'database:edit',
        NULL, NULL, '2019-11-17 11:12:58', NULL);
INSERT INTO `cas_menu`
VALUES (114, 98, 0, 2, '数据库删除', NULL, '', 999, '', '', `_binary` '\0', `_binary` '\0', `_binary` '\0', 'database:del',
        NULL, NULL, '2019-11-17 11:13:14', NULL);
INSERT INTO `cas_menu`
VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'code/generator/preview', 999, 'java',
        'code/preview/:dbId/:schemaName/:tableName', `_binary` '\0', `_binary` '', `_binary` '', NULL, NULL, NULL,
        '2019-11-26 14:54:36', NULL);
INSERT INTO `cas_menu`
VALUES (117, NULL, 1, 0, '项目案例', NULL, NULL, 50, 'demo', 'demo', `_binary` '\0', `_binary` '\0', `_binary` '\0', NULL,
        NULL, NULL, '2021-12-19 13:38:16', '2021-09-17 21:16:42');
INSERT INTO `cas_menu`
VALUES (118, 117, 0, 1, 'Hello', 'Hello', 'demo/index', 50, '', 'demo/index', `_binary` '\0', `_binary` '\0',
        `_binary` '\0', 'demo:view', NULL, 'admin', '2021-12-19 13:38:16', '2021-09-17 21:22:01');
INSERT INTO `cas_menu`
VALUES (119, 1, 0, 1, '全局配置', 'GlobalConfigList', 'sys/GlobalConfigList', 12, 'system1', 'config', `_binary` '\0',
        `_binary` '', `_binary` '\0', 'sys:config:view', 'admin', 'admin', '2021-10-03 19:00:00',
        '2021-10-03 19:07:15');

--
-- Table structure for table `cas_role`
--

DROP TABLE IF EXISTS `cas_role`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_role` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        VARCHAR(255)        NOT NULL COMMENT '编码',
    `name`        VARCHAR(255)        NOT NULL COMMENT '名称',
    `level`       INT(255)     DEFAULT NULL COMMENT '角色级别',
    `data_scope`  VARCHAR(255) DEFAULT NULL COMMENT '数据权限',
    `enabled`     BIT(1)              NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_code`(`code`),
    KEY `idx_name`(`name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_role`
--

INSERT INTO `cas_role`
VALUES (1, 'admin', '超级管理员', 1, '全部', `_binary` '', '-', NULL, 'admin', '2018-11-23 11:04:37', '2020-08-06 16:10:24');
INSERT INTO `cas_role`
VALUES (2, 'user', '普通用户', 2, '本级', `_binary` '', '-', NULL, 'admin', '2018-11-23 13:09:06', '2020-09-05 10:45:12');

--
-- Table structure for table `cas_role_menu_map`
--

DROP TABLE IF EXISTS `cas_role_menu_map`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_role_menu_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` BIGINT(20)          NOT NULL COMMENT '菜单ID',
    `role_id` BIGINT(20)          NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu`(`menu_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 256
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色菜单关联';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_role_menu_map`
--

INSERT INTO `cas_role_menu_map`
VALUES (209, 1, 1);
INSERT INTO `cas_role_menu_map`
VALUES (78, 1, 2);
INSERT INTO `cas_role_menu_map`
VALUES (208, 2, 1);
INSERT INTO `cas_role_menu_map`
VALUES (79, 2, 2);
INSERT INTO `cas_role_menu_map`
VALUES (205, 3, 1);
INSERT INTO `cas_role_menu_map`
VALUES (207, 5, 1);
INSERT INTO `cas_role_menu_map`
VALUES (206, 6, 1);
INSERT INTO `cas_role_menu_map`
VALUES (80, 6, 2);
INSERT INTO `cas_role_menu_map`
VALUES (212, 7, 1);
INSERT INTO `cas_role_menu_map`
VALUES (81, 7, 2);
INSERT INTO `cas_role_menu_map`
VALUES (214, 9, 1);
INSERT INTO `cas_role_menu_map`
VALUES (82, 9, 2);
INSERT INTO `cas_role_menu_map`
VALUES (213, 10, 1);
INSERT INTO `cas_role_menu_map`
VALUES (83, 10, 2);
INSERT INTO `cas_role_menu_map`
VALUES (210, 11, 1);
INSERT INTO `cas_role_menu_map`
VALUES (84, 11, 2);
INSERT INTO `cas_role_menu_map`
VALUES (211, 14, 1);
INSERT INTO `cas_role_menu_map`
VALUES (85, 14, 2);
INSERT INTO `cas_role_menu_map`
VALUES (218, 15, 1);
INSERT INTO `cas_role_menu_map`
VALUES (86, 15, 2);
INSERT INTO `cas_role_menu_map`
VALUES (219, 18, 1);
INSERT INTO `cas_role_menu_map`
VALUES (215, 19, 1);
INSERT INTO `cas_role_menu_map`
VALUES (87, 19, 2);
INSERT INTO `cas_role_menu_map`
VALUES (217, 21, 1);
INSERT INTO `cas_role_menu_map`
VALUES (88, 21, 2);
INSERT INTO `cas_role_menu_map`
VALUES (216, 22, 1);
INSERT INTO `cas_role_menu_map`
VALUES (89, 22, 2);
INSERT INTO `cas_role_menu_map`
VALUES (224, 23, 1);
INSERT INTO `cas_role_menu_map`
VALUES (90, 23, 2);
INSERT INTO `cas_role_menu_map`
VALUES (223, 24, 1);
INSERT INTO `cas_role_menu_map`
VALUES (91, 24, 2);
INSERT INTO `cas_role_menu_map`
VALUES (221, 27, 1);
INSERT INTO `cas_role_menu_map`
VALUES (92, 27, 2);
INSERT INTO `cas_role_menu_map`
VALUES (220, 28, 1);
INSERT INTO `cas_role_menu_map`
VALUES (222, 30, 1);
INSERT INTO `cas_role_menu_map`
VALUES (93, 30, 2);
INSERT INTO `cas_role_menu_map`
VALUES (230, 33, 1);
INSERT INTO `cas_role_menu_map`
VALUES (94, 33, 2);
INSERT INTO `cas_role_menu_map`
VALUES (229, 34, 1);
INSERT INTO `cas_role_menu_map`
VALUES (95, 34, 2);
INSERT INTO `cas_role_menu_map`
VALUES (226, 35, 1);
INSERT INTO `cas_role_menu_map`
VALUES (225, 36, 1);
INSERT INTO `cas_role_menu_map`
VALUES (96, 36, 2);
INSERT INTO `cas_role_menu_map`
VALUES (228, 37, 1);
INSERT INTO `cas_role_menu_map`
VALUES (227, 38, 1);
INSERT INTO `cas_role_menu_map`
VALUES (234, 39, 1);
INSERT INTO `cas_role_menu_map`
VALUES (235, 41, 1);
INSERT INTO `cas_role_menu_map`
VALUES (231, 44, 1);
INSERT INTO `cas_role_menu_map`
VALUES (233, 45, 1);
INSERT INTO `cas_role_menu_map`
VALUES (232, 46, 1);
INSERT INTO `cas_role_menu_map`
VALUES (239, 48, 1);
INSERT INTO `cas_role_menu_map`
VALUES (241, 49, 1);
INSERT INTO `cas_role_menu_map`
VALUES (240, 50, 1);
INSERT INTO `cas_role_menu_map`
VALUES (236, 52, 1);
INSERT INTO `cas_role_menu_map`
VALUES (238, 53, 1);
INSERT INTO `cas_role_menu_map`
VALUES (237, 54, 1);
INSERT INTO `cas_role_menu_map`
VALUES (245, 56, 1);
INSERT INTO `cas_role_menu_map`
VALUES (247, 57, 1);
INSERT INTO `cas_role_menu_map`
VALUES (246, 58, 1);
INSERT INTO `cas_role_menu_map`
VALUES (242, 60, 1);
INSERT INTO `cas_role_menu_map`
VALUES (244, 61, 1);
INSERT INTO `cas_role_menu_map`
VALUES (243, 62, 1);
INSERT INTO `cas_role_menu_map`
VALUES (248, 64, 1);
INSERT INTO `cas_role_menu_map`
VALUES (250, 65, 1);
INSERT INTO `cas_role_menu_map`
VALUES (249, 66, 1);
INSERT INTO `cas_role_menu_map`
VALUES (255, 73, 1);
INSERT INTO `cas_role_menu_map`
VALUES (254, 74, 1);
INSERT INTO `cas_role_menu_map`
VALUES (251, 75, 1);
INSERT INTO `cas_role_menu_map`
VALUES (253, 77, 1);
INSERT INTO `cas_role_menu_map`
VALUES (252, 78, 1);
INSERT INTO `cas_role_menu_map`
VALUES (180, 79, 1);
INSERT INTO `cas_role_menu_map`
VALUES (179, 80, 1);
INSERT INTO `cas_role_menu_map`
VALUES (97, 80, 2);
INSERT INTO `cas_role_menu_map`
VALUES (181, 82, 1);
INSERT INTO `cas_role_menu_map`
VALUES (98, 82, 2);
INSERT INTO `cas_role_menu_map`
VALUES (178, 83, 1);
INSERT INTO `cas_role_menu_map`
VALUES (99, 83, 2);
INSERT INTO `cas_role_menu_map`
VALUES (185, 90, 1);
INSERT INTO `cas_role_menu_map`
VALUES (182, 92, 1);
INSERT INTO `cas_role_menu_map`
VALUES (184, 93, 1);
INSERT INTO `cas_role_menu_map`
VALUES (183, 94, 1);
INSERT INTO `cas_role_menu_map`
VALUES (188, 97, 1);
INSERT INTO `cas_role_menu_map`
VALUES (187, 98, 1);
INSERT INTO `cas_role_menu_map`
VALUES (186, 102, 1);
INSERT INTO `cas_role_menu_map`
VALUES (194, 103, 1);
INSERT INTO `cas_role_menu_map`
VALUES (193, 104, 1);
INSERT INTO `cas_role_menu_map`
VALUES (196, 105, 1);
INSERT INTO `cas_role_menu_map`
VALUES (195, 106, 1);
INSERT INTO `cas_role_menu_map`
VALUES (190, 107, 1);
INSERT INTO `cas_role_menu_map`
VALUES (189, 108, 1);
INSERT INTO `cas_role_menu_map`
VALUES (192, 109, 1);
INSERT INTO `cas_role_menu_map`
VALUES (191, 110, 1);
INSERT INTO `cas_role_menu_map`
VALUES (201, 111, 1);
INSERT INTO `cas_role_menu_map`
VALUES (200, 112, 1);
INSERT INTO `cas_role_menu_map`
VALUES (203, 113, 1);
INSERT INTO `cas_role_menu_map`
VALUES (202, 114, 1);
INSERT INTO `cas_role_menu_map`
VALUES (197, 116, 1);
INSERT INTO `cas_role_menu_map`
VALUES (100, 116, 2);
INSERT INTO `cas_role_menu_map`
VALUES (199, 117, 1);
INSERT INTO `cas_role_menu_map`
VALUES (198, 118, 1);
INSERT INTO `cas_role_menu_map`
VALUES (204, 119, 1);

--
-- Table structure for table `cas_user`
--

DROP TABLE IF EXISTS `cas_user`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_user` (
    `id`             BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id`        BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '部门ID',
    `job_id`         BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '岗位ID',
    `username`       VARCHAR(255)        DEFAULT NULL COMMENT '用户名',
    `nickname`       VARCHAR(255)        DEFAULT NULL COMMENT '昵称',
    `gender`         VARCHAR(2)          DEFAULT NULL COMMENT '性别',
    `phone`          VARCHAR(255)        DEFAULT NULL COMMENT '手机号码',
    `email`          VARCHAR(255)        DEFAULT NULL COMMENT '邮箱',
    `avatar`         VARCHAR(255)        DEFAULT NULL COMMENT '头像地址',
    `password`       VARCHAR(255)        DEFAULT NULL COMMENT '密码',
    `is_admin`       BIT(1)              DEFAULT b'0' COMMENT '是否为admin账号',
    `pwd_reset_time` DATETIME            DEFAULT NULL COMMENT '修改密码的时间',
    `is_disabled`    TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '是否禁用：1禁用、0启用',
    `create_by`      VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`      VARCHAR(255)        DEFAULT NULL COMMENT '更新着',
    `create_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email`(`email`) USING BTREE,
    UNIQUE KEY `uk_username`(`username`) USING BTREE,
    KEY `key_enabled`(`is_disabled`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='用户表';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_user`
--

INSERT INTO `cas_user`
VALUES (1, 2, 1, 'admin', '管理员', '男', '18888888888', '201507802@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', 0, NULL, 1, 'admin', 'admin',
        '2020-05-03 16:38:31', '2018-08-23 09:11:56');
INSERT INTO `cas_user`
VALUES (2, 2, 2, 'test', '测试', '男', '15199999999', '231@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', 0, NULL, 1, 'admin', 'admin',
        '2020-05-05 11:15:49', '2020-09-05 10:43:38');

DROP TABLE IF EXISTS `cas_user_dept_map`;
CREATE TABLE `cas_user_dept_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT(20)          NOT NULL COMMENT '用户ID',
    `dept_id` BIGINT(20)          NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_dept`(`user_id`, `dept_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='用户部门关联表';

--
-- Table structure for table `cas_user_role_map`
--

DROP TABLE IF EXISTS `cas_user_role_map`;
/*!40101 SET @`saved_cs_client` = @@`character_set_client` */;
SET character_set_client = `utf8mb4`;
CREATE TABLE `cas_user_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT(20)          NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20)          NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_user_role`(`user_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='用户角色关联';
/*!40101 SET character_set_client = @`saved_cs_client` */;

--
-- Dumping data for table `cas_user_role_map`
--

INSERT INTO `cas_user_role_map`
VALUES (1, 1, 1);
INSERT INTO `cas_user_role_map`
VALUES (3, 2, 2);
