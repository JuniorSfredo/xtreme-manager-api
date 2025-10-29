package com.juniorsfredo.xtreme_management_api.api.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GlobalErrorResponse {

    private Integer statusCode;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;

    private List<FieldError> errors = new ArrayList<>();

    public GlobalErrorResponse(Integer statusCode, String message, Instant instant) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = instant;
    }

    public void addFields(FieldError fieldError) {
        this.errors.add(fieldError);
    }

    @Getter
    public static class FieldError {
        private String field;

        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
