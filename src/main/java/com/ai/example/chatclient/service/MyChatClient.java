package com.ai.example.chatclient.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;

public class MyChatClient implements ChatClient {
    @Override
    public ChatClientRequestSpec prompt() {
        return null;
    }

    @Override
    public ChatClientPromptRequestSpec prompt(Prompt prompt) {
        return null;
    }

    @Override
    public Builder mutate() {
        return null;
    }
}
