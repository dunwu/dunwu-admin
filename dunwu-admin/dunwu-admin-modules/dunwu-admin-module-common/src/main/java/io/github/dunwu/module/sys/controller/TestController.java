package io.github.dunwu.module.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.dunwu.module.sys.constant.enums.WebSocketMsgType;
import io.github.dunwu.module.sys.websocket.WebSocketEndpoint;
import io.github.dunwu.module.sys.websocket.WebSocketMsg;
import io.github.dunwu.tool.data.response.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/sys/test")
@Api(tags = "Test 类")
@RequiredArgsConstructor
public class TestController {

    @ApiOperation("添加一条 Dict 记录")
    @GetMapping("push")
    public DataResult<Boolean> push(String content) {
        try {
            WebSocketEndpoint.sendToOneUser(new WebSocketMsg(content, WebSocketMsgType.INFO), "notify", 1L);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return DataResult.ok(true);
    }

}
