DROP TABLE IF EXISTS `tool_file_info`;
CREATE TABLE `tool_file_info` (
    `id`           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `file_name`    VARCHAR(255)        NOT NULL COMMENT '文件名',
    `namespace`    VARCHAR(255) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    `tag`          VARCHAR(255) DEFAULT '' COMMENT '标签。供业务系统使用',
    `origin_name`  VARCHAR(255)        NOT NULL COMMENT '源文件名',
    `size`         BIGINT(20) UNSIGNED NOT NULL COMMENT '文件大小',
    `extension`    VARCHAR(32)         NOT NULL COMMENT '文件扩展名',
    `content_type` VARCHAR(255)        NOT NULL COMMENT '文件实际类型',
    `store_type`   VARCHAR(100) DEFAULT '' COMMENT '文件存储服务类型',
    `store_url`    VARCHAR(255) DEFAULT '' COMMENT '文件存储路径',
    `access_url`   VARCHAR(255)        NOT NULL COMMENT '文件访问路径',
    `create_by`    VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`    VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_name`(`file_name`),
    UNIQUE KEY `uk_access_url`(`access_url`),
    UNIQUE KEY `uk_keys`(`origin_name`, `tag`, `namespace`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='文件信息表';

DROP TABLE IF EXISTS `tool_file_content`;
CREATE TABLE `tool_file_content` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `file_name`   VARCHAR(128)        NOT NULL COMMENT '实际文件名',
    `namespace`   VARCHAR(32)  DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    `tag`         VARCHAR(32)  DEFAULT '' COMMENT '标签。供业务系统使用',
    `origin_name` VARCHAR(128)        NOT NULL COMMENT '源文件名',
    `store_type`  VARCHAR(128) DEFAULT '' COMMENT '文件存储服务类型',
    `store_url`   VARCHAR(128) DEFAULT '' COMMENT '文件存储路径',
    `content`     BLOB                NOT NULL COMMENT '文件内容',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_name`(`file_name`),
    UNIQUE KEY `uk_store_url`(`store_url`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='文件内容表';
