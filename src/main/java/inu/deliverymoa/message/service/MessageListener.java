package inu.deliverymoa.message.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inu.deliverymoa.message.domain.Message;
import inu.deliverymoa.message.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String receiveMessage) throws JsonProcessingException {
        Message message = objectMapper.readValue(receiveMessage, Message.class);
        messagingTemplate.convertAndSend("/sub/rooms/" + message.getRoomId(), MessageResponse.from(message));
    }
}
