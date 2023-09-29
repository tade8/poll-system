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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {
  @InjectMocks
  private VoteServiceImpl voteService;
  @Mock
  private ChoicesRepository choicesRepository;
  @Mock
  private PollRepository pollRepository;
  @Mock
  private VotesRepository votesRepository;
  private Choices choices;
  private Poll poll;

  @BeforeEach
  void setUp() {
    choices = new Choices("Peter Obi");
    choices = new Choices("Atiku Abubakar");
    LocalTime specifiedEndTime = LocalTime.of(1, 0);
    poll = new Poll("Who will be Nigeria's next president",
            List.of(choices), specifiedEndTime);
  }

  @Test
  void test_VoteOnChoice_ReturnsNumberOfVotes() {
    when(choicesRepository.findChoiceById(choices.getId())).
            thenReturn(Optional.of(choices));
    when(pollRepository.findPollById(poll.getId())).thenReturn(Optional.of(poll));

    voteService.voteDisplayResults(poll.getId(), choices.getId());

    assertEquals(1, choices.getNoOfVotes().size());
  }

  @Test
  public void test_Return_TotalVotes() {
    test_VoteOnChoice_ReturnsNumberOfVotes();
    voteService.voteDisplayResults(poll.getId(), choices.getId());

    voteService.displayTotalVotes(poll.getId());
    assertEquals(2L, choices.getNoOfVotes().size());
  }

  @Test
  void test_DoNothing_When_User_Votes_On_Expired_Poll() {
    assertFalse(poll.isOver());
    test_VoteOnChoice_ReturnsNumberOfVotes();
    assertEquals(1L, choices.getNoOfVotes().size());

    poll.setSpecifiedEndTime(LocalTime.now().plusSeconds(1));
    assertTrue(poll.isOver());
    voteService.voteDisplayResults(poll.getId(), choices.getId());

    assertEquals(1L, choices.getNoOfVotes().size());
  }
}
