package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Votes;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.VotesRepository;
import com.system.poll.dtos.requests.ChoicesRequest;
import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.services.VoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final VotesRepository votesRepository;

    public VoteServiceImpl(ChoicesRepository choicesRepository,
                           VotesRepository votesRepository) {
        this.choicesRepository = choicesRepository;
        this.votesRepository = votesRepository;
    }

    @Override
    public Choices voteOnChoice(String id) {
        Choices choices1 = choicesRepository.findById(id).
                orElseThrow(()-> new RuntimeException("This choice does not exist"));

        Votes votes = new Votes();
        votes.setNoOfVotes(1L);
        choices1.getNoOfVotes().add(votes);

        votesRepository.save(votes);
        return choicesRepository.save(choices1);
    }

    @Override
    public List<Votes> displayTotalVotes() {
        return votesRepository.findAll();
    }

    @Override
    public int calculateTotalVotes(ChoicesRequest choicesRequest) {
        List<Votes> totalVotes = new ArrayList<>();
        PollRequest pollRequest = new PollRequest();
        Choices choices = choicesRepository.findChoicesById(choicesRequest.getId()).
                orElseThrow(()-> new RuntimeException("Choice does not exist"));
        for (Votes vote : votesRepository.findVotesById(choices.getId()))
            totalVotes.add(vote);

        return totalVotes.size();
    }
}
