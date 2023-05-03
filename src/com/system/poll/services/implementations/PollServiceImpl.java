package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Poll;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.services.PollService;
import org.springframework.stereotype.Service;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final ChoicesRepository choicesRepository;


    public PollServiceImpl(PollRepository pollRepository,
                           ChoicesRepository choicesRepository) {
        this.pollRepository = pollRepository;
        this.choicesRepository = choicesRepository;
    }

    @Override
    public String createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        Choices choices = new Choices();

        poll.setQuestion(pollRequest.getQuestion());
        poll.setChoices(pollRequest.getChoices());

        choicesRepository.save(choices);
        pollRepository.save(poll);
        return "Poll has been created";
    }

    @Override
    public String deletePoll(String id) {
        Poll poll = viewPoll(id);
        pollRepository.deletePollById(poll.getId());
        return "Poll has been deleted";
    }

    @Override
    public Poll viewPoll(String id) {
        return pollRepository.findPollById(id);
    }


}
