package dev.backend.tutor.dtos.ai;

public record AiChatMessageDto(
        Long messageId,
        String chatName,
        String student,
        String role,
        String content
) {
}
