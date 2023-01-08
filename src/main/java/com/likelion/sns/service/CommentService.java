package com.likelion.sns.service;

import com.likelion.sns.domain.dto.comment.CommentCreateRequest;
import com.likelion.sns.domain.dto.comment.CommentCreateResponse;
import com.likelion.sns.domain.dto.comment.CommentModifyRequest;
import com.likelion.sns.domain.dto.comment.CommentModifyResponse;
import com.likelion.sns.domain.entity.Comment;
import com.likelion.sns.domain.entity.Post;
import com.likelion.sns.domain.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.CommentRepository;
import com.likelion.sns.repository.PostRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // Comment 등록
    public CommentCreateResponse createComment(Long postId, CommentCreateRequest commentCreateRequest, Authentication authentication) {

        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        Comment comment = commentCreateRequest.toEntity(user, post);
        Comment savedComment = commentRepository.save(comment);

        return CommentCreateResponse.toResponse(savedComment);
    }

    // comment 수정
    public CommentModifyResponse modifyComment(Long postId, Long id, CommentModifyRequest commentModifyRequest, Authentication authentication) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        if (!Objects.equals(comment.getUser().getUserId(),user.getUserId())){
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        comment.updateComment(commentModifyRequest.getComment());
        Comment savedComment = commentRepository.save(comment);

        return CommentModifyResponse.toResponse(savedComment);

    }
}
