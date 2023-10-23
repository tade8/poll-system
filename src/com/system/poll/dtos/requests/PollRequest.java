package com.system.poll.dtos.requests;

import com.system.poll.data.models.Choices;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PollRequest {
    private String poll_id;
    private String question;
    private List<Choices> choices = new ArrayList<>();
    private String specifiedEndTime;

    public PollRequest(String question, List<Choices> choices, String specifiedEndTime) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = specifiedEndTime;
    }

    public PollRequest() {
        specifiedEndTime = "23:59";
    }
}
