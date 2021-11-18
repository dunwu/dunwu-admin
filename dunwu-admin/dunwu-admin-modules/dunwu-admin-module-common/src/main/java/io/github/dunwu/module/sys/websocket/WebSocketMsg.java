package io.github.dunwu.module.sys.websocket;

import io.github.dunwu.module.sys.constant.enums.WebSocketMsgType;
import lombok.Data;

/**
 * WebSocket 消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
public class WebSocketMsg {

    /** 消息内容 */
    private String msg;
    /** 消息类型 */
    private WebSocketMsgType msgType;

    public WebSocketMsg(String msg, WebSocketMsgType msgType) {
        this.msg = msg;
        this.msgType = msgType;
    }

}
