package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.sys.dao.GlobalConfigDao;
import io.github.dunwu.module.sys.entity.GlobalConfig;
import io.github.dunwu.module.sys.entity.dto.GlobalConfigDto;
import io.github.dunwu.module.sys.entity.query.GlobalConfigQuery;
import io.github.dunwu.module.sys.service.GlobalConfigService;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统全局配置表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Service
public class GlobalConfigServiceImpl extends ServiceImpl implements GlobalConfigService {

    private final GlobalConfigDao dao;

    public GlobalConfigServiceImpl(GlobalConfigDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(GlobalConfig entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<GlobalConfig> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(GlobalConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<GlobalConfig> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(GlobalConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<GlobalConfig> list) {
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
    public List<GlobalConfigDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<GlobalConfigDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<GlobalConfigDto> pojoListByQuery(GlobalConfigQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<GlobalConfigDto> pojoSpringPageByQuery(GlobalConfigQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public GlobalConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public GlobalConfigDto pojoByQuery(GlobalConfigQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(GlobalConfigQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<GlobalConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(GlobalConfigQuery query, Pageable pageable, HttpServletResponse response) {
        Page<GlobalConfigDto> page = dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 GlobalConfigDto 列表，导出 excel 表单
     *
     * @param list     {@link GlobalConfigDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<GlobalConfigDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (GlobalConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("应用编码", item.getAppCode());
            map.put("模块编码", item.getModuleCode());
            map.put("命名空间", item.getNamespace());
            map.put("配置项编码", item.getCode());
            map.put("配置项配置名称", item.getName());
            map.put("配置项值", item.getValue());
            map.put("配置项默认值", item.getDefaultValue());
            map.put("配置项值类型", item.getValueType());
            map.put("是否禁用：1 表示禁用；0 表示启用", item.getIsDisabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public GlobalConfigDto doToDto(GlobalConfig entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, GlobalConfigDto.class);
    }

    @Override
    public GlobalConfig dtoToDo(GlobalConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, GlobalConfig.class);
    }

    @Override
    public List<GlobalConfigDto> pojoListByKeys(String appCode, String moduleCode, String namespace) {
        QueryWrapper<GlobalConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(GlobalConfig.APP_CODE, appCode)
               .eq(GlobalConfig.MODULE_CODE, moduleCode)
               .eq(GlobalConfig.NAMESPACE, namespace)
               .eq(GlobalConfig.IS_DISABLED, false);
        return dao.pojoList(wrapper, this::doToDto);
    }

    @Override
    public GlobalConfigDto pojoOneByKeys(String appCode, String moduleCode, String namespace, String code) {
        QueryWrapper<GlobalConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(GlobalConfig.APP_CODE, appCode)
               .eq(GlobalConfig.MODULE_CODE, moduleCode)
               .eq(GlobalConfig.NAMESPACE, namespace)
               .eq(GlobalConfig.CODE, code)
               .eq(GlobalConfig.IS_DISABLED, false);
        return dao.pojoOne(wrapper, this::doToDto);
    }

    @Override
    public boolean setConfigOptionValue(String appCode, String moduleCode, String namespace, String code,
        String value) {
        QueryWrapper<GlobalConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(GlobalConfig.APP_CODE, appCode)
               .eq(GlobalConfig.MODULE_CODE, moduleCode)
               .eq(GlobalConfig.NAMESPACE, namespace)
               .eq(GlobalConfig.CODE, code)
               .eq(GlobalConfig.IS_DISABLED, false);
        GlobalConfig record = dao.getOne(wrapper);
        if (record == null) {
            String msg = StrUtil.format("[{}] 表未找到 appCode = {}, moduleCode = {}, namespace = {}, code = {} 的配置项",
                "sys_global_config", appCode, moduleCode, namespace, code);
            throw new DataException(msg);
        }
        record.setValue(value);
        return updateById(record);
    }

    @Override
    public boolean setConfigOptionValues(String appCode, String moduleCode, String namespace, Map<String, String> map) {
        if (MapUtil.isEmpty(map)) {
            throw new DataException("修改 sys_global_config 表的 map 不能为空");
        }

        QueryWrapper<GlobalConfig> wrapper = new QueryWrapper<>();
        wrapper.eq(GlobalConfig.APP_CODE, appCode)
               .eq(GlobalConfig.MODULE_CODE, moduleCode)
               .eq(GlobalConfig.NAMESPACE, namespace)
               .eq(GlobalConfig.IS_DISABLED, false);
        List<GlobalConfig> records = dao.list(wrapper);
        if (CollectionUtil.isEmpty(records)) {
            String msg = StrUtil.format("[{}] 表未找到 appCode = {}, moduleCode = {}, namespace = {} 的配置项",
                "sys_global_config", appCode, moduleCode, namespace);
            throw new DataException(msg);
        }

        Set<String> codes = map.keySet();
        Set<String> allCodes = records.stream().map(GlobalConfig::getCode)
                                      .collect(Collectors.toSet());
        if (!CollectionUtil.containsAll(allCodes, codes)) {
            throw new DataException("map 中含有不存在于 sys_global_config 表的 key");
        }

        List<GlobalConfig> newRecords = new ArrayList<>();
        for (GlobalConfig record : records) {
            if (map.containsKey(record.getCode())) {
                record.setValue(map.get(record.getCode()));
                newRecords.add(record);
            }
        }
        return updateBatchById(newRecords);
    }

}
