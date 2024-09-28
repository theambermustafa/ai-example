package com.ai.example.chatclient.controller;

import com.ai.example.chatclient.service.MyChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ccc")
public class ChatClientController {

    @Autowired
    private final MyChatClient chatClient;

    public ChatClientController(MyChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai")
    public String generation(String input) {
        return this.chatClient.prompt().user(input).call().content();
    }
}
