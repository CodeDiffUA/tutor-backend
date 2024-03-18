package dev.backend.tutor.dtos.message;

public record MessageDto (
        String sender, String recipient, String content, String timestamp
){}
