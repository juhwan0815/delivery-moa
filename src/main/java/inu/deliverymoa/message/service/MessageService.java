package inu.deliverymoa.message.service;

import inu.deliverymoa.message.domain.Message;
import inu.deliverymoa.message.domain.MessageQueryRepository;
import inu.deliverymoa.message.domain.MessageRepository;
import inu.deliverymoa.message.dto.MessageCreateRequest;
import inu.deliverymoa.message.dto.MessageResponse;
import inu.deliverymoa.message.dto.MessageSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final MessageRepository messageRepository;
    private final MessageQueryRepository messageQueryRepository;

    /**
     * 메세지 생성
     */
    @Transactional
    public void createMessage(MessageCreateRequest request) {
        Message message = Message.createMessage(request.getRoomId(), request.getUserId(), request.getNickName(), request.getContent());
        messageRepository.save(message);
        redisTemplate.convertAndSend(channelTopic.getTopic(), MessageResponse.from(message));
    }


    public List<MessageResponse> searchMessage(Long roomId, MessageSearchRequest request) {
        List<Message> findMessages = messageQueryRepository.findByCondition(roomId, request);
        return findMessages.stream()
                .map(message -> MessageResponse.from(message))
                .collect(Collectors.toList());
    }
}
