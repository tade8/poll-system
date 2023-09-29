package com.system.poll.data.repository;

import com.system.poll.data.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, String> {
    Optional<Poll> findPollById(String id);
    @Transactional
    void deletePollById(String pollId);

}
