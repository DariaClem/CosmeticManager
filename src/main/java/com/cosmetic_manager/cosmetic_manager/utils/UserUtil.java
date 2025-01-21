package com.cosmetic_manager.cosmetic_manager.utils;

import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
import com.cosmetic_manager.cosmetic_manager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    public static User fromUserDtoToUser (UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .skinType(userDto.getSkinType())
                .birthDate(userDto.getBirthDate())
                .build();
    }
}
