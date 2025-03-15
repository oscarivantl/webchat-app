package com.quickers.webchatbackend.mappers;

import com.quickers.webchatbackend.dto.UserResponse;
import com.quickers.webchatbackend.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {
    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();

        if (attributes.containsKey("sub")) {
            user.setId(attributes.get("sub").toString());
        }

        if (attributes.containsKey("given_name")) {
            user.setFistName(attributes.get("given_name").toString());
        } else if (attributes.containsKey("nickname")) {
            user.setFistName(attributes.get("nickname").toString());
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName(attributes.get("family_name").toString());
        }

        if (attributes.containsKey("email")) {
            user.setEmail(attributes.get("email").toString());
        }

        user.setLastSeen(LocalDateTime.now());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fistName(user.getFistName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }
}
