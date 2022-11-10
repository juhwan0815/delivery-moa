package inu.deliverymoa.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateRequest {

    private Long roomId;

    private Long userId;

    private String nickName;

    private String content;

}
