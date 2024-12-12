package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Comment;
import com.example.jungle_board.entity.Member;
import com.example.jungle_board.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Transactional
public class CommentJpaRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Qualifier("commentJpaRepository")
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void save() throws Exception{
        // given
        System.out.println("====================test");
        Member member1 = Member.builder()
                .email("abc@gmail.com")
                .username("abc")
                .password("1q2w3e4r")
                .build();
        Member member2 = Member.builder()
                .email("bcd@gmail.com")
                .username("bcd")
                .password("1q2w3e4r")
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        Post post = Post.builder()
                .title("title")
                .content("content")
                .member(member1)
                .build();
        postRepository.save(post);
        Comment comment1 = Comment.builder()
                .content("content1")
                .member(member1)
                .post(post)
                .build();
        Comment comment2 = Comment.builder()
                .content("content2")
                .member(member2)
                .post(post)
                .build();
        Comment comment3 = Comment.builder()
                .content("content3")
                .member(member2)
                .post(post)
                .build();

        // when
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        // then
        List<Comment> allByPostId = commentRepository.findAllByPostId(post.getId());
        Assertions.assertThat(allByPostId.size()).isEqualTo(3);
        commentRepository.deleteById(comment1.getId());
        allByPostId = commentRepository.findAllByPostId(post.getId());
        Assertions.assertThat(allByPostId.size()).isEqualTo(1);
    }
}
