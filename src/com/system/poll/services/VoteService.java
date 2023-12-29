package com.system.poll.services;

import com.system.poll.dtos.requests.VoteRequest;
import com.system.poll.dtos.response.VoteResultsResponse;

public interface VoteService {
    VoteResultsResponse voteDisplayResults(VoteRequest voteRequest);

}
