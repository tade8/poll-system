package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
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
    @Mock
    private UserServiceImpl userService;
    private PollRequest pollRequest;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "Emmanuel", "Tade");
        Choice[] choices = {
                new Choice("Peter Obi"),
                new Choice("Atiku Abubakar")
        };
        pollRequest = new PollRequest(
                "Who will be Nigeria's next president",
                List.of(choices)
        );
    }

    @Test
    public void createPoll_GetsSaved() {
        when(pollRepository.save(any())).then(returnsFirstArg());
        when(userService.viewUserById(user.getUserId())).thenReturn(user);
        pollService.createPoll(user.getUserId(), pollRequest);

        assertNotNull(pollRequest);
        assertEquals(pollRequest.getQuestion(),
                "Who will be Nigeria's next president");
        assertEquals(pollRequest.getChoices().get(1).getChoiceText(),
                "Atiku Abubakar");
        verify(pollRepository).save(any());
    }

    @Test
    public void create_Empty_Poll_ThrowsNullPointerException() {
        when(userService.viewUserById(user.getUserId())).thenReturn(user);
        Choice[] choices = {};
        pollRequest.setQuestion("");
        pollRequest.setChoices(List.of(choices));

        assertThrows(NullPointerException.class, ()-> pollService.createPoll(user.getUserId(), pollRequest));
    }

    @Test
    public void invalid_TimeFormat_ThrowsDateTimeException() {
        pollRequest.setSpecifiedEndTime("23:0");
        pollRequest.setSpecifiedEndTime("3:00");

        assertThrows(DateTimeException.class, ()-> pollService.createPoll(user.getUserId(), pollRequest));
    }

    @Test
    public void test_View_SavedPoll_ReturnsPoll() {
        Poll poll = new Poll();
        when(pollRepository.findPollByPollId(poll.getPollId())).
                thenReturn(Optional.of(new Poll()));

        var foundPoll = pollService.viewPollById(poll.getPollId());

        assertEquals(poll, foundPoll);
    }

    @Test
    public void test_Delete_SavedPoll_Returns_SuccessMessage() {
        when(userService.viewUserById(user.getUserId())).thenReturn(user);
        pollService.createPoll(user.getUserId(), pollRequest);
        assertNotNull(pollRequest);

        assertEquals("Poll has been deleted successfully",
                pollService.deletePoll(pollRequest.getPollId()));
        verify(pollRepository).deletePollByPollId(pollRequest.getPollId());
    }
}
