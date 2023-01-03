package com.likelion.sns.service;

import com.likelion.sns.domain.dto.post.PostCreateRequest;
import com.likelion.sns.domain.dto.post.PostCreateResponse;
import com.likelion.sns.domain.entity.Post;
import com.likelion.sns.domain.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //포스트 등록
    public PostCreateResponse create(PostCreateRequest postCreateRequest, Authentication authentication) {

        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postCreateRequest.toEntity(user);
        Post savedPost = postRepository.save(post);

        return PostCreateResponse.toResponse(savedPost);
    }
}
