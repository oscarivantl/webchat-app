package com.quickers.webchatbackend.mappers;

import com.quickers.webchatbackend.dto.MessageResponse;
import com.quickers.webchatbackend.entities.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getMessageType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                // TODO read media file
                .build();
    }
}
