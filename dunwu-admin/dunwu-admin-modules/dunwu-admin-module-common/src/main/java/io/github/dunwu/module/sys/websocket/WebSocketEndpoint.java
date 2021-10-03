package io.github.dunwu.module.sys.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Slf4j
@Component
@ServerEndpoint("/webSocket/{sid}")
public class WebSocketEndpoint {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 线程安全的 WebSocketEndpoint Set
     */
    private static final CopyOnWriteArraySet<WebSocketEndpoint> WEB_SOCKET_SET = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收 sid
     */
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        // 如果存在就先删除一个，防止重复推送消息
        WEB_SOCKET_SET.removeIf(webSocket -> webSocket.sid.equals(sid));
        WEB_SOCKET_SET.add(this);
        this.session = session;
        this.sid = sid;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WEB_SOCKET_SET.remove(this);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【WebSocket】收到来自" + sid + "的信息:" + message);
        //群发消息
        for (WebSocketEndpoint item : WEB_SOCKET_SET) {
            try {
                item.sendMessage(message);
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
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(WebSocketMsg socketMsg, @PathParam("sid") String sid) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(socketMsg);
        log.info("【WebSocket】推送消息到" + sid + "，推送内容:" + message);
        for (WebSocketEndpoint item : WEB_SOCKET_SET) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketEndpoint that = (WebSocketEndpoint) o;
        return Objects.equals(session, that.session) &&
            Objects.equals(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, sid);
    }

}
