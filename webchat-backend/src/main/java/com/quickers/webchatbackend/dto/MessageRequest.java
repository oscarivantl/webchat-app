package com.quickers.webchatbackend.dto;

import com.quickers.webchatbackend.common.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType messageType;
    private String chatId;

}
