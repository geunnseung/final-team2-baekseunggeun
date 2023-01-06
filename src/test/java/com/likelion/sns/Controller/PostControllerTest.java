package com.likelion.sns.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.sns.domain.dto.post.PostCreateRequest;
import com.likelion.sns.domain.dto.post.PostCreateResponse;
import com.likelion.sns.domain.entity.Post;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    PostCreateRequest postCreateRequest = new PostCreateRequest("test", "test");

    @Test
    @DisplayName("포스트 작성 성공")
    @WithMockUser
    void create_success() throws Exception {

        when(postService.create(any(), any()))
                .thenReturn(PostCreateResponse.builder().postId(1L).build());

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print());

    }



}