package com.system.poll.exceptions;

import com.system.poll.dtos.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = {
            PollNotFoundException.class,
            ChoiceNotFoundException.class,
            NullPointerException.class,
            DateTimeException.class
    })
    public ResponseEntity<?> handleGenericException(RuntimeException exception,
                                                    HttpServletRequest httpServletRequest) {
        APIResponse apiResponse = APIResponse.
                builder().
                isSuccessful(false).
                data(exception.getMessage()).
                status(HttpStatus.BAD_REQUEST).
                path(httpServletRequest.getRequestURI()).
                timestamp(ZonedDateTime.now()).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
