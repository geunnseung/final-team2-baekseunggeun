package com.likelion.sns.Controller;

import com.likelion.sns.domain.Response;
import com.likelion.sns.domain.dto.user.UserJoinRequest;
import com.likelion.sns.domain.dto.user.UserJoinResponse;
import com.likelion.sns.domain.dto.user.UserLoginRequest;
import com.likelion.sns.domain.dto.user.UserLoginResponse;
import com.likelion.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        return Response.success(userService.join(userJoinRequest));
    }

    // 로그인
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token =  userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
