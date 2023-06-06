package com.system.poll.services;

import com.system.poll.dtos.response.VoteResultsResponse;

import java.util.List;

public interface VoteService {
    List<VoteResultsResponse> voteDisplayResults(String pollId, String choiceId);
    String displayTotalVotes(String pollId);
}
