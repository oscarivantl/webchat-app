package com.quickers.webchatbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String fistName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;
    private boolean isOnline;
}
