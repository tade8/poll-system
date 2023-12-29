package com.system.poll.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteRequest {
  private String userId;
  private String choiceId;
}
