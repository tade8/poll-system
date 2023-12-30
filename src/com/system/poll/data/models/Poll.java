package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String pollId;
    private String question;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "choices_id")
    private List<Choice> choices = new ArrayList<>();
    private LocalTime specifiedEndTime;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "poll_user_id")
    private User pollUser = new User();
    private long pollTotalVotes;


    public Poll(String question, List<Choice> choices, LocalTime specifiedEndTime) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = specifiedEndTime;
    }
}
