package com.sendquiz.member.application;

import com.sendquiz.member.domain.Member;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signup(MemberSignup memberSignup) {
        validateDuplicate(memberSignup);
        Member member = memberSignup.toEntity();
        memberRepository.signup(member);
    }

    protected void validateDuplicate(MemberSignup memberSignup) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberSignup.getEmail());
        if (optionalMember.isPresent()) {
            throw new MemberDuplicationException();
        }
    }
}
