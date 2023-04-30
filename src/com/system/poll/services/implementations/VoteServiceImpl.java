package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Votes;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.VotesRepository;
import com.system.poll.dtos.ChoicesRequest;
import com.system.poll.services.VoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final VotesRepository votesRepository;

    public VoteServiceImpl(ChoicesRepository choicesRepository, VotesRepository votesRepository) {
        this.choicesRepository = choicesRepository;
        this.votesRepository = votesRepository;
    }

    @Override
    public Choices voteOnChoice(String id) {
        ChoicesRequest choicesRequest = new ChoicesRequest();
        Choices choices = choicesRepository.findChoicesById(choicesRequest.getId()).
                orElseThrow(()-> new RuntimeException("This choice does not exist"));
        Votes votes = new Votes();
        votes.setNoOfVotes(1L);
        choices.getNoOfVotes().add(votes);
        votesRepository.save(votes);
        return choicesRepository.save(choices);
    }

    @Override
    public List<Votes> displayTotalVotes() {
        return votesRepository.findAll();
    }
}
