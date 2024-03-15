package dev.backend.tutor.dtos.messages;

public record SystemMessageDto (
        String recipient, String content, String timestamp
){
}
