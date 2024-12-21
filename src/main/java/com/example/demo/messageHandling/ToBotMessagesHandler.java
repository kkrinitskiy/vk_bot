package com.example.demo.messageHandling;

import com.example.demo.messageHandling.interfaces.AbstractMessageHandler;
import com.example.demo.services.OnOffService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.MessageNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ToBotMessagesHandler extends AbstractMessageHandler {
    @Autowired
    private OnOffService onOffService;

    @Override
    public void processMessage(MessageNew message) throws ClientException, ApiException {
        String text = message.getObject().getMessage().getText();
        if (text.contains("[club%d|@%s]".formatted(groupInfo.getId(), groupInfo.getScreenName()))
                && text.toLowerCase().contains("заткнись")) {
            onOffService.turnOff();
        }
        if (text.contains("[club%d|@%s]".formatted(groupInfo.getId(), groupInfo.getScreenName()))
                && text.toLowerCase().contains("говори")) {
            onOffService.turnOn();
        }
        if(onOffService.getState()) {
            messageUtils.sendPersonalRandomText(message);
        }
    }
}
