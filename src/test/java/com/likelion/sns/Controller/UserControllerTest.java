package com.likelion.sns.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.sns.domain.dto.user.UserJoinRequest;
import com.likelion.sns.domain.dto.user.UserJoinResponse;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    UserJoinRequest userJoinRequest = new UserJoinRequest("seunggeun", "1111");
    UserJoinResponse userJoinResponse = new UserJoinResponse("seunggeun", 1L);

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join_success() throws Exception {

        when(userService.join(any())).thenReturn(userJoinResponse);

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode")
                        .value("SUCCESS"));

    }

    @Test
    @DisplayName("회원가입 실패 - username 중복")
    @WithMockUser
    void join_fail() throws Exception {

        when(userService.join(any()))
                .thenThrow(new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage()));

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {

        when(userService.login(any(), any()))
                .thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("로그인 실패 - 없는 id")
    @WithMockUser
    void login_fail_noid() throws Exception {

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("로그인 실패 - 패스워드 오류")
    @WithMockUser
    void login_fail_wrongpwd() throws Exception {

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage()));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }



}