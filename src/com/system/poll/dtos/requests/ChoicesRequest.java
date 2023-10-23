package com.system.poll.dtos.requests;

import lombok.Data;

@Data
public class ChoicesRequest {
    private String choice_id;
    private String choiceText;
    private Long voteCount;
}
