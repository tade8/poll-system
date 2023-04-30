package com.system.poll.dtos;

import com.system.poll.data.models.Votes;
import lombok.Data;

import java.util.List;


@Data
public class ChoicesRequest {
    private String id;
    private String choiceText;
    private List<Votes> noOfVotes;
}
