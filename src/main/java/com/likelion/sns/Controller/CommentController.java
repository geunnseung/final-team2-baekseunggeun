package com.likelion.sns.Controller;

import com.likelion.sns.domain.Response;
import com.likelion.sns.domain.dto.comment.CommentCreateRequest;
import com.likelion.sns.domain.dto.comment.CommentCreateResponse;
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

}
