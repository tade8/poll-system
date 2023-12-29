package com.system.poll.dtos.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@Builder
public class APIResponse {
    private ZonedDateTime timestamp;
    private HttpStatus status;
    private Object data;
    private String path;
    private boolean isSuccessful;
}
