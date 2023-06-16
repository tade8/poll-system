package com.system.poll.data.repository;

import com.system.poll.data.models.Votes;
import com.system.poll.dtos.response.VoteResultsResponse;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VotesRepository extends JpaRepository<Votes, String> {
    @Query(value = "SELECT poll.id, no_of_votes " +
                    "FROM poll " +
                    "JOIN choices " +
                    "ON poll.id = poll_id " +
                    "JOIN votes " +
                    "ON choices.id = choice_id "+
                    "WHERE poll.id = ?1 ", nativeQuery = true)
    List<Votes> getTotalVotes(String id);

    @Query(value = "SELECT round(count(*) * 100 / sum(count(*)) OVER()) " +
                    "AS votePercentage " +
                    "FROM poll JOIN choices ON poll.id = poll_id " +
                    "JOIN votes ON choices.id = choice_id " +
                    "WHERE poll.id = ?1 " +
                    "GROUP BY choices.id ", nativeQuery = true)
    List<VoteResultsResponse> displayVoteResults(String id);

}
