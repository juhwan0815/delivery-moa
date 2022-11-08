package inu.deliverymoa.user.controller;

import inu.deliverymoa.config.LoginUser;
import inu.deliverymoa.user.domain.User;
import inu.deliverymoa.user.dto.*;
import inu.deliverymoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 카카오 로그인 -> 프론트에서 토큰 주면 -> 토큰 생성
    @PostMapping
    public ResponseEntity<Void> kakaoLogin(@RequestBody @Valid UserSnsLoginRequest request){
        TokenResponse token = userService.kakaoLogin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("accessToken", token.getAccessToken())
                .build();
    }

    // 회원 조회
    @GetMapping
    public ResponseEntity<UserInfoResponse> findUserInfo(@LoginUser User user){
        UserInfoResponse body = userService.findUserInfo(user);
        return ResponseEntity.ok().body(body);
    }

    // 회원 수정
    @PutMapping
    public ResponseEntity<Void> updateUser(@LoginUser User user,
                                           @RequestBody @Valid UpdateUserRequest request){
        userService.updateUser(user, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // fcm 토큰 생성 및 재발급
    @PatchMapping
    public ResponseEntity<Void> updateFcmToken(@LoginUser User user,
                                               @RequestBody @Valid UpdateFcmTokenRequest request){
        userService.updateFcmToken(user, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@LoginUser User user){
        userService.deleteUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
