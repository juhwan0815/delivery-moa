package inu.deliverymoa.message.service;

import inu.deliverymoa.common.exception.NotFoundException;
import inu.deliverymoa.message.domain.Message;
import inu.deliverymoa.message.domain.MessageRepository;
import inu.deliverymoa.message.dto.MessageRequest;
import inu.deliverymoa.message.dto.MessageResponse;
import inu.deliverymoa.user.domain.User;
import inu.deliverymoa.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static inu.deliverymoa.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    /**
     * 채팅방 채팅내역 조회 (페이징)
     * @param roomId
     * @param pageable
     * @param lastMessageDate
     * @return
     */
    public List<MessageResponse> findByRoomId(Long roomId, Pageable pageable, String lastMessageDate) {
        List<Message> messages = messageRepository
                .findAllByRoomIdAndCreatedAtIsBeforeOrderByCreatedAtDesc(
                        roomId, LocalDateTime.parse(lastMessageDate), PageRequest.of(0, pageable.getPageSize()));
        return messages.stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 채팅 생성 (채팅하기)
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public MessageResponse createChat(Long userId, MessageRequest request){
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Message message = Message.createMessage(request.getRoomId(), findUser.getId(), findUser.getNickName(), request.getContent());
        messageRepository.save(message);
        return MessageResponse.from(message);
    }

}
