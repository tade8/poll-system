package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.PollRepository;
import com.system.poll.dtos.requests.PollRequest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.time.DateTimeException;
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

    @BeforeEach
    void setUp() {
        Choices[] choices = {
                new Choices("Peter Obi"),
                new Choices("Atiku Abubakar")
        };
        String time = "01:00";
        pollRequest = new PollRequest(
                "Who will be Nigeria's next president",
                List.of(choices),
                time
        );
    }

    @Test
    public void createPoll_GetsSaved() {
        when(pollRepository.save(any())).then(returnsFirstArg());

        pollService.createPoll(pollRequest);

        assertNotNull(pollRequest);
        assertEquals(pollRequest.getQuestion(),
                "Who will be Nigeria's next president");
        assertEquals(pollRequest.getChoices().get(1).getChoiceText(),
                "Atiku Abubakar");
        verify(pollRepository).save(any());
    }

    @Test
    public void create_Empty_Poll_ThrowsNullPointerException() {
        Choices[] choices = {};
        pollRequest.setQuestion("");
        pollRequest.setChoices(List.of(choices));
        assertThrows(NullPointerException.class, ()-> pollService.createPoll(pollRequest));
    }

    @Test
    public void invalid_TimeFormat_ThrowsDateTimeException() {
        pollRequest.setSpecifiedEndTime("23:0");
        pollRequest.setSpecifiedEndTime("3:00");

        assertThrows(DateTimeException.class, ()-> pollService.createPoll(pollRequest));
    }

    @Test
    public void test_View_SavedPoll_ReturnsPoll() {
        Poll poll = new Poll();
        when(pollRepository.findPollById(pollRequest.getPoll_id())).
                thenReturn(Optional.of(new Poll()));

        var foundPoll = pollService.viewPollById(pollRequest.getPoll_id());

        assertEquals(poll, foundPoll);
    }

    @Test
    public void test_Delete_SavedPoll_Returns_SuccessMessage() {
        pollService.createPoll(pollRequest);
        assertNotNull(pollRequest);

        assertEquals("Poll has been deleted",
                pollService.deletePoll(pollRequest.getPoll_id()));
        verify(pollRepository).deletePollById(pollRequest.getPoll_id());
    }
}
