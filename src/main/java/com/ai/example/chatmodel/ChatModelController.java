package com.ai.example.chatmodel;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/cmc")
public class ChatModelController {

    private static final String DEFAULT_MESSAGE = "Tell me a joke";
    private final OllamaChatModel chatModel;

    @Autowired
    public ChatModelController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // String type response
    @GetMapping("/generate")
    public String generate(@RequestParam(value = "message", defaultValue = DEFAULT_MESSAGE) String message) {
        return chatModel.call(message);
    }

    // Map type response
    @GetMapping("/generate/map")
    public Map<String, String> generateMap(@RequestParam(value = "message", defaultValue = DEFAULT_MESSAGE) String message) {
        return Map.of("message", chatModel.call(message));
    }

    // Note: Currently, the Ollama API (0.3.8) does not support function calling in streaming mode.
    @GetMapping("/generate/stream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = DEFAULT_MESSAGE) String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}
