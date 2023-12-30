package com.system.poll.controller;

import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.dtos.response.APIResponse;
import com.system.poll.services.PollService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("poll/")
public class PollController {
  private final PollService pollService;

  public PollController(PollService pollService) {
    this.pollService = pollService;
  }

  @PostMapping("{userId}")
  public ResponseEntity<?> createPoll(@PathVariable String userId, @RequestBody PollRequest pollRequest,
                                 HttpServletRequest httpServletRequest) {
    APIResponse apiResponse = APIResponse.
            builder().
            timestamp(ZonedDateTime.now()).
            status(HttpStatus.CREATED).
            data(pollService.createPoll(userId, pollRequest)).
            path(httpServletRequest.getRequestURI()).
            isSuccessful(true).
            build();
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletePoll(@PathVariable String id,
                                      HttpServletRequest httpServletRequest) {
    APIResponse apiResponse = APIResponse.
            builder().
            timestamp(ZonedDateTime.now()).
            status(HttpStatus.OK).
            data(pollService.deletePoll(id)).
            path(httpServletRequest.getRequestURI()).
            isSuccessful(true).
            build();
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getPoll(@PathVariable String id,
                                      HttpServletRequest httpServletRequest) {
    APIResponse apiResponse = APIResponse.
            builder().
            timestamp(ZonedDateTime.now()).
            status(HttpStatus.OK).
            data(pollService.viewPollById(id)).
            path(httpServletRequest.getRequestURI()).
            isSuccessful(true).
            build();
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }

  @GetMapping("votes/{pollId}")
  public ResponseEntity<?> getPollTotalVotes(@PathVariable String pollId,
                                   HttpServletRequest httpServletRequest) {
    APIResponse apiResponse = APIResponse.
            builder().
            timestamp(ZonedDateTime.now()).
            status(HttpStatus.OK).
            data(pollService.getPollTotalVotes(pollId)).
            path(httpServletRequest.getRequestURI()).
            isSuccessful(true).
            build();
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }
}
