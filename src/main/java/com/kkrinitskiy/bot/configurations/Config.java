package com.kkrinitskiy.bot.configurations;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${vk.groupId}")
    private Long groupId;

    @Value("${vk.token}")
    private String accessToken;

    @Bean
    public GroupActor groupActor() {
        return new GroupActor(groupId, accessToken);
    }

    @Bean
    public VkApiClient vkApiClient() {
        TransportClient transportClient = new HttpTransportClient();
        return new VkApiClient(transportClient);
    }

    @Bean
    public GroupFull groupFull(VkApiClient vk, GroupActor groupActor) throws ClientException, ApiException {
        return vk.groups().getByIdObject(groupActor).groupId(groupActor.getGroupId().toString()).execute().getGroups().get(0);
    }
}
