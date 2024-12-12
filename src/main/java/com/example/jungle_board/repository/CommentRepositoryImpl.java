package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Comment;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class CommentRepositoryImpl implements CommentRepository {
    private final EntityManager em;

    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return em.createQuery("select m from Comment m where m.post.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        em.remove(em.find(Comment.class, id));
    }
}
