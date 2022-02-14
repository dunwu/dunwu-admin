package io.github.dunwu.module.storage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.storage.constant.StorageConstant;
import io.github.dunwu.module.storage.dao.FileContentDao;
import io.github.dunwu.module.storage.entity.FileContent;
import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 数据库文件存储服务
 * <p>
 * 文件将被存储到数据库表字段（file_content 表的 content 字段，BLOB 类型）中。
 * <p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@RequiredArgsConstructor
@Service(value = StorageConstant.DB_FILE_CONTENT_SERVICE)
public class DbFileStorageServiceImpl extends BaseFileStorageService implements FileStorageService {

    protected final FileContentDao fileContentDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoDto create(UploadFileDto uploadFileDto) throws IOException {
        MultipartFile file = uploadFileDto.getFile();
        FileInfoDto fileDTO = super.getFileInfo(uploadFileDto);
        FileContent fileContent = BeanUtil.toBean(fileDTO, FileContent.class);
        fileContent.setContent(file.getBytes());
        if (fileContentDao.save(fileContent)) {
            return fileDTO;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(FileContentDto fileContentDTO) {
        FileContent fileContent = BeanUtil.toBean(fileContentDTO, FileContent.class);
        return fileContentDao.delete(new QueryWrapper<>(fileContent));
    }

    @Override
    public FileContentDto getOne(FileContentDto fileContentDTO) {
        FileContent query = BeanUtil.toBean(fileContentDTO, FileContent.class);
        FileContent result = fileContentDao.getOne(new QueryWrapper<>(query));
        return BeanUtil.toBean(result, FileContentDto.class);
    }

}
