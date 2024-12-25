package com.kkrinitskiy.bot.utils;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.objects.callback.MessageNew;
import com.vk.api.sdk.objects.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
public class KeyboardUtils {
    @Autowired
    private VkApiClient vk;
    @Autowired
    private GroupActor groupActor;
    @Autowired
    private MessageUtils messageUtils;

    private static Keyboard keyboard = new Keyboard();
    private static List<List<KeyboardButton>> rows;
    public static Keyboard getKeyboard() {
        List<KeyboardButton> row1 = new ArrayList<>();
        rows = new ArrayList<>();
        row1.add(new KeyboardButton().setAction(new KeyboardButtonActionText().setType(KeyboardButtonActionTextType.TEXT).setLabel("Говори")).setColor(KeyboardButtonColor.POSITIVE));
        row1.add(new KeyboardButton().setAction(new KeyboardButtonActionText().setType(KeyboardButtonActionTextType.TEXT).setLabel("Заткнись")).setColor(KeyboardButtonColor.NEGATIVE));
        row1.add(new KeyboardButton().setAction(new KeyboardButtonActionText().setType(KeyboardButtonActionTextType.TEXT).setLabel("Погода")).setColor(KeyboardButtonColor.PRIMARY));
        rows.add(row1);
        keyboard.setButtons(rows);
        return keyboard;
    }

}
