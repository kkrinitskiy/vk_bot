package com.kkrinitskiy.bot.messageHandling;

import com.kkrinitskiy.bot.messageHandling.interfaces.AbstractMessageHandler;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.MessageNew;
import org.springframework.stereotype.Component;

@Component
public class ChatMessagesHandler extends AbstractMessageHandler {
    @Override
    public void processMessage(MessageNew message) throws ClientException, ApiException {
        messageUtils.sendRandomTextToGroup(message);
    }
}
