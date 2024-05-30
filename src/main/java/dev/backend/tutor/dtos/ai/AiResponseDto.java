package dev.backend.tutor.dtos.ai;

import java.util.List;

public record AiResponseDto (List<Choice> choices) {}