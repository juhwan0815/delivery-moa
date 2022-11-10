package inu.deliverymoa.message.domain;

import antlr.debug.MessageEvent;
import inu.deliverymoa.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    private Long roomId;

    private Long userId;

    private String userNickname;

    private String content;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static Message createMessage(Long roomId, Long userId, String userNickname, String content) {
        Message message = new Message();
        message.roomId = roomId;
        message.userId = userId;
        message.userNickname = userNickname;
        message.content = content;
        return message;
    }
}