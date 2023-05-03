package com.system.poll.data.repository;

import com.system.poll.data.models.Choices;
import com.system.poll.data.models.Votes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChoicesRepository extends JpaRepository<Choices, Long> {
    Optional<Choices> findChoicesById(String id);
    Optional<Choices> findById(String id);

}
