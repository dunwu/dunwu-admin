package io.github.dunwu.module.mail.service;

import io.github.dunwu.module.mail.entity.dto.MailDto;

import java.util.List;

/**
 * 邮件服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-28
 */
public interface MailService {

    void send(MailDto dto);

    void sendBatch(List<MailDto> mailDTO, boolean html);

}
