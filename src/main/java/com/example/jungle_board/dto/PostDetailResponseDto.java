package com.example.jungle_board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
