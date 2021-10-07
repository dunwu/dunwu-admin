package io.github.dunwu.module.storage.constant;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-27
 */

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.github.dunwu.tool.core.constant.GetStringCode;
import io.github.dunwu.tool.core.constant.GetStringValue;

/**
 * 文件存储类型
 */
public enum StorageTypeEnum implements GetStringCode, GetStringValue {
    /** 本地存储 */
    LOCAL("LOCAL", StorageConstant.LOCAL_FILE_CONTENT_SERVICE),
    /** DB 存储 */
    DB("DB", StorageConstant.DB_FILE_CONTENT_SERVICE),
    /** FastDFS 存储 */
    FDFS("FDFS", StorageConstant.FDFS_FILE_CONTENT_SERVICE);

    @EnumValue
    private final String code;

    private final String value;

    StorageTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
