package com.sendquiz.member.repository;

import com.sendquiz.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member getById(Long memberId);

    Optional<Member> findByEmail(String email);

    Member getByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    void save(Member member);

    void saveAll(List<Member> memberList);
}
