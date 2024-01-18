package com.hlqz.lpg.service.util;

import cn.hutool.extra.spring.SpringUtil;
import com.hlqz.lpg.ntfy.model.request.NtfySendParam;
import com.hlqz.lpg.ntfy.service.NtfyApiService;
import com.hlqz.lpg.util.AssertionUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@Slf4j
@Component
public class NtfyUtils {

    /**
     * 默认主题
     */
    private static final String DEFAULT_TOPIC = "LPG";

    private static NtfyApiService ntfy;

    private NtfyUtils() {
    }

    @PostConstruct
    public void init() {
        ntfy = SpringUtil.getBean(NtfyApiService.class);
    }

    public static void sendMessage(String message) {
        sendMessage(DEFAULT_TOPIC, message);
    }

    public static void sendMessage(String topic, String message) {
        sendMessage(topic, null, message);
    }

    public static void sendMessage(String topic, String title, String message) {
        AssertionUtils.assertNotBlank(topic, "topic不允许为空");
        final var param = new NtfySendParam();
        param.setTopic(topic);
        param.setTitle(title);
        param.setMessage(message);
        final var result = ntfy.send(param);
        log.info("NtfyUtils, sendMessage, result: {}", result);
    }
}
