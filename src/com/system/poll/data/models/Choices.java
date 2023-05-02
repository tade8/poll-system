package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Choices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String choiceText;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vote_id")
    private List<Votes> noOfVotes = new ArrayList<>();
}
