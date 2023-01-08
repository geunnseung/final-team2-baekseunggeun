package com.likelion.sns.domain.dto.comment;

import com.likelion.sns.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateResponse {

    private Long commentId;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;

    public static CommentCreateResponse toResponse(Comment comment) {
        return CommentCreateResponse.builder()
                .commentId(comment.getCommentId())
                .comment(comment.getComment())
                .userName(comment.getUser().getUserName())
                .postId(comment.getPost().getPostId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
