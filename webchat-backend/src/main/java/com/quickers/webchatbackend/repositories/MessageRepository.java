package com.quickers.webchatbackend.repositories;

import com.quickers.webchatbackend.common.MessageConstants;
import com.quickers.webchatbackend.common.MessageState;
import com.quickers.webchatbackend.dto.MessageResponse;
import com.quickers.webchatbackend.entities.Chat;
import com.quickers.webchatbackend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<Message> findMessagesByChatId(String chatId);

    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState messageState);
}
