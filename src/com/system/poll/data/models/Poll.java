package com.system.poll.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String question;
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "choice_id")
    private List<Choices> choices = new ArrayList<>();
}
