package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.response.VoteResultsResponse;
import com.system.poll.exceptions.*;
import com.system.poll.services.VoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final VotesRepository votesRepository;
    private final PollRepository pollRepository;

    @Override
    public List<VoteResultsResponse> voteDisplayResults(String id, String choiceId) {
      saveChoiceWithVote(choiceId, id);
      return votesRepository.displayVoteResults(id);
    }

    private void saveChoiceWithVote(String id, String pollId) {
      Poll poll = pollRepository.findPollById(pollId).
              orElseThrow(()-> new PollNotFoundException("Poll does not exist"));

      if (poll.isOver()) return;

      Choices choice = choicesRepository.findChoiceById(id).
              orElseThrow(()-> new ChoiceNotFoundException("This choice does not exist"));
      Votes votes = new Votes(1L);
      choice.getNoOfVotes().add(votes);

      choicesRepository.save(choice);
    }

    @Override
    public String displayTotalVotes(String id) {
        Long noOfVotes = votesRepository.getTotalVotes(id);
        if (noOfVotes == 0 || noOfVotes == 1)
            return String.format("%d vote", noOfVotes);
        return String.format("%d votes", noOfVotes);
    }
}
