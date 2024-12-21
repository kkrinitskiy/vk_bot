package com.kkrinitskiy.bot.messageHandling.interfaces;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.MessageNew;

public interface MessageHandler {
    void processMessage(MessageNew message) throws ClientException, ApiException;
}
