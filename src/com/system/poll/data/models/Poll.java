package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String question;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "choice_id")
    private List<Choices> choices = new ArrayList<>();
    @Column(name = "end_time")
    private LocalTime specifiedEndTime;

    public Poll(String question, List<Choices> choices, LocalTime specifiedEndTime) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = specifiedEndTime;
    }

    public boolean isOver() {
        return specifiedEndTime.isAfter(LocalTime.now());
    }
}
