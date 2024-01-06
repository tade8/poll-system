package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
import com.system.poll.dtos.requests.VoteRequest;
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
  private UserServiceImpl userService;
  @Mock
  private PollServiceImpl pollService;
  private Choice choice;
  private Poll poll;


  @BeforeEach
  void setUp() {
    Choice[] choices = {
            new Choice("1", "Peter Obi"),
            new Choice("2", "Atiku Abubakar")
    };
    LocalTime specifiedEndTime = LocalTime.of(1, 0);
    poll = new Poll("Who will be Nigeria's next president",
            List.of(choices), specifiedEndTime);
  }

  @Test
  void test_VoteOnChoice_ReturnsNumberOfVotes() {
    User user = new User("1", "Emmanuel", "Tade");
    VoteRequest voteRequest = new VoteRequest(user.getUserId(), poll.getChoices().get(0).getChoiceId());
    choice = new Choice(poll.getChoices().get(0).getChoiceId());

    when(userService.viewUserById(voteRequest.getUserId())).thenReturn(user);
    when(choicesRepository.findChoiceByChoiceId(poll.getChoices().get(0).getChoiceId())).
            thenReturn(Optional.of(choice));
    when(choicesRepository.save(any())).then(returnsFirstArg());
    when(pollService.getPollTotalVotes(voteRequest.getPollId())).thenReturn(1L);
    voteService.voteDisplayResults(voteRequest);

    assertNotNull(choice.getVoteCount());
    assertEquals(1L, choice.getVoteCount());
  }

  @Test
  void testWhenUserVotesDisplaysTheUserDetails() {
    test_VoteOnChoice_ReturnsNumberOfVotes();

    assertEquals(List.of(new User("1", "Emmanuel", "Tade")),
            choice.getUsers());
  }

}
