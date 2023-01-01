package com.likelion.sns.service;

import com.likelion.sns.domain.dto.user.UserJoinRequest;
import com.likelion.sns.domain.dto.user.UserJoinResponse;
import com.likelion.sns.domain.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.UserRepository;
import com.likelion.sns.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private  String secretKey;
    private long expireTime = 1000 * 60 * 60; //1시간

    public UserJoinResponse join(UserJoinRequest userJoinRequest) {

        // username 중복 쳌
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {throw new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
                });

        User savedUser = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserJoinResponse.builder()
                .userId(savedUser.getUserId())
                .userName(savedUser.getUserName())
                .build();
    }

    public String login(String userName, String password) {

        // userName 확인
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> {throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage());
                });

        // password 확인
        if(!encoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        // token 발행
        return JwtUtil.createToken(userName, secretKey, expireTime);


    }
}
