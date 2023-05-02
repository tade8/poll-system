package com.system.poll.services.implementations;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Poll;
import com.system.poll.data.repository.ChoicesRepository;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.data.repository.VotesRepository;
import com.system.poll.dtos.requests.ChoicesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    @Mock
    private PollRepository pollRepository;

    @BeforeEach
    void setUp() {
        choicesRequest = new ChoicesRequest();
        choices = new Choices();
        poll = new Poll();

        choices.setChoiceText("Peter Obi");
        choices.setChoiceText("Atiku Abubakar");
        choices.setChoiceText("Tinubu Bola");

        poll.setQuestion("Who will be Nigeria's next president");
        poll.setChoices(List.of(choices));

    }

    @Test
    void test_voteOnChoice_ReturnsNumberOfVotes() {
        assertNotNull(poll);

        when(choicesRepository.findById(choicesRequest.getId())).
                thenReturn(Optional.of(choices));
        when(votesRepository.save(any())).then(returnsFirstArg());
        when(choicesRepository.save(any())).then(returnsFirstArg());

        voteService.voteOnChoice(choicesRequest.getId());

        assertEquals(1L, choices.getNoOfVotes().size());
    }

    @Test
    public void testViewAllVotes() {
        voteService.displayTotalVotes();

        verify(votesRepository).findAll();
    }

    @Test
    @Disabled
    public void testCalculateTotalVotes() {
        when(choicesRepository.findById(choicesRequest.getId())).
                thenReturn(Optional.of(choices));
        when(votesRepository.save(any())).then(returnsFirstArg());
        when(choicesRepository.save(any())).then(returnsFirstArg());
        voteService.voteOnChoice(choices.getId());

        when(pollRepository.findPollById(poll.getId())).thenReturn(poll);
        when(choicesRepository.findChoicesById(choices.getId())).thenReturn(Optional.of(choices));
        voteService.calculateTotalVotes(choicesRequest);

        assertEquals(1, votesRepository.findVotesById(choices.getId()).size());
    }
}
