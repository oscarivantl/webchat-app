package com.quickers.webchatbackend.services;

import com.quickers.webchatbackend.dto.ChatResponse;
import com.quickers.webchatbackend.entities.Chat;
import com.quickers.webchatbackend.entities.User;
import com.quickers.webchatbackend.mappers.ChatMapper;
import com.quickers.webchatbackend.repositories.ChatRepository;
import com.quickers.webchatbackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper mapper;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId = currentUser.getName();
        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(c -> mapper.toChatResponse(c, userId))
                .toList();
    }

    public String createChat(String senderId, String receiverId) {
        Optional<Chat> exitingChat = chatRepository.findChatsByReceiverAndSender(senderId, receiverId);
        if (exitingChat.isPresent()) {
            return exitingChat.get().getId();
        }

        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + senderId + " not found"));

        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + receiverId + " not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);

        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }
}
