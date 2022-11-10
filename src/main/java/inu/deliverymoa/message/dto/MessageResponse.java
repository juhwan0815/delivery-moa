package inu.deliverymoa.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import inu.deliverymoa.message.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long userId;

    private String userNickname;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static MessageResponse from(Message message){
        return new MessageResponse(message.getUserId(), message.getUserNickname(), message.getContent(), LocalDateTime.now());
    }

}
