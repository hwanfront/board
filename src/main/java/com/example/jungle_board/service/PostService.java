package com.example.jungle_board.service;

import com.example.jungle_board.dto.PostCreateResponseDto;
import com.example.jungle_board.dto.PostDetailResponseDto;
import com.example.jungle_board.dto.PostResponseDto;
import com.example.jungle_board.entity.Member;
import com.example.jungle_board.entity.Post;
import com.example.jungle_board.repository.MemberRepository;
import com.example.jungle_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostDetailResponseDto getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) {
            throw new IllegalStateException("post not found");
        }
        PostDetailResponseDto postDetailResponseDto = new PostDetailResponseDto();
        postDetailResponseDto.setContent(post.get().getContent());
        postDetailResponseDto.setTitle(post.get().getTitle());
        postDetailResponseDto.setCreatedAt(post.get().getCreatedAt());
        postDetailResponseDto.setUpdatedAt(post.get().getUpdatedAt());
        return postDetailResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAll().stream().map(this::createPostResponseDto).toList();
    }

    @Transactional
    public void createPost(PostCreateResponseDto requestDto, String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()) {
            throw new IllegalStateException("member not found");
        }
        Post post = Post.builder()
                .member(member.get())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) {
            throw new IllegalStateException("post not found");
        }
        String postMemberEmail = post.get().getMember().getEmail();
        if(postMemberEmail.equals(email)) {
            postRepository.deleteById(postId);
        }
    }

    private PostResponseDto createPostResponseDto(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setUsername(post.getMember().getEmail());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        postResponseDto.setUpdatedAt(post.getUpdatedAt());
        return postResponseDto;
    }
}
