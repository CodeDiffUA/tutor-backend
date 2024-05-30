package dev.backend.tutor.dtos.ai;

import java.util.List;

public record RequestToAiHelper (
        String accessToken, Long chatId, List<RequestAiChatMessageDto> messages) {
}
