package com.likelion.sns.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDeleteResponse {

    private String message;
    private Long id;

    public static CommentDeleteResponse toResponse(Long id) {
        return CommentDeleteResponse.builder()
                .id(id)
                .build();
    }
}
