package io.github.dunwu.module.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.dunwu.module.security.annotation.AnonymousGetMapping;
import io.github.dunwu.module.sys.constant.enums.WebSocketMsgType;
import io.github.dunwu.module.sys.websocket.WebSocketEndpoint;
import io.github.dunwu.module.sys.websocket.WebSocketMsg;
import io.github.dunwu.tool.data.response.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息推送
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/sys/push")
@Api(tags = "【系统】消息推送")
@RequiredArgsConstructor
public class PushController {

    @ApiOperation("测试推送一条广播消息")
    @AnonymousGetMapping("broadcast")
    public DataResult<Boolean> broadcast(String content) {
        try {
            WebSocketEndpoint.sendToOneUser(new WebSocketMsg(content, WebSocketMsgType.INFO), "notify", 1L);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return DataResult.ok(true);
    }

}
