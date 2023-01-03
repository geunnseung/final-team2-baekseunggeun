package com.likelion.sns.domain.dto.post;

import com.likelion.sns.domain.entity.Post;
import com.likelion.sns.domain.entity.User;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRequest {

    private String title;
    private String body;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.getTitle())
                .body(this.getBody())
                .userName(user.getUserName())
                .user(user)
                .build();

    }
}
