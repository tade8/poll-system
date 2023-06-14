package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.requests.PollRequest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PollServiceImplTest {
    @InjectMocks
    private PollServiceImpl pollService;
    @Mock
    private PollRepository pollRepository;
    private PollRequest pollRequest;
    private static final String TIME = "01:00:00";

    @BeforeEach
    void setUp() {
        Choices[] choices = {
                new Choices("Peter Obi"),
                new Choices("Atiku Abubakar")
        };
        pollRequest = new PollRequest(
                "Who will be Nigeria's next president",
                List.of(choices),
                TIME
        );
        assertNotNull(pollRequest);
    }

    @Test
    public void createPoll_GetsSaved() {
        when(pollRepository.save(any())).then(returnsFirstArg());

        pollService.createPoll(pollRequest);

        assertEquals(pollRequest.getQuestion(),
                "Who will be Nigeria's next president");
        assertEquals(pollRequest.getChoices().get(1).getChoiceText(),
                "Atiku Abubakar");
    }

    @Test
    public void test_View_SavedPoll() {
        when(pollRepository.findPollById(pollRequest.getId())).
                thenReturn(Optional.of(new Poll()));

        pollService.viewPollById(pollRequest.getId());

        assertEquals("Who will be Nigeria's next president",
                pollRequest.getQuestion());
    }

    @Test
    public void test_Delete_SavedPoll() {
        when(pollRepository.findPollById(pollRequest.getId())).
                thenReturn(Optional.of(new Poll()));

        pollService.deletePoll(pollRequest.getId());

        assertEquals(0L, pollRepository.findAll().size());
    }

}
