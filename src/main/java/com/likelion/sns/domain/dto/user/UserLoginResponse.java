package com.likelion.sns.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private String jwt;
}
