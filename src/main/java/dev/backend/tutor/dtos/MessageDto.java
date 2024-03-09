package dev.backend.tutor.dtos;

import java.util.Date;

public record MessageDto (
        String sender, String recipient, String content, Date timestamp
){}
