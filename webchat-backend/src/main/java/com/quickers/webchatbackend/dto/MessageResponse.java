package com.quickers.webchatbackend.dto;

import com.quickers.webchatbackend.common.MessageState;
import com.quickers.webchatbackend.common.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    private Long id;
    private String content;
    private MessageState state;
    private MessageType type;
    private String senderId;
    private String receiverId;
    private LocalDateTime createdAt;
    private byte[] media;
}
