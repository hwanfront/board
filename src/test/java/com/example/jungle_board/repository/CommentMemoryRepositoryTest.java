package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Comment;
import com.example.jungle_board.entity.Member;
import com.example.jungle_board.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CommentMemoryRepositoryTest {
    MemberMemoryRepository memberMemoryRepository = new MemberMemoryRepository();
    PostMemoryRepository postMemoryRepository = new PostMemoryRepository();
    CommentMemoryRepository commentMemoryRepository = new CommentMemoryRepository();

    @AfterEach
    void afterEach() {
        memberMemoryRepository.clearMembers();
        postMemoryRepository.clearPosts();
        commentMemoryRepository.clearComments();
    }

    @Test
    void save() {
        // given
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
        memberMemoryRepository.save(member1);
        memberMemoryRepository.save(member2);

        Post post = Post.builder()
                .title("title")
                .content("content")
                .member(member1)
                .build();
        postMemoryRepository.save(post);
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

        // when
        commentMemoryRepository.save(comment1);
        commentMemoryRepository.save(comment2);

        // then
        List<Comment> allByPostId = commentMemoryRepository.findAllByPostId(post.getId());
        Assertions.assertThat(allByPostId.size()).isEqualTo(2);
        commentMemoryRepository.deleteById(comment1.getId());
        allByPostId = commentMemoryRepository.findAllByPostId(post.getId());
        Assertions.assertThat(allByPostId.size()).isEqualTo(1);
    }
}