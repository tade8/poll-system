package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Votes;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.VotesRepository;
import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.exceptions.ChoiceNotFoundException;
import com.system.poll.services.VoteService;
import org.springframework.stereotype.Service;

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
    public Long voteOnChoice(String id) {
        Choices choices1 = choicesRepository.findById(id).
                orElseThrow(()-> new ChoiceNotFoundException("This choice does not exist"));

        Votes votes = new Votes();
        votes.setNoOfVotes(1L);
        choices1.getNoOfVotes().add(votes);

        votesRepository.save(votes);
        var result = choicesRepository.save(choices1);
        return (long) result.getNoOfVotes().size();
    }

    @Override
    public List<Votes> displayTotalVotes() {
        return votesRepository.findAll();
    }

    @Override
    public String calculateTotalVotes(String id) {
        List<Votes> votes = votesRepository.findAll();

        return String.format("%d votes", votes.size());
    }

}
