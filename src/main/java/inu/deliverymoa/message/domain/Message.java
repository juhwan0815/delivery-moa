package inu.deliverymoa.message.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    private Long roomId;

    private Long userId;

    private String nickName;

    private String content;

    private LocalDateTime createdAt;

    public static Message createMessage(Long roomId, Long userId, String nickName, String content) {
        Message message = new Message();
        message.roomId = roomId;
        message.userId = userId;
        message.nickName = nickName;
        message.content = content;
        message.createdAt = LocalDateTime.now();
        return message;
    }
}