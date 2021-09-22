/*********************************************************************
dunwu_security 数据库初始化 SQL
*********************************************************************/

USE `dunwu_security`;


/*Table structure for table `department` */

CREATE TABLE `department` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '上级部门',
    `sub_count`   INT(5) UNSIGNED     DEFAULT '0' COMMENT '子部门数目',
    `name`        VARCHAR(255)        NOT NULL COMMENT '名称',
    `weight`      INT(5) UNSIGNED     DEFAULT '999' COMMENT '排序',
    `enabled`     BIT(1)              NOT NULL COMMENT '状态',
    `note`        VARCHAR(255)        DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_pid`(`pid`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 18
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='部门';

/*Data for the table `department` */

INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (2, 7, 1, '研发部', 3, '', NULL, 'admin', 'admin', '2019-03-25 09:15:32', '2020-08-02 14:48:47');
INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (5, 7, 0, '运维部', 4, '', NULL, 'admin', 'admin', '2019-03-25 09:20:44', '2020-05-17 14:27:27');
INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (6, 8, 0, '测试部', 6, '', NULL, 'admin', 'admin', '2019-03-25 09:52:18', '2020-06-08 11:59:21');
INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (7, 0, 2, '华南分部', 0, '', NULL, 'admin', 'admin', '2019-03-25 11:04:50', '2020-06-08 12:08:56');
INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (8, 0, 2, '华北分部', 1, '', NULL, 'admin', 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (15, 8, 0, 'UI部门', 7, '', NULL, 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
INSERT INTO `department`(`id`, `pid`, `sub_count`, `name`, `weight`, `enabled`, `note`, `create_by`, `update_by`,
                         `create_time`, `update_time`)
VALUES (17, 2, 0, '研发一组', 999, '', NULL, 'admin', 'admin', '2020-08-02 14:49:07', '2020-08-02 14:49:07');

/*Table structure for table `job` */

CREATE TABLE `job` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255)        NOT NULL COMMENT '岗位名称',
    `weight`      INT(5) UNSIGNED     DEFAULT NULL COMMENT '排序',
    `dept_id`     BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '部门ID',
    `enabled`     BIT(1)              NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255)        DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name`(`name`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 5
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='岗位';

/*Data for the table `job` */

INSERT INTO `job`(`id`, `name`, `weight`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                  `update_time`)
VALUES (1, '人事专员', 3, NULL, '', NULL, 'admin', 'admin', '2019-03-29 14:52:28', NULL);
INSERT INTO `job`(`id`, `name`, `weight`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                  `update_time`)
VALUES (2, '产品经理', 4, NULL, '', NULL, 'admin', 'admin', '2019-03-29 14:55:51', NULL);
INSERT INTO `job`(`id`, `name`, `weight`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                  `update_time`)
VALUES (3, '全栈开发', 2, NULL, '', NULL, 'admin', 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `job`(`id`, `name`, `weight`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                  `update_time`)
VALUES (4, '软件测试', 5, NULL, '', NULL, 'admin', 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');

/*Table structure for table `job_role` */

CREATE TABLE `job_role` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id`  BIGINT(20) UNSIGNED NOT NULL COMMENT '岗位ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_job_role`(`job_id`, `role_id`) USING BTREE
)
    ENGINE = INNODB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统岗位/角色关系表';

/*Data for the table `job_role` */

/*Table structure for table `menu` */

CREATE TABLE `menu` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count`   INT(5) UNSIGNED     DEFAULT '0' COMMENT '子菜单数目',
    `type`        INT(11) UNSIGNED    DEFAULT NULL COMMENT '菜单类型',
    `title`       VARCHAR(255)        DEFAULT NULL COMMENT '菜单标题',
    `name`        VARCHAR(255)        DEFAULT NULL COMMENT '组件名称',
    `component`   VARCHAR(255)        DEFAULT NULL COMMENT '组件',
    `weight`      INT(5) UNSIGNED     DEFAULT NULL COMMENT '排序',
    `icon`        VARCHAR(255)        DEFAULT NULL COMMENT '图标',
    `path`        VARCHAR(255)        DEFAULT NULL COMMENT '链接地址',
    `i_frame`     BIT(1)              DEFAULT NULL COMMENT '是否外链',
    `cache`       BIT(1)              DEFAULT b'0' COMMENT '缓存',
    `hidden`      BIT(1)              DEFAULT b'0' COMMENT '隐藏',
    `permission`  VARCHAR(255)        DEFAULT NULL COMMENT '权限',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_title`(`title`),
    UNIQUE KEY `uk_name`(`name`),
    KEY `key_pid`(`pid`)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 119
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统菜单';

/*Data for the table `menu` */
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (1, NULL, 7, 0, '系统管理', NULL, NULL, 1, 'system', 'system', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-18 15:11:29', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', 2, 'peoples', 'user', '\0', '\0', '\0', 'user:view', NULL,
        NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', 3, 'role', 'role', '\0', '\0', '\0', 'roles:view', NULL, NULL,
        '2018-12-18 15:16:07', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', 5, 'menu', 'menu', '\0', '\0', '\0', 'menu:view', NULL, NULL,
        '2018-12-18 15:17:28', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-18 15:17:48', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (7, 6, 0, 1, '应用日志', 'Log', 'monitor/log/index', 11, 'log', 'log', '\0', '', '\0', NULL, NULL, 'admin',
        '2018-12-18 15:18:26', '2020-06-06 13:11:57');
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-18 15:19:34', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (10, NULL, 5, 0, '组件管理', NULL, NULL, 50, 'zujian', 'components', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-19 13:38:16', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (11, 10, 0, 1, '图标库', 'Icons', 'components/icons/index', 51, 'icon', 'icon', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-19 13:38:49', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (14, 36, 0, 1, '邮件工具', 'Email', 'tools/email/index', 35, 'email', 'email', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-27 10:13:09', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (15, 10, 0, 1, '富文本', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-27 11:58:25', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (18, 36, 3, 1, '存储管理', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', '\0', '\0', '\0',
        'storage:view', NULL, NULL, '2018-12-31 11:12:15', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (19, 36, 0, 1, '支付宝工具', 'AliPay', 'tools/aliPay/index', 37, 'alipay', 'aliPay', '\0', '\0', '\0', NULL, NULL,
        NULL, '2018-12-31 14:52:38', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (21, NULL, 2, 0, '多级菜单', NULL, '', 900, 'menu', 'nested', '\0', '\0', '\0', NULL, NULL, 'admin',
        '2019-01-04 16:22:03', '2020-06-21 17:27:35');
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (22, 21, 2, 0, '二级菜单1', NULL, '', 999, 'menu', 'menu1', '\0', '\0', '\0', NULL, NULL, 'admin',
        '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (23, 21, 0, 1, '二级菜单2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', '\0', '\0', '\0', NULL, NULL, NULL,
        '2019-01-04 16:23:57', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (24, 22, 0, 1, '三级菜单1', 'Test', 'nested/menu1/menu1-1', 999, 'menu', 'menu1-1', '\0', '\0', '\0', NULL, NULL,
        NULL, '2019-01-04 16:24:48', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (27, 22, 0, 1, '三级菜单2', NULL, 'nested/menu1/menu1-2', 999, 'menu', 'menu1-2', '\0', '\0', '\0', NULL, NULL, NULL,
        '2019-01-07 17:27:32', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'system/timing/index', 999, 'timing', 'timing', '\0', '\0', '\0', 'timing:view',
        NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (30, 36, 0, 1, '代码生成', 'CodeIndex', 'code/generator/index', 32, 'dev', 'code', '\0', '', '\0', NULL, NULL, NULL,
        '2019-01-11 15:45:55', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', '\0', '\0', '\0', NULL,
        NULL, NULL, '2019-03-08 13:46:44', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (34, 10, 0, 1, 'Yaml编辑器', 'YamlEdit', 'components/YamlEdit', 54, 'dev', 'yaml', '\0', '\0', '\0', NULL, NULL,
        NULL, '2019-03-08 15:49:40', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'system/dept/index', 6, 'dept', 'dept', '\0', '\0', '\0', 'dept:view', NULL, NULL,
        '2019-03-25 09:46:00', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'sys-tools', '\0', '\0', '\0', NULL, NULL, NULL,
        '2019-03-29 10:57:35', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', '\0', '\0', '\0', 'job:view', NULL,
        NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (38, 36, 0, 1, '接口文档', 'Swagger', 'tools/swagger/index', 36, 'swagger', 'swagger2', '\0', '\0', '\0', NULL, NULL,
        NULL, '2019-03-29 19:57:53', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', '\0', '\0', '\0', 'dict:view', NULL,
        NULL, '2019-04-10 11:49:04', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (41, 6, 0, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', '\0', '\0', '\0', NULL,
        NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'user:add', NULL, NULL, '2019-10-29 10:59:46',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'user:edit', NULL, NULL, '2019-10-29 11:00:08',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'user:del', NULL, NULL, '2019-10-29 11:00:23',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', '\0', '\0', '\0', 'roles:add', NULL, NULL, '2019-10-29 12:45:34',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', '\0', '\0', '\0', 'roles:edit', NULL, NULL, '2019-10-29 12:46:16',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'roles:del', NULL, NULL, '2019-10-29 12:46:51',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'menu:add', NULL, NULL, '2019-10-29 12:55:07',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'menu:del', NULL, NULL, '2019-10-29 12:56:00',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'dept:add', NULL, NULL, '2019-10-29 12:57:09',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'dept:del', NULL, NULL, '2019-10-29 12:57:41',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'job:add', NULL, NULL, '2019-10-29 12:58:27',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'job:edit', NULL, NULL, '2019-10-29 12:58:45',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'job:del', NULL, NULL, '2019-10-29 12:59:04',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'dict:add', NULL, NULL, '2019-10-29 13:00:17',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'dict:del', NULL, NULL, '2019-10-29 13:00:59',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'timing:add', NULL, NULL, '2019-10-29 13:07:28',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'timing:edit', NULL, NULL, '2019-10-29 13:07:41',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'timing:del', NULL, NULL, '2019-10-29 13:07:54',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', '\0', '\0', '\0', 'storage:add', NULL, NULL, '2019-10-29 13:09:09',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'storage:edit', NULL, NULL, '2019-10-29 13:09:22',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'storage:del', NULL, NULL, '2019-10-29 13:09:34',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (80, 6, 0, 1, '服务监控', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', '\0', '\0', '\0',
        'monitor:view', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'code/generator/config', 33, 'dev',
        'code/config/:dbId/:schemaName/:tableName', '\0', '', '', '', NULL, NULL, '2019-11-17 20:08:56', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (83, 10, 0, 1, '图表库', 'Echarts', 'components/Echarts', 50, 'chart', 'echarts', '\0', '', '\0', '', NULL, NULL,
        '2019-11-21 09:04:32', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (90, NULL, 5, 1, '运维管理', 'Mnt', '', 20, 'mnt', 'mnt', '\0', '\0', '\0', NULL, NULL, NULL, '2019-11-09 10:31:08',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (92, 90, 3, 1, '服务器', 'ServerDeploy', 'mnt/server/index', 22, 'server', 'mnt/serverDeploy', '\0', '\0', '\0',
        'serverDeploy:view', NULL, NULL, '2019-11-10 10:29:25', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (93, 90, 3, 1, '应用管理', 'App', 'mnt/app/index', 23, 'app', 'mnt/app', '\0', '\0', '\0', 'app:view', NULL, NULL,
        '2019-11-10 11:05:16', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (94, 90, 3, 1, '部署管理', 'Deploy', 'mnt/deploy/index', 24, 'deploy', 'mnt/deploy', '\0', '\0', '\0', 'deploy:view',
        NULL, NULL, '2019-11-10 15:56:55', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (97, 90, 1, 1, '部署备份', 'DeployHistory', 'mnt/deployHistory/index', 25, 'backup', 'mnt/deployHistory', '\0', '\0',
        '\0', 'deployHistory:view', NULL, NULL, '2019-11-10 16:49:44', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (98, 36, 3, 1, '数据库管理', 'Database', 'code/database/index', 26, 'database', 'code/database', '\0', '\0', '\0',
        'database:view', NULL, NULL, '2019-11-10 20:40:04', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (102, 97, 0, 2, '删除', NULL, '', 999, '', '', '\0', '\0', '\0', 'deployHistory:del', NULL, NULL,
        '2019-11-17 09:32:48', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (103, 92, 0, 2, '服务器新增', NULL, '', 999, '', '', '\0', '\0', '\0', 'serverDeploy:add', NULL, NULL,
        '2019-11-17 11:08:33', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (104, 92, 0, 2, '服务器编辑', NULL, '', 999, '', '', '\0', '\0', '\0', 'serverDeploy:edit', NULL, NULL,
        '2019-11-17 11:08:57', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (105, 92, 0, 2, '服务器删除', NULL, '', 999, '', '', '\0', '\0', '\0', 'serverDeploy:del', NULL, NULL,
        '2019-11-17 11:09:15', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (106, 93, 0, 2, '应用新增', NULL, '', 999, '', '', '\0', '\0', '\0', 'app:add', NULL, NULL, '2019-11-17 11:10:03',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (107, 93, 0, 2, '应用编辑', NULL, '', 999, '', '', '\0', '\0', '\0', 'app:edit', NULL, NULL, '2019-11-17 11:10:28',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (108, 93, 0, 2, '应用删除', NULL, '', 999, '', '', '\0', '\0', '\0', 'app:del', NULL, NULL, '2019-11-17 11:10:55',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (109, 94, 0, 2, '部署新增', NULL, '', 999, '', '', '\0', '\0', '\0', 'deploy:add', NULL, NULL, '2019-11-17 11:11:22',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (110, 94, 0, 2, '部署编辑', NULL, '', 999, '', '', '\0', '\0', '\0', 'deploy:edit', NULL, NULL,
        '2019-11-17 11:11:41', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (111, 94, 0, 2, '部署删除', NULL, '', 999, '', '', '\0', '\0', '\0', 'deploy:del', NULL, NULL, '2019-11-17 11:12:01',
        NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (112, 98, 0, 2, '数据库新增', NULL, '', 999, '', '', '\0', '\0', '\0', 'database:add', NULL, NULL,
        '2019-11-17 11:12:43', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (113, 98, 0, 2, '数据库编辑', NULL, '', 999, '', '', '\0', '\0', '\0', 'database:edit', NULL, NULL,
        '2019-11-17 11:12:58', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (114, 98, 0, 2, '数据库删除', NULL, '', 999, '', '', '\0', '\0', '\0', 'database:del', NULL, NULL,
        '2019-11-17 11:13:14', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'code/generator/preview', 999, 'java',
        'code/preview/:dbId/:schemaName/:tableName', '\0', '', '', NULL, NULL, NULL, '2019-11-26 14:54:36', NULL);
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (117, NULL, 1, 0, '项目案例', NULL, NULL, 50, 'demo', 'demo', '\0', '\0', '\0', NULL, NULL, NULL,
        '2021-12-19 13:38:16', '2021-09-17 21:16:42');
INSERT INTO `menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `weight`, `icon`, `path`,
                   `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (118, 117, 0, 1, 'Hello', 'Hello', 'demo/index', 50, '', 'demo/index', '\0', '\0', '\0', 'demo:view', NULL,
        'admin', '2021-12-19 13:38:16', '2021-09-17 21:22:01');
/*Table structure for table `role` */

CREATE TABLE `role` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
    UNIQUE KEY `uk_name`(`name`),
    KEY `role_name_index`(`name`)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色表';

/*Data for the table `role` */

INSERT INTO `role`(`id`, `name`, `level`, `data_scope`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (1, '超级管理员', 1, '全部', '', '-', NULL, 'admin', '2018-11-23 11:04:37', '2020-08-06 16:10:24');
INSERT INTO `role`(`id`, `name`, `level`, `data_scope`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                   `update_time`)
VALUES (2, '普通用户', 2, '本级', '', '-', NULL, 'admin', '2018-11-23 13:09:06', '2020-09-05 10:45:12');

/*Table structure for table `role_dept_map` */

CREATE TABLE `role_dept_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL,
    `dept_id` BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_menu`(`role_id`, `dept_id`) USING BTREE
)
    ENGINE = INNODB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色部门关联';

/*Data for the table `role_dept_map` */

/*Table structure for table `role_menu_map` */

CREATE TABLE `role_menu_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '菜单ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_role_menu`(`menu_id`, `role_id`) USING BTREE
)
    ENGINE = INNODB
    AUTO_INCREMENT = 178
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色菜单关联';

/*Data for the table `role_menu_map` */

INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (131, 1, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (78, 1, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (130, 2, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (79, 2, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (127, 3, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (129, 5, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (128, 6, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (80, 6, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (134, 7, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (81, 7, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (136, 9, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (82, 9, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (135, 10, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (83, 10, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (132, 11, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (84, 11, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (133, 14, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (85, 14, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (140, 15, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (86, 15, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (141, 18, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (137, 19, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (87, 19, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (139, 21, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (88, 21, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (138, 22, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (89, 22, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (146, 23, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (90, 23, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (145, 24, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (91, 24, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (143, 27, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (92, 27, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (142, 28, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (144, 30, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (93, 30, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (152, 33, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (94, 33, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (151, 34, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (95, 34, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (148, 35, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (147, 36, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (96, 36, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (150, 37, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (149, 38, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (156, 39, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (157, 41, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (153, 44, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (155, 45, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (154, 46, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (161, 48, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (163, 49, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (162, 50, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (158, 52, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (160, 53, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (159, 54, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (167, 56, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (169, 57, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (168, 58, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (164, 60, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (166, 61, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (165, 62, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (170, 64, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (172, 65, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (171, 66, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (177, 73, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (176, 74, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (173, 75, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (175, 77, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (174, 78, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (103, 79, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (102, 80, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (97, 80, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (104, 82, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (98, 82, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (101, 83, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (99, 83, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (108, 90, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (105, 92, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (107, 93, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (106, 94, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (111, 97, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (110, 98, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (109, 102, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (117, 103, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (116, 104, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (119, 105, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (118, 106, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (113, 107, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (112, 108, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (115, 109, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (114, 110, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (124, 111, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (123, 112, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (126, 113, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (125, 114, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (120, 116, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (100, 116, 2);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (122, 117, 1);
INSERT INTO `role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (121, 118, 1);

/*Table structure for table `user` */

CREATE TABLE `user` (
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
    `enabled`        BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_by`      VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`      VARCHAR(255)        DEFAULT NULL COMMENT '更新着',
    `pwd_reset_time` DATETIME            DEFAULT NULL COMMENT '修改密码的时间',
    `create_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email`(`email`) USING BTREE,
    UNIQUE KEY `uk_username`(`username`) USING BTREE,
    KEY `key_dept_id`(`dept_id`) USING BTREE,
    KEY `key_avatar`(`avatar`) USING BTREE,
    KEY `key_enabled`(`enabled`)
)
    ENGINE = INNODB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统用户';

/*Data for the table `user` */

INSERT INTO `user`(`id`, `dept_id`, `job_id`, `username`, `nickname`, `gender`, `phone`, `email`, `avatar`,
                   `password`, `is_admin`, `enabled`, `create_by`, `update_by`, `pwd_reset_time`, `create_time`,
                   `update_time`)
VALUES (1, 2, 1, 'admin', '管理员', '男', '18888888888', '201507802@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', '', 1, NULL, 'admin', '2020-05-03 16:38:31',
        '2018-08-23 09:11:56', '2020-09-05 10:43:31');
INSERT INTO `user`(`id`, `dept_id`, `job_id`, `username`, `nickname`, `gender`, `phone`, `email`, `avatar`,
                   `password`, `is_admin`, `enabled`, `create_by`, `update_by`, `pwd_reset_time`, `create_time`,
                   `update_time`)
VALUES (2, 2, 2, 'test', '测试', '男', '15199999999', '231@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', '\0', 1, 'admin', 'admin', NULL,
        '2020-05-05 11:15:49', '2020-09-05 10:43:38');

/*Table structure for table `user_role_map` */

CREATE TABLE `user_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_user_role`(`user_id`, `role_id`) USING BTREE
)
    ENGINE = INNODB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='用户角色关联';

/*Data for the table `user_role_map` */

INSERT INTO `user_role_map`(`id`, `user_id`, `role_id`)
VALUES (1, 1, 1);
INSERT INTO `user_role_map`(`id`, `user_id`, `role_id`)
VALUES (3, 2, 2);
