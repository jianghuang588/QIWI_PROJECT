package com.example.mvp.service;

import com.example.mvp.database_table.Message;
import com.example.mvp.database_table.User;
import com.example.mvp.dto.MessageDto;
import com.example.mvp.repository.MessageRepository;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// MessageService
@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(MessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message(sender, receiver, dto.getContent());
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1Id, Long user2Id) {
        return messageRepository.findConversation(user1Id, user2Id);
    }

    public List<Message> getUserMessages(Long userId) {
        return messageRepository.findBySenderIdOrReceiverIdOrderByTimestampDesc(userId, userId);
    }

    // NEW: MISSING METHOD FOR SYSTEM MESSAGES
    public Message sendSystemMessage(Long fromUserId, Long toUserId, String subject, String content) {
        System.out.println("📧 Sending system message from " + fromUserId + " to " + toUserId);

        User sender = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(toUserId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        Message message = new Message(sender, recipient, content);

        Message saved = messageRepository.save(message);
        System.out.println("✅ System message sent: " + saved.getId());

        return saved;
    }
}