package inu.deliverymoa.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @NotNull(message = "주문 시간은 필수입니다.")
    private LocalDateTime orderDate;

    @NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;
}
