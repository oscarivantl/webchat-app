package com.quickers.webchatbackend.entities;

import com.quickers.webchatbackend.common.MessageState;
import com.quickers.webchatbackend.common.MessageType;
import com.quickers.webchatbackend.common.NotificationType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private String chatId;
    private String content;
    private String senderId;
    private String receiverId;
    private String chatName;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;
}
