package dev.backend.tutor.dtos;

public record MessageDto (
        String sender, String recipient, String content, String timestamp
){}
