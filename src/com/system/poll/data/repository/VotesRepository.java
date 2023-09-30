package com.system.poll.data.repository;

import com.system.poll.data.models.Votes;
import com.system.poll.dtos.response.VoteResultsResponse;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VotesRepository extends JpaRepository<Votes, String> {
    @Query(value = "SELECT count(no_of_votes) " +
                    "FROM poll " +
                    "JOIN choices " +
                    "ON poll.id = choice_id " +
                    "JOIN votes " +
                    "ON choices.id = vote_id "+
                    "WHERE poll.id = ?1 ", nativeQuery = true)
    Long getTotalVotes(String id);

    @Query(value = "SELECT round(count(votes.no_of_votes) * 100 / sum(count(votes.no_of_votes)) OVER()) " +
                    "AS votePercentage " +
                    "FROM poll JOIN choices ON poll.id = choice_id " +
                    "JOIN votes ON choices.id = vote_id " +
                    "WHERE poll.id = ?1 " +
                    "GROUP BY choices.id ", nativeQuery = true)
    List<VoteResultsResponse> displayVoteResults(String id);

}
