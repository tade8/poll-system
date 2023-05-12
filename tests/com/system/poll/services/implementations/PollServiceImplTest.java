package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.requests.PollRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PollServiceImplTest {
    @InjectMocks
    private PollServiceImpl pollService;
    @Mock
    private PollRepository pollRepository;
    @Mock
    private ChoicesRepository choicesRepository;
    private PollRequest pollRequest;
    private final String time = "01:00:00";

    @BeforeEach
    void setUp() {
        Choices choice1 = new Choices("Peter Obi");
        Choices choice2 = new Choices("Atiku Abubakar");

        Choices[] choices = {choice1, choice2};

        pollRequest = new PollRequest("Who will be Nigeria's next president",
                List.of(choices), time);

        assertNotNull(pollRequest);
    }

    @Test
    public void createPoll_GetsSaved() {
        when(choicesRepository.save(any())).then(returnsFirstArg());
        when(pollRepository.save(any())).then(returnsFirstArg());

        pollService.createPoll(pollRequest);

        assertEquals(pollRequest.getQuestion(),
                "Who will be Nigeria's next president");
        assertEquals(pollRequest.getChoices().get(1).getChoiceText(),
                "Atiku Abubakar");
        assertNotNull(pollRequest.getSpecifiedEndTime());
    }

    @Test
    public void test_View_SavedPoll() {
        when(pollRepository.findPollById(pollRequest.getId())).
                thenReturn(Optional.of(new Poll()));

        pollService.viewPollById(pollRequest.getId());

        verify(pollRepository).findPollById(pollRequest.getId());
    }

    @Test
    public void test_Delete_SavedPoll() {
        when(pollRepository.findPollById(pollRequest.getId())).thenReturn(Optional.of(new Poll()));

        pollService.deletePoll(pollRequest.getId());

        verify(pollRepository).deletePollById(pollRequest.getId());
    }

}
