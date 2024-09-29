package com.ai.example.summarizer.controller;

import com.ai.example.summarizer.dto.SummaryRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/v1/api")
public class SummarizerController {

    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/summary")
    public ResponseEntity<String> getSummary(@RequestBody SummaryRequest request) {
        String prompt = request.getPrompt();

        // Prepare the Ollama request payload
        String ollamaPayload = String.format("{\"model\": \"mistral\", \"prompt\": \"%s\"}", prompt);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity
        HttpEntity<String> ollamaRequest = new HttpEntity<>(ollamaPayload, headers);

        StringBuilder responseBuilder = new StringBuilder();

        try {
            RequestCallback requestCallback = restTemplate.httpEntityCallback(ollamaRequest);
            ResponseExtractor<Void> responseExtractor = response -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Append each chunk to responseBuilder
                        responseBuilder.append(line);
                    }
                }
                return null;
            };

            restTemplate.execute(OLLAMA_API_URL, HttpMethod.POST, requestCallback, responseExtractor);

            return ResponseEntity.ok(responseBuilder.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to communicate with Ollama: " + e.getMessage());
        }
    }

}
