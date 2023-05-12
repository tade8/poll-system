package com.system.poll.services;

public interface VoteService {
    Long voteOnChoice(String choiceId);
    String displayTotalVotes(String pollId);
}
