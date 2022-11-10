package inu.deliverymoa.chat.service;

import inu.deliverymoa.category.domain.Category;
import inu.deliverymoa.category.domain.CategoryRepository;
import inu.deliverymoa.chat.domain.ChatRoom;
import inu.deliverymoa.chat.domain.ChatRoomQueryRepository;
import inu.deliverymoa.chat.domain.ChatRoomRepository;
import inu.deliverymoa.chat.dto.ChatRoomCreateRequest;
import inu.deliverymoa.chat.dto.ChatRoomResponse;
import inu.deliverymoa.chat.dto.ChatRoomSearchRequest;
import inu.deliverymoa.chat.dto.ChatRoomUpdateRequest;
import inu.deliverymoa.common.exception.NotFoundException;
import inu.deliverymoa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static inu.deliverymoa.common.exception.NotFoundException.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomQueryRepository chatRoomQueryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createChatRoom(ChatRoomCreateRequest request, User user) {

        Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));

        ChatRoom chatRoom = ChatRoom.createChatRoom(request.getTitle(), request.getRestaurantName(),
                request.getOrderDate(), findCategory, user);
        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void updateChatRoom(Long roomId, ChatRoomUpdateRequest request, User user) {

        ChatRoom findChatRoom = chatRoomRepository.findWithChatRoomUsersById(roomId)
                .orElseThrow(() -> new NotFoundException(CHAT_ROOM_NOT_FOUND));

        if (!findChatRoom.isMaster(user)) {
            throw new AccessDeniedException("");
        }

        Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));


        findChatRoom.update(request.getTitle(), request.getRestaurantName(),
                request.getOrderDate(), findCategory);
    }

    @Transactional
    public void deleteChatRoom(Long roomId, User user) {
        ChatRoom findChatRoom = chatRoomRepository.findWithChatRoomUsersById(roomId)
                .orElseThrow(() -> new NotFoundException(CHAT_ROOM_NOT_FOUND));

        if (!findChatRoom.isMaster(user)) {
            throw new AccessDeniedException("");
        }

        findChatRoom.delete();
    }

    public List<ChatRoomResponse> searchChatRoom(ChatRoomSearchRequest request) {
        List<ChatRoom> findChatRooms = chatRoomQueryRepository.searchChatRoom(request.getCategoryId());
        return findChatRooms.stream()
                .map(chatRoom -> ChatRoomResponse.from(chatRoom))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createChatRoomUser(Long roomId, User user) {

        ChatRoom findChatRoom = chatRoomRepository.findWithChatRoomUsersById(roomId)
                .orElseThrow(() -> new NotFoundException(CHAT_ROOM_NOT_FOUND));

        findChatRoom.addChatRoomUser(user);
    }

    @Transactional
    public void deleteChatRoomUser(Long roomId, User user) {

        ChatRoom findChatRoom = chatRoomRepository.findWithChatRoomUsersById(roomId)
                .orElseThrow(() -> new NotFoundException(CHAT_ROOM_NOT_FOUND));

        findChatRoom.deleteChatRoomUser(user);
    }

    public List<ChatRoomResponse> findByUser(User user) {
        List<ChatRoom> findChatRooms = chatRoomQueryRepository.findByUser(user);
        return findChatRooms.stream()
                .map(chatRoom -> ChatRoomResponse.of(chatRoom))
                .collect(Collectors.toList());
    }
}
