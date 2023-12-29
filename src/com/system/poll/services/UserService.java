package com.system.poll.services;

import com.system.poll.data.models.User;
import com.system.poll.dtos.requests.UserRequest;


public interface UserService {
    User createAndReturnUser(UserRequest userRequest);
    User viewUserById(String userId);
}
