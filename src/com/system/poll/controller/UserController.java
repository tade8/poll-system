package com.system.poll.controller;

import com.system.poll.dtos.requests.UserRequest;
import com.system.poll.dtos.response.APIResponse;
import com.system.poll.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
public class UserController {
  private final UserService userService;

  @PostMapping("")
  public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest,
                                      HttpServletRequest httpServletRequest) {
    APIResponse apiResponse = APIResponse.
            builder().
            timestamp(ZonedDateTime.now()).
            status(HttpStatus.CREATED).
            data(userService.createAndReturnUser(userRequest)).
            path(httpServletRequest.getRequestURI()).
            isSuccessful(true).
            build();
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }
}
