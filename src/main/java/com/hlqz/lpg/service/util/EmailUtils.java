package com.hlqz.lpg.service.util;

import cn.hutool.extra.spring.SpringUtil;
import com.hlqz.lpg.exception.BizException;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Karbob
 * @date 2024-02-05
 */
@Slf4j
@Component
public class EmailUtils {

    private static String from;
    private static JavaMailSender sender;
    private static Thread.Builder.OfVirtual vt;

    private EmailUtils() {
    }

    @PostConstruct
    public void init() {
        sender = SpringUtil.getBean(JavaMailSender.class);
        from = StringUtils.join(
            "=?UTF-8?B?",
            Base64.encodeBase64String(SpringUtil.getProperty("spring.mail.from-name").getBytes(StandardCharsets.UTF_8)),
            "?= <",
            SpringUtil.getProperty("spring.mail.from"),
            ">"
        );
        vt = Thread.ofVirtual()
            .name("email-", 1);
    }

    public static void sendAsync(final String to, final String subject, final String content) {
        vt.start(() -> send(to, subject, content));
    }

    public static void send(String to, String subject, String content) {
        final var mm = createMimeMessage(to, subject, content);
        sender.send(mm);
    }

    private static MimeMessage createMimeMessage(String to, String subject, String content) {
        try {
            final var mm = sender.createMimeMessage();
            final var mmh = new MimeMessageHelper(mm, true);
            mmh.setFrom(from);
            mmh.setTo(to.split(","));
            mmh.setSubject(subject);
            mmh.setText(content, true);
            return mm;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }
}
