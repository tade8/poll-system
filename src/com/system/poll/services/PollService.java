package com.system.poll.services;

import com.system.poll.data.models.Poll;
import com.system.poll.dtos.requests.PollRequest;

public interface PollService {
    String createPoll(String userId, PollRequest pollRequest);
    Poll viewPollById(String pollId);
    String deletePoll(String pollId);
    long getPollTotalVotes(String pollId);
}
