package com.system.poll.dtos.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@Builder
public class ApiResponse {
    private ZonedDateTime timestamp;
    private HttpStatus status;
    private Object data;
    private String path;
    private boolean isSuccessful;
}
