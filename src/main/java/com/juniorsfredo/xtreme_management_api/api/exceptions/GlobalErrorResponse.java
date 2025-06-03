package com.juniorsfredo.xtreme_management_api.api.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class GlobalErrorResponse {

    private Integer statusCode;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;
}
