package io.github.dunwu.module.storage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.storage.dao.FileContentDao;
import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.service.FileContentService;
import io.github.dunwu.module.storage.entity.FileContent;
import io.github.dunwu.module.storage.entity.query.FileContentQuery;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件内容表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Service
public class FileContentServiceImpl extends ServiceImpl implements FileContentService {

    private final FileContentDao dao;

    public FileContentServiceImpl(FileContentDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(FileContent entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<FileContent> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(FileContent entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<FileContent> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(FileContent entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<FileContent> list) {
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
    public List<FileContentDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<FileContentDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<FileContentDto> pojoListByQuery(FileContentQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<FileContentDto> pojoSpringPageByQuery(FileContentQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public FileContentDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public FileContentDto pojoByQuery(FileContentQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(FileContentQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<FileContentDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(FileContentQuery query, Pageable pageable, HttpServletResponse response) {
        Page<FileContentDto> page = dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 FileContentDto 列表，导出 excel 表单
     *
     * @param list     {@link FileContentDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<FileContentDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (FileContentDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("实际文件名", item.getFileName());
            map.put("命名空间。一般对应业务系统", item.getNamespace());
            map.put("标签。供业务系统使用", item.getTag());
            map.put("源文件名", item.getOriginName());
            map.put("文件存储服务类型", item.getStoreType());
            map.put("文件存储路径", item.getStoreUrl());
            map.put("文件内容", item.getContent());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public FileContentDto doToDto(FileContent entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, FileContentDto.class);
    }

    @Override
    public FileContent dtoToDo(FileContentDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, FileContent.class);
    }

}
