package com.system.poll.services;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Votes;
import com.system.poll.dtos.requests.ChoicesRequest;

import java.util.List;

public interface VoteService {
    Choices voteOnChoice(String id);
    List<Votes> displayTotalVotes();
    int calculateTotalVotes(ChoicesRequest choicesRequest);
}
