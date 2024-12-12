package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>, CommentRepository {
    @Override
    List<Comment> findAllByPostId(Long postId);
}
