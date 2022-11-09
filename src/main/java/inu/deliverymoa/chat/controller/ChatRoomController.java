package inu.deliverymoa.chat.controller;

import inu.deliverymoa.chat.dto.ChatRoomCreateRequest;
import inu.deliverymoa.chat.dto.ChatRoomResponse;
import inu.deliverymoa.chat.dto.ChatRoomSearchRequest;
import inu.deliverymoa.chat.dto.ChatRoomUpdateRequest;
import inu.deliverymoa.chat.service.ChatRoomService;
import inu.deliverymoa.config.LoginUser;
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

    @PostMapping("/rooms")
    public ResponseEntity<Void> createChatRoom(@LoginUser User user,
                                               @RequestBody @Valid ChatRoomCreateRequest request) {
        chatRoomService.createChatRoom(request, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Void> updateChatRoom(@LoginUser User user,
                                               @PathVariable Long roomId,
                                               @RequestBody @Valid ChatRoomUpdateRequest request) {
        chatRoomService.updateChatRoom(roomId, request, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> deleteChatRoom(@LoginUser User user,
                                               @PathVariable Long roomId) {
        chatRoomService.deleteChatRoom(roomId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> searchChatRoom(ChatRoomSearchRequest request) {
        List<ChatRoomResponse> response = chatRoomService.searchChatRoom(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/rooms")
    public ResponseEntity<List<ChatRoomResponse>> findByUser(@LoginUser User user) {
        List<ChatRoomResponse> response = chatRoomService.findByUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rooms/{roomId}/users")
    public ResponseEntity<Void> createChatRoomUser(@LoginUser User user,
                                                   @PathVariable Long roomId) {
        chatRoomService.createChatRoomUser(roomId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rooms/{roomId}/users")
    public ResponseEntity<Void> deleteChatRoomUser(@LoginUser User user,
                                                   @PathVariable Long roomId) {
        chatRoomService.deleteChatRoomUser(roomId, user);
        return ResponseEntity.ok().build();
    }

}
