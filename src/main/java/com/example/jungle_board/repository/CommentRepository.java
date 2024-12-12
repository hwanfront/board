package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findAllByPostId(Long postId);
    void deleteById(Long id);
}
