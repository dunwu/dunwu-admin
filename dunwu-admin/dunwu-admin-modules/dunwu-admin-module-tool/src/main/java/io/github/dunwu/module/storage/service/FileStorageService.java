package io.github.dunwu.module.storage.service;

import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;

import java.io.IOException;

/**
 * 文件存储服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see BaseFileStorageService
 * @see DbFileStorageServiceImpl
 * @see TempFileStorageServiceImpl
 * @see FdfsFileStorageServiceImpl
 * @since 2019-07-24
 */
public interface FileStorageService {

    /**
     * 添加文件到存储介质
     *
     * @param uploadFileDto 上传文件信息
     * @return 成功返回文件信息 DTO；失败返回 null
     * @throws IOException IO 异常
     */
    FileInfoDto create(UploadFileDto uploadFileDto) throws IOException;

    /**
     * 从存储介质删除文件
     *
     * @param fileContentDTO 上传文件内容
     * @return 成功返回 true；失败返回 false
     * @throws IOException IO 异常
     */
    boolean delete(FileContentDto fileContentDTO) throws IOException;

    /**
     * 从存储介质删除文件
     *
     * @param fileContentDTO 查询条件
     * @return 成功返回文件内容 DTO；失败返回 null(含未找到)
     * @throws IOException IO 异常
     */
    FileContentDto getOne(FileContentDto fileContentDTO) throws IOException;

    /**
     * 获取文件信息
     *
     * @param uploadFileDto 上传文件信息
     * @return FileInfoDto 文件信息 DTO
     * @throws IOException IO 异常
     */
    FileInfoDto getFileInfo(UploadFileDto uploadFileDto) throws IOException;

}
