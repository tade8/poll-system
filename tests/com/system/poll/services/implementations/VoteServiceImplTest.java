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
  private Poll poll;
  private Choice choice = new Choice();
  private User user;
  private VoteRequest voteRequest;


  @BeforeEach
  void setUp() {
    user = new User("1", "Emmanuel", "Tade");
    Choice[] choices = {
            new Choice("Peter Obi"),
            new Choice("Atiku Abubakar")
    };
    LocalTime specifiedEndTime = LocalTime.of(1, 0);
    poll = new Poll("Who will be Nigeria's next president",
            List.of(choices), specifiedEndTime);
    voteRequest = new VoteRequest(user.getUserId(), choice.getChoiceId());
  }

  @Test
  void test_VoteOnChoice_ReturnsNumberOfVotes() {
    when(userService.viewUserById(user.getUserId())).thenReturn(user);
    when(choicesRepository.findChoiceByChoiceId(choice.getChoiceId())).
            thenReturn(Optional.of(choice));
    when(choicesRepository.save(any())).then(returnsFirstArg());

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
