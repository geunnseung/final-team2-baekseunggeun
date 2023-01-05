package com.likelion.sns.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDeleteResponse {

    private String message;
    private Long postId;

    public static PostDeleteResponse toResponse(Long postId) {
        return PostDeleteResponse.builder()
                .postId(postId)
                .build();
    }
}

