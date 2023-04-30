package com.system.poll.services;

import com.system.poll.data.models.Poll;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.ChoicesRequest;
import com.system.poll.dtos.PollRequest;

public interface PollService {

    void createPoll(PollRequest pollRequest);

    Poll viewPoll(String id);
}
