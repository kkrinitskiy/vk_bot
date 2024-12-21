package com.kkrinitskiy.bot.messageHandling;

import com.kkrinitskiy.bot.messageHandling.interfaces.AbstractMessageHandler;
import com.kkrinitskiy.bot.services.OnOffService;
import com.vk.api.sdk.objects.callback.MessageNew;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagesHandler extends AbstractMessageHandler {

    private final OnOffService onOffService;
    private final ToBotMessagesHandler toBotMessagesHandler;
    private final ChatMessagesHandler chatMessagesHandler;

    public MessagesHandler(ToBotMessagesHandler toBotMessagesHandler, ChatMessagesHandler chatMessagesHandler, OnOffService onOffService) {
        this.onOffService = onOffService;
        this.toBotMessagesHandler = toBotMessagesHandler;
        this.chatMessagesHandler = chatMessagesHandler;
    }

    public void processMessage(MessageNew message) {
        try {
            if (isPersonal(message)) {
                    toBotMessagesHandler.processMessage(message);
            } else {
                if (onOffService.getState()) {
                    chatMessagesHandler.processMessage(message);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public boolean isPersonal(MessageNew message) {
        return message.getObject().getMessage().getText().contains("[club%d|@%s]".formatted(groupInfo.getId(), groupInfo.getScreenName()));
    }
}
