package dev.backend.tutor.dtos.ai;

import java.util.List;

public record AiRequestDto(
        String model, int max_tokens, List<RequestAiChatMessageDto> messages
) {}
