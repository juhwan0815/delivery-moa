package inu.deliverymoa.user.domain;

import inu.deliverymoa.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String mobile;

    private String nickName;

    private Long kakaoId;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String fcmToken; // fcmToken

    public static User createUser(String nickName, Long kakaoId, String profileImage, UserRole role){
        User user = new User();
        user.nickName = nickName;
        user.kakaoId = kakaoId;
        user.role = role;
        if(profileImage != null){
            user.profileImage = profileImage;
        }
        return user;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateUser(String email, String mobile, String nickName) {
        this.email = email;
        this.mobile = mobile;
        this.nickName = nickName;
    }

}
