package com.kkrinitskiy.bot.messageHandling.interfaces;

import com.kkrinitskiy.bot.utils.MessageUtils;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.objects.groups.GroupFull;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMessageHandler implements MessageHandler {
    @Autowired
    public VkApiClient vk;
    @Autowired
    public GroupActor groupActor;
    @Autowired
    public GroupFull groupInfo;
    @Autowired
    public MessageUtils messageUtils;
}
