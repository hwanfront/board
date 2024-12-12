package com.example.jungle_board.controller;

import com.example.jungle_board.dto.PostCreateResponseDto;
import com.example.jungle_board.dto.PostDetailResponseDto;
import com.example.jungle_board.dto.PostResponseDto;
import com.example.jungle_board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> responseDto = postService.getPosts();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPost(@PathVariable Long postId) {
        PostDetailResponseDto responseDto = postService.getPost(postId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<String> createPost(
            @RequestBody @Validated PostCreateResponseDto requestDto, Authentication authentication) {
        System.out.println(authentication.getName());
        postService.createPost(requestDto, authentication.getName());
        return ResponseEntity.ok("Create successful");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, Authentication authentication) {
        postService.deletePost(postId, authentication.getName());
        return ResponseEntity.ok("Deleted post");
    }
}
