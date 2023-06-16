package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.response.VoteResultsResponse;
import com.system.poll.exceptions.ChoiceNotFoundException;
import com.system.poll.services.VoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final VotesRepository votesRepository;

    @Override
    public List<VoteResultsResponse> voteDisplayResults(String id, String choiceId) {
        saveChoiceWithVote(choiceId);
        return votesRepository.displayVoteResults(id);
    }

    private void saveChoiceWithVote(String id) {
        Choices choice = choicesRepository.findChoiceById(id).
                orElseThrow(()-> new ChoiceNotFoundException("This choice does not exist"));

        Votes votes = new Votes(1L);
        choice.getNoOfVotes().add(votes);

        choicesRepository.save(choice);
    }

    @Override
    public String displayTotalVotes(String id) {
        List<Votes> votes = votesRepository.getTotalVotes(id);
        return String.format("%d votes", (long) votes.size());
    }

}
