package com.likelion.sns.service;

import com.likelion.sns.domain.dto.user.UserJoinRequest;
import com.likelion.sns.domain.dto.user.UserJoinResponse;
import com.likelion.sns.domain.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserJoinResponse join(UserJoinRequest userJoinRequest) {

        // username 중복 쳌
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {throw new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
                });

    }
}
