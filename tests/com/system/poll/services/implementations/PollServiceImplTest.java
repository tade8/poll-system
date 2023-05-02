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

import java.util.Arrays;

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

    @BeforeEach
    void setUp() {
        pollRequest = new PollRequest();
    }

    @Test
    public void createPoll_GetsSaved() {
        Choices choice1 = new Choices();
        Choices choice2 = new Choices();
        Choices choice3 = new Choices();

        choice1.setChoiceText("Peter Obi");
        choice2.setChoiceText("Atiku Abubakar");
        choice3.setChoiceText("Bola Tinubu");

        pollRequest.setQuestion("Who will be Nigeria's next president");
        pollRequest.setChoices(Arrays.asList(choice1, choice2, choice3));

        when(choicesRepository.save(any())).then(returnsFirstArg());
        when(pollRepository.save(any())).then(returnsFirstArg());
        pollService.createPoll(pollRequest);

        assertEquals(pollRequest.getChoices().get(1).getChoiceText(),
                "Atiku Abubakar");
        assertEquals(pollRequest.getQuestion(),
                 "Who will be Nigeria's next president");
        assertNotNull(pollRequest);
    }

    @Test
    public void test_View_SavedPoll() {
        when(pollRepository.findPollById(pollRequest.getId())).
                thenReturn(new Poll());

        pollService.viewPoll(pollRequest.getId());

        verify(pollRepository).findPollById(pollRequest.getId());
    }

    @Test
    public void test_Deleted_SavedPoll() {
        when(pollRepository.findPollById(pollRequest.getId())).thenReturn(new Poll());

        pollService.deletePoll(pollRequest.getId());

        verify(pollRepository).deletePollById(pollRequest.getId());
    }

}