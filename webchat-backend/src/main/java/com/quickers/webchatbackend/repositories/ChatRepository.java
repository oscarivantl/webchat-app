package com.quickers.webchatbackend.repositories;

import com.quickers.webchatbackend.common.ChatConstants;
import com.quickers.webchatbackend.dto.ChatResponse;
import com.quickers.webchatbackend.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatsBySenderId(@Param("senderId") String userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER)
    Optional<Chat> findChatsByReceiverAndSender(@Param("senderId") String senderId, @Param("recipientId") String receiverId);
}
