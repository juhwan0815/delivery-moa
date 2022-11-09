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
    private Long kakaoId;

    private String nickName;

    private String image;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static User createUser(String nickName, Long kakaoId, String image, UserRole role){
        User user = new User();
        user.kakaoId = kakaoId;
        user.nickName = nickName;
        user.image = image;
        user.role = role;
        return user;
    }

    public void updateUser(String nickName) {
        this.nickName = nickName;
    }

}
