package io.github.dunwu.module.storage.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.dunwu.module.storage.config.DunwuStorageProperties;
import io.github.dunwu.module.storage.constant.StorageTypeEnum;
import io.github.dunwu.module.storage.dao.FileInfoDao;
import io.github.dunwu.module.storage.entity.FileInfo;
import io.github.dunwu.module.storage.entity.dto.AccessDto;
import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.dto.UploadFileDto;
import io.github.dunwu.module.storage.entity.query.FileInfoQuery;
import io.github.dunwu.module.storage.service.FileInfoService;
import io.github.dunwu.module.storage.service.FileStorageService;
import io.github.dunwu.module.storage.service.StorageService;
import io.github.dunwu.tool.bean.BeanUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 存储服务实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Service
@AllArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final FileInfoDao fileInfoDao;
    private final FileInfoService fileInfoService;
    private final DunwuStorageProperties dunwuStorageProperties;

    /**
     * 上传请求统计 Map
     */
    private final Map<String, AccessDto> map = new ConcurrentReferenceHashMap<>(1000);

    @Override
    public FileInfoDto upload(UploadFileDto uploadFileDto)
        throws IOException {
        StorageTypeEnum storeType = uploadFileDto.getStoreType();
        FileStorageService fileStorageService = SpringUtil.getBean(storeType.getValue(), FileStorageService.class);
        FileInfoDto fileInfoDto = fileStorageService.create(uploadFileDto);
        if (fileInfoDto == null) {
            return null;
        }
        FileInfo fileInfo = BeanUtil.toBean(fileInfoDto, FileInfo.class);
        fileInfoDao.save(fileInfo);
        fileInfoDto.setId(fileInfo.getId());
        return fileInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return fileInfoService.deleteBatchByIds(ids);
    }

    @Override
    public FileInfoDto getOne(FileInfoQuery fileQuery) throws IOException {
        FileInfo fileInfoQuery = BeanUtil.toBean(fileQuery, FileInfo.class);
        FileInfo fileInfo = fileInfoDao.getOne(fileInfoQuery);
        if (fileInfo == null) {
            return null;
        }

        StorageTypeEnum storeType = fileInfo.getStoreType();
        FileStorageService fileService = SpringUtil.getBean(storeType.getValue(), FileStorageService.class);
        FileContentDto query = BeanUtil.toBean(fileInfo, FileContentDto.class);
        FileContentDto result = fileService.getOne(query);

        FileInfoDto fileInfoDto = BeanUtil.toBean(fileInfo, FileInfoDto.class);
        fileInfoDto.setContent(result.getContent());
        return fileInfoDto;
    }

    @Override
    public Page<FileInfoDto> page(FileInfoQuery query, Pageable pageable) {
        return fileInfoService.pojoSpringPageByQuery(query, pageable);
    }

    /**
     * 判断请求方是否频繁发起上传请求，如果是，则拒绝请求
     *
     * @param ip 请求IP
     * @return boolean
     */
    @Override
    public boolean allowAccess(String ip) {
        AccessDto accessDTO;
        if (map.containsKey(ip)) {
            accessDTO = map.get(ip);

            Date now = new Date();
            Date first = accessDTO.getDate();

            // 首次请求时间已经超出时间间隔，刷新时间和次数
            DunwuStorageProperties.UploadTimeLimit uploadTimeLimit = dunwuStorageProperties.getLimit();
            if (now.getTime() - first.getTime() > uploadTimeLimit.getStatTimeRange()) {
                accessDTO.setCount(1);
                accessDTO.setDate(new Date());
            } else if (now.getTime() - first.getTime() < uploadTimeLimit.getStatTimeRange()
                && accessDTO.getCount() + 1 > uploadTimeLimit.getStatTimeRange()) {
                // 首次请求时间在时间间隔内，且访问次数超过限制，拒绝访问
                return true;
            } else {
                accessDTO.setCount(accessDTO.getCount() + 1);
            }
        } else {
            accessDTO = new AccessDto(ip, new Date(), 1);
        }

        synchronized (map) {
            map.put(ip, accessDTO);
        }
        return false;
    }

    /**
     * 每 5 分钟定时清除 map，避免占用内存过大
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cleanThreadLocal() {
        log.debug("[定时器启动] 清除上传请求统计 Map ，开始执行清除动作");
        if (MapUtil.isNotEmpty(map)) {
            map.clear();
        }
    }

}
