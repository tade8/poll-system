package com.system.poll.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class VoteRequest {
  private String userId;
  private String choiceId;
  private String pollId;

  public VoteRequest(String choiceId, String pollId) {
    this.choiceId = choiceId;
    this.pollId = pollId;
  }
}
