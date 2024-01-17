package com.hlqz.lpg.ntfy.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlqz.lpg.ntfy.model.enums.NtfyPriorityEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NtfySendParam {

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("message")
    private String message;

    @JsonProperty("title")
    private String title;

    @JsonProperty("priority")
    private NtfyPriorityEnum priority;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("click")
    private String click;

    @JsonProperty("icons")
    private String icons;

    @JsonProperty("markdown")
    private Boolean markdown;

    @JsonProperty("delay")
    private String delay;

    @JsonProperty("email")
    private String email;

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("attach")
    private String attach;
}
