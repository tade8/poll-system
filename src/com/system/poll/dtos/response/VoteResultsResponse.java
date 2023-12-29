package com.system.poll.dtos.response;

import com.system.poll.data.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class VoteResultsResponse {
    private List<User> users;
    private Long voteCount;
    private LocalDateTime voteTime;
}
