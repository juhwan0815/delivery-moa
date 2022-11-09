package inu.deliverymoa.chat.domain;

import inu.deliverymoa.category.domain.Category;
import inu.deliverymoa.common.domain.BaseEntity;
import inu.deliverymoa.common.domain.YN;
import inu.deliverymoa.common.exception.DuplicateException;
import inu.deliverymoa.common.exception.ExistException;
import inu.deliverymoa.common.exception.NotEnoughException;
import inu.deliverymoa.common.exception.NotFoundException;
import inu.deliverymoa.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

    private static final int LIMIT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String title;

    private String restaurantName;

    private LocalDateTime orderDate;

    private int userCount;

    @Enumerated(EnumType.STRING)
    private ChatRoomStatus status;

    @Enumerated(EnumType.STRING)
    private YN delYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    public static ChatRoom createChatRoom(String title, String restaurantName, LocalDateTime orderDate,
                                          Category category, User user) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.title = title;
        chatRoom.restaurantName = restaurantName;
        chatRoom.orderDate = orderDate;
        chatRoom.category = category;
        chatRoom.status = ChatRoomStatus.LACK;
        chatRoom.delYn = YN.N;
        chatRoom.chatRoomUsers.add(ChatRoomUser.createChatRoomUser(ChatRoomUserRole.MASTER, user, chatRoom));
        chatRoom.userCount++;
        return chatRoom;
    }

    public void update(String title, String restaurantName, LocalDateTime orderDate, Category category) {
        this.title = title;
        this.restaurantName = restaurantName;
        this.orderDate = orderDate;
        this.category = category;
    }

    public boolean isMaster(User user) {
        List<ChatRoomUser> result = this.chatRoomUsers.stream()
                .filter(chatRoomUser -> chatRoomUser.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        return result.size() == 1 && result.get(0).getRole() == ChatRoomUserRole.MASTER;
    }

    public void delete() {
        this.delYn = YN.Y;
    }

    public void addChatRoomUser(User user) {

        if (chatRoomUsers.size() == LIMIT) {
            throw new NotEnoughException("채팅방 인원이 다차서 입장하실 수 없습니다.");
        }

        List<ChatRoomUser> result = chatRoomUsers.stream()
                .filter(chatRoomUser -> chatRoomUser.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        if (result.size() > 0) {
            throw new DuplicateException("이미 참가한 채팅방입니다.");
        }

        chatRoomUsers.add(ChatRoomUser.createChatRoomUser(ChatRoomUserRole.USER, user, this));
        this.userCount++;
        this.status = chatRoomUsers.size() == LIMIT ? ChatRoomStatus.FULL : ChatRoomStatus.LACK;
    }

    public void deleteChatRoomUser(User user) {
        List<ChatRoomUser> result = chatRoomUsers.stream()
                .filter(chatRoomUser -> chatRoomUser.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        if (result.size() == 0) {
            throw new NotFoundException("존재하지 않는 참가자입니다.");
        }

        if(result.get(0).getRole() == ChatRoomUserRole.MASTER) {
            throw new ExistException("채팅방 생성자는 채팅방을 나갈 수 없습니다.");
        }

        chatRoomUsers.remove(result.get(0));
        this.userCount--;
        if (chatRoomUsers.size() < LIMIT) {
            this.status = ChatRoomStatus.LACK;
        }
    }
}
