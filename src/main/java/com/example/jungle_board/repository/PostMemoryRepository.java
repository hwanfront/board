package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Post;
import com.example.jungle_board.util.entity.EntityUtils;

import java.util.*;

public class PostMemoryRepository implements PostRepository {
    Map<Long, Post> posts = new HashMap<>();
    private static long nextId = 0L;

    @Override
    public Post save(Post post) {
        EntityUtils.setId(post, ++nextId, Post.class);
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return posts.values().stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        posts.remove(id);
    }


    public void clearPosts() {
        posts.clear();
    }
}
