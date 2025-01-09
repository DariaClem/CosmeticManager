package com.cosmetic_manager.cosmetic_manager.service;

import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
import com.cosmetic_manager.cosmetic_manager.model.User;
import com.cosmetic_manager.cosmetic_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cosmetic_manager.cosmetic_manager.utils.UserUtil.fromUserDtoToUser;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(UserDto userDto) {
        return userRepository.save(fromUserDtoToUser(userDto));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
