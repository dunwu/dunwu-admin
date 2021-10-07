package io.github.dunwu.module.storage.dao.impl;

import io.github.dunwu.module.storage.dao.FileInfoDao;
import io.github.dunwu.module.storage.dao.mapper.FileInfoMapper;
import io.github.dunwu.module.storage.entity.FileInfo;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 文件信息表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Dao
public class FileInfoDaoImpl extends BaseExtDaoImpl<FileInfoMapper, FileInfo> implements FileInfoDao {

}
