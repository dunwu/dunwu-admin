package io.github.dunwu.module.storage.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

/**
 * 存储配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Data
@ToString
@PropertySource(value = "classpath:config/storage.properties", encoding = "utf-8")
@ConfigurationProperties(prefix = "dunwu.storage")
public class DunwuStorageProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private UploadTimeLimit limit = new UploadTimeLimit();

    private Db db = new Db();

    private Local local = new Local();

    private Fdfs fdfs = new Fdfs();

    /**
     * 基本配置
     */
    @Data
    @ToString
    public static abstract class BaseFileProperties {

        /**
         * 文件大小下限。单位：B
         */
        protected Long minSize = 0L;

        /**
         * 文件大小上限。单位：B
         */
        protected Long maxSize = 1024 * 1024 * 10L;

        /**
         * 文件支持类型
         */
        protected String supportTypes = "image/jpg;image/jpeg;image/png;image/gif";

    }

    /**
     * 数据库型文件服务配置
     */
    @Data
    @ToString
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Db extends BaseFileProperties { }

    /**
     * 本地型文件服务配置
     */
    @Data
    @ToString
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Local extends BaseFileProperties {

        /**
         * 文件存储的磁盘路径
         */
        private String location = "/home/fs/data";

    }

    /**
     * FastDFS 文件服务配置
     */
    @Data
    @ToString
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Fdfs extends BaseFileProperties {

        /**
         * 文件存储的 FastDFS Group
         */
        private String group = "group1";

    }

    /**
     * 上传文件次数限制
     */
    @Data
    @ToString
    public static class UploadTimeLimit {

        /**
         * 上传文件次数统计时间间隔。单位：豪秒
         */
        private Long statTimeRange = 1000 * 60 * 10L;

        /**
         * 上传文件间隔时间内允许上传的次数
         */
        private Integer maxUploadTimeInInterval = 10;

        /**
         * 解封时间。单位：秒
         */
        private Long waitTime = 1000 * 60 * 10L;

    }

}

