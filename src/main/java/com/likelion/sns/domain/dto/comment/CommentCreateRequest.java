package com.likelion.sns.domain.dto.comment;

import com.likelion.sns.domain.entity.Comment;
import com.likelion.sns.domain.entity.Post;
import com.likelion.sns.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequest {

    private String comment;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .comment(this.getComment())
                .user(user)
                .post(post)
                .build();
    }
}
