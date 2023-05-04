package com.system.poll.services;
import com.system.poll.data.models.Votes;

import java.util.List;

public interface VoteService {
    Long voteOnChoice(String id);
    List<Votes> displayTotalVotes();
    String calculateTotalVotes(String id);

}
