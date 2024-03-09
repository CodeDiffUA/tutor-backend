package dev.backend.tutor.dtos;

import java.util.Date;

public record ExceptionDto (
        String message,
        Date timestamp

){}
