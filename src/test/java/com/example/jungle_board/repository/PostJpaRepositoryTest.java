package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Member;
import com.example.jungle_board.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class PostJpaRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 저장")
    public void save() throws Exception {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r!")
                .build();
        memberRepository.save(member);
        Post post = Post.builder()
                .member(member)
                .title("title")
                .content("content")
                .build();

        // when
        postRepository.save(post);

        // then
        Post result = postRepository.findById(post.getId()).get();
        Assertions.assertThat(post).isEqualTo(result);
        Assertions.assertThat(post.getMember()).isEqualTo(member);
    }

    @Test
    public void findAll() {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r")
                .build();

        memberRepository.save(member);

        Post post1 = Post.builder()
                .title("title1")
                .content("content1")
                .member(member)
                .build();
        Post post2 = Post.builder()
                .title("title2")
                .content("content2")
                .member(member)
                .build();
        Post post3 = Post.builder()
                .title("title3")
                .content("content3")
                .member(member)
                .build();

        // when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // then
        Assertions.assertThat(postRepository.findAll()).hasSize(3);
    }

    @Test
    public void findById() {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r")
                .build();

        memberRepository.save(member);

        Post post1 = new Post(member, "post1", "content1");
        Post post2 = new Post(member, "post2", "content2");

        // when
        postRepository.save(post1);
        postRepository.save(post2);

        // then
        boolean result1 = postRepository.findById(post1.getId()).isPresent();
        boolean result2 = postRepository.findById(post2.getId()).isPresent();
        Assertions.assertThat(result1).isTrue();
        Assertions.assertThat(result2).isTrue();
    }

    @Test
    public void deleteById() {
        // given
        Member member = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r")
                .build();

        memberRepository.save(member);

        Post post1 = new Post(member, "post1", "content1");
        Post post2 = new Post(member, "post2", "content2");

        // when
        postRepository.save(post1);
        postRepository.save(post2);

        // then
        postRepository.deleteById(post1.getId());
        boolean result1 = postRepository.findById(post1.getId()).isPresent();
        boolean result2 = postRepository.findById(post2.getId()).isPresent();
        Assertions.assertThat(result1).isFalse();
        Assertions.assertThat(result2).isTrue();

    }
}
