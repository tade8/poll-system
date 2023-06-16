package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.requests.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.LocalTime;
import java.util.*;

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
        when(choicesRepository.save(any())).then(returnsFirstArg());

        voteService.voteDisplayResults(poll.getId(), choicesRequest.getId());

        assertEquals(1L, choices.getNoOfVotes().size());
    }

    @Test
    public void test_PollHasZeroVotes_WhenCreated() {
        voteService.displayTotalVotes(poll.getId());
        assertEquals(0, choices.getNoOfVotes().size());
    }

    @Test
    public void test_Display_TotalVotes() {
        test_VoteOnChoice_ReturnsNumberOfVotes();
        voteService.voteDisplayResults(poll.getId(), choices.getId());

        voteService.displayTotalVotes(poll.getId());

        assertEquals(2, choices.getNoOfVotes().size());
    }
}
