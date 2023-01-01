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

    }
}
