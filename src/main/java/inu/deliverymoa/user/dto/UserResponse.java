package inu.deliverymoa.user.dto;


import inu.deliverymoa.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;

    private String nickName;

    private String image;

    public static UserResponse from(User user){
        UserResponse response = new UserResponse();
        response.userId = user.getId();
        response.nickName = user.getNickName();
        response.image = user.getImage();
        return response;
    }
}
