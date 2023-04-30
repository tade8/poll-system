package com.system.poll.data.repository;

import com.system.poll.data.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
    Poll findPollById(String id);
}
