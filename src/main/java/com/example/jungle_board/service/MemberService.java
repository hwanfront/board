package com.example.jungle_board.service;

import com.example.jungle_board.dto.SignInRequestDto;
import com.example.jungle_board.dto.SignInResponseDto;
import com.example.jungle_board.dto.SignUpRequestDto;
import com.example.jungle_board.entity.Member;
import com.example.jungle_board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequestDto member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Member newMember = Member.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .build();

        memberRepository.save(newMember);
    }
}
