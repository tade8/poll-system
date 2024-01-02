package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.requests.VoteRequest;
import com.system.poll.dtos.response.VoteResultsResponse;
import com.system.poll.exceptions.*;
import com.system.poll.services.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final ChoicesRepository choicesRepository;
    private final UserService userService;
    private final PollService pollService;

    @Override
    public VoteResultsResponse voteDisplayResults(VoteRequest voteRequest) {
      Choice savedChoice = saveChoiceWithVote(voteRequest.getUserId(), voteRequest.getChoiceId());
      pollService.getPollTotalVotes(voteRequest.getPollId());
      return new VoteResultsResponse(savedChoice.getUsers(), savedChoice.getVoteCount(), savedChoice.getVoteTime());
    }

    private Choice saveChoiceWithVote(String userId, String choiceId) {
      User user = userService.viewUserById(userId);
      Choice choice = findChoiceById(choiceId);

      choice.getUsers().add(user);
      choice.increaseVoteCount();
      choice.setVoteTime(LocalDateTime.now());

      return choicesRepository.save(choice);
    }

    private Choice findChoiceById(String choiceId) {
        return choicesRepository.findChoiceByChoiceId(choiceId).
                orElseThrow(()-> new ChoiceNotFoundException("This choice does not exist"));
    }
}
