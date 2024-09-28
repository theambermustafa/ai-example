package com.ai.example.chatclient.config;

import com.ai.example.chatclient.service.MyChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public MyChatClient myChatClient() {
        return new MyChatClient();
    }
}
