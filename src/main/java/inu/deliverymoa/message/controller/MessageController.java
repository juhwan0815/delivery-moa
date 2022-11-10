package inu.deliverymoa.message.controller;

import inu.deliverymoa.message.dto.MessageRequest;
import inu.deliverymoa.message.dto.MessageResponse;
import inu.deliverymoa.message.service.MessageService;
import inu.deliverymoa.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final JwtUtil jwtUtil;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 해당방의 채팅내역 조회 (페이징)
     * @param roomId
     * @param pageable
     * @param lastMessageDate
     * @return
     */
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<MessageResponse>> findByRoomId(@PathVariable("roomId") Long roomId,
                                                              @PageableDefault(size = 50, page = 0) Pageable pageable,
                                                              @RequestParam String lastMessageDate) {
        return ResponseEntity.ok(messageService.findByRoomId(roomId, pageable, lastMessageDate));
    }

    /**
     * 메세지 전송
     * @param request
     * @param bearerToken
     */
    @MessageMapping("/chats/messages")
    public void sendMessage(MessageRequest request, @Header(HttpHeaders.AUTHORIZATION) String bearerToken) {
        Long userId = jwtUtil.getUserId(bearerToken.substring(7));

        MessageResponse messageResponse = messageService.createChat(userId, request);
        messagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), messageResponse);
    }

}
