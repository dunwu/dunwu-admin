package io.github.dunwu.module.storage.service;

import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.entity.query.FileInfoQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

/**
 * 存储服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-25
 */
public interface StorageService {

    /**
     * 添加一个文件，并记录其文件信息
     *
     * @param uploadFileDto 上传文件信息 DTO
     * @return 成功则 Result 中 data 存储 FileInfoDto(ID 为数据库中 ID 值)；反之 data 为 null
     * @throws IOException IO 异常
     */
    FileInfoDto upload(UploadFileDto uploadFileDto) throws IOException;

    /**
     * 删除一个文件，并删除其文件信息
     *
     * @param ids ID 列表
     * @return 成功则 Result 中 data 为 true；反之 data 为 false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询一个文件的文件信息以及其内容
     *
     * @param fileQuery 不为 null 的字段将视为过滤条件
     * @return 成功则 Result 中 data 存储 FileInfoDto；反之 data 为 null
     * @throws IOException IO 异常
     */
    FileInfoDto getOne(FileInfoQuery fileQuery) throws IOException;

    /**
     * 按照过滤条件查询文件的文件信息
     *
     * @param fileQuery 不为 null 的字段将视为过滤条件
     * @param pageable  分页查询条件
     * @return 成功则 Result 中 data 存储 FileInfoDto 分页列表；反之 data 为 null
     */
    Page<FileInfoDto> page(FileInfoQuery fileQuery, Pageable pageable);

    /**
     * 判断当前 IP 是否允许访问文件服务
     *
     * @param ip IP地址
     * @return 成功则 Result 中 data 为 true；反之 data 为 false
     */
    boolean allowAccess(String ip);

}
