package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String choiceId;
    private String choiceText;
    private Long voteCount = 0L;
    private LocalDateTime voteTime;
    @JoinColumn(name = "choice_user_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    public Choice(String choiceText) {
        this.choiceText = choiceText;
    }

    public Choice(String choiceId, String choiceText) {
        this.choiceId = choiceId;
        this.choiceText = choiceText;
    }

    public void increaseVoteCount() {
        voteCount += 1;
    }
}
