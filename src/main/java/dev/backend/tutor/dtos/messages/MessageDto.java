package dev.backend.tutor.dtos.messages;

public record MessageDto (
        String sender, String recipient, String content, String timestamp
){}
