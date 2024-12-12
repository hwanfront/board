package com.example.jungle_board.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String email;
    private String username;
    private String password;
}
