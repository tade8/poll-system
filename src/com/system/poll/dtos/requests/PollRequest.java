package com.system.poll.dtos.requests;

import com.system.poll.data.models.Choices;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollRequest {
    private String id;
    @NotNull
    private String question;
    @NotNull
    private List<Choices> choices = new ArrayList<>();
    private String specifiedEndTime = "23:00:00";

    public PollRequest(String question, List<Choices> choices, String specifiedEndTime) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = specifiedEndTime;
    }
}
