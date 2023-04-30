package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Poll;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.ChoicesRequest;
import com.system.poll.dtos.PollRequest;
import com.system.poll.services.PollService;
import org.springframework.stereotype.Service;


@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final ChoicesRepository choicesRepository;


    public PollServiceImpl(PollRepository pollRepository, ChoicesRepository choicesRepository) {
        this.pollRepository = pollRepository;
        this.choicesRepository = choicesRepository;
    }

    @Override
    public void createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        ChoicesRequest choicesRequest = new ChoicesRequest();
        Choices choices = new Choices();

        choices.setChoiceText(choicesRequest.getChoiceText());

        poll.setQuestion(pollRequest.getQuestion());
        poll.setChoices(pollRequest.getChoices());

        choicesRepository.save(choices);
        pollRepository.save(poll);

    }

    @Override
    public Poll viewPoll(String id) {
        return pollRepository.findPollById(id);
    }
}
