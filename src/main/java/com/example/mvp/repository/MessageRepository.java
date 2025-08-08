package com.example.mvp.repository;

import com.example.mvp.database_table.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// MessageRepository
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :user1 AND m.receiver.id = :user2) OR (m.sender.id = :user2 AND m.receiver.id = :user1) ORDER BY m.timestamp")
    List<Message> findConversation(@Param("user1") Long user1, @Param("user2") Long user2);

    List<Message> findBySenderIdOrReceiverIdOrderByTimestampDesc(Long senderId, Long receiverId);
}
