package com.sendquiz.member.repository;

import com.sendquiz.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByEmail(String email);

    void signup(Member member);
}
