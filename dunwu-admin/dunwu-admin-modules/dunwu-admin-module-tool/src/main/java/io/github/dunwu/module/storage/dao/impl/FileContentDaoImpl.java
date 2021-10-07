package io.github.dunwu.module.storage.dao.impl;

import io.github.dunwu.module.storage.dao.FileContentDao;
import io.github.dunwu.module.storage.entity.FileContent;
import io.github.dunwu.module.storage.dao.mapper.FileContentMapper;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 文件内容表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Dao
public class FileContentDaoImpl extends BaseExtDaoImpl<FileContentMapper, FileContent> implements FileContentDao {

}
