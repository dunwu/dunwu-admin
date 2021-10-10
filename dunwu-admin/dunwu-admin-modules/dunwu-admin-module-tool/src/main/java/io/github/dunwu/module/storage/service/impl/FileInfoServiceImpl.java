package io.github.dunwu.module.storage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.storage.dao.FileInfoDao;
import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.service.FileInfoService;
import io.github.dunwu.module.storage.entity.FileInfo;
import io.github.dunwu.module.storage.entity.query.FileInfoQuery;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件信息表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl implements FileInfoService {

    private final FileInfoDao dao;

    public FileInfoServiceImpl(FileInfoDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(FileInfo entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<FileInfo> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(FileInfo entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<FileInfo> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(FileInfo entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<FileInfo> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public List<FileInfoDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<FileInfoDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<FileInfoDto> pojoListByQuery(FileInfoQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<FileInfoDto> pojoSpringPageByQuery(FileInfoQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public FileInfoDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public FileInfoDto pojoByQuery(FileInfoQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(FileInfoQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<FileInfoDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, FileInfoQuery query, HttpServletResponse response) {
        Page<FileInfoDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 FileInfoDto 列表，导出 excel 表单
     *
     * @param list     {@link FileInfoDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<FileInfoDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (FileInfoDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("文件名", item.getFileName());
            map.put("命名空间。一般对应业务系统", item.getNamespace());
            map.put("标签。供业务系统使用", item.getTag());
            map.put("源文件名", item.getOriginName());
            map.put("文件大小", item.getSize());
            map.put("文件扩展名", item.getExtension());
            map.put("文件实际类型", item.getContentType());
            map.put("文件存储服务类型", item.getStoreType());
            map.put("文件存储路径", item.getStoreUrl());
            map.put("文件访问路径", item.getAccessUrl());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public FileInfoDto doToDto(FileInfo entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, FileInfoDto.class);
    }

    @Override
    public FileInfo dtoToDo(FileInfoDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, FileInfo.class);
    }

}
