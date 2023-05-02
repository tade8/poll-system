package com.system.poll.data.repository;

import com.system.poll.data.models.Votes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotesRepository extends JpaRepository<Votes, String> {
    List<Votes> findVotesById(String id);

}
