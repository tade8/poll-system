package com.system.poll.dtos.requests;

import com.system.poll.data.models.Choices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollRequest {
    private String id;
    private String question;
    private List<Choices> choices = new ArrayList<>();
    private String specifiedEndTime = "23:00:00";

    public PollRequest(String question, List<Choices> choices, String specifiedEndTime) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = specifiedEndTime;
    }
}
