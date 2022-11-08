package inu.deliverymoa.user.dto;


import inu.deliverymoa.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Long id;

    private String email;

    private String mobile;

    private String nickName;

    private String profileImage;

    public static UserInfoResponse from(User user){
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.id = user.getId();
        userInfoResponse.email = user.getEmail();
        userInfoResponse.mobile = user.getMobile();
        userInfoResponse.nickName = user.getNickName();
        userInfoResponse.profileImage = user.getProfileImage();
        return userInfoResponse;
    }
}
