package com.likelion.sns.Controller;

import com.likelion.sns.domain.Response;
import com.likelion.sns.domain.dto.comment.*;
import com.likelion.sns.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // comment 등록
    @PostMapping("/{postId}/comments")
    public Response<CommentCreateResponse> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest commentCreateRequest, Authentication authentication) {
        return Response.success(commentService.createComment(postId, commentCreateRequest, authentication));
    }

    // comment 수정
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentModifyResponse> modifyComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentModifyRequest commentModifyRequest, Authentication authentication) {
        return Response.success(commentService.modifyComment(postId, id, commentModifyRequest, authentication));
    }

    // comment 삭제
    @DeleteMapping("/{postId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Long postId, @PathVariable Long id, Authentication authentication) {
        return Response.success(commentService.deleteComment(postId, id, authentication));
    }
}
