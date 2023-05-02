package com.system.poll.services;

import com.system.poll.data.models.Poll;
import com.system.poll.dtos.requests.PollRequest;

public interface PollService {

    String createPoll(PollRequest pollRequest);

    Poll viewPoll(String id);

    String deletePoll(String id);
}
