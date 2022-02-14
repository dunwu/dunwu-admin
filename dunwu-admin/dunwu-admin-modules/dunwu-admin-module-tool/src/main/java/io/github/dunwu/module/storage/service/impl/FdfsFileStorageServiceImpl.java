package io.github.dunwu.module.storage.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.github.dunwu.module.storage.config.DunwuStorageProperties;
import io.github.dunwu.module.storage.constant.StorageConstant;
import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * FastDFS 文件存储服务
 * <p>
 * 文件将被存储在 FastDFS 中的 {@link DunwuStorageProperties.Fdfs#group}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/happyfish100/fastdfs/wiki">fastdfs wiki</a>
 * @see <a href="https://github.com/tobato/FastDFS_Client">FastDFS_Client</a>
 * @since 2019-07-26
 */
@RequiredArgsConstructor
@Service(value = StorageConstant.FDFS_FILE_CONTENT_SERVICE)
public class FdfsFileStorageServiceImpl extends BaseFileStorageService implements FileStorageService {

    private final FastFileStorageClient storageClient;

    private final DunwuStorageProperties dunwuStorageProperties;

    @Override
    public FileInfoDto create(UploadFileDto uploadFileDto) throws IOException {
        FileInfoDto fileDTO = super.getFileInfo(uploadFileDto);
        StorePath path = storageClient.uploadFile(dunwuStorageProperties.getFdfs().getGroup(),
            uploadFileDto.getFile().getInputStream(),
            fileDTO.getSize(), fileDTO.getExtension());
        fileDTO.setStoreUrl(path.getFullPath());
        return fileDTO;
    }

    @Override
    public boolean delete(FileContentDto fileContentDTO) {
        String storeUrl = fileContentDTO.getStoreUrl();
        storageClient.deleteFile(storeUrl);
        return true;
    }

    @Override
    public FileContentDto getOne(FileContentDto fileContentDTO) {
        StorePath path = StorePath.parseFromUrl(fileContentDTO.getStoreUrl());
        byte[] bytes = storageClient.downloadFile(dunwuStorageProperties.getFdfs().getGroup(), path.getPath(),
            new DownloadByteArray());
        fileContentDTO.setContent(bytes);
        return fileContentDTO;
    }

}
