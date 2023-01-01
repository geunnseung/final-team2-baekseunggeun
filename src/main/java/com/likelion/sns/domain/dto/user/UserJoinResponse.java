package com.likelion.sns.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserJoinResponse {

    private String userName;
    private Long userId;
}
