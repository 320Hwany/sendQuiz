package com.sendquiz.member.application;

import com.sendquiz.certification.application.CertificationService;
import com.sendquiz.certification.exception.CertificationNotMatchException;
import com.sendquiz.member.domain.Member;
import com.sendquiz.member.exception.MemberDuplicationException;
import com.sendquiz.member.dto.request.MemberSignup;
import com.sendquiz.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQuery {

    private final MemberRepository memberRepository;
    private final CertificationService certificationService;

    public void validateDuplicate(MemberSignup memberSignup) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberSignup.getEmail());
        Optional<Member> byNickname = memberRepository.findByNickname(memberSignup.getNickname());
        if (byEmail.isPresent() || byNickname.isPresent()) {
            throw new MemberDuplicationException();
        }
    }

    public void validateCertificationNum(MemberSignup memberSignup) {
        String certificationNumFromCache = certificationService.getCertificationNumFromCache(memberSignup.getEmail());
        String inputCertificationNum = memberSignup.getCertificationNum();
        if (!certificationNumFromCache.equals(inputCertificationNum)) {
            throw new CertificationNotMatchException();
        }
    }
}
