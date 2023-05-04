package com.system.poll.data.repository;

import com.system.poll.data.models.Votes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VotesRepository extends JpaRepository<Votes, String> {

}
