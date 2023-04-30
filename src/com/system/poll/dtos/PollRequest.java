package com.system.poll.dtos;

import com.system.poll.data.models.Choices;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PollRequest {
    private String id;
    private String question;
    private List<Choices> choices = new ArrayList<>();
}
