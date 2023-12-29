package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.requests.PollRequest;
import com.system.poll.exceptions.*;
import com.system.poll.services.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final UserService userService;

    @Override
    public String createPoll(String userId, PollRequest pollRequest) {
        User user = userService.viewUserById(userId);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = formatSpecifiedTime(pollRequest, timeFormatter);
        Poll poll = Poll.builder()
                .pollUser(user)
                .question(pollRequest.getQuestion())
                .choices(pollRequest.getChoices())
                .specifiedEndTime(time)
                .build();
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
        pollRepository.deletePollByPollId(pollId);
        return "Poll has been deleted";
    }

    @Override
    public Poll viewPollById(String pollId) {
        return pollRepository.findPollByPollId(pollId).
                orElseThrow(()-> new PollNotFoundException(
                        "This poll does not exist"));
    }
}
