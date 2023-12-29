package com.system.poll.dtos.requests;

import com.system.poll.data.models.Choice;
import lombok.*;

import java.util.*;

@Data
public class PollRequest {
    private String pollId;
    private String userId;
    private String question;
    private List<Choice> choices;
    private String specifiedEndTime;

    public PollRequest(String question, List<Choice> choices) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = "23:59";
    }
}
