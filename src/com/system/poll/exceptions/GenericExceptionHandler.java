package com.system.poll.exceptions;

import com.system.poll.dtos.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = {
            PollNotFoundException.class,
            ChoiceNotFoundException.class,
            UserNotFoundException.class,
    })
    public ResponseEntity<?> handleNotFoundException(RuntimeException exception,
                                                     HttpServletRequest httpServletRequest) {
        APIResponse apiResponse = APIResponse.
                builder().
                isSuccessful(false).
                data(exception.getMessage()).
                status(HttpStatus.NOT_FOUND).
                path(httpServletRequest.getRequestURI()).
                timestamp(ZonedDateTime.now()).
                build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ExceptionHandler(value = {InvalidInputException.class, NullPointerException.class,
            DateTimeException.class,})
    public ResponseEntity<?> handleBadRequestException(RuntimeException exception,
                                                         HttpServletRequest httpServletRequest) {
        APIResponse apiResponse = APIResponse.
                builder().
                isSuccessful(false).
                data(exception.getMessage()).
                status(HttpStatus.BAD_REQUEST).
                path(httpServletRequest.getRequestURI()).
                timestamp(ZonedDateTime.now()).
                build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
