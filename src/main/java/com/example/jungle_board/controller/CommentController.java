package com.example.jungle_board.controller;

import com.example.jungle_board.dto.CommentCreateRequestDto;
import com.example.jungle_board.dto.CommentResponseDto;
import com.example.jungle_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<String> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto requestDto,
            Authentication authentication
    ) {
        commentService.createComment(requestDto, authentication.getName(), postId);
        return ResponseEntity.ok("Comment created");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, Authentication authentication) {
        commentService.removeComment(authentication.getName(), commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}