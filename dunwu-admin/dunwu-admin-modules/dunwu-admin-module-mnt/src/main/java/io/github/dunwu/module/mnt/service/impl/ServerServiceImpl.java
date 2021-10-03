package io.github.dunwu.module.mnt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.annotation.Query;
import io.github.dunwu.module.mnt.dao.DeployServerMapDao;
import io.github.dunwu.module.mnt.dao.ServerDao;
import io.github.dunwu.module.mnt.entity.DeployServerMap;
import io.github.dunwu.module.mnt.entity.Server;
import io.github.dunwu.module.mnt.entity.dto.ServerDto;
import io.github.dunwu.module.mnt.entity.query.ServerQuery;
import io.github.dunwu.module.mnt.service.ServerService;
import io.github.dunwu.module.mnt.util.ShellUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务器配置表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServerServiceImpl extends ServiceImpl implements ServerService {

    private final ServerDao serverDao;
    private final DeployServerMapDao deployServerMapDao;

    @Override
    public boolean insert(Server entity) {
        return serverDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Server> list) {
        return serverDao.insertBatch(list);
    }

    @Override
    public boolean updateById(Server entity) {
        return serverDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Server> list) {
        return serverDao.updateBatchById(list);
    }

    @Override
    public boolean save(Server entity) {
        return serverDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Server> list) {
        return serverDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return serverDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return serverDao.deleteBatchByIds(ids);
    }

    @Override
    public List<ServerDto> pojoList() {
        return serverDao.pojoList(this::doToDto);
    }

    @Override
    public List<ServerDto> pojoListByIds(Collection<? extends Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return serverDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<ServerDto> pojoListByQuery(ServerQuery query) {
        return serverDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<ServerDto> pojoSpringPageByQuery(ServerQuery query, Pageable pageable) {
        return serverDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public ServerDto pojoById(Serializable id) {
        return serverDao.pojoById(id, this::doToDto);
    }

    @Override
    public ServerDto pojoByQuery(ServerQuery query) {
        return serverDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(ServerQuery query) {
        return serverDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<ServerDto> list = serverDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(ServerQuery query, Pageable pageable, HttpServletResponse response) {
        Page<ServerDto> page = serverDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 ServerDto 列表，导出 excel 表单
     *
     * @param list     {@link ServerDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<ServerDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ServerDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("账号", item.getAccount());
            map.put("IP地址", item.getIp());
            map.put("名称", item.getName());
            map.put("密码", item.getPassword());
            map.put("端口", item.getPort());
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
    public ServerDto doToDto(Server entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, ServerDto.class);
    }

    @Override
    public Server dtoToDo(ServerDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Server.class);
    }

    @Override
    public ServerDto pojoByIp(String ip) {
        Server entity = serverDao.getByIp(ip);
        if (entity == null) {
            return null;
        }
        return doToDto(entity);
    }

    @Override
    public List<ServerDto> pojoListByDeployId(Long deployId) {
        if (deployId == null) {
            return Collections.emptyList();
        }
        QueryWrapper<DeployServerMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeployServerMap.DEPLOY_ID, deployId);
        List<DeployServerMap> list = deployServerMapDao.list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        Set<Long> serverIds = list.stream()
            .map(DeployServerMap::getServerId)
            .collect(Collectors.toSet());

        return pojoListByIds(serverIds);
    }

    @Override
    public Boolean testConnect(ServerDto dto) {
        ShellUtil executeShellUtil = null;
        try {
            executeShellUtil = new ShellUtil(dto.getIp(), dto.getAccount(), dto.getPassword(), dto.getPort());
            return executeShellUtil.execute("ls") == 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (executeShellUtil != null) {
                executeShellUtil.close();
            }
        }
    }

}
