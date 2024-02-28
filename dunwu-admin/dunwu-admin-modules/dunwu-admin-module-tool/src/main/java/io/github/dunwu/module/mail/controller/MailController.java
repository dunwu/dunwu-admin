package io.github.dunwu.module.mail.controller;

import io.github.dunwu.module.mail.entity.dto.MailDto;
import io.github.dunwu.module.mail.service.MailService;
import io.github.dunwu.tool.data.response.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@RestController
@RequestMapping("mail")
@Api(tags = "mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("send")
    @ApiOperation(value = "发送邮件")
    public DataResult<Boolean> send(@RequestBody MailDto mailDto) {
        mailService.send(mailDto);
        return DataResult.ok();
    }

}
