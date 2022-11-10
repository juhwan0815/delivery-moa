package inu.deliverymoa.user.controller;

import inu.deliverymoa.config.web.LoginUser;
import inu.deliverymoa.user.domain.User;
import inu.deliverymoa.user.dto.UserUpdateRequest;
import inu.deliverymoa.user.dto.UserLoginRequest;
import inu.deliverymoa.user.dto.UserResponse;
import inu.deliverymoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static inu.deliverymoa.security.util.JwtUtil.ACCESS_TOKEN;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> login(@RequestBody @Valid UserLoginRequest request) {
        String accessToken = userService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(ACCESS_TOKEN, accessToken)
                .build();
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponse> findUser(@LoginUser User user) {
        UserResponse response = UserResponse.from(user);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/users")
    public ResponseEntity<Void> updateUser(@LoginUser User user,
                                           @RequestBody @Valid UserUpdateRequest request) {
        userService.updateUser(user, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@LoginUser User user) {
        userService.deleteUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
