package inu.deliverymoa.user.service;

import inu.deliverymoa.chat.domain.ChatRoom;
import inu.deliverymoa.chat.domain.ChatRoomQueryRepository;
import inu.deliverymoa.client.KakaoClient;
import inu.deliverymoa.common.exception.NotFoundException;
import inu.deliverymoa.client.KakaoProfile;
import inu.deliverymoa.security.util.JwtUtil;
import inu.deliverymoa.user.domain.User;
import inu.deliverymoa.user.domain.UserRepository;
import inu.deliverymoa.user.domain.UserRole;
import inu.deliverymoa.user.dto.UserUpdateRequest;
import inu.deliverymoa.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static inu.deliverymoa.common.exception.NotFoundException.USER_NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChatRoomQueryRepository chatRoomQueryRepository;

    private final KakaoClient kakaoClient;
    private final JwtUtil jwtUtil;
    @Value("${token.access_token.expiration_time}")
    private String accessTokenExpirationTime;

    @Transactional
    public String login(UserLoginRequest request) {

        KakaoProfile kakaoProfile = kakaoClient.getKakaoProfile(
                "bearer " + request.getKakaoToken(),
                APPLICATION_FORM_URLENCODED_VALUE);

        Optional<User> findUser = userRepository.findByKakaoId(kakaoProfile.getId());

        User user = findUser.isPresent() ?
                findUser.get() : userRepository.save(
                        User.createUser(kakaoProfile.getProperties().getNickname(),
                                        kakaoProfile.getId(),
                                        kakaoProfile.getProperties().getProfile_image(),
                                        UserRole.ROLE_USER));

        return jwtUtil.createToken(user.getId(), String.valueOf(user.getRole()), accessTokenExpirationTime);
    }

    @Transactional
    public void updateUser(User user, UserUpdateRequest request) {

        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        findUser.updateUser(request.getNickName());
    }

    @Transactional
    public void deleteUser(User user) {

        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        List<ChatRoom> findChatRooms = chatRoomQueryRepository.findByUser(user);
        findChatRooms.stream()
                        .forEach(chatRoom -> {
                            chatRoom.deleteChatRoomUser(user);
                        });

        userRepository.delete(findUser);
    }

}
