package com.system.poll.data.repository;

import com.system.poll.data.models.Choices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChoicesRepository extends JpaRepository<Choices, String> {
    Optional<Choices> findChoiceById(String id);
}
