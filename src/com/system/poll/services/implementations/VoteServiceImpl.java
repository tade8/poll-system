package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Votes;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.VotesRepository;
import com.system.poll.exceptions.ChoiceNotFoundException;
import com.system.poll.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final VotesRepository votesRepository;

    @Override
    public Long voteOnChoice(String id) {
        Choices choice = choicesRepository.findChoiceById(id).
                orElseThrow(()-> new ChoiceNotFoundException("This choice does not exist"));

        Votes votes = new Votes();
        votes.setNoOfVotes(1L);
        choice.getNoOfVotes().add(votes);

        votesRepository.save(votes);
        var savedChoice = choicesRepository.save(choice);

        return (long) savedChoice.getNoOfVotes().size();
    }

    @Override
    public String displayTotalVotes(String pollId) {
        List<Votes> votes = votesRepository.findAll();

        return String.format("%d votes", votes.size());
    }

}
