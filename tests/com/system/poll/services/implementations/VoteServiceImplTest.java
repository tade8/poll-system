package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Poll;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.VotesRepository;
import com.system.poll.dtos.requests.ChoicesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {
    @InjectMocks
    private VoteServiceImpl voteService;
    @Mock
    private ChoicesRepository choicesRepository;
    @Mock
    private VotesRepository votesRepository;
    private ChoicesRequest choicesRequest;
    private Choices choices;
    private Poll poll;
    private final LocalTime specifiedEndTime = LocalTime.of(
            23, 59, 59
    );

    @BeforeEach
    void setUp() {
        choicesRequest = new ChoicesRequest();
        choices = new Choices();

        choices.setChoiceText("Peter Obi");
        choices.setChoiceText("Atiku Abubakar");

        poll = new Poll("Who will be Nigeria's next president",
                List.of(choices), specifiedEndTime);

        assertNotNull(poll);
    }

    @Test
    void test_VoteOnChoice_ReturnsNumberOfVotes() {
        when(choicesRepository.findChoiceById(choicesRequest.getId())).
                thenReturn(Optional.of(choices));
        when(votesRepository.save(any())).then(returnsFirstArg());
        when(choicesRepository.save(any())).then(returnsFirstArg());

        voteService.voteOnChoice(choicesRequest.getId());

        assertEquals(1L, choices.getNoOfVotes().size());
    }

    @Test
    public void test_PollHasZeroVotes_WhenCreated() {
        voteService.displayTotalVotes(poll.getId());
        assertEquals(0, choices.getNoOfVotes().size());
    }

    @Test
    public void test_Display_TotalVotes() {
        when(choicesRepository.findChoiceById(choicesRequest.getId())).
                thenReturn(Optional.of(choices));
        when(choicesRepository.save(any())).then(returnsFirstArg());

        voteService.voteOnChoice(choices.getId());
        voteService.voteOnChoice(choices.getId());
        voteService.displayTotalVotes(poll.getId());

        assertEquals(2, choices.getNoOfVotes().size());
    }
}
