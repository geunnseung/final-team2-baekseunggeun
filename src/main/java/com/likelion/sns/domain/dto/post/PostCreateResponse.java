package com.likelion.sns.domain.dto.post;

import com.likelion.sns.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateResponse {

    private String message;
    private Long postId;

    public static PostCreateResponse toResponse(Post post) {
        return PostCreateResponse.builder()
                .postId(post.getPostId())
                .build();
    }
}
