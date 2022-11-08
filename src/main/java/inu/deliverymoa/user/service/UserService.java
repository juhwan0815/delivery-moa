package inu.deliverymoa.user.service;

import inu.deliverymoa.common.exception.DuplicateException;
import inu.deliverymoa.common.exception.NotFoundException;
import inu.deliverymoa.config.KakaoClient;
import inu.deliverymoa.config.KakaoProfile;
import inu.deliverymoa.security.util.JwtUtil;
import inu.deliverymoa.user.domain.User;
import inu.deliverymoa.user.domain.UserRepository;
import inu.deliverymoa.user.domain.UserRole;
import inu.deliverymoa.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static inu.deliverymoa.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KakaoClient kakaoClient;
    private final JwtUtil jwtUtil;
    @Value("${token.access_token.expiration_time}")
    private String accessTokenExpirationTime;

    // 카카오 로그인
    @Transactional
    public TokenResponse kakaoLogin(UserSnsLoginRequest request){
        // 프론트에서 준 카카오토큰으로 카카오 계정 객체 받기
        KakaoProfile kakaoProfile = kakaoClient.getKakaoProfile(request.getKakaoToken());

        // 이미 가입되어있는지 확인
        Optional<User> findUser = userRepository.findByKakaoId(kakaoProfile.getId());
        // 가입 안되어있으면
        if (findUser == null) {
            // 회원가입
            User user = User.createUser(kakaoProfile.getProperties().getNickname(), kakaoProfile.getId(), kakaoProfile.getProperties().getProfile_image(), UserRole.USER);
            userRepository.save(user);

            findUser = userRepository.findByKakaoId(kakaoProfile.getId());
        }

        // 토큰 생성
        String accessToken = jwtUtil.createToken(findUser.get().getId(), String.valueOf(findUser.get().getRole()), accessTokenExpirationTime);
        return new TokenResponse(accessToken);
    }

    // 회원 조회
    public UserInfoResponse findUserInfo(User user){
        return UserInfoResponse.from(user);
    }

    // 회원 수정
    @Transactional
    public void updateUser(User user, UpdateUserRequest request){
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        findUser.updateUser(request.getEmail(), request.getMobile(), request.getNickName());
    }

    // fcm 토큰 생성 및 재발급
    @Transactional
    public void updateFcmToken(User user, UpdateFcmTokenRequest request){
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        findUser.updateFcmToken(request.getFcmToken());
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(User user){
        userRepository.delete(user);
    }

}
