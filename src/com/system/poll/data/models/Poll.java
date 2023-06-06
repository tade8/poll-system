package com.system.poll.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String question;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "poll_id")
    @NotBlank
    private List<Choices> choices = new ArrayList<>();
    @Column(name = "end_time")
    @NotBlank
    private LocalTime specifiedEndTime = LocalTime.of(
            23, 0, 0
    );

    public Poll(String question, List<Choices> choices, LocalTime specifiedEndTime) {
        this.question = question;
        this.choices = choices;
        this.specifiedEndTime = specifiedEndTime;
    }
}
