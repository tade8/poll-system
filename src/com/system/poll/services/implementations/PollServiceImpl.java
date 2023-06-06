package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.exceptions.PollNotFoundException;
import com.system.poll.services.PollService;
import lombok.*;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;

    @Override
    public String createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_TIME;
        LocalTime specifiedEndTime = formatSpecifiedTime(pollRequest, timeFormatter);
        try {
            poll.setQuestion(pollRequest.getQuestion());
            poll.setChoices(pollRequest.getChoices());
            poll.setSpecifiedEndTime(specifiedEndTime);
            pollRepository.save(poll);
        }
        catch (Exception e) {
            throw new NullPointerException("This field cannot be empty");
        }
        return "Poll has been created";
    }

    private LocalTime formatSpecifiedTime(PollRequest pollRequest, DateTimeFormatter timeFormatter) {
        return LocalTime.parse(pollRequest.getSpecifiedEndTime(), timeFormatter);
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
