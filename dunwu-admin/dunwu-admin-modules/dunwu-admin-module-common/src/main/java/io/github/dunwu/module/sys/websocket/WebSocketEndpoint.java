package io.github.dunwu.module.sys.websocket;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Slf4j
@Component
@ServerEndpoint("/webSocket/{module}/{userId}")
public class WebSocketEndpoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 线程安全的 WebSocketEndpoint Set
     */
    private static final ConcurrentHashMap<Long, WebSocketEndpoint> SOCKET_MAP = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收模块
     */
    private String module = "";
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("module") String module, @PathParam("userId") Long userId) {
        // 如果存在就先删除一个，防止重复推送消息
        SOCKET_MAP.keySet().removeIf(key -> key.equals(userId));
        SOCKET_MAP.put(userId, this);
        this.session = session;
        session.getUserPrincipal();
        this.module = module;
        this.userId = userId;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        SOCKET_MAP.remove(userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        log.info("【WebSocket】【接收消息】module = {}, userId = {}，message = {}", module, userId, message);

        if (MapUtil.isEmpty(SOCKET_MAP)) {
            return;
        }

        //群发消息
        for (WebSocketEndpoint item : SOCKET_MAP.values()) {
            try {
                item.send(message);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("【WebSocket】发生错误", error);
    }

    /**
     * 实现服务器主动推送
     */
    private void send(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 向指定用户发送消息
     */
    public static void sendToOneUser(WebSocketMsg socketMsg, String module, Long userId)
        throws JsonProcessingException {

        if (userId == null) {
            return;
        }
        if (MapUtil.isEmpty(SOCKET_MAP)) {
            return;
        }

        WebSocketEndpoint endpoint = SOCKET_MAP.get(userId);
        if (endpoint == null) {
            return;
        }

        String message = OBJECT_MAPPER.writeValueAsString(socketMsg);
        log.info("【WebSocket】【发送消息】module = {}, userId = {}，message = {}", module, userId, message);

        try {
            //这里可以设定只推送给这个模块，为null则全部推送
            if (StrUtil.isBlank(module)) {
                endpoint.send(message);
            } else if (endpoint.module.equals(module)) {
                endpoint.send(message);
            }
        } catch (IOException e) {
            // 忽略
        }
    }

    /**
     * 向指定用户群发送消息
     */
    public static void sendToMultiUser(WebSocketMsg socketMsg, String module, Collection<Long> userIds)
        throws JsonProcessingException {

        if (CollectionUtil.isEmpty(userIds)) {
            return;
        }

        for (Long userId : userIds) {
            sendToOneUser(socketMsg, module, userId);
        }
    }

    /**
     * 向所有用户群发送消息
     */
    public static void sendToAllUser(WebSocketMsg socketMsg, String module) throws JsonProcessingException {

        String message = OBJECT_MAPPER.writeValueAsString(socketMsg);
        log.info("【WebSocket】【发送消息】module = {}, message = {}", module, message);

        if (MapUtil.isEmpty(SOCKET_MAP)) {
            return;
        }

        for (WebSocketEndpoint item : SOCKET_MAP.values()) {
            try {
                //这里可以设定只推送给这个模块，为null则全部推送
                if (StrUtil.isNotBlank(module)) {
                    item.send(message);
                } else if (item.module.equals(module)) {
                    item.send(message);
                }
            } catch (IOException e) {
                // 忽略
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebSocketEndpoint)) {
            return false;
        }
        WebSocketEndpoint that = (WebSocketEndpoint) o;
        return Objects.equals(session, that.session) &&
            Objects.equals(module, that.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, module);
    }

}
