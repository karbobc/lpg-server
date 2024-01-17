package com.hlqz.lpg.ntfy.service;

import com.hlqz.lpg.ntfy.model.request.NtfySendParam;
import com.hlqz.lpg.util.AssertionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@Slf4j
@Service
public class NtfyService {

    private static final String DEFAULT_TOPIC = "LPG";

    @Resource
    private NtfyApiService ntfyApiService;

    public void sendMessage(String message) {
        sendMessage(DEFAULT_TOPIC, message);
    }

    public void sendMessage(String topic, String message) {
        sendMessage(topic, null, message);
    }

    public void sendMessage(String topic, String title, String message) {
        AssertionUtils.assertNotBlank(topic, "topic不允许为空");
        final var param = new NtfySendParam();
        param.setTopic(topic);
        param.setTitle(title);
        param.setMessage(message);
        final var result = ntfyApiService.send(param);
        log.info("NtfyService, sendMessage, result: {}", result);
    }
}
