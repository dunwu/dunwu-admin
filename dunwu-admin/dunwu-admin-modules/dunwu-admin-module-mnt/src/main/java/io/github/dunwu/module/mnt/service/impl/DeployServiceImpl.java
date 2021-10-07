package io.github.dunwu.module.mnt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.mnt.dao.DeployDao;
import io.github.dunwu.module.mnt.dao.DeployServerMapDao;
import io.github.dunwu.module.mnt.entity.Deploy;
import io.github.dunwu.module.mnt.entity.DeployHistory;
import io.github.dunwu.module.mnt.entity.DeployServerMap;
import io.github.dunwu.module.mnt.entity.dto.AppDto;
import io.github.dunwu.module.mnt.entity.dto.DeployDto;
import io.github.dunwu.module.mnt.entity.dto.DeployHistoryDto;
import io.github.dunwu.module.mnt.entity.dto.ServerDto;
import io.github.dunwu.module.mnt.entity.query.DeployQuery;
import io.github.dunwu.module.mnt.service.AppService;
import io.github.dunwu.module.mnt.service.DeployHistoryService;
import io.github.dunwu.module.mnt.service.DeployService;
import io.github.dunwu.module.mnt.service.ServerService;
import io.github.dunwu.module.mnt.util.ScpClientUtil;
import io.github.dunwu.module.mnt.util.ShellUtil;
import io.github.dunwu.module.security.util.SecurityUtil;
import io.github.dunwu.module.sys.constant.enums.WebSocketMsgType;
import io.github.dunwu.module.sys.websocket.WebSocketEndpoint;
import io.github.dunwu.module.sys.websocket.WebSocketMsg;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 部署管理 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployServiceImpl extends ServiceImpl implements DeployService {

    /**
     * 循环次数
     */
    private final Integer count = 30;
    private final String FILE_SEPARATOR = "/";
    private final DeployDao deployDao;
    private final DeployServerMapDao deployServerMapDao;
    private final AppService appService;
    private final ServerService serverService;
    private final DeployHistoryService deployHistoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(DeployDto dto) {
        Deploy entity = dtoToDo(dto);
        boolean isOk = deployDao.insert(entity);
        if (!isOk) {
            return false;
        }

        if (CollectionUtil.isEmpty(dto.getServers())) {
            return false;
        }
        List<DeployServerMap> mapList = new ArrayList<>();
        dto.getServers().forEach(server -> {
            mapList.add(new DeployServerMap(entity.getId(), server.getId()));
        });
        return deployServerMapDao.insertBatch(mapList);
    }

    @Override
    public boolean insertBatch(Collection<Deploy> list) {
        return deployDao.insertBatch(list);
    }

    @Override
    public boolean updateById(DeployDto dto) {
        Deploy entity = dtoToDo(dto);
        boolean isOk = deployDao.updateById(entity);
        if (!isOk) {
            return false;
        }

        QueryWrapper<DeployServerMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeployServerMap.DEPLOY_ID, entity.getId());
        deployServerMapDao.delete(wrapper);

        if (CollectionUtil.isEmpty(dto.getServers())) {
            return false;
        }
        List<DeployServerMap> mapList = new ArrayList<>();
        dto.getServers().forEach(server -> {
            mapList.add(new DeployServerMap(entity.getId(), server.getId()));
        });
        return deployServerMapDao.insertBatch(mapList);
    }

    @Override
    public boolean updateBatchById(Collection<Deploy> list) {
        return deployDao.updateBatchById(list);
    }

    @Override
    public boolean save(Deploy entity) {
        return deployDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Deploy> list) {
        return deployDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return deployDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return deployDao.deleteBatchByIds(ids);
    }

    @Override
    public List<DeployDto> pojoList() {
        return deployDao.pojoList(this::doToDto);
    }

    @Override
    public List<DeployDto> pojoListByQuery(DeployQuery query) {
        return deployDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DeployDto> pojoSpringPageByQuery(DeployQuery query, Pageable pageable) {
        return deployDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public DeployDto pojoById(Serializable id) {
        return deployDao.pojoById(id, this::doToDto);
    }

    @Override
    public DeployDto pojoByQuery(DeployQuery query) {
        return deployDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DeployQuery query) {
        return deployDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DeployDto> list = deployDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(DeployQuery query, Pageable pageable, HttpServletResponse response) {
        Page<DeployDto> page = deployDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DeployDto 列表，导出 excel 表单
     *
     * @param list     {@link DeployDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DeployDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DeployDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("应用编号", item.getAppId());
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
    public DeployDto doToDto(Deploy entity) {
        if (entity == null) {
            return null;
        }

        AppDto appDto = appService.pojoById(entity.getAppId());
        List<ServerDto> servers = serverService.pojoListByDeployId(entity.getId());
        DeployDto deployDto = BeanUtil.toBean(entity, DeployDto.class);
        deployDto.setApp(appDto)
                 .setServers(servers);
        return deployDto;
    }

    @Override
    public Deploy dtoToDo(DeployDto dto) {
        if (dto == null) {
            return null;
        }

        Deploy deploy = new Deploy();
        deploy.setId(dto.getId())
              .setAppId(dto.getApp().getId())
              .setNote(dto.getNote());
        return deploy;
    }

    @Override
    public void deployApp(String fileSavePath, Long appId) {
        DeployDto deploy = pojoById(appId);
        if (deploy == null) {
            sendMsg("部署信息不存在", WebSocketMsgType.ERROR);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "文件只能上传在opt目录或者home目录");
        }
        AppDto app = deploy.getApp();
        if (app == null) {
            sendMsg("包对应应用信息不存在", WebSocketMsgType.ERROR);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "包对应应用信息不存在");
        }
        int port = app.getPort();
        //这个是服务器部署路径
        String uploadPath = app.getUploadPath();
        StringBuilder sb = new StringBuilder();
        String msg;
        List<ServerDto> servers = deploy.getServers();
        for (ServerDto server : servers) {
            String ip = server.getIp();
            ShellUtil shellUtil = newShellUtil(server);
            //判断是否第一次部署
            String shellResult = shellUtil.executeForResult(
                "find " + app.getDeployPath() + " -name " + app.getName());
            boolean flag = shellResult.indexOf(app.getName()) > 0;
            //第一步要确认服务器上有这个目录
            shellUtil.execute("mkdir -p " + app.getUploadPath());
            shellUtil.execute("mkdir -p " + app.getBackupPath());
            shellUtil.execute("mkdir -p " + app.getDeployPath());
            //上传文件
            msg = String.format("登陆到服务器:%s", ip);
            ScpClientUtil scpClientUtil = ScpClientUtil.getInstance(ip, server.getPort(),
                server.getAccount(), server.getPassword());
            log.info(msg);
            sendMsg(msg, WebSocketMsgType.INFO);
            msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", ip, uploadPath);
            sendMsg(msg, WebSocketMsgType.INFO);
            scpClientUtil.putFile(fileSavePath, uploadPath);
            if (flag) {
                sendMsg("停止原来应用", WebSocketMsgType.INFO);
                //停止应用
                shellUtil.stopPidByPort(port);
                sendMsg("备份原来应用", WebSocketMsgType.INFO);
                //备份应用
                backupApp(shellUtil, ip, app.getDeployPath() + FILE_SEPARATOR, app.getName(),
                    app.getBackupPath() + FILE_SEPARATOR, appId);
            }
            sendMsg("部署应用", WebSocketMsgType.INFO);
            //部署文件,并启动应用
            String deployScript = app.getDeployScript();
            shellUtil.execute(deployScript);
            sleep(3);
            sendMsg("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态", WebSocketMsgType.INFO);
            int i = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count) {
                result = shellUtil.isPortRunning(port);
                if (result) {
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(app.getName());
            sendResultMsg(result, sb);
            shellUtil.close();
        }
    }

    private void sendMsg(String msg, WebSocketMsgType webSocketMsgType) {
        try {
            WebSocketEndpoint.sendInfo(new WebSocketMsg(msg, webSocketMsgType), "deploy");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void backupApp(ShellUtil shellUtil, String ip, String fileSavePath, String appName,
        String backupPath, Long id) {
        String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        StringBuilder sb = new StringBuilder();
        backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
        sb.append("mkdir -p ").append(backupPath);
        sb.append("mv -f ").append(fileSavePath);
        sb.append(appName).append(" ").append(backupPath);
        log.info("备份应用脚本:" + sb.toString());
        shellUtil.execute(sb.toString());
        //还原信息入库
        DeployHistory deployHistory = new DeployHistory();
        deployHistory.setAppName(appName);
        deployHistory.setDeployUser(SecurityUtil.getCurrentUsername());
        deployHistory.setIp(ip);
        deployHistory.setDeployId(id);
        deployHistoryService.insert(deployHistory);
    }

    private void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public ShellUtil newShellUtil(ServerDto server) {
        return new ShellUtil(server.getIp(), server.getAccount(), server.getPassword(), server.getPort());
    }

    @Override
    public String getServerStatus(DeployDto dto) {
        AppDto app = dto.getApp();
        List<ServerDto> servers = dto.getServers();
        for (ServerDto server : servers) {
            StringBuilder sb = new StringBuilder();
            ShellUtil shellUtil = newShellUtil(server);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(app.getName());
            boolean result = shellUtil.isPortRunning(app.getPort());
            if (result) {
                sb.append("<br>正在运行");
                sendMsg(sb.toString(), WebSocketMsgType.INFO);
            } else {
                sb.append("<br>已停止!");
                sendMsg(sb.toString(), WebSocketMsgType.ERROR);
            }
            log.info(sb.toString());
            shellUtil.close();
        }
        return "执行完毕";
    }

    @Override
    public String startServer(DeployDto dto) {
        AppDto app = dto.getApp();
        List<ServerDto> servers = dto.getServers();
        for (ServerDto server : servers) {

            ShellUtil shellUtil = newShellUtil(server);
            //为了防止重复启动，这里先停止应用
            shellUtil.stopPidByPort(server.getPort());
            StringBuilder sb = new StringBuilder();
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(app.getName());
            sendMsg("下发启动命令", WebSocketMsgType.INFO);
            shellUtil.execute(app.getStartScript());
            sleep(3);
            sendMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看运行状态", WebSocketMsgType.INFO);
            int i = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count) {
                result = shellUtil.isPortRunning(app.getPort());
                if (result) {
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sendResultMsg(result, sb);
            log.info(sb.toString());
            shellUtil.close();
        }
        return "执行完毕";
    }

    @Override
    public String stopServer(DeployDto dto) {
        AppDto app = dto.getApp();
        List<ServerDto> servers = dto.getServers();
        for (ServerDto server : servers) {
            StringBuilder sb = new StringBuilder();
            ShellUtil shellUtil = newShellUtil(server);
            sb.append("服务器:").append(server.getName()).append("<br>应用:").append(app.getName());
            sendMsg("下发停止命令", WebSocketMsgType.INFO);
            //停止应用
            shellUtil.stopPidByPort(app.getPort());
            sleep(1);
            boolean result = shellUtil.isPortRunning(app.getPort());
            if (result) {
                sb.append("<br>关闭失败!");
                sendMsg(sb.toString(), WebSocketMsgType.ERROR);
            } else {
                sb.append("<br>关闭成功!");
                sendMsg(sb.toString(), WebSocketMsgType.INFO);
            }
            log.info(sb.toString());
            shellUtil.close();
        }
        return "执行完毕";
    }

    @Override
    public String rollbackServer(DeployHistoryDto dto) {
        Long deployId = dto.getDeployId();
        DeployDto deployDto = pojoById(deployId);
        String deployDate = DateUtil.format(dto.getDeployDate(), DatePattern.PURE_DATETIME_PATTERN);
        AppDto app = deployDto.getApp();
        if (app == null) {
            sendMsg("应用信息不存在：" + dto.getAppName(), WebSocketMsgType.ERROR);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "应用信息不存在：" + dto.getAppName());
        }
        String backupPath = app.getBackupPath() + FILE_SEPARATOR;
        backupPath += dto.getAppName() + FILE_SEPARATOR + deployDate;
        //这个是服务器部署路径
        String deployPath = app.getDeployPath();
        String ip = dto.getIp();
        ServerDto serverDto = serverService.pojoByIp(dto.getIp());
        ShellUtil shellUtil = newShellUtil(serverDto);
        String msg;

        msg = String.format("登陆到服务器:%s", ip);
        log.info(msg);
        sendMsg(msg, WebSocketMsgType.INFO);
        sendMsg("停止原来应用", WebSocketMsgType.INFO);
        //停止应用
        shellUtil.stopPidByPort(app.getPort());
        //删除原来应用
        sendMsg("删除应用", WebSocketMsgType.INFO);
        shellUtil.execute("rm -rf " + deployPath + FILE_SEPARATOR + dto.getAppName());
        //还原应用
        sendMsg("还原应用", WebSocketMsgType.INFO);
        shellUtil.execute("cp -r " + backupPath + "/. " + deployPath);
        sendMsg("启动应用", WebSocketMsgType.INFO);
        shellUtil.execute(app.getStartScript());
        sendMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看启动状态", WebSocketMsgType.INFO);
        int i = 0;
        boolean result = false;
        // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
        while (i++ < count) {
            result = shellUtil.isPortRunning(app.getPort());
            if (result) {
                break;
            }
            // 休眠6秒
            sleep(6);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("服务器:").append(ip).append("<br>应用:").append(dto.getAppName());
        sendResultMsg(result, sb);
        shellUtil.close();
        return "";
    }

    private void sendResultMsg(boolean result, StringBuilder sb) {
        if (result) {
            sb.append("<br>启动成功!");
            sendMsg(sb.toString(), WebSocketMsgType.INFO);
        } else {
            sb.append("<br>启动失败!");
            sendMsg(sb.toString(), WebSocketMsgType.ERROR);
        }
    }

}
