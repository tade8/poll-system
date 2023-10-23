package com.system.poll.services.implementations;

import com.system.poll.data.models.*;
import com.system.poll.data.repository.*;
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
  private PollServiceImpl pollService;
  private Poll poll;
  Choices choices = new Choices(0L);


  @BeforeEach
  void setUp() {
    Choices[] choices = {
            new Choices("Peter Obi"),
            new Choices("Atiku Abubakar")
    };
    LocalTime specifiedEndTime = LocalTime.of(1, 0);
    poll = new Poll("Who will be Nigeria's next president",
            List.of(choices), specifiedEndTime);
  }

  @Test
  void test_VoteOnChoice_ReturnsNumberOfVotes() {
    when(pollService.viewPollById(poll.getId())).thenReturn(poll);
    when(choicesRepository.findChoiceById(choices.getId())).
            thenReturn(Optional.of(choices));
    when(choicesRepository.save(any())).then(returnsFirstArg());

    voteService.voteDisplayResults(poll.getId(), choices.getId());
    voteService.voteDisplayResults(poll.getId(), choices.getId());

    assertNotNull(choices.getVoteCount());
    assertEquals(2L, choices.getVoteCount());
  }

  @Test
  void test_ThrowException_When_User_Votes_On_Expired_Poll() {
    assertFalse(poll.isOver());
    test_VoteOnChoice_ReturnsNumberOfVotes();
    assertEquals(2L, choices.getVoteCount());

    poll.setSpecifiedEndTime(LocalTime.now().plusSeconds(1));

    assertTrue(poll.isOver());
    assertThrows(IllegalStateException.class,
            ()-> voteService.voteDisplayResults(poll.getId(), choices.getId()));
    assertEquals(2L, choices.getVoteCount());
  }
}
