package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Poll;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.exceptions.PollNotFoundException;
import com.system.poll.services.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final ChoicesRepository choicesRepository;

    @Override
    public String createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        Choices choices = new Choices();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;
        LocalTime specifiedEndTime = formatSpecifiedTime(pollRequest, timeFormatter);

        poll.setQuestion(pollRequest.getQuestion());
        poll.setChoices(pollRequest.getChoices());
        poll.setSpecifiedEndTime(specifiedEndTime);

        choicesRepository.save(choices);
        pollRepository.save(poll);

        return "Poll has been created";
    }

    private LocalTime formatSpecifiedTime(PollRequest pollRequest, DateTimeFormatter timeFormatter) {
        return LocalTime.parse(
                pollRequest.getSpecifiedEndTime(),
                timeFormatter
        );
    }

    @Override
    public String deletePoll(String pollId) {
        Poll poll = viewPollById(pollId);
        pollRepository.deletePollById(poll.getId());
        return "Poll has been deleted";
    }

    @Override
    public Poll viewPollById(String pollId) {
        return pollRepository.findPollById(pollId).
                orElseThrow(()-> new PollNotFoundException(
                        "This poll does not exist"));
    }

}
