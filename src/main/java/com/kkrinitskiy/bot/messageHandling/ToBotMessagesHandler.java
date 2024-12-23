package com.kkrinitskiy.bot.messageHandling;

import com.kkrinitskiy.bot.messageHandling.interfaces.AbstractMessageHandler;
import com.kkrinitskiy.bot.models.weatherModels.WeatherApiResponse;
import com.kkrinitskiy.bot.services.OnOffService;
import com.kkrinitskiy.bot.services.WeatherService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.MessageNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ToBotMessagesHandler extends AbstractMessageHandler {
    @Autowired
    private OnOffService onOffService;
    @Autowired
    private WeatherService weatherService;

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
        if (text.contains("[club%d|@%s]".formatted(groupInfo.getId(), groupInfo.getScreenName()))
            && text.contains("погода")){
            WeatherApiResponse weather = weatherService.getWeather();
            StringBuilder stringBuilder = new StringBuilder();
            weather.getList().forEach(weatherItem -> {
                stringBuilder.append("погода на: ").append(weatherItem.getDtTxt()).append("\n");
                stringBuilder.append("\tтемпература: ").append(weatherItem.getMain().getTemp()).append("\n");
                stringBuilder.append("\tчувствуется как: ").append(weatherItem.getMain().getFeelsLike()).append("\n");
                stringBuilder.append("\t").append(weatherItem.getWeather().get(0).getDescription()).append("\n\n");
            });
            messageUtils.sendMessageToGroup(message, stringBuilder.toString());
            return;
        }
        if(onOffService.getState()) {
            messageUtils.sendPersonalRandomText(message);
        }
    }
}
