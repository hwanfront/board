package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemberMemoryRepositoryTest {

    MemberMemoryRepository memoryRepository = new MemberMemoryRepository();

    @AfterEach
    public void afterEach() {
        memoryRepository.clearMembers();
    }

    @Test
    public void save() throws Exception {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r!").build();

        // when
        memoryRepository.save(member);
        // then
        Member result = memoryRepository.findById(member.getId()).get();
        Assertions.assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByEmail() {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r!").build();

        // when
        memoryRepository.save(member);

        // then
        Member result = memoryRepository.findByEmail("abc@gmail.com").get();
        Assertions.assertThat(result).isEqualTo(member);
    }
}