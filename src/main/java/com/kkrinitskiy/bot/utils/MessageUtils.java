package com.kkrinitskiy.bot.utils;

import com.kkrinitskiy.bot.services.TextGeneratorService;
import com.vk.api.sdk.actions.Users;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.MessageNew;
import com.vk.api.sdk.objects.messages.ConversationMember;
import com.vk.api.sdk.objects.messages.responses.GetConversationMembersResponse;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class MessageUtils {
    @Autowired
    private VkApiClient vk;
    @Autowired
    private GroupActor groupActor;
    @Autowired
    private TextGeneratorService textGeneratorService;

    /**
     * Проставить сообщению реакцию
     *
     * @param message
     * @param reactionId
     * @throws ApiException
     * @throws ClientException
     */
    public void sendReact(MessageNew message, int reactionId) throws ApiException, ClientException {
        Long peerId = message.getObject().getMessage().getPeerId();
        Integer cmid = message.getObject().getMessage().getConversationMessageId();
        vk.messages().sendReaction(groupActor, peerId, cmid, reactionId).execute();
    }

    /**
     * Создает префикс для обращения к юзеру
     *
     * @param message
     * @return
     * @throws ApiException
     * @throws ClientException
     */
    public String getAppeal(MessageNew message) throws ApiException, ClientException {
        Long fromId = message.getObject().getMessage().getFromId();
        String firstName = new Users(vk).get(groupActor).userIds(fromId.toString()).fields(Fields.NICKNAME).execute().get(0).getFirstName();
        return "[id%d|%s], ".formatted(fromId, firstName);
    }

    public void sendPersonalRandomText(MessageNew message) throws ApiException, ClientException {
        vk.messages().sendDeprecated(groupActor).message(getAppeal(message) + textGeneratorService.getRandomText()).peerId(message.getObject().getMessage().getPeerId()).randomId(new Random().nextInt(10000)).execute();
    }

    /**
     * Рандомное сообщение в чат, в ответ на событие
     *
     * @param message
     * @throws ApiException
     * @throws ClientException
     */
    public void sendRandomTextToGroup(MessageNew message) throws ApiException, ClientException {
//        TODO проверку on/off убрать в аспект
        if (new Random().nextInt(100) >= 80) {
            vk.messages().sendDeprecated(groupActor).message(textGeneratorService.getRandomText()).peerId(message.getObject().getMessage().getPeerId()).randomId(new Random().nextInt(10000)).execute();
        }
    }

    /**
     * Метод для экспериментов
     *
     * @param message
     * @throws ApiException
     * @throws ClientException
     */
    private void extracted(MessageNew message) throws ApiException, ClientException {
        GetConversationMembersResponse execute = vk.messages().getConversationMembers(groupActor, message.getObject().getMessage().getPeerId()).execute();
        List<ConversationMember> items = execute.getItems();
        log.info("количество: {}", items.size());
        List<String> listIds = items.stream().map(ConversationMember::getMemberId).filter(x -> x > 0).map(String::valueOf).toList();
        items.forEach(item -> {
            log.info("id: " + item.getMemberId());
        });
        List<GetResponse> execute1 = vk.users().get(groupActor).userIds(listIds).execute();
        execute1.forEach(response -> {
            log.info(response.getFirstName());
        });
    }

    public void sendMessageToGroup(MessageNew message,String string) throws ClientException, ApiException {
        vk.messages().sendDeprecated(groupActor).message(string).peerId(message.getObject().getMessage().getPeerId()).randomId(new Random().nextInt(10000)).execute();

    }
}
