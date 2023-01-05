package com.likelion.sns.domain.dto.post;

import com.likelion.sns.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostModifyResponse {

    private String message;
    private Long postId;

    public static PostModifyResponse toResponse(Post post) {
        return PostModifyResponse.builder()
                .postId(post.getPostId())
                .build();
    }
}
