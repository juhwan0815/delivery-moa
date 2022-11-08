package inu.deliverymoa.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreateRequest {

    @NotBlank(message = "채팅방 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "가게 이름은 필수입니다.")
    private String restaurantName;

    private LocalDateTime orderDate;

    @NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;
}
