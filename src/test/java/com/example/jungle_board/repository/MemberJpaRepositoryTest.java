package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest
@Transactional
public class MemberJpaRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 저장")
    public void save() throws Exception {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r!")
                .build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member result = memberRepository.findById(savedMember.getId()).get();
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    @DisplayName("회원 이메일 검색")
    public void findByEmail() throws Exception {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r!")
                .build();

        // when
        memberRepository.save(member);

        // then
        Member result = memberRepository.findByEmail("abc@gmail.com").get();
        Assertions.assertThat(result).isEqualTo(member);
    }
}
