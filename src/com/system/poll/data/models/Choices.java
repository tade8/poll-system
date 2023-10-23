package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Choices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String choiceText;
    private Long voteCount = 0L;

    public Choices(String choiceText) {
        this.choiceText = choiceText;
    }

    public Choices(long voteCount) {
        this.voteCount = voteCount;
    }

    public void increaseVoteCount() {
        voteCount += 1;
    }
}
