package io.github.dunwu.module.storage.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.service.FileInfoService;
import io.github.dunwu.module.storage.service.FileStorageService;
import io.github.dunwu.tool.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件存储服务基类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see DbFileStorageServiceImpl
 * @see LocalFileStorageServiceImpl
 * @see FdfsFileStorageServiceImpl
 * @since 2019-07-29
 */
public abstract class BaseFileStorageService implements FileStorageService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String FILE_SEPARATOR = SystemUtil.get(SystemUtil.FILE_SEPARATOR);

    @Autowired
    protected FileInfoService fileInfoService;

    @Override
    public FileInfoDto getFileInfo(UploadFileDto uploadFileDto) throws IOException {
        MultipartFile file = uploadFileDto.getFile();

        String extension = FileUtil.getExtensionName(file.getOriginalFilename());
        if (extension != null) {
            extension = extension.toLowerCase();
        }

        String originName;
        if (StrUtil.isNotBlank(uploadFileDto.getOriginName())) {
            originName = FileUtil.getName(uploadFileDto.getOriginName()) + "." + extension;
        } else {
            originName = file.getOriginalFilename();
        }
        String fileName = IdUtil.fastSimpleUUID() + "." + extension;

        if (StrUtil.isBlank(uploadFileDto.getNamespace())) {
            uploadFileDto.setNamespace("default");
        }
        if (StrUtil.isBlank(uploadFileDto.getTag())) {
            uploadFileDto.setTag("default");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFileDto.getNamespace())
          .append("/")
          .append(uploadFileDto.getTag())
          .append("/")
          .append(originName);

        FileInfoDto fileInfoDTO = new FileInfoDto();
        fileInfoDTO.setNamespace(uploadFileDto.getNamespace())
                   .setTag(uploadFileDto.getTag())
                   .setOriginName(originName)
                   .setFileName(fileName)
                   .setSize(file.getSize())
                   .setExtension(extension)
                   .setContentType(file.getContentType())
                   .setStoreType(uploadFileDto.getStoreType())
                   .setStoreUrl(fileName)
                   .setAccessUrl(sb.toString())
                   .setContent(file.getBytes());
        return fileInfoDTO;
    }

}
