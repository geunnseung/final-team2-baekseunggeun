package com.likelion.sns.service;

import com.likelion.sns.domain.dto.post.*;
import com.likelion.sns.domain.entity.Post;
import com.likelion.sns.domain.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // post 등록
    public PostCreateResponse create(PostCreateRequest postCreateRequest, Authentication authentication) {

        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postCreateRequest.toEntity(user);
        Post savedPost = postRepository.save(post);

        return PostCreateResponse.toResponse(savedPost);
    }

    // post 단건 조회
    public PostOneResponse getOnePost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        return PostOneResponse.toResponse(post);
    }

    // post 전체 조회
    public Page<PostListResponse> getAllPosts(Pageable pageable) {

        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostListResponse::toResponse);
    }

    // post 수정
    public PostModifyResponse modifyPost(Long postId, PostModifyRequest postModifyRequest, Authentication authentication) {

        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        if (!Objects.equals(post.getUser().getUserId(), user.getUserId())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        post.setTitle(postModifyRequest.getTitle());
        post.setBody(postModifyRequest.getBody());
        // setter 쓰지않고는?
        Post savedPost = postRepository.saveAndFlush(post);

        return PostModifyResponse.toResponse(savedPost);
    }

}
