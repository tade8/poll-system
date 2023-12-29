package com.system.poll.services.implementations;

import com.system.poll.data.models.User;
import com.system.poll.data.repository.UserRepository;
import com.system.poll.dtos.requests.UserRequest;
import com.system.poll.exceptions.UserNotFoundException;
import com.system.poll.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createAndReturnUser(UserRequest userRequest) {
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .build();
        userRepository.save(user);
        return viewUserById(user.getUserId());
    }

    @Override
    public User viewUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }
}
