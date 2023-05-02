package com.system.poll.dtos.requests;

import com.system.poll.data.models.Votes;
import lombok.Data;

import java.util.List;


@Data
public class ChoicesRequest {
    private String id;
    private String choiceText;
    private List<Votes> noOfVotes;
}
