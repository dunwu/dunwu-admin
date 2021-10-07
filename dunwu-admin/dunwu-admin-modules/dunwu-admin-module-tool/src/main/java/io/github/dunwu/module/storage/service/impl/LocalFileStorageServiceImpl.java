package io.github.dunwu.module.storage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.storage.config.DunwuStorageProperties;
import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.service.FileStorageService;
import io.github.dunwu.module.storage.constant.StorageConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.annotation.PostConstruct;

/**
 * 临时文件存储服务
 * <p>
 * 文件将被存储在服务所在本机的临时磁盘空间，路径为 {@link DunwuStorageProperties.Local#location}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-26
 */
@RequiredArgsConstructor
@Service(value = StorageConstant.LOCAL_FILE_CONTENT_SERVICE)
public class LocalFileStorageServiceImpl extends BaseFileStorageService implements FileStorageService {

    private final DunwuStorageProperties dunwuStorageProperties;

    @PostConstruct
    public void init() {
        Path path = Paths.get(dunwuStorageProperties.getLocal().getLocation());
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("创建临时文件存储目录 {} 失败", dunwuStorageProperties.getLocal().getLocation());
        }
    }

    @Override
    public FileInfoDto create(UploadFileDto uploadFileDto) throws IOException {
        MultipartFile file = uploadFileDto.getFile();
        FileInfoDto fileDTO = this.getFileInfo(uploadFileDto);
        FileContentDto fileContentDTO = BeanUtil.toBean(fileDTO, FileContentDto.class);
        fileContentDTO.setContent(file.getBytes());
        Path path = Paths.get(dunwuStorageProperties.getLocal().getLocation());
        Files.write(path.resolve(fileContentDTO.getStoreUrl()), fileContentDTO.getContent(), StandardOpenOption.CREATE);
        return fileDTO;
    }

    @Override
    public boolean delete(FileContentDto fileContentDTO) throws IOException {
        Path path = Paths.get(fileContentDTO.getStoreUrl());
        Files.delete(path);
        return true;
    }

    @Override
    public FileContentDto getOne(FileContentDto fileContentDTO) throws IOException {
        Path path = Paths.get(fileContentDTO.getStoreUrl());
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            byte[] bytes = FileUtils.readFileToByteArray(resource.getFile());
            fileContentDTO.setContent(bytes);
            return fileContentDTO;
        } else {
            throw new IOException("Could not read file: " + fileContentDTO.getStoreUrl());
        }
    }

    @Override
    public FileInfoDto getFileInfo(UploadFileDto uploadFileDto) throws IOException {
        FileInfoDto fileDTO = super.getFileInfo(uploadFileDto);
        String storeUrl = dunwuStorageProperties.getLocal().getLocation() + "/" + fileDTO.getFileName();
        fileDTO.setStoreUrl(storeUrl);
        return fileDTO;
    }

}
