package com.system.poll.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Choices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotBlank
    private String choiceText;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "vote_id")
    private List<Votes> noOfVotes = new ArrayList<>();

    public Choices(String choiceText) {
        this.choiceText = choiceText;
    }
}
