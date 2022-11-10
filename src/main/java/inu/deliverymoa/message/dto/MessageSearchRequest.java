package inu.deliverymoa.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSearchRequest {

    @NotBlank(message = "마지막 메세지 날짜는 필수입니다.")
    private String lastMessageDate;

    @NotNull(message = "페이지 사이즈는 필수입니다.")
    private Integer size;
}
