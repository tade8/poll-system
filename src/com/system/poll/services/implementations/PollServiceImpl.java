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
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = formatSpecifiedTime(pollRequest, timeFormatter);
        Poll poll = new Poll(
                pollRequest.getQuestion(),
                pollRequest.getChoices(),
                time
        );
        if (poll.getQuestion().isEmpty() || poll.getChoices().isEmpty()) {
            throw new NullPointerException("This field cannot be empty");
        }
        pollRepository.save(poll);
        return "Poll has been created";
    }

    private static LocalTime formatSpecifiedTime(PollRequest pollRequest, DateTimeFormatter timeFormatter) {
        try {
            return LocalTime.parse(
                    pollRequest.getSpecifiedEndTime(), timeFormatter);
        }
        catch (RuntimeException e) {
            throw new DateTimeException("This time format is invalid");
        }
    }

    @Override
    public String deletePoll(String pollId) {
        pollRepository.deletePollById(pollId);
        return "Poll has been deleted";
    }

    @Override
    public Poll viewPollById(String pollId) {
        return pollRepository.findPollById(pollId).
                orElseThrow(()-> new PollNotFoundException(
                        "This poll does not exist"));
    }
}
