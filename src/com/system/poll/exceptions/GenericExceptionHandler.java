package com.system.poll.exceptions;

import com.system.poll.dtos.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleGenericException(RuntimeException exception,
                                                    HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder().
                isSuccessful(false).
                data(exception.getMessage()).status(HttpStatus.BAD_REQUEST).
                path(httpServletRequest.getRequestURI()).
                timestamp(ZonedDateTime.now()).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
