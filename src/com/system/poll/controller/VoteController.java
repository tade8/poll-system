package com.system.poll.controller;

import com.system.poll.dtos.response.ApiResponse;
import com.system.poll.services.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("poll/")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("vote/{id}")
    public ResponseEntity<?> voteOnChoice(@PathVariable String id,
                                          HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.
                builder().
                status(HttpStatus.OK).
                data(voteService.voteOnChoice(id)).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("totalVotes/{id}")
    public ResponseEntity<?> displayTotalVotes(@PathVariable String id,
                                                 HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.
                builder().
                status(HttpStatus.OK).
                data(voteService.calculateTotalVotes(id)).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).
                build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
