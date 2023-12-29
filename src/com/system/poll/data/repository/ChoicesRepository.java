package com.system.poll.data.repository;

import com.system.poll.data.models.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChoicesRepository extends JpaRepository<Choice, String> {
    Optional<Choice> findChoiceByChoiceId(String id);
}
