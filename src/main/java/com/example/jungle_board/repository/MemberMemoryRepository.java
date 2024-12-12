package com.example.jungle_board.repository;

import com.example.jungle_board.entity.Member;
import com.example.jungle_board.util.entity.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberMemoryRepository implements MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();
    private static long nextId = 0L;

    @Override
    public Member save(Member member) {
        EntityUtils.setId(member, ++nextId, Member.class);
        members.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return members.values().stream().filter(member -> member.getEmail().equals(email)).findFirst();
    }

    public void clearMembers() {
        members.clear();
    }
}
