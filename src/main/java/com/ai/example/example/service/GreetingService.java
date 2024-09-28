package com.ai.example.example.service;

public class GreetingService {

    private final String message;

    public GreetingService(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
