package com.quickers.webchatbackend.services;

import com.quickers.webchatbackend.common.MessageState;
import com.quickers.webchatbackend.common.MessageType;
import com.quickers.webchatbackend.dto.MessageResponse;
import com.quickers.webchatbackend.dto.MessageRequest;
import com.quickers.webchatbackend.entities.Chat;
import com.quickers.webchatbackend.entities.Message;
import com.quickers.webchatbackend.mappers.MessageMapper;
import com.quickers.webchatbackend.repositories.ChatRepository;
import com.quickers.webchatbackend.repositories.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;

    public void saveMessage(MessageRequest messageResquest) {
        Chat chat = chatRepository.findById(messageResquest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        Message message = new Message();
        message.setContent(messageResquest.getContent());
        message.setChat(chat);
        message.setSenderId(messageResquest.getSenderId());
        message.setReceiverId(messageResquest.getReceiverId());
        message.setMessageType(messageResquest.getMessageType());
        message.setState(MessageState.SENT);

        messageRepository.save(message);

        //TODO notifications
    }

    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        //final String recipientId = getRecipientId(chat, authentication);

        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        //TODO notifications
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);
        final String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setMessageType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setMediaFilePath(filePath);
        messageRepository.save(message);

        // TODO notification
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }
}
