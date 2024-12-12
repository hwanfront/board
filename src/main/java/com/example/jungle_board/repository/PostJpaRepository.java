package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostRepository {
}
