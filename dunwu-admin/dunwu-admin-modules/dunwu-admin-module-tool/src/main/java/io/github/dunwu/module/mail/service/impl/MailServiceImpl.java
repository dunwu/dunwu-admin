package io.github.dunwu.module.mail.service.impl;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.autoconfigure.mail.DunwuMailProperties;
import io.github.dunwu.module.mail.entity.dto.MailDto;
import io.github.dunwu.module.mail.service.MailService;
import io.github.dunwu.tool.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2019-01-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    @Qualifier("mailExecutorService")
    private final ExecutorService mailExecutorService;
    private final DunwuMailProperties mailExtProperties;

    @Override
    public void send(MailDto mailDTO) {
        if (mailDTO.getHtml()) {
            mailExecutorService.execute(() -> sendMimeMessage(mailDTO));
        } else {
            mailExecutorService.execute(() -> sendSimpleMessage(mailDTO));
        }
    }

    @Override
    public void sendBatch(List<MailDto> mailDTOS, boolean html) {
        if (html) {
            mailExecutorService.execute(() -> sendMimeMessage(mailDTOS));
        } else {
            mailExecutorService.execute(() -> sendSimpleMessage(mailDTOS));
        }
    }

    private void sendSimpleMessage(MailDto mailDTO) {
        SimpleMailMessage simpleMailMessage = BeanUtil.toBean(mailDTO, SimpleMailMessage.class);
        if (StrUtil.isBlank(mailDTO.getFrom())) {
            simpleMailMessage.setFrom(mailExtProperties.getFrom());
        }

        try {
            javaMailSender.send(simpleMailMessage);
            if (log.isDebugEnabled()) {
                log.debug("发送 SIMPLE 邮件成功");
            }
        } catch (MailException e) {
            log.error("发送 SIMPLE 邮件失败", e);
        }
    }

    private void sendSimpleMessage(List<MailDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<SimpleMailMessage> simpleMailMessages = BeanUtil.toBeanList(list, SimpleMailMessage.class);
        for (SimpleMailMessage simpleMailMessage : simpleMailMessages) {
            if (StrUtil.isBlank(simpleMailMessage.getFrom())) {
                simpleMailMessage.setFrom(mailExtProperties.getFrom());
            }
        }

        try {
            javaMailSender.send(simpleMailMessages.toArray(new SimpleMailMessage[] {}));
            if (log.isDebugEnabled()) {
                log.debug("批量发送 SIMPLE 邮件成功");
            }
        } catch (MailException e) {
            log.error("批量发送 SIMPLE 邮件失败", e);
        }
    }

    private void sendMimeMessage(MailDto mailDTO) {
        try {
            MimeMessage mimeMessage = fillMimeMessage(mailDTO);
            javaMailSender.send(mimeMessage);
            if (log.isDebugEnabled()) {
                log.debug("发送 MIME 邮件成功");
            }
        } catch (MessagingException | MailException e) {
            log.error("发送 MIME 邮件失败", e);
        }
    }

    private void sendMimeMessage(List<MailDto> list) {
        List<MimeMessage> messages = new ArrayList<>();
        for (MailDto mailDTO : list) {
            MimeMessage mimeMessage = null;
            try {
                mimeMessage = fillMimeMessage(mailDTO);
            } catch (MessagingException e) {
                log.error("批量发送 MIME 邮件失败", e);
            }
            messages.add(mimeMessage);
        }

        javaMailSender.send(messages.toArray(new MimeMessage[] {}));
        if (log.isDebugEnabled()) {
            log.debug("批量发送 MIME 邮件成功");
        }
    }

    private MimeMessage fillMimeMessage(MailDto mailDTO) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

        if (StrUtil.isBlank(mailDTO.getFrom())) {
            messageHelper.setFrom(mailExtProperties.getFrom());
        } else {
            messageHelper.setFrom(mailDTO.getFrom());
        }
        messageHelper.setTo(mailDTO.getTo());
        messageHelper.setSubject(mailDTO.getSubject());
        messageHelper.setText(mailDTO.getText(), true);

        // 添加邮件附件
        if (mailDTO.getFilenames() != null && mailDTO.getFilenames().length > 0) {
            for (String filename : mailDTO.getFilenames()) {
                messageHelper.addAttachment(filename, new File(filename));
            }
        }

        return mimeMessage;
    }

}
