package com.hlqz.lpg.util;

import cn.hutool.extra.spring.SpringUtil;
import com.hlqz.lpg.ntfy.model.request.NtfySendParam;
import com.hlqz.lpg.ntfy.service.NtfyApiService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@Slf4j
public class NtfyUtils {

    private static final String DEFAULT_TOPIC = "LPG";

    private NtfyUtils() {
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
        final var ntfy = SpringUtil.getBean(NtfyApiService.class);
        final var result = ntfy.send(param);
        log.info("NtfyUtils, sendMessage, result: {}", result);
    }
}
