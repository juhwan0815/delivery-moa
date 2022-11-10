package inu.deliverymoa.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import inu.deliverymoa.chat.domain.ChatRoom;
import inu.deliverymoa.chat.domain.ChatRoomUser;
import inu.deliverymoa.chat.domain.ChatRoomUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse {

    private Long roomId;

    private String title;

    private String restaurantName;

    private LocalDateTime orderDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean master;

    private Integer userCount;

    public static ChatRoomResponse from(ChatRoom chatRoom) {
        ChatRoomResponse response = new ChatRoomResponse();
        response.roomId = chatRoom.getId();
        response.title = chatRoom.getTitle();
        response.restaurantName = chatRoom.getRestaurantName();
        response.orderDate = chatRoom.getOrderDate();
        response.userCount = chatRoom.getUserCount();
        return response;
    }

    public static ChatRoomResponse of(ChatRoom chatRoom) {
        ChatRoomResponse response = new ChatRoomResponse();
        response.roomId = chatRoom.getId();
        response.title = chatRoom.getTitle();
        response.restaurantName = chatRoom.getRestaurantName();
        response.orderDate = chatRoom.getOrderDate();
        response.userCount = chatRoom.getUserCount();

        List<ChatRoomUser> result = chatRoom.getChatRoomUsers().stream()
                .filter(chatRoomUser -> chatRoomUser.getRole() == ChatRoomUserRole.MASTER)
                .collect(Collectors.toList());

        response.master = result.size() == 0 ? false : true;
        return response;
    }

}
