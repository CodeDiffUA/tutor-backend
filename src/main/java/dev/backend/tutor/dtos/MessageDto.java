package dev.backend.tutor.dtos;


import java.sql.Timestamp;

public record MessageDto (
        String sender, String recipient, String content, String timestamp
){}
