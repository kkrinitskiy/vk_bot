package com.kkrinitskiy.bot.services;

import com.kkrinitskiy.bot.messageHandling.MessagesHandler;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.objects.callback.MessageNew;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CallbackService extends com.vk.api.sdk.events.callback.CallbackApi {


    private final MessagesHandler messagesHandler;

    protected CallbackService(@Value("${vk.confirmationCode}") String confirmationCode, @Value("${vk.secretKey}") String secretKey, VkApiClient vk, GroupActor groupActor, MessagesHandler messagesHandler) {
        super(confirmationCode, secretKey);
        this.messagesHandler = messagesHandler;
    }

    @Override
    public void messageNew(Integer groupId, MessageNew message) {
        messagesHandler.processMessage(message);
    }

    @Override
    public String parse(String json) {
//        CallbackMessage callbackMessage = new GsonHolder().getGson().fromJson(json, CallbackMessage.class);
//        if(callbackMessage.getType() == null){
//            json = "{\n" +
//                    "  \"group_id\": 224667898,\n" +
//                    "  \"type\": \"message_new\",\n" +
//                    "  \"event_id\": \"5adbda3923e28b790d64121a3b34a153d01d1a7d\",\n" +
//                    "  \"v\": \"5.199\",\n" +
//                    "  \"object\": {\n" +
//                    "    \"message\": {\n" +
//                    "      \"date\": 1734678706,\n" +
//                    "      \"from_id\": 230306667,\n" +
//                    "      \"id\": 0,\n" +
//                    "      \"version\": 10006048,\n" +
//                    "      \"out\": 0,\n" +
//                    "      \"important\": false,\n" +
//                    "      \"is_hidden\": false,\n" +
//                    "      \"attachments\": [],\n" +
//                    "      \"conversation_message_id\": 20,\n" +
//                    "      \"fwd_messages\": [],\n" +
//                    "      \"text\": \"\",\n" +
//                    "      \"is_unavailable\": true,\n" +
//                    "      \"peer_id\": 2000000001,\n" +
//                    "      \"random_id\": 0\n" +
//                    "    },\n" +
//                    "    \"client_info\": {\n" +
//                    "      \"button_actions\": [\n" +
//                    "        \"text\",\n" +
//                    "        \"vkpay\",\n" +
//                    "        \"open_app\",\n" +
//                    "        \"location\",\n" +
//                    "        \"open_link\",\n" +
//                    "        \"open_photo\",\n" +
//                    "        \"callback\",\n" +
//                    "        \"intent_subscribe\",\n" +
//                    "        \"intent_unsubscribe\"\n" +
//                    "      ],\n" +
//                    "      \"keyboard\": true,\n" +
//                    "      \"inline_keyboard\": true,\n" +
//                    "      \"carousel\": true,\n" +
//                    "      \"lang_id\": 0\n" +
//                    "    }\n" +
//                    "  }\n" +
//                    "}";
//        }
        return super.parse(json);
    }
}
