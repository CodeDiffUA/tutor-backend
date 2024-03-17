package dev.backend.tutor.dtos.message;

public record NotificationDto(
        String recipient, String content, String timestamp
){
}
