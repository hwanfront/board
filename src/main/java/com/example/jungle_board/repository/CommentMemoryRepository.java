package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Comment;
import com.example.jungle_board.util.entity.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentMemoryRepository implements CommentRepository {
    Map<Long, Comment> comments = new HashMap<>();
    private static long nextId = 0L;


    @Override
    public Comment save(Comment comment) {
        EntityUtils.setId(comment, ++nextId, Comment.class);
        comments.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return comments.values().stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        comments.remove(id);
    }

    public void clearComments() {
        comments.clear();
    }
}
