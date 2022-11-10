package inu.deliverymoa.chat.controller;

import inu.deliverymoa.chat.dto.ChatRoomCreateRequest;
import inu.deliverymoa.chat.dto.ChatRoomResponse;
import inu.deliverymoa.chat.dto.ChatRoomSearchRequest;
import inu.deliverymoa.chat.dto.ChatRoomUpdateRequest;
import inu.deliverymoa.chat.service.ChatRoomService;
import inu.deliverymoa.config.web.LoginUser;
import inu.deliverymoa.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/rooms")
    public ResponseEntity<Void> createChatRoom(@LoginUser User user,
                                               @RequestBody @Valid ChatRoomCreateRequest request) {
        chatRoomService.createChatRoom(request, user);
        return ResponseEntity.ok().build();
    }

    /**
     * 채팅방 수정
     * @param user
     * @param roomId
     * @param request
     * @return
     */
    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Void> updateChatRoom(@LoginUser User user,
                                               @PathVariable Long roomId,
                                               @RequestBody @Valid ChatRoomUpdateRequest request) {
        chatRoomService.updateChatRoom(roomId, request, user);
        return ResponseEntity.ok().build();
    }

    /**
     * 채팅방 삭제
     * @param user
     * @param roomId
     * @return
     */
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> deleteChatRoom(@LoginUser User user,
                                               @PathVariable Long roomId) {
        chatRoomService.deleteChatRoom(roomId, user);
        return ResponseEntity.ok().build();
    }

    /**
     * 채팅방 리스트 조회
     * @param request
     * @return
     */
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> searchChatRoom(ChatRoomSearchRequest request) {
        List<ChatRoomResponse> response = chatRoomService.searchChatRoom(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원이 참가한 채팅방 리스트
     * @param user
     * @return
     */
    @GetMapping("/users/rooms")
    public ResponseEntity<List<ChatRoomResponse>> findByUser(@LoginUser User user) {
        List<ChatRoomResponse> response = chatRoomService.findByUser(user);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 입장
     * @param user
     * @param roomId
     * @return
     */
    @PostMapping("/rooms/{roomId}/users")
    public ResponseEntity<Void> createChatRoomUser(@LoginUser User user,
                                                   @PathVariable Long roomId) {
        chatRoomService.createChatRoomUser(roomId, user);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 퇴장
     * @param user
     * @param roomId
     * @return
     */
    @DeleteMapping("/rooms/{roomId}/users")
    public ResponseEntity<Void> deleteChatRoomUser(@LoginUser User user,
                                                   @PathVariable Long roomId) {
        chatRoomService.deleteChatRoomUser(roomId, user);
        return ResponseEntity.ok().build();
    }

}
