package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.exceptions.*;
import com.system.poll.services.PollService;
import com.system.poll.services.VoteService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final PollService pollService;

    @Override
    public Long voteDisplayResults(String id, String choiceId) {
      var choice = saveChoiceWithVote(choiceId, id);
      return choice.getVoteCount();
    }

    private Choices saveChoiceWithVote(String id, String pollId) {
      Poll poll = pollService.viewPollById(pollId);
      if (poll.isOver()) throw new IllegalStateException("Poll duration is over");

      Choices choice = findChoiceById(id);
      choice.increaseVoteCount();

      return choicesRepository.save(choice);
    }

    private Choices findChoiceById(String choiceId) {
        return choicesRepository.findChoiceById(choiceId).
                orElseThrow(()-> new ChoiceNotFoundException("This choice does not exist"));
    }
}
