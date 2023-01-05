package com.likelion.sns.domain.dto.post;

import com.likelion.sns.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {

    private Long id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PostListResponse toResponse(Post post) {
        return PostListResponse.builder()
                .id(post.getPostId())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUserName())
                .createdAt(post.getCreatedAt())
                .createdAt(post.getLastModifiedAt())
                .build();
    }
}
