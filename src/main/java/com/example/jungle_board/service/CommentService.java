package com.example.jungle_board.service;

import com.example.jungle_board.dto.CommentCreateRequestDto;
import com.example.jungle_board.dto.CommentResponseDto;
import com.example.jungle_board.entity.Comment;
import com.example.jungle_board.entity.Member;
import com.example.jungle_board.entity.Post;
import com.example.jungle_board.repository.CommentRepository;
import com.example.jungle_board.repository.MemberRepository;
import com.example.jungle_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream()
                .map(this::createCommentResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public void createComment(CommentCreateRequestDto commentCreateRequestDto, String email, Long postId) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new IllegalStateException("member not found");
        }

        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalStateException("post not found");
        }

        Comment comment = Comment.builder()
                .member(member.get())
                .post(post.get())
                .content(commentCreateRequestDto.getContent())
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void removeComment(String email, Long commentId) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new IllegalStateException("member not found");
        }
        commentRepository.deleteById(commentId);
    }

    private CommentResponseDto createCommentResponseDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setUsername(comment.getMember().getUsername());
        return commentResponseDto;
    }
}
