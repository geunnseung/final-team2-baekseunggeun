package com.likelion.sns.Controller;

import com.likelion.sns.domain.Response;
import com.likelion.sns.domain.dto.post.PostCreateRequest;
import com.likelion.sns.domain.dto.post.PostCreateResponse;
import com.likelion.sns.domain.dto.post.PostListResponse;
import com.likelion.sns.domain.dto.post.PostOneResponse;
import com.likelion.sns.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // post 등록
    @PostMapping
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        return Response.success(postService.create(postCreateRequest, authentication));
    }

    // post 단건 조회
    @GetMapping("/{id}")
    public Response<PostOneResponse> getOnePost(@PathVariable Long id) {
        return Response.success(postService.getOnePost(id));
    }

    // post 전체 조회
    @GetMapping
    public Response<Page<PostListResponse>> getList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postService.getAllPosts(pageable));
    }


}
