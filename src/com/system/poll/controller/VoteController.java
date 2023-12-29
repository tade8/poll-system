package com.system.poll.controller;

import com.system.poll.dtos.requests.VoteRequest;
import com.system.poll.dtos.response.APIResponse;
import com.system.poll.services.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("poll/")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("")
    public ResponseEntity<?> voteOnChoice(@RequestBody VoteRequest voteRequest,
                                          HttpServletRequest httpServletRequest) {
        APIResponse apiResponse = APIResponse.
                builder().
                timestamp(ZonedDateTime.now()).
                status(HttpStatus.OK).
                data(voteService.voteDisplayResults(voteRequest)).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
