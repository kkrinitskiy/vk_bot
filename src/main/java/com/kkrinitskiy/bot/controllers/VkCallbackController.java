package com.kkrinitskiy.bot.controllers;

import com.kkrinitskiy.bot.services.CallbackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VkCallbackController {

    private final CallbackService callbackService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public String handleVkCallback(@RequestBody String json) {
        log.info("Handling event: " + json);
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode.has("type") && jsonNode.get("type").asText().equals("confirmation")) {
                return callbackService.confirmation();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            return callbackService.parse(json);
        }catch (Exception e) {
            if (e instanceof NullPointerException
            && e.getMessage().contains("\"com.vk.api.sdk.objects.callback.messages.CallbackMessage.getType()\" is null")) {
                return null;
            }
            e.printStackTrace();
        }
        return null;
    }
}
