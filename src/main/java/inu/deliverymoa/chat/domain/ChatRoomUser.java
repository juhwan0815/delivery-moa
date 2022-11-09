package inu.deliverymoa.chat.domain;

import inu.deliverymoa.common.domain.BaseEntity;
import inu.deliverymoa.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoomUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatRoomUserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    public ChatRoom chatRoom;

    public static ChatRoomUser createChatRoomUser(ChatRoomUserRole role, User user, ChatRoom chatRoom) {
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.role = role;
        chatRoomUser.user = user;
        chatRoomUser.chatRoom = chatRoom;
        return chatRoomUser;
    }

}
