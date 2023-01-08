package com.likelion.sns.domain.dto.comment;

import com.likelion.sns.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {

    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private LocalDateTime createdAt;

    public static CommentListResponse toCommentListResponse(Comment comment) {
        return CommentListResponse.builder()
                .id(comment.getCommentId())
                .comment(comment.getComment())
                .userName(comment.getUser().getUserName())
                .postId(comment.getPost().getPostId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
