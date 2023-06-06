package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Votes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long noOfVotes;

    public Votes(long voteCount) {
        noOfVotes = voteCount;
    }
}
